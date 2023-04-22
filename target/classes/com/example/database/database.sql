use projetTutore;
DROP TABLE IF EXISTS Personne;
CREATE TABLE Personne(
   id_personne INT AUTO_INCREMENT,
   numero_assurance VARCHAR(50),
   nom VARCHAR(50),
   telephone VARCHAR(50),
   adresse VARCHAR(50),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_personne),
   UNIQUE(numero_assurance)
);

DROP TABLE IF EXISTS Medecin;
CREATE TABLE Medecin(
   id_medecin INT AUTO_INCREMENT,
   matricule VARCHAR(50),
   id_personne INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_medecin),
   UNIQUE(id_personne),
   UNIQUE(matricule),
   FOREIGN KEY(id_personne) REFERENCES Personne(id_personne)
);

DROP TABLE IF EXISTS Specialiste;
CREATE TABLE Specialiste(
   id_medecin INT,
   domaine VARCHAR(50),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_medecin),
   FOREIGN KEY(id_medecin) REFERENCES Medecin(id_medecin)
);

DROP TABLE IF EXISTS Generaliste;
CREATE TABLE Generaliste(
   id_medecin INT,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_medecin),
   FOREIGN KEY(id_medecin) REFERENCES Medecin(id_medecin)
);

DROP TABLE IF EXISTS Paiement;
CREATE TABLE Paiement(
   id_paiement INT AUTO_INCREMENT,
   mode VARCHAR(100) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_paiement)
);

DROP TABLE IF EXISTS Medicament;
CREATE TABLE Medicament(
   id_medicament INT AUTO_INCREMENT,
   nom_Medicament VARCHAR(80),
   description VARCHAR(50),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_medicament)
);

DROP TABLE IF EXISTS Personnel;
CREATE TABLE Personnel(
   id_personnel INT AUTO_INCREMENT,
   matricule VARCHAR(50),
   id_personne INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_personnel),
   UNIQUE(id_personne),
   UNIQUE(matricule),
   FOREIGN KEY(id_personne) REFERENCES Personne(id_personne)
);

DROP TABLE IF EXISTS Utilisateur;
CREATE TABLE Utilisateur(
   id_utilisateur INT AUTO_INCREMENT,
   login VARCHAR(50),
   mot_de_passe VARCHAR(300),
   role VARCHAR(50),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(login)
);

DROP TABLE IF EXISTS Patient;
CREATE TABLE Patient(
   id_patient INT AUTO_INCREMENT,
   taille DOUBLE,
   poids DOUBLE,
   groupe_sanguin VARCHAR(2),
   groupe_rhesus VARCHAR(10),
   antecedent VARCHAR(255),
   id_personne INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_patient),
   UNIQUE(id_personne),
   FOREIGN KEY(id_personne) REFERENCES Personne(id_personne)
);

DROP TABLE IF EXISTS Consultation;
CREATE TABLE Consultation(
   id_consultation INT AUTO_INCREMENT,
   date_Heure DATETIME,
   staut VARCHAR(50),
   id_patient INT NOT NULL,
   id_medecin INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_consultation),
   FOREIGN KEY(id_patient) REFERENCES Patient(id_patient),
   FOREIGN KEY(id_medecin) REFERENCES Medecin(id_medecin)
);

DROP TABLE IF EXISTS Prescription_Consultation;
CREATE TABLE Prescription_Consultation(
   id_prescription INT AUTO_INCREMENT,
   id_consultation INT NOT NULL,
   id_medecin INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_prescription),
   UNIQUE(id_consultation),
   FOREIGN KEY(id_consultation) REFERENCES Consultation(id_consultation),
   FOREIGN KEY(id_medecin) REFERENCES Specialiste(id_medecin)
);

DROP TABLE IF EXISTS Historique;
CREATE TABLE Historique(
   id_historique INT AUTO_INCREMENT,
   action VARCHAR(255),
   date_action DATETIME,
   id_utilisateur INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_historique),
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);

DROP TABLE IF EXISTS Feuille_Maladie;
CREATE TABLE Feuille_Maladie(
   Id_feuille INT AUTO_INCREMENT,
   montant INT,
   rembourser boolean,
   id_consultation INT NOT NULL,
   id_paiement INT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(Id_feuille),
   UNIQUE(id_consultation),
   FOREIGN KEY(id_consultation) REFERENCES Consultation(id_consultation),
   FOREIGN KEY(id_paiement) REFERENCES Paiement(id_paiement)
);

DROP TABLE IF EXISTS  Ordonance;
CREATE TABLE Ordonance(
   id_consultation INT,
   id_medicament INT,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(id_consultation, id_medicament),
   FOREIGN KEY(id_consultation) REFERENCES Consultation(id_consultation),
   FOREIGN KEY(id_medicament) REFERENCES Medicament(id_medicament)
);