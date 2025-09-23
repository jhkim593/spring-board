package jhkim593.springboard.common.core.common.event.model;

import jhkim593.springboard.common.core.common.event.util.DataSerializer;
import jhkim593.springboard.common.core.common.event.payload.EventPayload;
import lombok.Getter;

@Getter
public class EventData<T extends EventPayload> {
    private Long id;
    private Long aggregateId;
    private EventType type;
    private T payload;

    public EventData(Long id, Long aggregateId, EventType type, T payload) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
    }

    public static EventData<EventPayload> create(Long eventId, Long aggregateId, EventType type, EventPayload payload) {
        return new EventData(eventId,aggregateId, type, payload);
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static EventData<EventPayload> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) {
            return null;
        }

        EventType eventType = EventType.from(eventRaw.getType());
        EventPayload payload = DataSerializer.deserialize(
            DataSerializer.serialize(eventRaw.getPayload()),
            eventType.getPayloadClass()
        );

        return new EventData<>(eventRaw.getId(), eventRaw.getAggregateId(), eventType, payload);
    }

    @Getter
    private static class EventRaw {
        private Long id;
        private Long aggregateId;
        private String type;
        private Object payload;
    }
}
