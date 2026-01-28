import React from 'react';
import { BookOpen, CheckCircle } from 'lucide-react';
import './LearningPath.css';

const modules = [
    { id: 1, title: 'Week 1: IT Fundamentals & Introductions', status: 'completed' },
    { id: 2, title: 'Week 2: Agile Methodologies & Meetings', status: 'completed' },
    { id: 3, title: 'Week 3: Requirement Gathering', status: 'completed' },
    { id: 4, title: 'Week 4: Technical Architecture & System Design', status: 'current' },
    { id: 5, title: 'Week 5: Code Reviews & QA', status: 'locked' },
    { id: 6, title: 'Week 6: Deployment & DevOps', status: 'locked' },
    { id: 7, title: 'Week 7: Incident Management', status: 'locked' },
    { id: 8, title: 'Week 8: Client Communication & Presentation', status: 'locked' },
];

const LearningPath = () => {
    return (
        <div className="learning-container">
            <div className="learning-header">
                <h2>Your Learning Path</h2>
                <p>Master English for the Modern IT Workplace</p>
            </div>

            <div className="modules-list">
                {modules.map((module, index) => (
                    <div key={module.id} className={`module-card ${module.status}`}>
                        <div className="module-icon">
                            {module.status === 'completed' ? <CheckCircle size={24} /> : <BookOpen size={24} />}
                        </div>
                        <div className="module-info">
                            <span className="module-week">Module {index + 1}</span>
                            <h3 className="module-title">{module.title}</h3>
                            <span className="module-status-text">
                                {module.status === 'completed' ? 'Completed' :
                                    module.status === 'current' ? 'In Progress' : 'Locked'}
                            </span>
                        </div>
                        {module.status === 'current' && (
                            <button className="start-btn">Continue</button>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LearningPath;
