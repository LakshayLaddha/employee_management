INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MANAGER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Admin user (password: 'admin123')
INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@example.com', '$2a$10$WC0BdLdfJ2QtazwZ/XIZ1e6BdLlFvMXb/8cNH7WiKKN.3uhwhtOMi');

-- Assign admin role
INSERT INTO user_roles (user_id, role_id) 
VALUES (1, 3);

-- Sample departments
INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, salary, job_title, department, address, is_active)
VALUES 
('John', 'Doe', 'john.doe@example.com', '1234567890', '2020-01-15', 75000.00, 'Senior Developer', 'Engineering', '123 Main St, City, Country', true),
('Jane', 'Smith', 'jane.smith@example.com', '9876543210', '2021-03-20', 82000.00, 'Product Manager', 'Product', '456 Oak St, City, Country', true),
('Mike', 'Johnson', 'mike.johnson@example.com', '5551234567', '2019-11-10', 68000.00, 'UX Designer', 'Design', '789 Pine St, City, Country', true),
('Sarah', 'Williams', 'sarah.williams@example.com', '7778889999', '2022-02-05', 62000.00, 'QA Engineer', 'Engineering', '101 Elm St, City, Country', true),
('Robert', 'Brown', 'robert.brown@example.com', '3334445555', '2021-08-12', 78000.00, 'DevOps Engineer', 'Infrastructure', '202 Cedar St, City, Country', true);

-- Set up manager relationships
UPDATE employees SET manager_id = 1 WHERE id = 4;
UPDATE employees SET manager_id = 2 WHERE id = 3;
UPDATE employees SET manager_id = 1 WHERE id = 5;