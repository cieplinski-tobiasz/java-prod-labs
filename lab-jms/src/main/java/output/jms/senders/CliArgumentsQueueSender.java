package output.jms.senders;

import app.spring.conditions.QueuePresentCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.List;

@Component
@Conditional(QueuePresentCondition.class)
@Primary
@Slf4j
public final class CliArgumentsQueueSender implements QueueSender {
    private final String brokerUrl;
    private final String queueName;

    public CliArgumentsQueueSender(@Value("${broker}") String brokerUrl, @Value("${queue}") String queueName) {
        this.brokerUrl = brokerUrl;
        this.queueName = queueName;
    }

    public void sendMessagesToQueue(List<String> messages) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageProducer mp = session.createProducer(queue);

            for (String message : messages) {
                sendMessage(message, session, mp);
            }

            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("JMS message sending failed.", e);
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String content, Session session, MessageProducer producer) throws JMSException {
        Message message = session.createTextMessage(content);
        log.debug("Sending {}", message);
        producer.send(message);
    }
}
