package log.analyzer.parser;

import com.alibaba.fastjson.JSONObject;

import log.analyzer.dto.SimpleLogDTO;

public abstract class AbstractLogParser {

    protected static final String ID_PARAM_NAME = "id";
    protected static final String STATE_PARAM_NAME = "state";
    protected static final String TIMESTAMP_PARAM_NAME = "timestamp";
    protected static final String TYPE_PARAM_NAME = "type";

    protected String getStringOrThrowException(JSONObject json, String fieldName) throws Exception {
        if (!json.containsKey(fieldName)) {
            throw new Exception(String.format("Json does not contain field %s", fieldName));
        } else {
            return json.getString(fieldName);
        }
    }

    protected Long getLongOrThrowException(JSONObject json, String fieldName) throws Exception {
        if (!json.containsKey(fieldName)) {
            throw new Exception(String.format("Json does not contain field %s", fieldName));
        } else {
            return json.getLong(fieldName);
        }
    }

    public abstract SimpleLogDTO parse(JSONObject json) throws Exception;

    public abstract boolean support(JSONObject json);
}
