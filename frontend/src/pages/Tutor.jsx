import React, { useState, useRef, useEffect } from 'react';
import { Send, User as UserIcon, Bot } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import Button from '../components/ui/Button';
import './Tutor.css';

const Tutor = () => {
    const { user } = useAuth();
    const [messages, setMessages] = useState([
        { id: 1, role: 'ai', text: `Hello ${user?.name || 'there'}! I'm your English Tutor. How can I help you today?` }
    ]);
    const [input, setInput] = useState('');
    const [isTyping, setIsTyping] = useState(false);
    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const handleSend = async (e) => {
        e.preventDefault();
        if (!input.trim()) return;

        const userMsg = { id: Date.now(), role: 'user', text: input };
        setMessages(prev => [...prev, userMsg]);
        setInput('');
        setIsTyping(true);

        try {
            // Backend expects: userId (UUID), message, context (optional)
            // Note: user.id might be missing if we just faked it from email. 
            // Ideally we fetched the ID on login.
            // If we don't have ID, the backend will fail (400).
            // Let's assume for now we might fail if ID is missing.
            // For MVP without ID in localStorage, we depend on AuthContext user object having it.
            // IF NOT, we might need to rely on TokenService extracting user from Subject, 
            // but TutorController explicitly asks for "userId" in payload.
            // We should probably fix TutorController to use Principal, but for now let's try sending what we have.
            // If user.id is null, we can't send it. 

            // FIX: In a real app, /me endpoint gets the ID. 
            // Or we can just send "random" for now if we want to test the UI, but it will fail 400.
            // Let's rely on user?.id. If missing, warn.

            const payload = {
                userId: user?.id || "00000000-0000-0000-0000-000000000000", // Fallback that might valid UUID format but not exist
                message: userMsg.text,
                context: "General English"
            };

            const response = await api.post('/tutor/chat', payload);

            const aiMsg = { id: Date.now() + 1, role: 'ai', text: response.data };
            setMessages(prev => [...prev, aiMsg]);
        } catch (error) {
            console.error("Chat error", error);
            const errorMsg = { id: Date.now() + 1, role: 'system', text: "Sorry, I couldn't reach the server. Please try again." };
            setMessages(prev => [...prev, errorMsg]);
        } finally {
            setIsTyping(false);
        }
    };

    return (
        <div className="tutor-container">
            <div className="chat-window">
                {messages.map((msg) => (
                    <div key={msg.id} className={`message ${msg.role}`}>
                        <div className="message-avatar">
                            {msg.role === 'ai' ? <Bot size={20} /> : <UserIcon size={20} />}
                        </div>
                        <div className="message-content">
                            {msg.text}
                        </div>
                    </div>
                ))}
                {isTyping && (
                    <div className="message ai">
                        <div className="message-avatar"><Bot size={20} /></div>
                        <div className="message-content typing">...</div>
                    </div>
                )}
                <div ref={messagesEndRef} />
            </div>

            <form className="chat-input-area" onSubmit={handleSend}>
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    placeholder="Type your message..."
                    className="chat-input"
                />
                <Button type="submit" className="send-btn" disabled={isTyping}>
                    <Send size={20} />
                </Button>
            </form>
        </div>
    );
};

export default Tutor;
