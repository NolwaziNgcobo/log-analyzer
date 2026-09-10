package com.nolwazi.loganalyzer;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runs detection rules over a list of parsed log entries and returns
 * any entries considered suspicious, along with a reason
 */

public class DetectionEngine {
    private static final int FAILED_LOGIN_THRESHOLD = 5;
    private static final Duration BRUTE_FORCE_WINDOW = Duration.ofMinutes(5);
    private static final LocalTime UNUSUAL_HOURS_START = LocalTime.of(0,0);
    private static final LocalTime UNUSUAL_HOURS_END = LocalTime.of(5,0);

    public List<String> detect(List<LogEntry> entries){
        List<String> alerts = new ArrayList<>();
        alerts.addAll(detectBruteForce(entries));
        alerts.addAll(detectUnusualHours(entries));

        return alerts;
    }
    private List<String> detectBruteForce(List<LogEntry> entries){
        List<String> alerts = new ArrayList<>();
        Map<String, List<LogEntry>> failedByIp = new HashMap<>();

        for (LogEntry entry : entries){
            if(entry.getEventType().equals("LOGIN_FAILED")){
                failedByIp
                        .computeIfAbsent(entry.getIpAddress(), ip -> new ArrayList<>()).add(entry);
            }
        }
        for (Map.Entry<String, List<LogEntry>> ipGroup : failedByIp.entrySet()){
            List<LogEntry> failures = ipGroup.getValue();
            if(failures.size() >= FAILED_LOGIN_THRESHOLD){
                Duration span = Duration.between(failures.get(0).getTimeStamp(),
                        failures.get(failures.size() - 1).getTimeStamp());

                if (span.compareTo(BRUTE_FORCE_WINDOW) <= 0){
                    alerts.add("BRUTE FORCE suspected from " + ipGroup.getKey()
                    + ": " + failures.size() + " failed login within " + span);
                }
            }
        }
        return alerts;
    }
    private List<String> detectUnusualHours(List<LogEntry> entries){
        List<String> alerts = new ArrayList<>();
        for (LogEntry entry : entries){
            LocalTime time =entry.getTimeStamp().toLocalTime();
            if (!time.isBefore(UNUSUAL_HOURS_START) && time.isBefore(UNUSUAL_HOURS_END)){
                alerts.add("UNUSUAL HOUR login: " + entry.getUser()
                + " from " + entry.getIpAddress() + " at " + entry.getTimeStamp());
            }
        }
        return alerts;
    }
}
