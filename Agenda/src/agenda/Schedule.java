/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.util.ArrayList;

/**
 *
 * @author Humanoide
 */
public class Schedule {
     //Container of the actual appointments registered. This is the one to be updated when new appointments are registered, moved, deleted, etc.
    static ArrayList<Appointment> ScheduleList;
    /**
     * getter method
     * @return 
     */
    public ArrayList<Appointment> getSchedule() {
        return ScheduleList;
    }

    /**
     * add an appointment to the schedule
     * @param ap is the appointment
     */
    public static void add(Appointment ap){
        ScheduleList.add(ap);
    }      
    
}
