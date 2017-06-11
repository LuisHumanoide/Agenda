/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * ReAgend agent
 *
 * @author Humanoide
 */
public class ReAgend extends Agent {

    //list of agents in the system
    AMSAgentDescription[] agents;

    @Override
    protected void setup() {
        agents = null;
        obtainAgents();
        /*=============================================================================
        BEHAVIOR OF THE AGENT
 =============================================================================*/
        Behaviour receiveBehaviour = new CyclicBehaviour() {
            @Override
            public void action() {
                //NOTE: THIS PART WILL BE MODIFIED..***********
                ACLMessage msg = receive();
                if (msg != null) {
                    try {
                        //process the message               
                        ProcessMessage(msg);
                    } catch (Exception ex) {
                        //ocurrió una excepción
                        System.out.println(ex.toString());
                    }
                } else {
                    block();
                }
            }
        };

        //Add the behaviours for this agent to execute.
        addBehaviour(receiveBehaviour);
    }

    /**
     * method for processing the message
     *
     * @param in_pACLMessage
     */
    protected void ProcessMessage(ACLMessage in_pACLMessage) {

        System.out.println("Entered ProcessMessage method. with the ACL message: " + in_pACLMessage.toString());
        /*Switch used to decide which action must be taken BASED ON THE PERFORMATIVE, 
        but another switch will be used, depending on which of "Create/Delete/Move" is desired. */
        switch (in_pACLMessage.getPerformative()) {
            case jade.lang.acl.ACLMessage.INFORM: {
                /*receive the signal for update the list*/

                try {
                    TwoAppointments ta2 = (TwoAppointments) in_pACLMessage.getContentObject();
                    for (Appointment ap : ta2.initials) {
                        JOptionPane.showMessageDialog(null, "se va a reagendar " + ap);
                        reAgendAppintment(ap);
                    }
                    updateList();
                } catch (UnreadableException ex) {
                    Logger.getLogger(ReAgend.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            break;
            case jade.lang.acl.ACLMessage.AGREE: {
                /*AGREE is used when the two agents have reached an agreement on when will
                the DATE will be on the Schedule (AGENDA)*/
                try {
                    TwoAppointments ta2 = (TwoAppointments) in_pACLMessage.getContentObject();
                    JOptionPane.showMessageDialog(null, "se va a reagendar " + ta2.oldAppoint);
                    reAgendAppintment(ta2.oldAppoint);
                    updateList();
                } catch (UnreadableException ex) {
                    Logger.getLogger(ReAgend.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            break;
            case jade.lang.acl.ACLMessage.PROPOSE: {
                /*PROPOSE identifies a message to which the agents must AGREE or REJECT_PROPOSAL.
                It is in this case where the AGENDA is checked to see if the date will be created of discarded.*/

            }
            break;
            case jade.lang.acl.ACLMessage.REJECT_PROPOSAL: {
                /*REJECT_PROPOSAL is received when the "Common sense" agent has rejected the proposal of the
                "User profile" agent. It may or may not be necessary. */

            }
            break;
            case jade.lang.acl.ACLMessage.UNKNOWN: {
                System.out.println("WARNING, UNKNOWN performative was given to this ACLMessage, its contents were: " + in_pACLMessage.toString());
            }
            break;
            default: {
                System.out.println("WARNING, no performative was given to this ACLMessage, its contents were: " + in_pACLMessage.toString());
            }
            break;
        }

    }

    /**
     * return the agent by the name
     *
     * @param name , name of the agent
     * @return the agent such as AMSAgentDescription
     */
    public AMSAgentDescription getAgentByName(String name) {
        for (AMSAgentDescription agent : agents) {
            if (name.equals(agent.getName().getLocalName())) {
                return agent;
            }
        }
        return null;
    }

    /**
     * Checks for conflicting appointments on the Agenda.
     *
     * @param in_DateToCheck An appointment object, which we want to see if it's
     * in conflict of dates with another one.
     * @return An ArrayList of appointments, containing all the ones in conflict
     * with in_DateToCheck. Empty if there's none.
     */
    protected ArrayList<Appointment> CheckForConflict(Appointment in_DateToCheck) {
        ArrayList<Appointment> tempConflictingAppointments = new ArrayList<>();
        for (Appointment app : Schedule.ScheduleList) {
            if (!(in_DateToCheck.BeginDate.after(app.EndDate) || in_DateToCheck.EndDate.before(app.BeginDate))) {
                //Else, this two appointments are now in conflict. It doesn't matter if this is contained or not
                //or contains another one completely, it just matters they are conflicting.
                tempConflictingAppointments.add(app); //Then, we add it to the list so we can weight them all together.
                //Print the times so we can easily corroborate the result.
                System.out.println("The new appointment is in conflict with the one from: " + app.BeginDate.toString() + " to " + app.EndDate.toString());
            }
        }
        return tempConflictingAppointments;
    }

    /**
     * re agend the appointment to the next empty space in the schedule
     *
     * @param appointment
     */
    public void reAgendAppintment(Appointment appointment) {
        while (!CheckForConflict(appointment).isEmpty()) {
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(appointment.BeginDate);
            cal2.setTime(appointment.EndDate);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            cal2.add(Calendar.HOUR_OF_DAY, 1);
            appointment.BeginDate = cal.getTime();
            appointment.EndDate = cal2.getTime();
        }
        Schedule.ScheduleList.add(appointment);
    }

    /**
     * send a message to the window agent for update the list
     */
    public void updateList() {
        Collections.sort(Schedule.ScheduleList);
        /*send the message for update the list*/
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(getAgentByName("window").getName());
        send(reply);
    }

    /**
     * get the list of the agents in the environment
     */
    public void obtainAgents() {
        /*=============================================================================
 |  this block is for identify the agents in the system
 *===========================================================================*/
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }

        AID myID = getAID();
        for (int i = 0; i < agents.length; i++) {
            AID agentID = agents[i].getName();
            System.out.println(
                    (agentID.equals(myID) ? "*** " : "    ")
                    + i + ": " + agentID.getName()
            );
        }
        //<------------------------------------------------------- end the identification
    }

}
