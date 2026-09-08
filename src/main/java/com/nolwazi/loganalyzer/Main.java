package com.nolwazi.loganalyzer;

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

    public static void main(String[] args) {
        System.out.println("Log Analyzer - scaffold running.");
        // TODO: wire up LogParser -> DetectionEngine -> Report
    }
}
