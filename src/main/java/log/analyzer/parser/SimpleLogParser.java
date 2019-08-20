package log.analyzer.parser;

import com.alibaba.fastjson.JSONObject;

import log.analyzer.dto.EventState;
import log.analyzer.dto.SimpleLogDTO;

public class SimpleLogParser extends AbstractLogParser {

    @Override
    public SimpleLogDTO parse(JSONObject json) throws Exception {
        return new SimpleLogDTO(getStringOrThrowException(json, ID_PARAM_NAME),
                EventState.valueOf(getStringOrThrowException(json, STATE_PARAM_NAME)),
                getLongOrThrowException(json, TIMESTAMP_PARAM_NAME));
    }

    @Override
    public boolean support(JSONObject json) {
        return !json.containsKey(TYPE_PARAM_NAME);
    }

}
