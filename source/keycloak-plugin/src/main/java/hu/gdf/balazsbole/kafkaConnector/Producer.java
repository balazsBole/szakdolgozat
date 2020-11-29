package hu.gdf.balazsbole.kafkaConnector;

import hu.gdf.balazsbole.kafka.user.UserProtocolKey;
import hu.gdf.balazsbole.kafka.user.UserProtocolValue;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class Producer {


    private static final String BOOTSTRAP_SERVER = "kafka:9092";
    private static final String SCHEMA_REGISTRY_URL = "http://schema-registry:8081";
    private static final String TOPIC = "user.v1.pub";

    public static void publishEvent(UserProtocolValue value) {
        resetThreadContext();
        KafkaProducer<UserProtocolKey, UserProtocolValue> producer = new KafkaProducer<>(getProperties());

        UserProtocolKey userProtocolKey = new UserProtocolKey();
        userProtocolKey.setId(value.getId());
        ProducerRecord<UserProtocolKey, UserProtocolValue> eventRecord =
                new ProducerRecord<>(TOPIC, userProtocolKey, value);
        producer.send(eventRecord);
        producer.flush();
        producer.close();
    }

    private static void resetThreadContext() {
        Thread.currentThread().setContextClassLoader(null);
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty("schema.registry.url", SCHEMA_REGISTRY_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class.getName());
        return properties;
    }


}
