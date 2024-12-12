/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;

import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Consultas {

    // 1. Mascotas atendidas: Número de visitas, procedimientos realizados, vacunas aplicadas
    public static List<PetVisitInfo> getPetVisits() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<PetVisitInfo> petVisitList = new ArrayList<>();
        String sql = "SELECT p.name AS PetName, " +
                     "COUNT(DISTINCT vc.IDVisitControl) AS VisitCount, " +
                     "COUNT(DISTINCT pr.IDProcedure) AS ProcedureCount, " +
                     "COUNT(DISTINCT v.IDVaccine) AS VaccineCount " +
                     "FROM Pets p " +
                     "LEFT JOIN Services s ON p.IDPet = s.IDPet " +
                     "LEFT JOIN VisitControl vc ON s.IDService = vc.IDService " +
                     "LEFT JOIN Procedures pr ON s.IDService = pr.IDService " +
                     "LEFT JOIN Vaccines v ON s.IDService = v.IDService " +
                     "GROUP BY p.IDPet";

        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                PetVisitInfo petVisit = new PetVisitInfo(
                        rs.getString("PetName"),
                        rs.getInt("VisitCount"),
                        rs.getInt("ProcedureCount"),
                        rs.getInt("VaccineCount")
                );
                petVisitList.add(petVisit);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing pet visits: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return petVisitList;
    }

    // 2. Servicios más solicitados: Consultas médicas, cirugías, guardería, peluquería
    public static List<ServiceInfo> getMostRequestedServices() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<ServiceInfo> serviceList = new ArrayList<>();
        String sql = "SELECT ts.name AS ServiceType, " +
                     "COUNT(s.IDService) AS ServiceCount " +
                     "FROM Services s " +
                     "JOIN TypeService ts ON s.IDTypeService = ts.IDTypeService " +
                     "WHERE ts.name IN ('Consultation', 'Surgery', 'Daycare', 'Grooming') " +
                     "GROUP BY ts.name " +
                     "ORDER BY ServiceCount DESC";

        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                ServiceInfo service = new ServiceInfo(
                        rs.getString("ServiceType"),
                        rs.getInt("ServiceCount")
                );
                serviceList.add(service);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing most requested services: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return serviceList;
    }

    // 3. Desempeño del equipo veterinario: Cantidad de consultas atendidas por cada profesional
    public static List<EmployeeConsultationCount> getVeterinarianPerformance() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<EmployeeConsultationCount> performanceList = new ArrayList<>();
        String sql = "SELECT e.name AS EmployeeName, " +
                     "COUNT(c.IDConsultation) AS ConsultationCount " +
                     "FROM Employees e " +
                     "LEFT JOIN Services s ON e.IDEmployee = s.IDEmployee " +
                     "LEFT JOIN Consultations c ON s.IDService = c.IDService " +
                     "GROUP BY e.IDEmployee " +
                     "ORDER BY ConsultationCount DESC";

        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                EmployeeConsultationCount employeePerformance = new EmployeeConsultationCount(
                        rs.getString("EmployeeName"),
                        rs.getInt("ConsultationCount")
                );
                performanceList.add(employeePerformance);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing veterinarian performance: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return performanceList;
    }

    // 4. Inventario: Medicamentos utilizados, próximos a vencer y necesidad de reabastecimiento
    public static List<MedicineInventory> getMedicineInventory() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<MedicineInventory> inventoryList = new ArrayList<>();
        String sql = "SELECT m.name AS MedicineName, " +
                     "COUNT(ms.IDMedicine) AS UsageCount, " +
                     "m.expirationDate, " +
                     "CASE " +
                     "WHEN m.expirationDate <= CURDATE() THEN 'Expired' " +
                     "WHEN m.expirationDate <= CURDATE() + INTERVAL 30 DAY THEN 'Expiring Soon' " +
                     "ELSE 'Good' END AS ExpirationStatus " +
                     "FROM Medicines m " +
                     "JOIN ServicesSupplies ss ON m.IDMedicine = ss.IDSupplies " +
                     "JOIN Supplies s ON ss.IDSupplies = s.IDSupplies " +
                     "JOIN Services se ON ss.IDService = se.IDService " +
                     "JOIN ServiceSupplies ms ON m.IDMedicine = ms.IDSupplies " +
                     "GROUP BY m.IDMedicine";

        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                MedicineInventory inventory = new MedicineInventory(
                        rs.getString("MedicineName"),
                        rs.getInt("UsageCount"),
                        rs.getDate("expirationDate"),
                        rs.getString("ExpirationStatus")
                );
                inventoryList.add(inventory);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing medicine inventory: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return inventoryList;
    }

    // 5. Facturación: Total facturado por período, servicios más rentables, clientes frecuentes
    public static List<InvoiceInfo> getBillingInfo() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<InvoiceInfo> invoiceList = new ArrayList<>();
        
        // Total facturado por período (por ejemplo, este mes)
        String sql = "SELECT SUM(i.total) AS TotalRevenue, " +
                     "MONTH(i.dateInvoice) AS InvoiceMonth, " +
                     "YEAR(i.dateInvoice) AS InvoiceYear " +
                     "FROM Invoice i " +
                     "WHERE i.dateInvoice BETWEEN '2024-12-01' AND '2024-12-31' " +
                     "GROUP BY InvoiceMonth, InvoiceYear";

        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                InvoiceInfo invoice = new InvoiceInfo(
                        rs.getDouble("TotalRevenue"),
                        rs.getInt("InvoiceMonth"),
                        rs.getInt("InvoiceYear")
                );
                invoiceList.add(invoice);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing billing information: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return invoiceList;
    }
    
    public static List<PetVisitInfo> petsAttendedReport() {
    List<PetVisitInfo> petVisitInfos = new ArrayList<>();
    String sql = "SELECT p.name AS petName, " +
                 "COUNT(v.id) AS visitCount, " +
                 "COUNT(pro.id) AS procedureCount, " +
                 "COUNT(vacc.id) AS vaccineCount " +
                 "FROM Pets p " +
                 "LEFT JOIN Visits v ON p.id = v.petId " +
                 "LEFT JOIN Procedures pro ON v.id = pro.visitId " +
                 "LEFT JOIN Vaccines vacc ON v.id = vacc.visitId " +
                 "GROUP BY p.id";
    
    try (Connection conn = DBConnection.connectionDB(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String petName = rs.getString("petName");
            int visitCount = rs.getInt("visitCount");
            int procedureCount = rs.getInt("procedureCount");
            int vaccineCount = rs.getInt("vaccineCount");
            petVisitInfos.add(new PetVisitInfo(petName, visitCount, procedureCount, vaccineCount));
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching Pets Attended Report: " + ex.getMessage());
    }
    
    return petVisitInfos;
}
public static List<ServiceInfo> mostRequestedServicesReport() {
    List<ServiceInfo> serviceInfos = new ArrayList<>();
    String sql = "SELECT s.type AS serviceType, COUNT(s.id) AS serviceCount " +
                 "FROM Services s " +
                 "JOIN Visits v ON s.id = v.serviceId " +
                 "GROUP BY s.id ORDER BY serviceCount DESC";
    
    try (Connection conn = DBConnection.connectionDB(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String serviceType = rs.getString("serviceType");
            int serviceCount = rs.getInt("serviceCount");
            serviceInfos.add(new ServiceInfo(serviceType, serviceCount));
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching Most Requested Services Report: " + ex.getMessage());
    }
    
    return serviceInfos;
}
public static List<EmployeeConsultationCount> employeePerformanceReport() {
    List<EmployeeConsultationCount> employeeConsultationCounts = new ArrayList<>();
    String sql = "SELECT e.name AS employeeName, COUNT(v.id) AS consultationCount " +
                 "FROM Employees e " +
                 "JOIN Visits v ON e.id = v.employeeId " +
                 "GROUP BY e.id";
    
    try (Connection conn = DBConnection.connectionDB(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String employeeName = rs.getString("employeeName");
            int consultationCount = rs.getInt("consultationCount");
            employeeConsultationCounts.add(new EmployeeConsultationCount(employeeName, consultationCount));
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching Employee Performance Report: " + ex.getMessage());
    }
    
    return employeeConsultationCounts;
}
public static List<InvoiceInfo> billingReport() {
    List<InvoiceInfo> invoiceInfos = new ArrayList<>();
    String sql = "SELECT EXTRACT(MONTH FROM i.date) AS invoiceMonth, " +
                 "EXTRACT(YEAR FROM i.date) AS invoiceYear, " +
                 "SUM(i.totalAmount) AS totalRevenue " +
                 "FROM Invoices i " +
                 "GROUP BY invoiceMonth, invoiceYear";
    
    try (Connection conn = DBConnection.connectionDB(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            int invoiceMonth = rs.getInt("invoiceMonth");
            int invoiceYear = rs.getInt("invoiceYear");
            double totalRevenue = rs.getDouble("totalRevenue");
            invoiceInfos.add(new InvoiceInfo(invoiceMonth, invoiceYear, (int) totalRevenue));
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching Billing Report: " + ex.getMessage());
    }
    
    return invoiceInfos;
}
public static List<MedicineInventory> suppliesUsageReport() {
    List<MedicineInventory> medicineInventories = new ArrayList<>();
    String sql = "SELECT m.name AS medicineName, " +
                 "SUM(mu.quantity) AS usageCount, " +
                 "m.expirationDate, " +
                 "CASE WHEN m.expirationDate < CURRENT_DATE THEN 'Expired' ELSE 'Valid' END AS expirationStatus " +
                 "FROM Medicines m " +
                 "JOIN MedicineUsage mu ON m.id = mu.medicineId " +
                 "GROUP BY m.id";
    
    try (Connection conn = DBConnection.connectionDB(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String medicineName = rs.getString("medicineName");
            int usageCount = rs.getInt("usageCount");
            Date expirationDate = rs.getDate("expirationDate");
            String expirationStatus = rs.getString("expirationStatus");
            medicineInventories.add(new MedicineInventory(medicineName, usageCount, expirationDate, expirationStatus));
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching Supplies Usage Report: " + ex.getMessage());
    }
    
    return medicineInventories;
}


}
