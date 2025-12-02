-- MEMBERS
INSERT INTO Member (first_name, last_name, email, date_of_birth, gender) VALUES
('Alice', 'Smith', 'alice.smith@example.com', '1995-02-15', 'F'),
('Bob', 'Johnson', 'bob.johnson@example.com', '1990-07-20', 'M'),
('Carol', 'Davis', 'carol.davis@example.com', '1988-11-05', 'F');

-- TRAINERS
INSERT INTO Trainer (first_name, last_name, email) VALUES
('Tom', 'Harris', 'tom.harris@example.com'),
('Laura', 'Green', 'laura.green@example.com');

-- ADMINS
INSERT INTO Admin (first_name, last_name, email) VALUES
('Mark', 'White', 'mark.white@example.com');

-- ROOMS
INSERT INTO Room (room_name, capacity) VALUES
('Studio A', 20),
('Studio B', 15);

-- GROUP CLASSES
INSERT INTO GroupClass (class_name, trainer_id, room_id, scheduled_time, capacity) VALUES
('Yoga Basics', 1, 1, '2025-12-01 09:00:00', 20),
('HIIT Advanced', 2, 2, '2025-12-01 11:00:00', 15);

-- CLASS REGISTRATION
INSERT INTO ClassRegistration (member_id, class_id) VALUES
(1, 1),
(2, 1),
(3, 2);

-- HEALTH METRICS
INSERT INTO HealthMetric (member_id, current_weight, current_body_fat, current_heart_rate) VALUES
(1, 65.5, 22.0, 72),
(2, 78.2, 18.5, 68),
(3, 55.0, 25.0, 75);

-- FITNESS GOALS
INSERT INTO FitnessGoal (member_id, weight_target, fat_target) VALUES
(1, 60.0, 20.0),
(2, 75.0, 16.0),
(3, 50.0, 22.0);

-- INVOICES
INSERT INTO Invoice (member_id, amount, status) VALUES
(1, 100, 'PAID'),
(2, 150, 'UNPAID'),
(3, 120, 'UNPAID');