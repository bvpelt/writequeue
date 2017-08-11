package nl.bsoft.writequeue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jms.ConnectionFactory;

@SpringBootApplication
public class WritequeueApplication {

    //private static final String JMS_BROKER_URL = "vm://embedded?broker.persistent=false,useShutdownHook=false";
    private static final String JMS_BROKER_URL = "tcp://localhost:61616";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(JMS_BROKER_URL);
    }

    public static void main(String[] args) {
        SpringApplication.run(WritequeueApplication.class, args);
    }
}
