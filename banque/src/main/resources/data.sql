-- Population des tables

-- Table Worker
INSERT INTO Worker (firstname, lastname, job) VALUES ('Roger', 'Capital', 0);
INSERT INTO Worker (firstname, lastname, job) VALUES ('Gaston', 'Crédit', 1);
INSERT INTO Worker (firstname, lastname, job) VALUES ('Gisèle', 'Economie', 0);
INSERT INTO Worker (firstname, lastname, job) VALUES ('Maurice', 'Bilan', 0);

-- Table Client
INSERT INTO Client (firstname, lastname, email, address, job, salary3years)
VALUES
    ('Jean', 'Dupont', 'jean.dupont@example.com', '123 Rue de la République', 'Ingénieur', 60000),
    ('Marie', 'Martin', 'marie.martin@example.com', '456 Avenue des Fleurs', 'Avocat', 75000),
    ('Pierre', 'Durand', 'pierre.durand@example.com', '789 Boulevard des Étoiles', 'Médecin', 80000),
    ('Sophie', 'Lefebvre', 'sophie.lefebvre@example.com', '1010 Rue du Paradis', 'Professeur', 55000),
    ('Luc', 'Dubois', 'luc.dubois@example.com', '111 Rue de la Paix', 'Artiste', 40000);

-- Table LoanApplication
INSERT INTO loan_application (created_at, updated_at, amount, duration, rate, status, reviewed_by, validate_by, client_id)
VALUES
    ('2024-03-01', '2024-03-01 10:30:00', 50000, 36, 3.18, 0, NULL, NULL, 1),
    ('2024-03-02', '2024-03-10 11:45:00', 70000, 48, 3.6, 3, 1, 2, 2),
    ('2024-03-03', '2024-03-07 09:15:00', 60000, 60, 2.95, 1, NULL, NULL, 3),
    ('2024-03-08', '2024-03-08 13:20:00', 45000, 24, 2.98, 0, NULL, NULL, 4),
    ('2024-03-05', '2024-03-09 15:00:00', 80000, 72, 2.6, 4, 4, NULL, 5);

-- Table Credit
INSERT INTO Credit (due, acceptation_date, loan_application_id) VALUES (1510.83, '2024-03-10', 2);

-- Table Event
INSERT INTO Event (loan_id, date, status) VALUES
    (1, '2024-03-01 10:30:00', 0),
    (2, '2024-03-02 09:12:36', 0),
    (2, '2024-03-04 11:32:54', 1),
    (2, '2024-03-08 15:56:08', 2),
    (2, '2024-03-10 11:45:00', 3),
    (3, '2024-03-03 14:25:00', 0),
    (3, '2024-03-07 09:15:00', 1),
    (4, '2024-03-08 13:20:00', 0),
    (5, '2024-03-05 15:00:00', 0),
    (5, '2024-03-06 17:22:04', 1),
    (5, '2024-03-09 15:00:00', 4);




