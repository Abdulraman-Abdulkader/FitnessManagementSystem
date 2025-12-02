-- MEMBER
CREATE TABLE Member (
    member_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_of_birth DATE,
    gender CHAR(1)
);

-- TRAINER
CREATE TABLE Trainer (
    trainer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- ADMIN
CREATE TABLE Admin (
    admin_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- ROOM 
CREATE TABLE Room (
    room_id SERIAL PRIMARY KEY,
    room_name VARCHAR(50) NOT NULL UNIQUE,
    capacity INT NOT NULL
);

-- GROUP CLASS
CREATE TABLE GroupClass (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    trainer_id INT REFERENCES Trainer(trainer_id) ON DELETE SET NULL,
    room_id INT REFERENCES Room(room_id) ON DELETE SET NULL,
    scheduled_time TIMESTAMP NOT NULL,
    capacity INT NOT NULL
);

-- CLASS REGISTRATION
CREATE TABLE ClassRegistration (
    member_id INT REFERENCES Member(member_id) ON DELETE CASCADE,
    class_id INT REFERENCES GroupClass(class_id) ON DELETE CASCADE,
    PRIMARY KEY(member_id, class_id)
);

-- HEALTH METRICS
CREATE TABLE HealthMetric (
    metric_id SERIAL PRIMARY KEY,
    member_id INT REFERENCES Member(member_id) ON DELETE CASCADE,
    current_weight DECIMAL(5,2),
    current_body_fat DECIMAL(4,2),
    current_heart_rate INT
);

-- FITNESS GOALS
CREATE TABLE FitnessGoal (
    goal_id SERIAL PRIMARY KEY,
    member_id INT REFERENCES Member(member_id) ON DELETE CASCADE,
    weight_target DECIMAL(5,2),
    fat_target DECIMAL(4,2)
);

-- INVOICE 
CREATE TABLE Invoice (
    invoice_id SERIAL PRIMARY KEY,
    member_id INT REFERENCES Member(member_id) ON DELETE CASCADE,
    amount INT NOT NULL,
    status VARCHAR(20) DEFAULT 'UNPAID'
);

-- INDEX
CREATE INDEX idx_member_email ON Member(email);

-- VIEW 
CREATE VIEW MemberFullInfo AS
SELECT 
    m.member_id,
    m.first_name,
    m.last_name,
    fg.weight_target,
    fg.fat_target,
    hm.current_weight,
    hm.current_body_fat,
    hm.current_heart_rate
FROM Member m
LEFT JOIN FitnessGoal fg ON m.member_id = fg.member_id
LEFT JOIN HealthMetric hm ON m.member_id = hm.member_id;

-- Trigger Function
CREATE OR REPLACE FUNCTION check_class_capacity()
RETURNS TRIGGER AS $$
DECLARE
    roomCap INT;
BEGIN
    SELECT capacity INTO roomCap FROM Room WHERE room_id = NEW.room_id;

    IF roomCap IS NULL THEN
        RETURN NEW;
    END IF;

    IF NEW.capacity > roomCap THEN
        RAISE EXCEPTION 
        'Cannot create class: class capacity (%) exceeds room capacity (%)',
        NEW.capacity, roomCap;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger 
CREATE TRIGGER validate_class_capacity
BEFORE INSERT OR UPDATE ON GroupClass
FOR EACH ROW
EXECUTE FUNCTION check_class_capacity();
