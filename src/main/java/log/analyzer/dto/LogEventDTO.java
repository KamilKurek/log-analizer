package log.analyzer.dto;

public class LogEventDTO {

    private String id;
    private Long duration;
    private String host;
    private String type;

    public LogEventDTO(SimpleLogDTO first, SimpleLogDTO second) {
        this.id = first.getId();
        this.duration = Math.abs(second.getTimestamp() - first.getTimestamp());
        if (first instanceof AppServerLogDTO) {
            AppServerLogDTO appServerLog = (AppServerLogDTO) first;
            this.host = appServerLog.getHost();
            this.type = appServerLog.getType();
        }
    }

    public String getId() {
        return id;
    }

    public Long getDuration() {
        return duration;
    }

    public String getHost() {
        return host;
    }

    public String getType() {
        return type;
    }

    public boolean alert() {
        return duration > 4l;
    }
}
