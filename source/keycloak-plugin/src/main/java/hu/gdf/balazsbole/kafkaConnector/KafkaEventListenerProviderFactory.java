package hu.gdf.balazsbole.kafkaConnector;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;


public class KafkaEventListenerProviderFactory implements EventListenerProviderFactory {


    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {

        return new KafkaEventListenerProvider();
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "kafka_user_log";
    }
}