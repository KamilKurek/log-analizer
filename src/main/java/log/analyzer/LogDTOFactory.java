package log.analyzer;

import java.util.Collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import log.analyzer.dto.SimpleLogDTO;
import log.analyzer.parser.AbstractLogParser;

public class LogDTOFactory {

    private Collection<AbstractLogParser> parsers;

    public LogDTOFactory(Collection<AbstractLogParser> parsers) {
        this.parsers = parsers;
    }

    public SimpleLogDTO parseLog(String logDataString) throws Exception {
        JSONObject logDataJson = JSON.parseObject(logDataString);
        AbstractLogParser parser = getParser(logDataJson);
        if (null == parser) {
            throw new Exception(String.format("Parser not found for json %s", logDataString));
        } else {
            return parser.parse(logDataJson);
        }
    }

    private AbstractLogParser getParser(JSONObject logJson) {
        for (AbstractLogParser parser : parsers) {
            if (parser.support(logJson)) {
                return parser;
            }
        }
        return null;
    }
}
