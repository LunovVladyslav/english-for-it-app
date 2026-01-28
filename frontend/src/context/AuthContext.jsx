import { createContext, useState, useEffect, useContext } from 'react';
import api from '../api/axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // On mount, check if token exists. 
        // Ideally we would validate it with an endpoint like /api/auth/me,
        // but for now, if token exists, we assume logged in.
        // The user object might be missing if we only stored token.
        // Let's decode token or just store user in localStorage too.
        const storedUser = localStorage.getItem('user');
        if (token && storedUser) {
            setUser(JSON.parse(storedUser));
        }
        setLoading(false);
    }, [token]);

    const login = async (email, password) => {
        try {
            const response = await api.post('/auth/login', { email, password });
            const newToken = response.data; // The endpoint returns the token string directly

            setToken(newToken);
            localStorage.setItem('token', newToken);

            // Since backend doesn't return User object on login, we might need to decode token
            // or make another call. For MVP, let's fake the user object or use email.
            // Or we can update backend to return JSON { token, user }.
            // CURRENT BACKEND: returns String (token).

            // Let's create a minimal user object from email
            const userObj = { email, name: email.split('@')[0] };
            setUser(userObj);
            localStorage.setItem('user', JSON.stringify(userObj));

            return { success: true };
        } catch (error) {
            console.error("Login failed", error);
            let errMsg = error.response?.data || "Login failed";
            // Check if error is HTML
            if (typeof errMsg === 'string' && errMsg.trim().startsWith('<')) {
                errMsg = "Service Unavailable: Backend not reachable.";
            }
            return { success: false, error: errMsg };
        }
    };

    const register = async (userData) => {
        try {
            const response = await api.post('/auth/register', userData);
            const newToken = response.data;

            setToken(newToken);
            localStorage.setItem('token', newToken);

            const userObj = { email: userData.email, name: userData.email.split('@')[0] };
            setUser(userObj);
            localStorage.setItem('user', JSON.stringify(userObj));

            return { success: true };
        } catch (error) {
            console.error("Registration failed", error);
            let errMsg = error.response?.data || "Registration failed";
            // Check if error is HTML (e.g., 404/500/405 from server/proxy)
            if (typeof errMsg === 'string' && errMsg.trim().startsWith('<')) {
                errMsg = "Service Unavailable: Backend not reachable or method not allowed.";
            }
            return { success: false, error: errMsg };
        }
    };

    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    };

    return (
        <AuthContext.Provider value={{ user, token, login, register, logout, loading }}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
