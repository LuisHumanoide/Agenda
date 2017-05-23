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
    
    public static boolean validateHour(int hour){
        return (hour>=0 && hour <=23);
    }
    
    public static boolean validateTimeRange(int time1, int time2){
        return time2 >=time1;
    }
    
    public static boolean validateHour(String hour){
        String[] hourParts=hour.split(":");
        int hours=Integer.parseInt(hourParts[0]);
        int minutes=Integer.parseInt(hourParts[1]);
        return (hours>=0 && hours<=23)&&(minutes>=0 && minutes<=59);       
    }
    
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

