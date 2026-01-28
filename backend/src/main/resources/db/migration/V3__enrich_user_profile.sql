-- Enroll Profile Columns
ALTER TABLE users ADD COLUMN job_role VARCHAR(255);
ALTER TABLE users ADD COLUMN native_language VARCHAR(100);
ALTER TABLE users ADD COLUMN primary_goal TEXT;
