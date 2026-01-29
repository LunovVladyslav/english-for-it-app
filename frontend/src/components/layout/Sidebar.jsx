import React from 'react';
import { NavLink } from 'react-router-dom';
import { Home, MessageSquare, BookOpen, User as UserIcon, X } from 'lucide-react';
import './Layout.css';

const Sidebar = ({ isOpen, onClose }) => {
    return (
        <>
            {/* Overlay for mobile */}
            <div
                className={`sidebar-overlay ${isOpen ? 'open' : ''}`}
                onClick={onClose}
            />

            <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
                <div className="sidebar-logo">
                    <h2>English<span style={{ color: 'var(--color-primary)' }}>ForIT</span></h2>
                    <button className="close-sidebar-btn" onClick={onClose}>
                        <X size={24} />
                    </button>
                </div>

                <nav className="sidebar-nav">
                    <NavLink to="/dashboard" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
                        <Home size={20} />
                        <span>Dashboard</span>
                    </NavLink>
                    <NavLink to="/tutor" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
                        <MessageSquare size={20} />
                        <span>AI Tutor</span>
                    </NavLink>
                    <NavLink to="/learn" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
                        <BookOpen size={20} />
                        <span>Learning Path</span>
                    </NavLink>
                    <NavLink to="/practice" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
                        <UserIcon size={20} />
                        <span>Roleplay</span>
                    </NavLink>
                </nav>
            </aside>
        </>
    );
};

export default Sidebar;
