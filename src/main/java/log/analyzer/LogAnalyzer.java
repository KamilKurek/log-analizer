package log.analyzer;

import java.util.logging.Logger;

public class LogAnalyzer {

    private static final Logger LOGGER = Logger.getLogger(LogAnalyzer.class.getCanonicalName());
    private static final String DEFAULT_LOG_FILE_PATH = "C:\\Work\\Projects\\log-analyzer\\example.log";

    public static void main(String[] args) throws Exception {
        String logsFilePath = getLogsFilePath(args);
        LOGGER.info("File path: " + logsFilePath);
        LogFileProcessor processor = new LogFileProcessor();
        processor.processLogFile(logsFilePath);
    }

    private static String getLogsFilePath(String[] args) throws Exception {
        if (args.length > 1) {
            throw new Exception("Application can have only one input parameter!");
        } else if (args.length == 1) {
            return args[0];
        } else {
            return DEFAULT_LOG_FILE_PATH;
        }
    }

}
