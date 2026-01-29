import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import Header from './Header';
import './Layout.css';

const Layout = () => {
    const [isMobileNavOpen, setMobileNavOpen] = React.useState(false);

    return (
        <div className="app-layout">
            <Sidebar isOpen={isMobileNavOpen} onClose={() => setMobileNavOpen(false)} />
            <div className="main-content-wrapper">
                <Header onMenuClick={() => setMobileNavOpen(true)} />
                <main className="main-content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
};

export default Layout;
