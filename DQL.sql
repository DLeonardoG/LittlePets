USE SaveYourPet;

-- Inserting into TypeServices
INSERT INTO TypeService (name) VALUES
('Pharmacy'),
('Procedures'),
('Consultations'),
('Grooming'),
('Daycare'),
('Training'),
('Vaccination'),
('Deworming');

INSERT INTO Contacts (name, ID, phoneNumber, email) 
VALUES 
('Juan Pérez', 'J123', '555-1234', 'juan.perez@email.com');

INSERT INTO Contacts (name, ID, phoneNumber, email) 
VALUES 
('María López', 'M456', '555-5678', 'maria.lopez@email.com');

INSERT INTO Contacts (name, ID, phoneNumber, email) 
VALUES 
('Carlos García', 'C789', '555-9101', 'carlos.garcia@email.com');

-- Inserting into TypeSupplies
INSERT INTO TypeSupplies (name) VALUES 
('Medical Supply'),
('Medicine'),
('Vaccine'),
('Grooming Product');

-- Inserting into TypesEmployees
INSERT INTO TypeEmployee (name) VALUES
('Veterinarian'),
('Groomer'),
('Trainer'),
('Caretaker');

-- Inserting into TypeVaccine
INSERT INTO TypeVaccine (name) VALUES
('Rabies'),
('Distemper'),
('Parvovirus'),
('Leptospirosis'),
('Bordetella'),
('Lyme Disease'),
('Feline Leukemia'),
('Canine Influenza');

-- Inserting into TypeMedicine
INSERT INTO TypeMedicine (name) VALUES 
('Antibiotic'),
('Painkiller'),
('Anti-inflammatory'),
('Antiparasitic'),
('Sedative'),
('Hormonal Therapy');


-- Inserting into TypeProcedure
INSERT INTO TypeProcedure (name) VALUES 
('Surgery'),
('Adoption'),
('Sales'),
('Laboratory Tests'),
('Dental Procedure');

-- Inserting into TypeEvent
INSERT INTO TypeEvent(name) VALUES 
('Vaccination'),
('Adoption'),
('Sterilization');

-- Inserting into TypeIdentifiers
INSERT INTO TypeIdentifier (name) VALUES 
('Microchip'),
('Collar ID'),
('Tattoo');

-- Inserting into Species
INSERT INTO TypeSpecies (name) VALUES 
('Dog'),
('Cat'),
('Bird');

-- Inserting into Owners
INSERT INTO Owners (IDOwner, ID, name, adress, phoneNumber, email, signature, points, subscription, CUFE, IDContact) VALUES
(1, 'OWNER001', 'John Doe', '1234 Elm St', '555-1234', 'johndoe@example.com', 'John Doe Signature', 10, TRUE, 'CUFE1234',1),
(2, 'OWNER002', 'Jane Smith', '5678 Oak St', '555-5678', 'janesmith@example.com', 'Jane Smith Signature', 5, FALSE, 'CUFE5678',2);

-- Inserting into Pets
INSERT INTO Pets (IDPet, name, dateBirth, sex, weight, conditions, allergies, isAvailable, urlPhoto, IDTypeSpecies, IDOwner) VALUES
(1, 'Rex', '2020-05-15', 'Male', 25.5, 'Healthy', 'None', 'ADOPTION', 'http://example.com/rex.jpg', 1, 1),
(2, 'Whiskers', '2019-08-10', 'Female', 4.0, 'Healthy', 'Dust', 'FOR_SALE', 'http://example.com/whiskers.jpg', 2, 2);

-- Inserting into FeaturesPets
INSERT INTO FeaturesPets (feature) VALUES
('Breed'),         
('Color'),          
('Type'),           
('Habitat'),       
('Feeding'),       
('Size'),           
('Flight ability'); 

-- Inserting into PetsFeaturesPets
INSERT INTO PetsFeaturesPets (IDPetFeaturePet, value, IDPet, IDFeaturePet) VALUES 
(1, 'Yes', 1, 1),
(2, 'Yes', 2, 2);

-- Inserting into Identifiers
INSERT INTO Identifiers (IDIdentifier, number, IDPet, IDTypeIdentifier) VALUES
(1, 'MICRO123', 1, 1),
(2, 'COLLAR567', 2, 2);

-- Inserting into Employees
INSERT INTO Employees (IDEmployee, ID, name, phoneNumber, email, IDTypeEmployee) VALUES
(1, 'EMP001', 'Sarah Connor', '555-0001', 'sarah.connor@example.com', 1),
(2, 'EMP002', 'Kyle Reese', '555-0002', 'kyle.reese@example.com', 2);

-- Inserting into Services
INSERT INTO Services (IDService, dateService, IDEmployee, IDPet, IDTypeService,status,paid) VALUES
(1, '2024-12-01', 1, 1, 1,'SCHEDULED',1),
(2, '2024-12-02', 2, 2, 3,'CANCELLED',0);

-- Inserting into Daycare
INSERT INTO Daycare (IDDayCare, startDate, endDate, diet, medication, price, IDService) VALUES
(1, '2024-12-01', '2024-12-03', 'Premium Dog Food', 'None', 150.00, 1);

-- Inserting into Events
INSERT INTO Events (IDEvent, name, startDate, endDate, place, IDTypeEvent) VALUES
(1, 'Pet Health Expo', '2024-12-15', '2024-12-16', 'Convention Center',1),
(2, 'Pet Health Expo', '2024-12-15', '2024-12-16', 'Convention Center',2);

-- Inserting into ServicesEvents
INSERT INTO ServicesEvents (IDServiceEvent, IDService, IDEvent) VALUES
(1, 1, 1);

-- Inserting into Breed
INSERT INTO TypeBreed (name, price) VALUES 
('Small Breed', 50.00), 
('Large Breed', 150.00), 
('Medium Breed', 100.00);

-- Inserting into Behavior
INSERT INTO TypeBehavior (name, price) VALUES 
('Aggressive', 60.00), 
('Shy', 20.00), 
('Curious', 50.00), 
('Protective', 70.00), 
('Independent', 55.00);

-- Inserting into Training
INSERT INTO Training (IDTraining, estimatedTime, IDService, totalPrice, IDTypeBehavior, IDTypeBreed) VALUES
(1, '3 weeks', 1, 500, 1, 1),
(2, '2 weeks', 2, 400, 2, 2);

-- Inserting into Grooming
INSERT INTO TypeGrooming (name, price) VALUES 
('Grooming', 40.00), 
('Bath', 25.00), 
('Ear Cleaning', 15.00), 
('Haircut', 35.00), 
('Nail Trim', 10.00), 
('Teeth Cleaning', 20.00), 
('De-shedding', 30.00), 
('Fur Conditioning', 18.00);

-- Inserting into GroomingServices
INSERT INTO GroomingServices (IDGroomingServices, IDTypeGrooming, IDService, comments) VALUES
(1, 1, 1, 'Shampooing for medium size dog'),
(2, 2, 2, 'Nail trim for cat');

-- Inserting into VisitControl
INSERT INTO VisitControl (IDVisitControl, comments, date, status, remainingVisits, IDService) VALUES
(1, 'Great service, pet was happy','2024-12-01', 'COMPLETED', 2, 1);

-- Inserting into Procedures
INSERT INTO Procedures (name, price, IDTypeProcedure, IDService) VALUES 
('Spaying Surgery', 150.00, 1, 1), 
('Neutering Surgery', 120.00, 1, 1),
('Pet Adoption', 50.00, 2, 1),
('Pet Sale', 100.00, 3, 2),
('Blood Test', 30.00, 4, 2),
('Dental Cleaning', 75.00, 5, 2);

-- Inserting into PostProcedure
INSERT INTO PostProcedure (IDPostProcedure, nextDate, status, estimedControls, comments, IDProcedure) VALUES
(1, '2024-12-15', 'SCHEDULED', 2, 'Routine check after surgery', 1);

-- Inserting into PreProcedure
INSERT INTO PreProcedure (IDPreProcedure, analysis, expectedTime, IDProcedure) VALUES
(1, 'Blood Test', '1 hour', 1);

-- Inserting into Reasons
INSERT INTO TypeReason (name) VALUES 
('Routine Checkup'),
('Vaccination'),
('Emergency'),
('Infection'),
('Skin Problems'),
('Digestive Issues'),
('Wound Treatment'),
('Behavioral Problems'),
('Weight Loss'),
('Chronic Illness'),
('Dental Issues'),
('Injury'),
('Parasites'),
('Allergic Reaction'),
('Spaying/Neutering');

-- Inserting into Consultations
INSERT INTO Consultations (IDConsultation, IDTypeReason, isControl, price, IDService) VALUES
(1, 1, TRUE, 100, 1),
(2, 2, FALSE, 200, 2);

-- Inserting into Prescriptions
INSERT INTO Prescriptions (IDPrescription, IDConsultation, recomendations, diagnostic, services,supplies) VALUES
(1, 1, 'Administer medication daily', 'Healthy','Healthy, ,,,,,,........', 'AAcetominafen, jarabe de tos'),
(2, 2, 'Immediate surgery required', 'Infection','Healthy, ,,,,,,........', 'AAcetominafen, jarabe de tos');


-- Inserting into ServicesConsultations
INSERT INTO ServicesConsultations (IDServiceConsultation, IDService, IDConsultation) VALUES
(1, 1, 1),
(2, 2, 2);

INSERT INTO Supplies (name, stock, price, IDTypeSupplies) VALUES 
('Aspirin', 100, 15.50, 1),
('Rabies Vaccine', 50, 30.00, 2),
('Bandages', 200, 10.00, 3),
('Dog Food - 10kg', 150, 25.00, 2),
('Shampoo for Dogs', 80, 12.00, 2),
('Antibiotic Ointment', 120, 20.00, 2),
('Distemper Vaccine', 60, 35.00, 2),
('Surgical Masks', 300, 5.00, 3),
('Cat Food - 5kg', 130, 18.00, 2),
('Flea Shampoo', 90, 14.00, 2);

-- Inserting into ServicesSupplies
INSERT INTO ServicesSupplies (IDServiceSupplies, IDService, IDSupplies, quantity) VALUES
(1, 1, 1, 2),
(2, 2, 2, 1);

-- Inserting into Vaccines
INSERT INTO Vaccines (IDVaccine, IDTypeVaccine, IDSupplies, batch, expirationDate) VALUES
(1, 1, 1, 12345, '2025-12-01'),
(2, 2, 2, 67890, '2026-12-01');

-- Inserting into Medicines
INSERT INTO Medicines (IDMedicine, IDTypeMedicine, IDSupplies, expirationDate) VALUES
(1, 1, 1, '2025-06-01'),
(2, 2, 2, '2026-06-01');

-- Inserting into Manufacturers
INSERT INTO Manufacturers (name, phoneNumber, IDSupplies) VALUES 
('PharmaVet', '123-456-7890', 1),  -- Aspirin
('VetMedCo', '234-567-8901', 2),  -- Rabies Vaccine
('SurgiSupply', '345-678-9012', 3),  -- Bandages
('PetNutrition', '456-789-0123', 4),  -- Dog Food - 10kg
('GroomTech', '567-890-1234', 5),  -- Shampoo for Dogs
('MedPharm', '678-901-2345', 6),  -- Antibiotic Ointment
('VaccineWorld', '789-012-3456', 7),  -- Distemper Vaccine
('SurgicalGear', '890-123-4567', 8),  -- Surgical Masks
('KittyFoodInc', '901-234-5678', 9),  -- Cat Food - 5kg
('FleaFree', '012-345-6789', 10);  -- Flea Shampoo

-- Inserting into SuppliesManufacturers
INSERT INTO SuppliesManufacturers (IDSuppliesManufacturer, IDManufacturer, IDSupplies, cost) VALUES
(1, 1, 1, 10),
(2, 2, 2, 5);

-- Inserting into Purchases
INSERT INTO Purchases (IDPurchase, datePurchase, total, IDManufacturer) VALUES
(1, '2024-12-01', 200, 1),
(2, '2024-12-02', 150, 2);

-- Inserting into PurchasesSupplies
INSERT INTO PurchasesSupplies (IDPurchaseSupplies, quantity, IDPurchase, IDSupplies, subTotal) VALUES
(1, 20, 1, 1, 200),
(2, 10, 2, 2, 150);

-- Inserting into Invoice
INSERT INTO Invoice (IDInvoice, dateInvoice, total, IDOwner, IDTypeService) VALUES
(1, '2024-12-03', 350, 1, 1),
(2, '2024-12-04', 150, 2, 1);

-- Inserting into InvoiceDetails
INSERT INTO InvoiceDetails (IDInvoiceDetails, IDInvoice, subTotal, IDService) VALUES
(1, 1, 150, 1),
(2, 2, 50, 2);

-- Inserting into PaymentMethods
INSERT INTO PaymentMethods (IDPaymentMethod, name) VALUES
(1, 'Credit Card'),
(2, 'Cash');

-- Inserting into Transactions
INSERT INTO Transactions (IDTransaction, status, dateTransaction, IDPaymentMethod, IDInvoice) VALUES
(1, 'SCHEDULED', '2024-12-03', 1, 1),
(2, 'CANCELLED', '2024-12-04', 2, 2);