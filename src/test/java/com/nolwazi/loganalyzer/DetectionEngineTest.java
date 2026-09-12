package com.nolwazi.loganalyzer;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetectionEngineTest {
    @Test
    void detectsBrutalForceFromSameIp(){
        List<LogEntry> entries = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(2026, 9, 7, 8, 0, 0);
        for (int i = 0; i < 5; i++){
            entries.add(new LogEntry(start.plusSeconds(i * 10), "192.168.1.15",
                    "LOGIN_FAILED", "admin"));
        }
        DetectionEngine engine = new DetectionEngine();
        List<String> alerts = engine.detect(entries);

        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).contains("BRUTE FORCE"));
    }
    @Test
    void doesNotFlagNormalLoginActivity(){
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalDateTime.of(2026, 9, 7, 9, 0, 0),
                "192.168.1.15", "LOGIN_SUCCESS", "jsmith"));

        DetectionEngine engine = new DetectionEngine();
        List<String> alerts = engine.detect(entries);

        assertEquals(0, alerts.size());
    }
    @Test
    void detectsUnusualHourLogin(){
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalDateTime.of(2026, 9, 7, 3, 47, 0),
                "41.13.22.9", "LOGIN_SUCCESS", "root"));

        DetectionEngine engine = new DetectionEngine();
        List<String> alerts = engine.detect(entries);

        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).contains("UNUSUAL HOUR"));
    }
}
