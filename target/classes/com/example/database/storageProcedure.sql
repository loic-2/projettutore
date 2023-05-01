USE projetTutore;
--Procedure permettant l'ajout d'un medecin
DROP PROCEDURE IF EXISTS addMedecin;
CREATE PROCEDURE addMedecin(IN pnom VARCHAR(50), IN pmatricule VARCHAR(50), IN pspecialite VARCHAR(50),
IN padresse VARCHAR(50), IN ptelephone VARCHAR(50), IN pnumero VARCHAR(50),IN isspecialiste BOOLEAN)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET @matricule_exist = (SELECT COUNT(*) FROM Medecin WHERE matricule=pmatricule);
        SET @numero_exist = (SELECT COUNT(*) FROM Personne WHERE numero_assurance=pnumero);
        IF @matricule_exist > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Le matricule est déjà utilisé.';
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom AND numero_assurance=pnumero) > 0 THEN
            START TRANSACTION;
                SET @personne_id= (SELECT id_personne FROM `Personne` WHERE nom=pnom AND numero_assurance=pnumero);
                INSERT INTO Medecin(matricule, id_personne) VALUES (pmatricule, @personne_id);
                SET @medecin_id = LAST_INSERT_ID();
                IF isspecialiste = TRUE THEN
                    INSERT INTO Specialiste(id_medecin, domaine) VALUES (@medecin_id, pspecialite);
                ELSE
                    INSERT INTO `Generaliste`(id_medecin) VALUES (@medecin_id);
                END IF;
            COMMIT;
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom) > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ce nom est deja utilise';
        ELSEIF @numero_exist > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Le numéro d\'assurance est déjà utilisé.';
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Une erreur c\'est produite lors l\'insertion';
        END IF;  
    END;
    START TRANSACTION;
        INSERT INTO Personne(nom, adresse, telephone, numero_assurance) VALUES (pnom, padresse,
        ptelephone, pnumero);
        SET @personne_id = LAST_INSERT_ID();
        INSERT INTO Medecin(matricule, id_personne) VALUES (pmatricule, @personne_id);
        SET @medecin_id = LAST_INSERT_ID();
        IF isspecialiste = TRUE THEN
            INSERT INTO Specialiste(id_medecin, domaine) VALUES (@medecin_id, pspecialite);
        ELSE
            INSERT INTO `Generaliste`(id_medecin) VALUES (@medecin_id);
        END IF;
    COMMIT;
END;

--Procedure permettant l'ajout d'un patient
DROP PROCEDURE IF EXISTS addPatient;
CREATE PROCEDURE addPatient(IN pnom VARCHAR(50),IN ptelephone VARCHAR(50),IN padresse VARCHAR(50),
IN pnumero VARCHAR(50),IN pgroupe VARCHAR(2),IN prhesus VARCHAR(10),IN ptaille DOUBLE,IN ppoids DOUBLE,
pantecedent VARCHAR(255))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET @numero_exist= (SELECT COUNT(*) FROM `Personne` AS pers INNER JOIN Patient AS pat ON pers.id_personne=pat.id_personne WHERE pers.numero_assurance=pnumero);
        SET @medecin_exist_with_number= (SELECT COUNT(*) FROM `Personne` AS pers 
            INNER JOIN `Medecin` AS med ON pers.id_personne=med.id_personne 
            WHERE pers.numero_assurance=pnumero);
        IF @medecin_exist_with_number = 0 THEN
                SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT="Le numero est utilise par un personnel.";
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom AND numero_assurance=pnumero) > 0 THEN
            START TRANSACTION;
                SET @get_id_personne=(SELECT id_personne FROM `Personne` 
                WHERE numero_assurance=pnumero);
                INSERT INTO `Patient`(id_personne,groupe_sanguin,groupe_rhesus,taille,poids,antecedent) VALUES
                (@get_id_personne,pgroupe,prhesus,ptaille,ppoids,pantecedent);
            COMMIT;
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom) > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Ce nom est deja utilise";
        ELSEIF @numero_exist > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le numero d'assurance est deja utilise par un patient.";
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Une erreur est survenue lors de l'insertion";
        END IF;
    END;
    START TRANSACTION;
        INSERT INTO `Personne`(nom,adresse,telephone,numero_assurance) VALUES (pnom,padresse,ptelephone,
        pnumero);
        SET @personne_id= LAST_INSERT_ID();
        INSERT INTO `Patient`(id_personne,groupe_sanguin,groupe_rhesus,taille,poids,antecedent) VALUES
        (@personne_id,pgroupe,prhesus,ptaille,ppoids,pantecedent);
    COMMIT;
END;

--Procedure pour la suppression d'un Patient
DROP PROCEDURE IF EXISTS deletePatient;
CREATE PROCEDURE deletePatient(IN pid_patient INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        IF (SELECT COUNT(*) FROM `patientInfo` WHERE id_patient=pid_patient) = 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Aucun patient ne correspond";
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Impossible de supprimer ce patient";
        END IF;
    END;
    START TRANSACTION;
        IF (SELECT COUNT(*) FROM `patientInfo` WHERE id_patient=pid_patient) = 0 THEN
            ROLLBACK;
            SIGNAL SQLSTATE '45000';
        ELSE
            SET @personne_id= (SELECT id_personne FROM `patientInfo` WHERE id_patient=pid_patient);
            DELETE FROM `Patient` WHERE id_patient=pid_patient;
            SET @is_generaliste= (SELECT COUNT(*) FROM `medecinGeneralisteInfo` WHERE id_personne=@personne_id);
            SET @is_specialiste= (SELECT COUNT(*) FROM `medecinSpecialisteInfo` WHERE id_personne=@personne_id);
            IF @is_specialiste = 0 AND @is_generaliste = 0 THEN
                DELETE FROM `Personne` WHERE id_personne=@personne_id;
            END IF;
        END IF;
    COMMIT;
END;

--Procedure pour supprimer un medecin
DROP PROCEDURE IF EXISTS deleteMedecin;
CREATE PROCEDURE deleteMedecin(IN pid_medecin INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        IF (SELECT COUNT(*) FROM `Medecin` WHERE id_medecin=pid_medecin) = 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Aucun medecin correspondant";
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Impossible de supprimer ce medecin";
        END IF;
    END;
    START TRANSACTION;
        IF (SELECT COUNT(*) FROM `Medecin` WHERE id_medecin=pid_medecin) = 0 THEN
            SIGNAL SQLSTATE '45000';
        ELSE
            IF (SELECT COUNT(*) FROM `medecinGeneralisteInfo` WHERE id_medecin=pid_medecin) > 0 THEN
                DELETE FROM `Generaliste` WHERE id_medecin=pid_medecin;
            ELSEIF (SELECT COUNT(*) FROM `medecinSpecialisteInfo` WHERE id_medecin=pid_medecin) > 0 THEN
                DELETE FROM `Specialiste` WHERE id_medecin=pid_medecin;
            END IF;
            SET @personne_id=(SELECT id_personne FROM `Medecin` WHERE id_medecin=pid_medecin);
            DELETE FROM `Medecin` WHERE id_medecin=pid_medecin;
            IF (SELECT COUNT(*) FROM `patientInfo` WHERE id_personne=@personne_id) = 0 THEN
                DELETE FROM `Personne` WHERE id_personne=@personne_id;
            END IF;
        END IF;
    COMMIT;
END;

--Procedure pour la modification d'un Medecin
DROP PROCEDURE IF EXISTS modifyMedecin;
CREATE PROCEDURE modifyMedecin(IN pnom VARCHAR(50),IN padresse VARCHAR(50),IN ptelephone VARCHAR(50),
    IN pnumero VARCHAR(50),IN pmatricule VARCHAR(50),IN pspecialite VARCHAR(50),IN pid_medecin INT,
    IN isspecialiste BOOLEAN)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        IF (SELECT COUNT(*) FROM Medecin WHERE id_medecin=pid_medecin) = 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Aucun medecin correspondant";
        ELSEIF (SELECT COUNT(*) FROM `Medecin` WHERE matricule=pmatricule) > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le matricule deja utilise";
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom) > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le nom deja utilise";
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE numero_assurance=pnumero)>0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le numero d'assurance est deja utilise";
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Impossible de modifier les informations de ce medecin";
        END IF;
    END;
    START TRANSACTION;
        IF (SELECT COUNT(*) FROM Medecin WHERE id_medecin=pid_medecin) = 0 THEN
            SIGNAL SQLSTATE '45000';
        ELSE
            SET @personne_id= (SELECT id_personne FROM `Medecin` WHERE id_medecin=pid_medecin);
            IF isspecialiste = FALSE THEN
                UPDATE `Medecin` SET matricule=pmatricule WHERE id_medecin=pid_medecin;
                UPDATE `Personne` SET nom=pnom,adresse=padresse,telephone=ptelephone,numero_assurance=pnumero
                WHERE id_personne = @personne_id;
            ELSE
                UPDATE `Specialiste` SET domaine=pspecialite WHERE id_medecin=pid_medecin;
                UPDATE `Medecin` SET matricule=pmatricule WHERE id_medecin=pid_medecin;
                UPDATE `Personne` SET nom=pnom,adresse=padresse,telephone=ptelephone,numero_assurance=pnumero 
                WHERE id_personne = @personne_id;
            END IF;
        END IF;
    COMMIT;
END;

--Procedure pour la modification d'un patient
DROP PROCEDURE IF EXISTS modifyPatient;
CREATE PROCEDURE modifyPatient(IN pnom VARCHAR(50),IN padresse VARCHAR(50),IN ptelephone VARCHAR(50),
    IN pnumero VARCHAR(50),IN pgroupe VARCHAR(2),IN prhesus VARCHAR(10),IN pantecedent VARCHAR(255),
    IN pid_patient INT,IN ptaille DOUBLE,IN ppoids DOUBLE)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        IF (SELECT COUNT(*) FROM `patientInfo` WHERE id_patient=pid_patient) = 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Aucun patient correspondant";
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE nom=pnom) > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le nom deja utilise";
        ELSEIF (SELECT COUNT(*) FROM `Personne` WHERE numero_assurance=pnumero) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le numero d'assurance est deja utilise";
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Impossible de modifier les informations de ce patient";
        END IF;
    END;
    START TRANSACTION;
        IF (SELECT COUNT(*) FROM `patientInfo` WHERE id_patient=pid_patient) = 0 THEN
            SIGNAL SQLSTATE '45000';
        ELSE
            SET @personne_id= (SELECT id_personne FROM `patientInfo` WHERE id_patient=pid_patient);
            UPDATE `Patient` SET groupe_sanguin=pgroupe,groupe_rhesus=prhesus,antecedent=pantecedent,
            poids=ppoids,taille=ptaille WHERE id_patient=pid_patient;
            UPDATE `Personne` SET nom=pnom,numero_assurance=pnumero,telephone=ptelephone,adresse=padresse
            WHERE id_personne=@personne_id;
        END IF;
    COMMIT;
END;