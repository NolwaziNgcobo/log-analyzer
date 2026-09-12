package com.nolwazi.loganalyzer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogParserTest {
    @Test
    void parsesValidLogLineCorrectly() throws IOException {
        Path tempFile = Files.createTempFile("test-log",".log");
        Files.writeString(tempFile,"2026-09-07 08:12:03 192.168.1.10 LOGIN_SUCCESS user=jsmith");

        LogParser parser = new LogParser();
        List<LogEntry> entries = parser.parse(tempFile);

        assertEquals(1, entries.size());
        LogEntry entry = entries.get(0);
        assertEquals(LocalDateTime.of(2026, 9, 7, 8, 12, 3), entry.getTimeStamp());
        assertEquals("192.168.1.10",entry.getIpAddress());
        assertEquals("LOGIN_SUCCESS", entry.getEventType());
        assertEquals("jsmith", entry.getUser());
    }
    @Test
    void skipsMalformedLines() throws IOException {
        Path tempFile = Files.createTempFile("test-log", ".log");
        Files.writeString(tempFile, "this is not a valid log line\n2026-09-07 08:12:03 192.168.1.10 LOGIN_SUCCESS user=jsmith");

        LogParser parser = new LogParser();
        List<LogEntry> entries = parser.parse(tempFile);

        assertEquals(1, entries.size());

    }
}
