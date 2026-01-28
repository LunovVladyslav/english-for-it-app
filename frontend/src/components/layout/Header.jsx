import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { LogOut } from 'lucide-react';

const Header = () => {
    const { user, logout } = useAuth();

    return (
        <header className="header">
            <div className="header-content">
                <h3 className="page-title">Dashboard</h3> {/* Dynamic title later */}

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
