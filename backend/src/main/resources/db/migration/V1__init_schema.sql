-- Create Users Table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    current_level VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Skill Nodes Table
CREATE TABLE skill_nodes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT
);

-- Create Skill Dependencies Table (Self-referencing Many-to-Many)
CREATE TABLE skill_dependencies (
    child_id UUID NOT NULL,
    parent_id UUID NOT NULL,
    PRIMARY KEY (child_id, parent_id),
    FOREIGN KEY (child_id) REFERENCES skill_nodes(id),
    FOREIGN KEY (parent_id) REFERENCES skill_nodes(id)
);

-- Create User Skills Table
CREATE TABLE user_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    skill_node_id UUID NOT NULL,
    proficiency_level INT NOT NULL DEFAULT 0,
    is_unlocked BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (skill_node_id) REFERENCES skill_nodes(id)
);

-- Create Learning Sessions Table
CREATE TABLE learning_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    current_state VARCHAR(50) NOT NULL,
    active_skill_id UUID,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Content Structure: Modules, Weeks, Days, Lessons
CREATE TABLE modules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    module_number INT NOT NULL,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE weeks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    module_id UUID NOT NULL,
    week_number INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    FOREIGN KEY (module_id) REFERENCES modules(id)
);

CREATE TABLE days (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    week_id UUID NOT NULL,
    day_number INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    FOREIGN KEY (week_id) REFERENCES weeks(id)
);

CREATE TABLE lessons (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    day_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type VARCHAR(50) NOT NULL,
    FOREIGN KEY (day_id) REFERENCES days(id)
);
