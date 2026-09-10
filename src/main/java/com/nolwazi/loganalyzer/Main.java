package com.nolwazi.loganalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Entry point for the Log Analyzer.
 *
 * Plan:
 *   1. Read a log file (see resources/sample-auth.log)
 *   2. Parse each line into a LogEntry
 *   3. Run detection rules over the parsed entries
 *   4. Report anything suspicious
 *
 * Each step above becomes its own class, built out over the next few commits.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Log Analyzer");

        Path logFile = Path.of(args.length > 0 ? args[0] : "src/main/resources/sample-auth.log");
        LogParser parser = new LogParser();
        List<LogEntry> entries = parser.parse(logFile);

        DetectionEngine engine = new DetectionEngine();
        List<String> alerts = engine.detect(entries);

        System.out.println("Parsed " + entries.size() + " log entries.");
        System.out.println("Alerts found: " + alerts.size());
        alerts.forEach(System.out::println);
    }
}
