package com.mahdy.codes.thenotesapp.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM,yyy");
            String currentDate = simpleDateFormat.format(new Date());
            return currentDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMonthsFromNumbers(String monthsNumbers) {
        switch (monthsNumbers) {
            case "01": {
                return "Jan";
            }
            case "02": {
                return "Feb";
            }
            case "03": {
                return "Mar";
            }
            case "04": {
                return "Apr";
            }
            case "05": {
                return "May";
            }
            case "06": {
                return "Jun";
            }
            case "07": {
                return "Jul";
            }
            case "08": {
                return "Aug";
            }
            case "09": {
                return "set";
            }
            case "10": {
                return "oct";
            }
            case "11": {
                return "Nov";
            }
            case "12": {
                return "Dec";
            }
            default: {return  "ERROR" ;
            }
        }
    }

}
