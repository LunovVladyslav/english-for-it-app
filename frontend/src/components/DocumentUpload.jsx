import React, { useState } from 'react';
import api from '../api/axios';

const DocumentUpload = () => {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState('');

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
        setMessage('');
    };

    const handleUpload = async () => {
        if (!file) {
            setMessage('Please select a file first.');
            return;
        }

        setUploading(true);
        const formData = new FormData();
        formData.append('file', file);

        try {
            await api.post('/tutor/documents', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            setMessage('Document uploaded successfully! The AI Tutor will now use this for context.');
            setFile(null);
        } catch (error) {
            console.error('Upload failed:', error);
            setMessage('Failed to upload document. Please try again.');
        } finally {
            setUploading(false);
        }
    };

    return (
        <div style={{
            padding: '1.5rem',
            backgroundColor: 'var(--bg-card)',
            borderRadius: 'var(--radius-lg)',
            marginTop: '2rem'
        }}>
            <h3 style={{ color: 'var(--color-primary)', marginBottom: '1rem' }}>Personalize Your Tutor</h3>
            <p style={{ color: 'var(--text-secondary)', marginBottom: '1rem' }}>
                Upload PDF documents (e.g., technical specs, company handbooks) to help the AI understand your specific context.
            </p>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                <input
                    type="file"
                    accept="application/pdf"
                    onChange={handleFileChange}
                    style={{
                        padding: '0.5rem',
                        border: '1px solid var(--border-color)',
                        borderRadius: 'var(--radius-sm)'
                    }}
                />

                <button
                    onClick={handleUpload}
                    disabled={!file || uploading}
                    className="btn btn-primary"
                    style={{ alignSelf: 'flex-start' }}
                >
                    {uploading ? 'Uploading...' : 'Upload Document'}
                </button>

                {message && (
                    <p style={{
                        color: message.includes('success') ? 'var(--color-success)' : 'var(--color-error)',
                        marginTop: '0.5rem'
                    }}>
                        {message}
                    </p>
                )}
            </div>
        </div>
    );
};

export default DocumentUpload;
