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
        ELSEIF @numero_exist > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Le numéro d\'assurance est déjà utilisé.';
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
        IF @numero_exist > 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT="Le numero d'assurance est deja utilise par un patient.";
        ELSEIF @medecin_exist_with_number = 0 THEN
                SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT="Le numero est utilise par un personnel.";
        ELSE
            START TRANSACTION;
                SET @get_id_personne=(SELECT id_personne FROM `Personne` 
                WHERE numero_assurance=pnumero);
                INSERT INTO `Patient`(id_personne,groupe_sanguin,groupe_rhesus,taille,poids,antecedent) VALUES
                (@get_id_personne,pgroupe,prhesus,ptaille,ppoids,pantecedent);
            COMMIT;
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