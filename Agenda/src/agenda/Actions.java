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
public class Actions {
    /**
     * crear la cita con los siquientes parametros
     * @param name , el sujeto con el que se irá a la cita, sustantivo
     * @param start , inicio de la cita
     * @param end , fin de la cita
     * @return si se creó la cita o no
     */
    public static boolean createDate(String name, Date start, Date end){
        //code
        System.out.println(name+" , "+start+" , "+end);
        return true;
    }
    
    
    /**
     * eliminar la cita con el nombre del sustantuivo y la fecha dada
     * Si la fecha está entre el tiempo de inicio de la cita y el tiempo de fin, entonces se elimina
     * @param name
     * @param start
     * @return 
     */
    public static boolean deleteDate(String name, Date start){
        System.out.println(name+" , "+start);
        return true;
    }
    
    
    /**
     * actualizo la cita, busco primero por el nombre name, y el tiempo start1, y la cambió al nuevo empiezo:
     * start2 y al nuevo fin : end2
     * @param name
     * @param start1 el tiempo en el que está la cita anterior, o cuando empieza
     * @param start2 el inicio nuevo
     * @param end2 el fin nuevo
     * @return 
     */
    public static boolean updateDate(String name, Date start1, Date start2, Date end2){
        //code
        System.out.println(name+" , "+start1+" , "+start2+" , "+end2);
        return true;
    }
    
}
