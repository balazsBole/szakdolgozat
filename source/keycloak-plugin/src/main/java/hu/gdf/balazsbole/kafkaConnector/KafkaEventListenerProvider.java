package hu.gdf.balazsbole.kafkaConnector;

import hu.gdf.balazsbole.kafka.user.UserProtocolValue;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;


public class KafkaEventListenerProvider implements EventListenerProvider {

    @Override
    public void onEvent(Event event) {
        if (EventType.REGISTER.equals(event.getType()) || EventType.UPDATE_EMAIL.equals(event.getType())) {
            UserProtocolValue value = generateProtocolValue(event);
            Producer.publishEvent(value);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    @Override
    public void close() {
    }

    private UserProtocolValue generateProtocolValue(Event event) {
        UserProtocolValue value = new UserProtocolValue();
        value.setId(event.getUserId());
        value.setUsername(event.getDetails().get("username"));

        switch (event.getType()) {
            case REGISTER:
                value.setEmail(event.getDetails().get("email"));
                break;
            case UPDATE_EMAIL:
                value.setEmail(event.getDetails().get("updated_email"));
                break;
        }
        return value;
    }

}