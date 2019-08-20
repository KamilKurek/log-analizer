package log.analyzer.parser;

import com.alibaba.fastjson.JSONObject;

import log.analyzer.dto.AppServerLogDTO;
import log.analyzer.dto.EventState;
import log.analyzer.dto.SimpleLogDTO;

public class AppServerLogParser extends AbstractLogParser {

    private static final String HOST_PARAM_NAME = "host";

    @Override
    public SimpleLogDTO parse(JSONObject json) throws Exception {
        return new AppServerLogDTO(getStringOrThrowException(json, ID_PARAM_NAME),
                EventState.valueOf(getStringOrThrowException(json, STATE_PARAM_NAME)),
                getLongOrThrowException(json, TIMESTAMP_PARAM_NAME), getStringOrThrowException(json, HOST_PARAM_NAME),
                getStringOrThrowException(json, TYPE_PARAM_NAME));
    }

    @Override
    public boolean support(JSONObject json) {
        return json.containsKey(TYPE_PARAM_NAME) && json.containsKey(HOST_PARAM_NAME);
    }

}
