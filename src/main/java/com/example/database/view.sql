DROP VIEW IF EXISTS medecinGeneralisteInfo;
CREATE VIEW medecinGeneralisteInfo AS SELECT
     pers.*,med.matricule,gen.id_medecin 
     FROM Personne  AS pers 
     INNER JOIN Medecin AS med ON pers.id_personne=med.id_personne 
     INNER JOIN Generaliste AS gen ON gen.id_medecin=med.id_medecin;

DROP VIEW IF EXISTS medecinSpecialisteInfo;
CREATE VIEW medecinSpecialisteInfo AS SELECT
     pers.*,med.id_medecin,med.matricule,spec.domaine 
     FROM Personne  AS pers 
     INNER JOIN Medecin AS med ON pers.id_personne=med.id_personne
     INNER JOIN Specialiste AS spec ON spec.id_medecin=med.id_medecin;

DROP VIEW IF EXISTS patientInfo;
CREATE VIEW patientInfo AS SELECT pat.*,pers.nom,pers.adresse,pers.telephone,pers.numero_assurance
    FROM `Patient` AS pat INNER JOIN `Personne` AS pers
    ON pat.id_personne=pers.id_personne;
