package com.dtolabs.rundeck.plugin.notificationplugin;


/**
 * Date information to be included in the notification
 */
public class DateStarted {

    private String date;
    private String unixtime;

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

}
