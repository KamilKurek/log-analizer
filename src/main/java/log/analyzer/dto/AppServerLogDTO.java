package log.analyzer.dto;

public class AppServerLogDTO extends SimpleLogDTO {

    private String host;
    private String type;

    public AppServerLogDTO(String id, EventState eventState, Long timestamp, String host, String type) {
        super(id, eventState, timestamp);
        this.host = host;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public String getType() {
        return type;
    }
}
