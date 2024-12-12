package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.enumsandinterfaces.Facturable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDateTime;

public class Consultation extends Service implements Facturable{

    private int idConsultation;
    private Type reason;
    private boolean isControl;
    private double price;
    // esta es la parte de appointment
    private String recommendations;
    private String diagnostic;
    private String services;
    private String supplies;
    // /esto es lo que la convierte en consulta

    private Consultation(Builder builder) {
        super(builder.serviceId, builder.serviceDate, builder.pet, builder.employee, builder.typeService, builder.status, builder.paid);
        this.idConsultation = builder.idConsultation;
        this.reason = builder.reason;
        this.isControl = builder.isControl;
        this.price = builder.price;
        this.recommendations = builder.recommendations;
        this.diagnostic = builder.diagnostic;
        this.services = builder.services;
        this.supplies = builder.supplies;
    }

    public Consultation() {
    }

    ;
    public int getIdConsultation() {
        return idConsultation;
    }

    public Type getReason() {
        return reason;
    }

    public boolean getIsControl() {
        return isControl;
    }

    public double getPrice() {
        return price;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getServices() {
        return services;
    }

    public String getSupplies() {
        return supplies;
    }

    public String getDetailsAppointment() {
        return super.toString()
                + "idConsultation: " + idConsultation
                + "\nreason: " + reason
                + "\nisControl: " + isControl
                + "\nprice: " + price
                + "\n-----------------\n";
    }
    @Override
    public String toString() {
        return "Consultation{"
                + "idConsultation=" + idConsultation
                + ", reason=" + reason
                + ", isControl=" + isControl
                + ", price=" + price
                + ", recommendations='" + recommendations + '\''
                + ", diagnostic='" + diagnostic + '\''
                + ", services=" + services
                + ", supplies=" + supplies
                + "} " + super.toString();
    }

    @Override
    public void calcular() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static class Builder {

        private int serviceId;
        private LocalDateTime serviceDate;
        private Pet pet;
        private Employee employee;
        private Type typeService;
        private Status status;
        private boolean paid;

        private int idConsultation;
        private Type reason;
        private boolean isControl;
        private double price;
        private String recommendations;
        private String diagnostic;
        private String services;
        private String supplies;

        public Builder(Service service) {
            this.serviceId = service.getIdService();
            this.serviceDate = service.getDate();
            this.pet = service.getPet();
            this.employee = service.getEmployee();
            this.typeService = service.getTypeService();
        }

        public Builder(Consultation c) {
            this.serviceId = c.getIdService();
            this.serviceDate = c.getDate();
            this.pet = c.getPet();
            this.employee = c.getEmployee();
            this.typeService = c.getTypeService();
            this.status = c.getStatus();
            this.paid = c.getPaid();
            this.idConsultation = c.getIdConsultation();
            this.reason = c.getReason();
            this.isControl = c.getIsControl();
            this.price = c.getPrice();
        }

        public Builder() {
        }

        public Builder setServiceId(int serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder setServiceDate(LocalDateTime serviceDate) {
            this.serviceDate = serviceDate;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setPaid(boolean paid) {
            this.paid = paid;
            return this;

        }

        public Builder setPet(Pet pet) {
            this.pet = pet;
            return this;
        }

        public Builder setEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder setTypeService(Type typeService) {
            this.typeService = typeService;
            return this;
        }

        public Builder setIdConsultation(int idConsultation) {
            this.idConsultation = idConsultation;
            return this;
        }

        public Builder setReason(Type reason) {
            this.reason = reason;
            return this;
        }

        public Builder setIsControl(boolean isControl) {
            this.isControl = isControl;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setRecommendations(String recommendations) {
            this.recommendations = recommendations;
            return this;
        }

        public Builder setDiagnostic(String diagnostic) {
            this.diagnostic = diagnostic;
            return this;
        }

        public Builder setServices(String services) {
            this.services = services;
            return this;
        }

        public Builder setSupplies(String supplies) {
            this.supplies = supplies;
            return this;
        }

        public Consultation build() {
            return new Consultation(this);
        }
    }
}
