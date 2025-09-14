//package jhkim593.springboard.common.outbox.event;
//
//import jhkim593.springboard.common.event.Event;
//import jhkim593.springboard.common.event.payload.EventPayload;
//import jhkim593.springboard.common.event.EventType;
//import jhkim593.springboard.common.outbox.Outbox;
//import jhkim593.springboard.common.snowflake.Snowflake;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class OutboxEventPublisherImpl implements EventPublisher{
//    private final Snowflake outboxIdSnowflake = new Snowflake();
//    private final Snowflake eventIdSnowflake = new Snowflake();
//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    @Override
//    public void publish(EventType type, EventPayload payload) {
//        Outbox outbox = Outbox.create(
//                outboxIdSnowflake.getId(),
//                type,
//                Event.of(
//                        eventIdSnowflake.getId(), type, payload
//                ).toJson()
////                shardKey % MessageRelayConstants.SHARD_COUNT
//        );
//        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
//    }
//}
