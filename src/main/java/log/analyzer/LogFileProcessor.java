package log.analyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import log.analyzer.dto.LogEventDTO;
import log.analyzer.dto.SimpleLogDTO;
import log.analyzer.parser.AppServerLogParser;
import log.analyzer.parser.SimpleLogParser;
import log.analyzer.service.DatabaseService;

public class LogFileProcessor {

    private static final Logger LOGGER = Logger.getLogger(LogFileProcessor.class.getCanonicalName());

    private LogDTOFactory logDTOFactory;
    private LogMatcher logMatcher;
    private DatabaseService databaseService;

    public LogFileProcessor() throws SQLException {
        this.logDTOFactory = new LogDTOFactory(Arrays.asList(new SimpleLogParser(), new AppServerLogParser()));
        this.logMatcher = new LogMatcher();
        this.databaseService = new DatabaseService();
    }

    public void processLogFile(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String logLine;
            while ((logLine = reader.readLine()) != null) {
                processLogLine(logLine);
            }
            logMatcher.logNotMatchedLogs();
        }
    }

    private void processLogLine(String logLine) {
        SimpleLogDTO parsedLog = parseLogLine(logLine);
        if (null != parsedLog) {
            SimpleLogDTO matchedLog = logMatcher.addOrReturnMatchedLog(parsedLog);
            if (null != matchedLog) {
                handleLogPairFound(parsedLog, matchedLog);
            }
        }
    }

    private SimpleLogDTO parseLogLine(String logLine) {
        try {
            return logDTOFactory.parseLog(logLine);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while parsing line " + logLine, e);
            return null;
        }
    }

    private void handleLogPairFound(SimpleLogDTO first, SimpleLogDTO second) {
        try {
            LogEventDTO logEvent = new LogEventDTO(first, second);
            if (logEvent.alert()) {
                LOGGER.severe(
                        String.format("Event %s took long time (%dms)", logEvent.getId(), logEvent.getDuration()));
            }
            databaseService.addLog(logEvent);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while storing the log event with id " + first.getId(), e);
        }
    }

}
