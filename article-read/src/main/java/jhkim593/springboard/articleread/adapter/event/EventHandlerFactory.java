package jhkim593.springboard.articleread.adapter.event;

import jhkim593.springboard.common.event.model.EventType;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventHandlerFactory {
    private final Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    public EventHandlerFactory(List<EventHandler> eventHandlers) {
        eventHandlers.forEach(e -> eventHandlerMap.put(e.getType(), e));
    }
    public EventHandler get(String type) {
        return eventHandlerMap.get(type);
    }
}
