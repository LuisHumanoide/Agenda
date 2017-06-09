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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Humanoide
 */
public class WindowAgent extends Agent implements MouseListener{
    //list of agents in the system
    AMSAgentDescription[] agents;
    Intro in;
    @Override
    public void setup(){
        agents = null;
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
        //new window interface
        in=new Intro();
        in.execute.addMouseListener(this);
        in.setVisible(true);
        
        
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
    
    
     protected void ProcessMessage(ACLMessage in_pACLMessage) {

        System.out.println("Entered ProcessMessage method. with the ACL message: " + in_pACLMessage.toString());
        /*Switch used to decide which action must be taken BASED ON THE PERFORMATIVE, 
        but another switch will be used, depending on which of "Create/Delete/Move" is desired. */
        switch (in_pACLMessage.getPerformative()) {
            case jade.lang.acl.ACLMessage.INFORM: {
                   in.updateList(Schedule.ScheduleList);

            }
            break;
            case jade.lang.acl.ACLMessage.AGREE: {
                /*AGREE is used when the two agents have reached an agreement on when will
                the DATE will be on the Schedule (AGENDA)*/

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
     * when the button is pressed, the message will be send to the user agent
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.setContent(in.field.getText());
                    msg.addReceiver(getAgentByName("user").getName());
                    send(msg);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
