import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import DocumentUpload from '../components/DocumentUpload';
import FeedbackForm from '../components/FeedbackForm';

const Dashboard = () => {
    const { user } = useAuth();
    const [stats, setStats] = useState({ xp: 0, level: 1, streak: 0 });
    const [showFeedback, setShowFeedback] = useState(false);

    const [modules, setModules] = useState([]);

    useEffect(() => {
        const fetchStats = async () => {
            try {
                // If in demo mode, use fake stats
                if (user?.email === 'demo@english4it.com') {
                    setStats({ xp: 1250, level: 5, streak: 3 });
                    return;
                }

                const response = await api.get('/auth/me');
                const userData = response.data;
                setStats({
                    xp: userData.xp || 0,
                    level: userData.level || 1,
                    streak: userData.currentStreak || 0
                });
            } catch (error) {
                console.error("Failed to fetch user stats", error);
            }
        };

        const fetchModules = async () => {
            try {
                const response = await api.get('/learning/modules');
                setModules(response.data);
            } catch (error) {
                console.error("Failed to fetch modules", error);
            }
        };

        if (user) {
            fetchStats();
            fetchModules();
        }
    }, [user]);

    const handleDownload = async (moduleId, title) => {
        try {
            const response = await api.get(`/learning/modules/${moduleId}/export`);
            const blob = new Blob([JSON.stringify(response.data, null, 2)], { type: 'application/json' });
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `${title.replace(/\s+/g, '_')}_offline.json`;
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Download failed", error);
            alert("Failed to download module.");
        }
    };

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
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>Level {stats.level}</p>
                </div>

                <div style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)' }}>
                    <h3 style={{ color: 'var(--color-accent)' }}>Day Streak</h3>
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>🔥 {stats.streak} Days</p>
                </div>

                <div style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)' }}>
                    <h3 style={{ color: 'var(--color-success)' }}>XP Points</h3>
                    <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>{stats.xp} XP</p>
                </div>
            </div>

            <div style={{ marginTop: '2rem' }}>
                <h2>Offline Learning</h2>
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1rem', marginTop: '1rem' }}>
                    {modules.map(module => (
                        <div key={module.id} style={{ padding: '1.5rem', backgroundColor: 'var(--bg-card)', borderRadius: 'var(--radius-lg)', border: '1px solid var(--border-color)' }}>
                            <h3>{module.title}</h3>
                            <button
                                onClick={() => handleDownload(module.id, module.title)}
                                style={{
                                    marginTop: '1rem',
                                    padding: '0.5rem 1rem',
                                    backgroundColor: 'var(--color-primary)',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: 'var(--radius-md)',
                                    cursor: 'pointer'
                                }}
                            >
                                Download for Offline ⬇️
                            </button>
                        </div>
                    ))}
                    {modules.length === 0 && <p>No modules available for download.</p>}
                </div>
            </div>

            <DocumentUpload />

            <div style={{ position: 'fixed', bottom: '30px', right: '30px' }}>
                <button
                    onClick={() => setShowFeedback(true)}
                    style={{
                        backgroundColor: 'var(--color-primary)',
                        color: 'white',
                        padding: '1rem',
                        borderRadius: '50%',
                        boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}
                    title="Send Feedback"
                >
                    <span style={{ fontSize: '1.5rem' }}>💬</span>
                </button>
            </div>

            <FeedbackForm isOpen={showFeedback} onClose={() => setShowFeedback(false)} />
        </div>
    );
};

export default Dashboard;
