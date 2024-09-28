-- machinesTable
CREATE TABLE machinesTable (
machineID SERIAL PRIMARY KEY,
machineName VARCHAR(50),
maxweight FLOAT,
exercise VARCHAR(50));
ALTER SEQUENCE machinesTable_machineID_seq RESTART WITH 1000;

-- programTable
CREATE TABLE programTable (
programID SERIAL PRIMARY KEY,
programName VARCHAR(50) NOT NULL);

-- personsTable
CREATE TABLE personsTable (
personID VARCHAR(9) PRIMARY KEY,
firstName VARCHAR(50) NOT NULL,
lastName VARCHAR(50) NOT NULL,
dateOfBirth VARCHAR(10) NOT NULL,
gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
contactNumber VARCHAR(10));

-- trainersTable
CREATE TABLE trainersTable (
trainerID VARCHAR(9) PRIMARY KEY REFERENCES personsTable(personID),
salary INT);

-- membersTable
CREATE TABLE membersTable (
memberID VARCHAR(9) PRIMARY KEY REFERENCES personsTable(personID),
weight DECIMAL NOT NULL,  
height DECIMAL NOT NULL,  
programID INT REFERENCES programTable(programID),  
trainerID VARCHAR(9) REFERENCES trainersTable(trainerID),  
workoutSession VARCHAR(50),  
MemberShip VARCHAR(100));

-- AdminTable
CREATE TABLE AdminTable (
adminID VARCHAR(10) PRIMARY KEY,
adminPass VARCHAR(50));

-- Booking
CREATE TABLE bookingTable (
bookingID SERIAL PRIMARY KEY,
memberID VARCHAR(9) REFERENCES membersTable(memberID),
machineID INT REFERENCES machinesTable(machineID),
workoutSession VARCHAR(50)); 

-- INSERT to tables

--Program
INSERT INTO programTable (programName)
VALUES 
    ('Yoga Beginners'),
    ('Advanced Weightlifting'),
    ('Cardio Blast'),
    ('Pilates for Strength'),
    ('CrossFit Intro');

--Persons
INSERT INTO personsTable (personID, firstName, lastName, dateOfBirth, gender, contactNumber) VALUES
    ('314649211', 'Tomer', 'Levy', '1998-04-10', 'M', '0528041673'),
    ('111111111', 'David', 'Dvir', '1990-05-12', 'M', '0541234567'),
    ('222222222', 'Ana', 'Aronov', '1985-08-20', 'F', '0542345678'),
    ('333333333', 'Eli', 'Mizrahi', '1975-03-15', 'M', '0543456789'),
    ('444444444', 'Lea', 'Yanai', '2000-12-01', 'F', '0544567890'),
    ('555555555', 'Tal', 'Morad', '1998-12-17', 'M', '0545678901'),
	('666666666', 'Linoy', 'Ashram', '1993-07-23', 'F', '0535674345'),
	('777777777', 'Dor', 'Harari', '1990-07-05', 'M', '0546783421'),
	('888888888', 'Maya', 'Key', '1999-04-23', 'F', '0523458299');

--Trainer
INSERT INTO trainersTable (trainerID, salary) VALUES
    ('111111111', 12000),
    ('222222222', 15000),
    ('333333333', 14000),
    ('444444444', 11000);

--Member
INSERT INTO membersTable (memberID, weight, height, programID, trainerID, workoutSession, MemberShip) VALUES
    ('314649211', 64.5, 167, 1, '111111111', 'Morning', 'One Year (1,800 NIS)'),
    ('555555555', 70.5, 175, 2, '111111111', 'Morning', 'Three Month (500 NIS)'),
    ('666666666', 85.3, 180, 3, '222222222', 'Evening', 'One Month (200 NIS)'),
    ('777777777', 62.0, 165, 4, '333333333', 'Morning', 'Six Month (1,000 NIS)'),
    ('888888888', 78.6, 172, 5, '444444444', 'Morning', 'Three Month (500 NIS)');

--Admin
INSERT INTO adminTable (adminID, adminPass)
VALUES 	('tomer','1234'),
		('adir','1234'),
		('omer','1234');    

--Machines
INSERT INTO machinesTable (machineName, maxweight, exercise) VALUES
    ('Leg Press', 300.0, 'Leg Exercise'),
    ('Treadmill', 120.0, 'Cardio Exercise'),
    ('Bench Press', 250.0, 'Chest Exercise'),
    ('Lat Pulldown', 200.0, 'Back Exercise'),
    ('Shoulder Press', 150.0, 'Shoulder Exercise'),
    ('Rowing Machine', 140.0, 'Cardio Exercise'),
    ('Squat Rack', 300.0, 'Leg Exercise'),
    ('Elliptical', 150.0, 'Cardio Exercise'),
    ('Dumbbell Set', 100.0, 'Strength Exercise'),
    ('Pull-Up Bar', 200.0, 'Upper Body Exercise');

--Booking
INSERT INTO bookingTable (memberID, machineID, workoutSession)
VALUES
    ('314649211', 1000, 'Morning'),
    ('555555555', 1001, 'Morning'),
    ('666666666', 1002, 'Evening'),
    ('555555555', 1003, 'Morning'),
    ('777777777', 1004, 'Morning'),
    ('666666666', 1005, 'Evening');

-- Select Table
SELECT*FROM programTable;
SELECT*FROM adminTable;
SELECT*FROM membersTable;
SELECT*FROM trainersTable;
SELECT*FROM personsTable;
SELECT*FROM machinesTable;
SELECT*FROM bookingTable;




