import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { LogOut, Menu } from 'lucide-react';

const Header = ({ onMenuClick }) => {
    const { user, logout } = useAuth();

    return (
        <header className="header">
            <div className="header-content">
                <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                    <button
                        className="menu-btn"
                        onClick={onMenuClick}
                        style={{ display: 'none' }} // Hidden by default, shown via CSS
                    >
                        <Menu size={24} />
                    </button>
                    <h3 className="page-title">Dashboard</h3>
                </div>

                <div className="user-menu">
                    <span className="user-name">{user?.name || user?.email}</span>
                    <button onClick={logout} className="logout-btn" title="Logout">
                        <LogOut size={20} />
                    </button>
                </div>
            </div>
        </header>
    );
};

export default Header;
