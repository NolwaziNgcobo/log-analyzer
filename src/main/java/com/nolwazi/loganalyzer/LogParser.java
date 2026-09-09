package com.nolwazi.loganalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a Log file and coverts each valid line into LogEntry.
 */
public class LogParser {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<LogEntry> parse(Path logFilePath)throws IOException {
        List<LogEntry> entries = new ArrayList<>();
        List<String> lines = Files.readAllLines(logFilePath);

        for( String line : lines){
            LogEntry entry = parseLine(line);

            if (entry != null){
                entries.add(entry);

            }
        }
        return entries;
    }
    private LogEntry parseLine(String line){
        //expected format: 2026-09-09 08:14:22 192.168.2.15 LOGIN_FAILED user=admin
        String[] parts = line.split(" ", 5);
        if (parts.length < 5){
            return null;
        }
        LocalDateTime timeStamp = LocalDateTime.parse(parts[0] + " " + parts[1], TIMESTAMP_FORMAT);
                String ipAddress = parts[2];
                String eventType = parts[3];
                String user = parts[4].replace("user=","");

                return new  LogEntry(timeStamp, ipAddress, eventType, user);
    }
}
