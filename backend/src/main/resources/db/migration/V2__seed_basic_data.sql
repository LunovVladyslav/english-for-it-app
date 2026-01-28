-- Seed Modules
INSERT INTO modules (id, module_number, title) VALUES
(gen_random_uuid(), 1, 'Soft Skills & Communication'),
(gen_random_uuid(), 2, 'Projects & Products'),
(gen_random_uuid(), 3, 'Meetings & Facilitation'),
(gen_random_uuid(), 4, 'Work Communication'),
(gen_random_uuid(), 5, 'IT Operations & Innovations'),
(gen_random_uuid(), 6, 'Technical Writing');

-- Seed Top-Level Skill Categories
INSERT INTO skill_nodes (id, name, category, description) VALUES
(gen_random_uuid(), 'General Communication', 'SOFT_SKILL', 'Core communication skills for IT'),
(gen_random_uuid(), 'Technical Vocabulary', 'VOCABULARY', 'Tech-specific terminology'),
(gen_random_uuid(), 'Grammar for IT', 'GRAMMAR', 'Grammar structures frequent in tech'),
(gen_random_uuid(), 'Presentation Skills', 'SOFT_SKILL', 'Demoing and presenting'),
(gen_random_uuid(), 'Written Communication', 'SOFT_SKILL', 'Emails, Tickets, Reports');

-- Note: We will let the ContentIngestionService populate Weeks/Days/Lessons dynamically
-- to keep the content source-of-truth in the Markdown files.
