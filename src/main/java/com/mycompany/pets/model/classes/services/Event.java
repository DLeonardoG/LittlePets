
package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.model.classes.superclasses.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private String name; 
    private Date startDaate; 
    private Date endDaate; 
    private String place;
    private List<Service> services;

    public Event(String name, Date startDaate, Date endDaate, String place) {
        this.name = name;
        this.startDaate = startDaate;
        this.endDaate = endDaate;
        this.place = place;
        this.services = new ArrayList<>();
    }
    public Event() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDaate() {
        return startDaate;
    }

    public void setStartDaate(Date startDaate) {
        this.startDaate = startDaate;
    }

    public Date getEndDaate() {
        return endDaate;
    }

    public void setEndDaate(Date endDaate) {
        this.endDaate = endDaate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", startDaate=" + startDaate + ", endDaate=" + endDaate + ", place=" + place + ", services=" + services + '}';
    }
}
