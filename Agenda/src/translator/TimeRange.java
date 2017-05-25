/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.util.Date;

/**
 *
 * @author Humanoide
 */
public class TimeRange {
    
    Date time1;
    Date time2;

    public TimeRange(Date time1, Date time2) {
        this.time1 = time1;
        this.time2 = time2;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }
    
}
