/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import agenda.ActionMessage;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Obtain the data from the parser
 * @author Humanoide
 */
public class DataFromParser {
    /**
     * obtain the action message from the string to parse
     * @param string
     * @return 
     */
    public static ActionMessage getAm(String string){
        ActionMessage am=null;
        parser p;
         p = new parser(new Yylex(new BufferedReader(new StringReader(string))));
        try {
            p.parse();
            //this is the actionMessage object that is extracted from the parser:
            am=p.action_obj.actionMessage;
        } catch (Exception ex) {
            System.out.println("error"+ex.toString());
        }
        return am;
    }
}
