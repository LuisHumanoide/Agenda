/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.util.Date;

/**
 *
 * @author Humanoide
 */



public class ActionMessage {
    agenda.eAction e_action;
    String s_object;
    Date start1;
    Date end1;
    Date start2;
    Date end2;
    /**
     * constructor para crear una cita
     * @param i_action CREATE
     * @param s_object
     * @param start1
     * @param end1 
     */
    public ActionMessage(agenda.eAction e_action, String s_object, Date start1, Date end1) {
        this.e_action = e_action;
        this.s_object = s_object;
        this.start1 = start1;
        this.end1 = end1;
    }
    /**
     * constructor para eliminar una cita
     * @param e_action
     * @param i_action DELETE
     * @param s_object
     * @param start1 
     */
    public ActionMessage(agenda.eAction e_action, String s_object, Date start1) {
        this.e_action = e_action;
        this.s_object = s_object;
        this.start1 = start1;
    }
    /**
     * Constructor para hacer el update de una cita
     * @param i_action UPDATE
     * @param s_object
     * @param start1
     * @param end1
     * @param start2
     * @param end2 
     */
    public ActionMessage(agenda.eAction e_action, String s_object, Date start1, Date start2, Date end2) {
        this.e_action = e_action;
        this.s_object = s_object;
        this.start1 = start1;
        this.start2 = start2;
        this.end2 = end2;
    }

    public eAction getE_action() {
        return e_action;
    }

    public void setE_action(agenda.eAction e_action) {
        this.e_action = e_action;
    }

    public String getS_object() {
        return s_object;
    }

    public void setS_object(String s_object) {
        this.s_object = s_object;
    }

    public Date getStart1() {
        return start1;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public Date getEnd1() {
        return end1;
    }

    public void setEnd1(Date end1) {
        this.end1 = end1;
    }

    public Date getStart2() {
        return start2;
    }

    public void setStart2(Date start2) {
        this.start2 = start2;
    }

    public Date getEnd2() {
        return end2;
    }

    public void setEnd2(Date end2) {
        this.end2 = end2;
    }

    @Override
    public String toString() {
       String str="";
       str=str+"ActionMessage( Action= "+e_action+", +Sustantive= "+s_object+" , start1="+start1.toString()+" ";
       str=(end1!=null)?str+", end1= "+end1.toString():str+", end1= "+"null";
       str=(start2!=null)?str+", start2= "+start2.toString():str+", start2= "+"null";
       str=(end2!=null)?str+", end2= "+end2.toString():str+", end2= "+"null";
       str=str+")";
       return str;
    }
}
