import React from 'react';
import { useAuth } from '../context/AuthContext';

const Dashboard = () => {
    const { user } = useAuth();

    return (
        <div>
            <h1>Welcome back, {user?.name || user?.email?.split('@')[0]}!</h1>
            <p style={{ color: 'var(--text-secondary)', marginTop: '0.5rem' }}>
                Ready to continue your English learning journey?
            </p>

            <div style={{
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
                gap: '1.5rem',
                marginTop: '2rem'
            }}>
                <div style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)' }}>
                    <h3 style={{ color: 'var(--color-primary)' }}>Current Level</h3>
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>B1 Intermediate</p>
                </div>

                <div style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)' }}>
                    <h3 style={{ color: 'var(--color-accent)' }}>Module Progress</h3>
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>Week 4 / 30</p>
                </div>

                <div style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)' }}>
                    <h3 style={{ color: 'var(--color-success)' }}>XP Points</h3>
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>1,250 XP</p>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
