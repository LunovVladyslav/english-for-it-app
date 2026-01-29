import React, { useState } from 'react';
import api from '../api/axios';
import { MessageSquare, X, Send } from 'lucide-react';

const FeedbackForm = ({ isOpen, onClose }) => {
    const [message, setMessage] = useState('');
    const [type, setType] = useState('Feature Request');
    const [status, setStatus] = useState('idle'); // idle, sending, success, error

    if (!isOpen) return null;

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('sending');

        try {
            await api.post('/feedback', { message, type });
            setStatus('success');
            setTimeout(() => {
                onClose();
                setStatus('idle');
                setMessage('');
            }, 2000);
        } catch (error) {
            console.error('Feedback failed:', error);
            setStatus('error');
        }
    };

    return (
        <div style={{
            position: 'fixed',
            bottom: '20px',
            right: '20px',
            backgroundColor: 'var(--bg-card)',
            padding: '1.5rem',
            borderRadius: 'var(--radius-lg)',
            boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
            width: '320px',
            zIndex: 1000,
            border: '1px solid var(--border-color)'
        }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem', alignItems: 'center' }}>
                <h3 style={{ fontSize: '1.1rem', fontWeight: '600' }}>Send Feedback</h3>
                <button onClick={onClose} style={{ color: 'var(--text-secondary)' }}>
                    <X size={20} />
                </button>
            </div>

            {status === 'success' ? (
                <div style={{ color: 'var(--color-success)', textAlign: 'center', padding: '1rem' }}>
                    Thank you! We received your feedback.
                </div>
            ) : (
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: '1rem' }}>
                        <select
                            value={type}
                            onChange={(e) => setType(e.target.value)}
                            style={{
                                width: '100%',
                                padding: '0.5rem',
                                borderRadius: 'var(--radius-sm)',
                                border: '1px solid var(--border-color)',
                                backgroundColor: 'var(--bg-app)',
                                color: 'var(--text-primary)',
                                marginBottom: '0.5rem'
                            }}
                        >
                            <option>Feature Request</option>
                            <option>Bug Report</option>
                            <option>General Comment</option>
                        </select>
                        <textarea
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            placeholder="Tell us what you think..."
                            required
                            rows={4}
                            style={{
                                width: '100%',
                                padding: '0.5rem',
                                borderRadius: 'var(--radius-sm)',
                                border: '1px solid var(--border-color)',
                                backgroundColor: 'var(--bg-app)',
                                color: 'var(--text-primary)',
                                resize: 'none'
                            }}
                        />
                    </div>
                    {status === 'error' && <p style={{ color: 'var(--color-error)', fontSize: '0.9rem', marginBottom: '0.5rem' }}>Failed to send. Please try again.</p>}
                    <button
                        type="submit"
                        disabled={status === 'sending'}
                        className="btn btn-primary"
                        style={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.5rem' }}
                    >
                        {status === 'sending' ? 'Sending...' : <><Send size={16} /> Send</>}
                    </button>
                </form>
            )}
        </div>
    );
};

export default FeedbackForm;
