/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represent two appointments for compare the priorities
 * @author Humanoide
 */
public class TwoAppointments implements Serializable{
    ArrayList<Appointment> initials;
    Appointment newAppoint;

    public TwoAppointments(ArrayList<Appointment> initials, Appointment newApp) {
        this.initials = initials;
        this.newAppoint = newApp;
    }

    public ArrayList<Appointment> getInitials() {
        return initials;
    }

    public void setInitials(ArrayList<Appointment> initials) {
        this.initials = initials;
    }

    public Appointment getNewAppoint() {
        return newAppoint;
    }

    public void setNewApp(Appointment newApp) {
        this.newAppoint = newApp;
    }


    
}
