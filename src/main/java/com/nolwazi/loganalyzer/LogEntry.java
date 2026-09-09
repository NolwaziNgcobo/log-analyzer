package com.nolwazi.loganalyzer;

import java.time.LocalDateTime;

/**
 * Represents a single parsed line from the auth log.
 * Immutable on purpose - once we've parsed a line, that fact shouldn't  change.
 */
public class LogEntry {
    private final LocalDateTime timestamp;
    private final String ipAddress;
    private final String eventType;
    private final String user;

    public LogEntry(LocalDateTime timeStamp, String ipAddress, String eventType, String user){
        this.timestamp = timeStamp;
        this.ipAddress = ipAddress;
        this.eventType = eventType;
        this.user = user;
    }
    public LocalDateTime getTimeStamp(){return timestamp;}
    public String getIpAddress(){return ipAddress;}
    public String getEventType(){return eventType;}
    public String getUser(){return user;}

    @Override
    public String toString(){
        return timestamp + " " + ipAddress + " " + eventType + " user=" + user;
    }

}
