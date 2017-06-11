/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Humanoide
 */
public class DateValidation {
    /**
     * validate the range of the hours
     * @param hour
     * @return 
     */
    public static boolean validateHour(int hour){
        return (hour>=0 && hour <=23);
    }
    /**
     * the time 2 must be greater than the time 1
     * @param time1
     * @param time2
     * @return true if the condition is reached
     */
    public static boolean validateTimeRange(int time1, int time2){
        return time2 >=time1;
    }
    /**
     * validate that the hour is correct
     * @param hour in hh:mm format
     * @return true if the hour string is correct
     */
    public static boolean validateHour(String hour){
        String[] hourParts=hour.split(":");
        int hours=Integer.parseInt(hourParts[0]);
        int minutes=Integer.parseInt(hourParts[1]);
        return (hours>=0 && hours<=23)&&(minutes>=0 && minutes<=59);       
    }
    /**
     * validate if the day of the date is correct
     * @param day
     * @return 
     */
    public static boolean validateDate(String day) {
        Calendar c1 = Calendar.getInstance();
        try {
            String date=day+"/"+c1.get(Calendar.MONTH)+"/"+c1.get(Calendar.YEAR);
            System.out.println(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    /**
     * validate the date if the date is in the format dd/mm
     * @param day
     * @param month
     * @return 
     */
     public static boolean validateDate(String day, String month) {
        Calendar c1 = Calendar.getInstance();
        try {
            String date=day+"/"+month+"/"+c1.get(Calendar.YEAR);
            System.out.println(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
     /**
      * validate the date if the date is in the dd/mm/yyyy format
      * @param day
      * @param month
      * @param year
      * @return 
      */
      public static boolean validateDate(String day, String month, String year) {
        try {
            String date=day+"/"+month+"/"+year;
            System.out.println(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}

