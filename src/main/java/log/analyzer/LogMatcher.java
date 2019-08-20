package log.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import log.analyzer.dto.SimpleLogDTO;

public class LogMatcher {
    private static final Logger LOGGER = Logger.getLogger(LogMatcher.class.getCanonicalName());

    private Map<String, SimpleLogDTO> logBuffer;

    public LogMatcher() {
        logBuffer = new HashMap<String, SimpleLogDTO>();
    }

    public SimpleLogDTO addOrReturnMatchedLog(SimpleLogDTO logDto) {
        if (!logBuffer.containsKey(logDto.getId())) {
            logBuffer.put(logDto.getId(), logDto);
            return null;
        } else {
            return readAndRemoveFromBuffer(logDto.getId());
        }
    }

    public void logNotMatchedLogs() {
        logBuffer.entrySet().forEach(entry -> LOGGER.severe("Match log for log id " + entry.getKey() + " not found"));
    }

    private SimpleLogDTO readAndRemoveFromBuffer(String key) {
        SimpleLogDTO logFromBuffer = logBuffer.get(key);
        logBuffer.remove(key);
        return logFromBuffer;
    }

}
