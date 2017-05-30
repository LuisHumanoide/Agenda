/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import javax.swing.JOptionPane;
import translator.DataFromParser;
import translator.parser;

/**
 * Enumeration ePriorityCategories is used to differentiate the category to which an Appointment belongs,
 * depending on the place or person specified by the command.
 * This affects the weight of the Utility Calculation function.
 */
 enum ePriorityCategories
    {
        EPC_HEALTH,
        EPC_SCHOOL,
        EPC_WORK,
        EPC_FAMILY,
        //EPC_FRIENDS,
        EPC_OTHER
    };


class Appointment
{
    public String PlaceOrPeople;
    ePriorityCategories eCategory;
    public Date BeginDate;
    public Date EndDate;
    public float Priority;
    
    /**
     * toString Method override.
     * @return a special formatted representation of this object, in a more human form and language.
     */
    @Override
    public String toString()
    {
        return ("Date with " + PlaceOrPeople + " from: " + BeginDate.toString() + " to: " + EndDate.toString() );
    }
};



/**
 *
 * @author Adrian Gonzalez
 * @since 25/05/2017
 */
public class AgendaAgent extends Agent{
    
    
    /**
    * Used to retrieve the category to which a certain word belongs. 
    */
    HashMap<String, ePriorityCategories> PrioritiesDictionary = new HashMap<>();
  
    //Used to retrieve the Weight or value for the utility function.
    HashMap<ePriorityCategories, Float> CategoriesWeightMap = new HashMap<>();
  
    //Used to retrieve the Weight or value for the utility function, given the preferences of the user.
    HashMap<ePriorityCategories, Float> UserCategoriesWeightMap = new HashMap<>();
    
    //Container of the actual appointments registered. This is the one to be updated when new appointments are registered, moved, deleted, etc.
    ArrayList<Appointment> Schedule;
  
    /**
     *  InitializeCategoryWeights method.
     *  @description this method sets a default value to the weight of each category, according to "common sense".
     *  also according to the user preferences.
     */
    protected void InitializeCategoryWeights()
    {
        //For now, the total of the weights is 100, distributed according to "common sense".
        CategoriesWeightMap.put(ePriorityCategories.EPC_HEALTH, 40.0f);
        CategoriesWeightMap.put(ePriorityCategories.EPC_SCHOOL, 10.0f);
        CategoriesWeightMap.put(ePriorityCategories.EPC_WORK, 20.0f);
        CategoriesWeightMap.put(ePriorityCategories.EPC_FAMILY, 25.0f);
        CategoriesWeightMap.put(ePriorityCategories.EPC_OTHER, 5.0f);
        
        //The one from the user, are values I set because YOLO
        UserCategoriesWeightMap.put(ePriorityCategories.EPC_HEALTH, 15.0f);
        UserCategoriesWeightMap.put(ePriorityCategories.EPC_SCHOOL, 35.0f);
        UserCategoriesWeightMap.put(ePriorityCategories.EPC_WORK, 10.0f);
        UserCategoriesWeightMap.put(ePriorityCategories.EPC_FAMILY, 30.0f);
        UserCategoriesWeightMap.put(ePriorityCategories.EPC_OTHER, 10.0f);
    }
  
    /**
    * @Description ReadPriorityFiles method is used to read a file at in_szPriorityFilePath and
    * add the elements to the agent's PrioritiesDictionary, with their respective category.
    * @param in_szPriorityFilePath denotes the path of the file to be read from.
    */
    protected void ReadPriorityFiles(String in_szPriorityFilePath)
    {
        //In this case, the FILENAME is CategoryDictionary.txt
        BufferedReader br = null;
        try {
            //fr = new FileReader(in_szPriorityFilePath);
            //br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(in_szPriorityFilePath));

            int iActualCategory = 0;//This is a counter to know which category is the actual, according to the '*' markers on the input file.           
            //This while is used to read the file and add the words to the CategoryDictionary
            while ((sCurrentLine = br.readLine()) != null) {
		System.out.println(sCurrentLine);
                if(sCurrentLine.equals("*")) //Check if the line was the Category separator.
                {
                    /*if it was equal to '*', which is the symbol for category separation, then,
                    the next elements will now belong to the next category according to the Enum.*/
                    iActualCategory++; 
                    System.err.println("Now, the elements' category will be: " +  ePriorityCategories.values()[iActualCategory].toString());
                }
                else
                {
                    PrioritiesDictionary.put(sCurrentLine, ePriorityCategories.values()[iActualCategory]);    
                }            
            }
	} catch (IOException e) 
        {
            e.printStackTrace();
	} finally 
        {
            try {
                    if (br != null)
                        br.close();
		} catch (IOException ex) 
                {
                    ex.printStackTrace();
		}
	}
    }
    
  
    
    /**
     * setup()
     * @description: Method used by JADE when an instance of this class is instantiated.
    */
    protected void setup ()
    {
        //Prints the local name of this agent.
        System.out.println(getLocalName());
        
        ReadPriorityFiles("CategoryDictionary.txt");
        
        Behaviour receiveBehaviour = new CyclicBehaviour() 
        {
            @Override
            public void action() 
            {
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
     * ProcessMessage
     * @description Function called to translate from the Reduced English to messages the agents can understand better.
     * @param in_pACLMessage is an ACLMessage object which must contain a Performative and a message content, which will be translated so agents can manipulate it.
     */
    protected void ProcessMessage(ACLMessage in_pACLMessage)
    {
        //the action message is created
        ActionMessage actionMessage;
        //actionMessage=getActionMessage(in_pACLMessage.getContent());
        System.out.println("Entered ProcessMessage method. with the ACL message: " + in_pACLMessage.toString());
        /*Switch used to decide which action must be taken BASED ON THE PERFORMATIVE, 
        but another switch will be used, depending on which of "Create/Delete/Move" is desired. */
        switch (in_pACLMessage.getPerformative())
        {
            case  jade.lang.acl.ACLMessage.INFORM:
            {
                /*INFORM will be used when the application (user) inputs a statement
                in Natural English. In this case, it will be translated to something easier for the agent.*/
                System.out.println("Entered INFORM case.");
            }
            break;
            case  jade.lang.acl.ACLMessage.AGREE:
            {
                /*AGREE is used when the two agents have reached an agreement on when will
                the DATE will be on the Schedule (AGENDA)*/
                System.out.println("Entered AGREE case.");
            }
            break;
            case  jade.lang.acl.ACLMessage.PROPOSE:
            {
                /*PROPOSE identifies a message to which the agents must AGREE or REJECT_PROPOSAL.
                It is in this case where the AGENDA is checked to see if the date will be created of discarded.*/
                System.out.println("Entered PROPOSE case.");
            }
            break;
            case  jade.lang.acl.ACLMessage.REJECT_PROPOSAL:
            {
                /*REJECT_PROPOSAL is received when the "Common sense" agent has rejected the proposal of the
                "User profile" agent. It may or may not be necessary. */
                System.out.println("Entered REJECT_PROPOSAL case.");
            }
            break;
            case  jade.lang.acl.ACLMessage.UNKNOWN :
            {
                System.out.println("WARNING, UNKNOWN performative was given to this ACLMessage, its contents were: " + in_pACLMessage.toString());
            }
            break;
            default:
            {
                System.out.println("WARNING, no performative was given to this ACLMessage, its contents were: " + in_pACLMessage.toString());
            }
            break;
        }
        
        System.out.println("Exit the ProcessMessage method. ");
    }
    
    /**
     * PrintSchedule prints the actual Appointments stored in Schedule arrayList, in a nicely formatted manner.
     */
    protected void PrintSchedule ()
    {
        for(Appointment app : Schedule)
        {
            System.out.println( app.toString() ); //Using the nice format of the "toString()" override, it is as simple as this.
        }
    }
    
    
    /**
     * CheckSchedule method is used to iterate the actual schedule to find possible conflicts between the new appointment
     * and the ones already scheduled. If there is conflict, it checks the utilities (priorities) to resolve the conflict.
     * @param in_DateToCheck is the new Appointment to try to insert on the schedule.
     * @return true if in_DateToCheck has a greater Priority than all the other appointments in conflict with it (always if there's no conflict); false otherwise.
     */
    protected boolean CheckSchedule (Appointment in_DateToCheck)
    {
        System.out.println("Entered CheckSchedule function, the appointment to check is from : " + in_DateToCheck.BeginDate.toString() + " to " + in_DateToCheck.EndDate.toString());
        ArrayList<Appointment> tempConflictingAppointments = new ArrayList<>();
        float fTotalUtilityOfConflictingAppointments = 0.0f;
        for( Appointment app: Schedule  )
        {
            if( !(in_DateToCheck.BeginDate.after(app.EndDate) || in_DateToCheck.EndDate.before(app.BeginDate) ) )
            {
                //Else, this two appointments are now in conflict. It doesn't matter if this is contained or not
                //or contains another one completely, it just matters they are conflicting.
                tempConflictingAppointments.add(app); //Then, we add it to the list so we can weight them all together.
                fTotalUtilityOfConflictingAppointments += app.Priority;//We add it at the same time, so we don't have to reiterate the list.
                //Print the times so we can easily corroborate the result.
                System.out.println("The new appointment is in conflict with the one from: " + app.BeginDate.toString() + " to " + app.EndDate.toString());
            }            
        }
        
        //Compare the total utility of the old ones to the total of the new one.
        if( in_DateToCheck.Priority > fTotalUtilityOfConflictingAppointments)
        {
            System.out.println("The newest appointment has a greater utility than the: " + tempConflictingAppointments.size() 
                    + " previous appointments, so it has replaced them.");
            //Then, we have to remove from the actual schedule the conflicting ones and add the new one.
            Schedule.removeAll(tempConflictingAppointments);//Use this function to remove all of them.
            Schedule.add(in_DateToCheck); //Then, add the new one.
            return true;// return true so it is not necessary to use an else statement below.
        }

        //Then, the new one is just discarded. Maybe i'll leave a notification.
        System.out.println("The newest Appointment was not good enough to replace the ones made before.");

        return false;
    }
    
    
    /**
     * CalculateUtility method
     * @description Uses the given criteria to calculate the utility value of this appointment, in other words: how convenient is this one compared to others.
     * @param in_AppointmentToCheck is an appointment object, which contains the date and people the appointment is with.
     * @return the value of Utility to this appointment, given by its duration (length), the user priority, general priority, etc.
     */
    protected float CalculateUtility(Appointment in_AppointmentToCheck )
    {
        //********************CHANGE THE VALUE OF LENGTH
        float fLength = 1.0f; // in_AppointmentToCheck.EndDate - in_AppointmentToCheck.BeginDate ;
        float fUserPriority = UserCategoriesWeightMap.get(in_AppointmentToCheck.eCategory) ;//Check the beliefs of the "User profile" agent, to see the priority values.
        float fGeneralPriority = CategoriesWeightMap.get(in_AppointmentToCheck.eCategory) ; //Check the beliefs of the "Common sense" agent, to check priority values.
        float fRealUrgency = 1.0f; //For now, it will be UNUSED.
        
        float fTotalUtility = (fUserPriority + fGeneralPriority) * (fRealUrgency / fLength);
        System.out.println("The total utility value of this Appointment is: " + fTotalUtility);
       
        return fTotalUtility;
    }
            
    
    
    /**
     * takeDown() 
     * @description: Method used by JADE agent class when an instance of this class is deleted.
     */
    protected void takeDown()
    {
        System.out.println("TakeDown method of AgendaAgent");
    }
    /**
     * get Action Message from the parser
     * @param string is the string to parse
     * @return 
     */
    ActionMessage getActionMessage(String string) throws NullPointerException{
        ActionMessage am = DataFromParser.getAm(string);
        if(am==null){
            throw new NullPointerException("Action Message is null, the parse process was done wrong");
        }
        else{
            System.out.println(am.toString());
        }
        return am;
    }
}
