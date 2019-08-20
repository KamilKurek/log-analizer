package log.analyzer.dto;

public class SimpleLogDTO {
    private String id;
    private EventState eventState;
    private Long timestamp;

    public SimpleLogDTO(String id, EventState eventState, Long timestamp) {
        this.id = id;
        this.eventState = eventState;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public EventState getEventState() {
        return eventState;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
