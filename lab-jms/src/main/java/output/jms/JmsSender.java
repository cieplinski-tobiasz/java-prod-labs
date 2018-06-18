package output.jms;

import model.Transaction;
import org.springframework.stereotype.Component;
import output.jms.senders.QueueSender;
import output.jms.senders.TopicSender;
import serializers.Serializer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class JmsSender {
    private final QueueSender queueSender;
    private final TopicSender topicSender;
    private final Serializer<Transaction> transactionSerializer;

    public JmsSender(QueueSender queueSender, TopicSender topicSender, Serializer<Transaction> transactionSerializer) {
        this.queueSender = queueSender;
        this.topicSender = topicSender;
        this.transactionSerializer = transactionSerializer;
    }

    public void sendTransactions(List<Transaction> transactions) {
        List<String> messages = transactions.stream()
                .map(transactionSerializer::serialize)
                .collect(Collectors.toList());

        queueSender.sendMessagesToQueue(messages);
        topicSender.sendMessagesToTopic(messages);
    }
}
