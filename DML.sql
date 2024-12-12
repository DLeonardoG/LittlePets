DROP database if exists SaveYourPet;
CREATE database SaveYourPet;
USE SaveYourPet;

CREATE TABLE IF NOT EXISTS TypeService (
    IDTypeService INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeService),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS TypeSupplies (
    IDTypeSupplies INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeSupplies),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS TypeEmployee (
    IDTypeEmployee INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeEmployee)
);

CREATE TABLE IF NOT EXISTS TypeVaccine (
    IDTypeVaccine INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeVaccine),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS TypeMedicine (
    IDTypeMedicine INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeMedicine),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS TypeProcedure (
    IDTypeProcedure INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeProcedure),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS TypeIdentifier (
    IDTypeIdentifier INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeIdentifier),
    UNIQUE KEY (name)
);

CREATE TABLE TypeSpecies (
    IDTypeSpecies INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeSpecies)
);

CREATE TABLE Contacts(
    IDContact INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    ID VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDContact)
);

CREATE TABLE Owners (
    IDOwner INT NOT NULL AUTO_INCREMENT,
    ID VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    adress VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    signature VARCHAR(255) NOT NULL,
    points INT NOT NULL, 
    subscription boolean not null,
    CUFE VARCHAR(255) NOT NULL,
    IDContact INT,
    FOREIGN KEY (IDContact) REFERENCES Contacts(IDContact),
    PRIMARY KEY (IDOwner)
);

CREATE TABLE Pets (
    IDPet INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    dateBirth date NOT NULL,
    sex VARCHAR(255) NOT NULL,
    weight double NOT NULL,
    conditions VARCHAR(255) NOT NULL,
    allergies VARCHAR(255) NOT NULL,
    isAvailable enum('ADOPTION','FOR_SALE','NONE'),
    urlPhoto VARCHAR(255) NOT NULL,
    IDTypeSpecies INT NOT NULL,
    IDOwner INT NOT NULL,
    PRIMARY KEY (IDPet),
    FOREIGN KEY (IDTypeSpecies) REFERENCES TypeSpecies(IDTypeSpecies),
    FOREIGN KEY (IDOwner) REFERENCES Owners(IDOwner)
);

CREATE TABLE FeaturesPets (
    IDFeaturePet INT NOT NULL AUTO_INCREMENT,
    feature VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDFeaturePet),
    unique key (feature)
);

CREATE TABLE IF NOT EXISTS PetsFeaturesPets (
    IDPetFeaturePet INT NOT NULL AUTO_INCREMENT,
    value VARCHAR(255) NOT NULL,
    IDPet INT not null,
    IDFeaturePet INT NOT NULL,
    PRIMARY KEY (IDPetFeaturePet),
    FOREIGN KEY (IDFeaturePet) REFERENCES FeaturesPets(IDFeaturePet),
    FOREIGN KEY (IDPet) REFERENCES Pets(IDPet)
);

CREATE TABLE IF NOT EXISTS Identifiers (
    IDIdentifier INT NOT NULL AUTO_INCREMENT,
    number VARCHAR(255) NOT NULL,
    IDPet INT not null,
    IDTypeIdentifier INT NOT NULL,
    PRIMARY KEY (IDIdentifier),
    FOREIGN KEY (IDTypeIdentifier) REFERENCES TypeIdentifier(IDTypeIdentifier),
    FOREIGN KEY (IDPet) REFERENCES Pets(IDPet)
);

CREATE TABLE Employees (
    IDEmployee INT NOT NULL AUTO_INCREMENT,
    ID VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    IDTypeEmployee INT NOT NULL,
    PRIMARY KEY (IDEmployee),
    FOREIGN KEY (IDTypeEmployee) REFERENCES TypeEmployee(IDTypeEmployee)
);

CREATE TABLE IF NOT EXISTS Services (
    IDService INT NOT NULL AUTO_INCREMENT,
    dateService datetime NOT NULL,
    IDEmployee INT not null,
    IDPet INT not null,
    status enum('SCHEDULED', 'IN_PROCESS','COMPLETED', 'CANCELLED'),
    paid boolean,
    IDTypeService INT NOT NULL,
    PRIMARY KEY (IDService),
    FOREIGN KEY (IDTypeService) REFERENCES TypeService(IDTypeService),
    FOREIGN KEY (IDEmployee) REFERENCES Employees(IDEmployee),
    FOREIGN KEY (IDPet) REFERENCES Pets(IDPet)
);

CREATE TABLE Daycare (
    IDDayCare INT NOT NULL AUTO_INCREMENT,
    startDate date NOT NULL,
    endDate date NOT NULL,
    diet VARCHAR(255) NOT NULL,
    medication VARCHAR(255) NOT NULL,
    price double,
    IDService INT NOT NULL,
    PRIMARY KEY (IDDayCare),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE TypeEvent (
    IDTypeEvent INT NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeEvent)
);

CREATE TABLE Events (
    IDEvent INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    startDate date NOT NULL,
    endDate date NOT NULL,
    place VARCHAR(255) NOT NULL,
    IDTypeEvent INT,
    FOREIGN KEY (IDTypeEvent) REFERENCES TypeEvent(IDTypeEvent),
    PRIMARY KEY (IDEvent)
);

CREATE TABLE ServicesEvents (
    IDServiceEvent INT NOT NULL AUTO_INCREMENT,
    IDService INT NOT NULL,
    IDEvent INT NOT NULL,
    PRIMARY KEY (IDServiceEvent),
    FOREIGN KEY (IDService) REFERENCES Services(IDService),
    FOREIGN KEY (IDEvent) REFERENCES Events(IDEvent)
);

CREATE TABLE IF NOT EXISTS TypeBreed (
    IDTypeBreed INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price double,
    PRIMARY KEY (IDTypeBreed)
);

CREATE TABLE IF NOT EXISTS TypeBehavior (
    IDTypeBehavior INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price double,
    PRIMARY KEY (IDTypeBehavior)
);

CREATE TABLE Training (
    IDTraining INT NOT NULL AUTO_INCREMENT,
    estimatedTime VARCHAR(255) NOT NULL,
    IDService INT NOT NULL,
    totalPrice double,
    IDTypeBehavior INT NOT NULL,
    IDTypeBreed INT NOT NULL,
    PRIMARY KEY (IDTraining),
    FOREIGN KEY (IDService) REFERENCES Services(IDService),
    FOREIGN KEY (IDTypeBehavior) REFERENCES TypeBehavior(IDTypeBehavior),
    FOREIGN KEY (IDTypeBreed) REFERENCES TypeBreed(IDTypeBreed)
);

CREATE TABLE IF NOT EXISTS TypeGrooming (
    IDTypeGrooming INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price double,
    PRIMARY KEY (IDTypeGrooming)
);

CREATE TABLE GroomingServices (
    IDGroomingServices INT NOT NULL AUTO_INCREMENT,
    IDTypeGrooming INT NOT NULL,
    IDService INT NOT NULL,
    comments VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDGroomingServices),
    FOREIGN KEY (IDTypeGrooming) REFERENCES TypeGrooming(IDTypeGrooming),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE VisitControl (
    IDVisitControl INT NOT NULL AUTO_INCREMENT,
    comments VARCHAR(255) NOT NULL,
    date date,
   status enum('SCHEDULED', 'IN_PROCESS','COMPLETED', 'CANCELLED'),
    remainingVisits INT,
    IDService INT NOT NULL,
    PRIMARY KEY (IDVisitControl),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE Procedures (
    IDProcedure INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
	price double,
    IDTypeProcedure INT NOT NULL,
    IDService INT NOT NULL,
    PRIMARY KEY (IDProcedure),
    FOREIGN KEY (IDTypeProcedure) REFERENCES TypeProcedure(IDTypeProcedure),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE PostProcedure (
    IDPostProcedure INT NOT NULL AUTO_INCREMENT,
    nextDate date,
    status enum('SCHEDULED', 'IN_PROCESS','COMPLETED', 'CANCELLED'),
    estimedControls INT,
    comments VARCHAR(255),
    IDProcedure INT NOT NULL,
    PRIMARY KEY (IDPostProcedure),
    FOREIGN KEY (IDProcedure) REFERENCES Procedures(IDProcedure)
);

CREATE TABLE PreProcedure (
    IDPreProcedure INT NOT NULL AUTO_INCREMENT,
    analysis VARCHAR(255),
    expectedTime VARCHAR(255),
    IDProcedure INT NOT NULL,
    PRIMARY KEY (IDPreProcedure),
    FOREIGN KEY (IDProcedure) REFERENCES Procedures(IDProcedure)
);

CREATE TABLE TypeReason (
    IDTypeReason INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDTypeReason),
    UNIQUE KEY (name)
);

CREATE TABLE Consultations (
    IDConsultation INT NOT NULL AUTO_INCREMENT,
    IDTypeReason INT NOT NULL,
    isControl boolean,
    price double,
    IDService INT NOT NULL,
    PRIMARY KEY (IDConsultation),
    FOREIGN KEY (IDTypeReason) REFERENCES TypeReason(IDTypeReason),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE Prescriptions (
    IDPrescription INT NOT NULL AUTO_INCREMENT,
    IDConsultation INT NOT NULL,
    recomendations varchar(255),
    services varchar(255),
	supplies varchar(255),
    diagnostic varchar(255),
    PRIMARY KEY (IDPrescription),
    FOREIGN KEY (IDConsultation) REFERENCES Consultations(IDConsultation)
);

CREATE TABLE ServicesConsultations (
    IDServiceConsultation INT NOT NULL AUTO_INCREMENT,
    IDService INT NOT NULL,
    IDConsultation INT NOT NULL,
    PRIMARY KEY (IDServiceConsultation),
    FOREIGN KEY (IDService) REFERENCES Services(IDService),
    FOREIGN KEY (IDConsultation) REFERENCES Consultations(IDConsultation)
);

CREATE TABLE ConsultationsProcedures (
    IDConsultationProcedure INT NOT NULL AUTO_INCREMENT,
    IDProcedure INT NOT NULL,
    IDConsultation INT NOT NULL,
    PRIMARY KEY (IDConsultationProcedure),
    FOREIGN KEY (IDProcedure) REFERENCES Procedures(IDProcedure),
    FOREIGN KEY (IDConsultation) REFERENCES Consultations(IDConsultation)
);


CREATE TABLE IF NOT EXISTS Supplies (
	IDSupplies INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	stock INT not null,
	price double not null,
	IDTypeSupplies INT NOT NULL,
	PRIMARY KEY (IDSupplies),
	FOREIGN KEY (IDTypeSupplies) REFERENCES TypeSupplies(IDTypeSupplies)		
);

CREATE TABLE ServicesSupplies (
    IDServiceSupplies INT NOT NULL AUTO_INCREMENT,
    IDService INT NOT NULL,
    IDSupplies INT NOT NULL,
    quantity int,
    PRIMARY KEY (IDServiceSupplies),
    FOREIGN KEY (IDService) REFERENCES Services(IDService),
    FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE Vaccines (
    IDVaccine INT NOT NULL AUTO_INCREMENT,
    IDTypeVaccine INT NOT NULL,
    IDSupplies INT NOT NULL,
    batch int,
    expirationDate date,
    PRIMARY KEY (IDVaccine),
    FOREIGN KEY (IDTypeVaccine) REFERENCES TypeVaccine(IDTypeVaccine),
    FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE Medicines (
	IDMedicine INT NOT NULL AUTO_INCREMENT,
	IDTypeMedicine INT NOT NULL,
	IDSupplies INT NOT NULL,
	expirationDate date,
	PRIMARY KEY (IDMedicine),
	FOREIGN KEY (IDTypeMedicine) REFERENCES TypeMedicine(IDTypeMedicine),
	FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE IF NOT EXISTS Manufacturers (
    IDManufacturer INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
	phoneNumber VARCHAR(255) NOT NULL,
    IDSupplies INT NOT NULL,
    PRIMARY KEY (IDManufacturer), 
    FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE SuppliesManufacturers (
    IDSuppliesManufacturer INT NOT NULL AUTO_INCREMENT,
    IDManufacturer INT NOT NULL,
    IDSupplies INT NOT NULL,
    cost double,
    PRIMARY KEY (IDSuppliesManufacturer),
    FOREIGN KEY (IDManufacturer) REFERENCES Manufacturers(IDManufacturer),
    FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE IF NOT EXISTS Purchases (
    IDPurchase INT NOT NULL AUTO_INCREMENT,
    datePurchase date,
    total double,
	IDManufacturer INT NOT NULL,
	FOREIGN KEY (IDManufacturer) REFERENCES Manufacturers(IDManufacturer),
    PRIMARY KEY (IDPurchase)
);

CREATE TABLE PurchasesSupplies (
    IDPurchaseSupplies INT NOT NULL AUTO_INCREMENT,
    quantity int,
    IDPurchase INT NOT NULL,
    IDSupplies INT NOT NULL,
    subTotal double,
    PRIMARY KEY (IDPurchaseSupplies),
    FOREIGN KEY (IDPurchase) REFERENCES Purchases(IDPurchase),
    FOREIGN KEY (IDSupplies) REFERENCES Supplies(IDSupplies)
);

CREATE TABLE Invoice (
    IDInvoice INT NOT NULL AUTO_INCREMENT,
    dateInvoice date,
    total double,
    IDOwner INT NOT NULL,
    IDTypeService INT NOT NULL,
    PRIMARY KEY (IDInvoice),
    FOREIGN KEY (IDOwner) REFERENCES Owners(IDOwner),
    FOREIGN KEY (IDTypeService) REFERENCES TypeService(IDTypeService)
);

CREATE TABLE InvoiceDetails (
    IDInvoiceDetails INT NOT NULL AUTO_INCREMENT,
    IDInvoice INT not null,
    subTotal double,
    IDService INT NOT NULL,
    PRIMARY KEY (IDInvoiceDetails),
    FOREIGN KEY (IDService) REFERENCES Services(IDService)
);

CREATE TABLE PaymentMethods (
    IDPaymentMethod INT NOT NULL AUTO_INCREMENT,
    name varchar(255) not null,
    PRIMARY KEY (IDPaymentMethod)
);

CREATE TABLE Transactions (
    IDTransaction INT NOT NULL AUTO_INCREMENT,
    status enum('SCHEDULED', 'IN_PROCESS','COMPLETED', 'CANCELLED'),
    dateTransaction date,
    IDPaymentMethod INT not null,
    IDInvoice INT not null,
    PRIMARY KEY (IDTransaction),
    FOREIGN KEY (IDPaymentMethod) REFERENCES PaymentMethods(IDPaymentMethod),
    FOREIGN KEY (IDInvoice) REFERENCES Invoice(IDInvoice)
);

SELECT * FROM Invoice;

/* SELECT COUNT(*) AS total_tablas
FROM information_schema.tables
WHERE table_schema = 'SaveYourPet';

show tables; */
