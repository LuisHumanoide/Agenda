/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Humanoide
 */
public class DateUtils {

    /**
     * obtiene la fecha del día de hoy
     * @return 
     */
    public static Date getToday(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    /**
     * obtiene la fecha del día mas proximo dependiendo de la entrada
     *
     * @param day puede ser Monday, Tuesday....Sunday
     * @return
     */
    public static Date getProxDay(String day) {
        //encuentro el numero de dia con respecto al nombre
        String days[] = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        int nDay = -1;
        for (int i = 0; i < days.length; i++) {
            if (days[i].contains(day)) {
                nDay = i + 1;
                break;
            }
        }
        //hago el desplazamiento, sumando hasta que el día coincida 
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i <= 7; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == nDay) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                break;
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        //seteo la hora a una hora 0
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    /**
     * obtiene una fecha con la hora, la fecha es la mínima del sistema
     *
     * @param hour
     * @param meridian
     * @return
     */
    public static Date getHour(String hour, String meridian) {
        String[] hourParts = hour.split(":");
        int hours = Integer.parseInt(hourParts[0]);
        int minutes = Integer.parseInt(hourParts[1]);
        if (meridian.equals("pm")) {
            hours = hours + 12;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.HOUR, hours);
        calendar.set(Calendar.MINUTE, minutes);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    /**
     * obtiene la hora si le doy una variable entera
     *
     * @param hour
     * @param meridian
     * @return
     */
    public static Date getHour(int hour, String meridian) {
        if (meridian.equals("pm")) {
            hour = hour + 12;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.HOUR, hour);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }
    /**
     * regresa la fecha con solo poner el dia
     * @param day
     * @return 
     */
    public static Date getDate(int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }
    /**
     * regresa la fecha con solo el día y el mes
     * @param day
     * @param month
     * @return 
     */
    public static Date getDate(int day, int month){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH,month-1);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }
    /**
     * regresa la fecha a partir del dia, mes y año
     * @param day
     * @param month
     * @return 
     */
    public static Date getDate(int day, int month, int year){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.YEAR,year);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }
    /**
     * Obtengo la hora final a partir de la duración que le doy en minutos
     * esta está definida en la clase ConfigDuration
     * @param date
     * @param duration
     * @return 
     */
    public static Date getHourWithDuration(Date date, int duration){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, duration);
        return calendar.getTime();
    }
    /**
     * se encarga de juntar la fecha y la hora en un solo objeto de fecha
     * @param date
     * @param hour
     * @return 
     */
    public static Date mergeTimes(Date date, Date hour){
        Calendar calendar=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();
        calendar.setTime(date);
        calendar2.setTime(hour);
        calendar.set(Calendar.HOUR, calendar2.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));
        calendar.add(Calendar.DAY_OF_MONTH, calendar2.get(Calendar.DAY_OF_YEAR)-1);
        return calendar.getTime();
    }
    

}
