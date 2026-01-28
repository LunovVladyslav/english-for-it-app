import React from 'react';
import { Mic, Play } from 'lucide-react';
import Button from '../components/ui/Button';

const scenarios = [
    { id: 1, title: 'Daily Standup Update', difficulty: 'Easy', time: '5 min' },
    { id: 2, title: 'Handling a Production Incident', difficulty: 'Hard', time: '15 min' },
    { id: 3, title: 'Negotiating Requirements', difficulty: 'Medium', time: '10 min' },
];

const Practice = () => {
    return (
        <div style={{ maxWidth: '800px', margin: '0 auto' }}>
            <div style={{ marginBottom: '2rem' }}>
                <h2>Roleplay Practice</h2>
                <p style={{ color: 'var(--text-secondary)' }}>Simulate real-world IT scenarios to improve your speaking confidence.</p>
            </div>

            <div style={{ display: 'grid', gap: '1rem' }}>
                {scenarios.map(scenario => (
                    <div key={scenario.id} style={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        backgroundColor: 'var(--bg-card)',
                        padding: '1.5rem',
                        borderRadius: 'var(--radius-lg)',
                        border: '1px solid var(--bg-input)'
                    }}>
                        <div>
                            <h3 style={{ fontSize: '1.1rem', marginBottom: '0.25rem' }}>{scenario.title}</h3>
                            <div style={{ display: 'flex', gap: '1rem', fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                                <span>{scenario.difficulty}</span>
                                <span>•</span>
                                <span>{scenario.time}</span>
                            </div>
                        </div>

                        <Button variant="secondary" style={{ width: 'auto' }}>
                            <Play size={18} style={{ marginRight: '0.5rem' }} /> Start
                        </Button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Practice;
