# Log Analyzer

**Verification code:** WTC-WH4K3YEZ

A Java command-line tool that parses authentication logs and flags suspicious
activity — repeated failed logins from a single IP, and logins at unusual hours.

Built as part of my WeThinkCode_ Cybersecurity elective proof-of-work.

## Why this project

After working through Cisco's Introduction to Cybersecurity and TryHackMe's
security fundamentals, I wanted to build something that applies those concepts
directly rather than just completing quizzes about them. Log analysis is a
core blue-team skill — this is a small, from-scratch version of what a SIEM
tool does under the hood.

## How it works

1. **Parse** — reads a log file line by line, extracting timestamp, IP address,
   event type, and user from each entry.
2. **Detect** — runs a set of rules over the parsed entries:
   - Flags 5+ failed logins from the same IP within a short time window (brute-force pattern)
   - Flags logins occurring outside normal hours
3. **Report** — prints a summary of flagged events to the console.

## Project status

This is a work in progress, built incrementally:

- [x] Project scaffold
- [ ] Log parser (`LogParser`, `LogEntry`)
- [ ] Detection engine (`DetectionEngine`)
- [ ] Reporting output
- [ ] Unit tests (JUnit 5)
- [ ] Demo video

## Running it

```bash
mvn clean package
java -jar target/log-analyzer.jar src/main/resources/sample-auth.log
```

## Demo video

_Link will go here once the tool is complete._

## Tech

Java 17, Maven, JUnit 5.
