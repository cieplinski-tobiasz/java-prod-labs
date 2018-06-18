package output.jms.senders;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class NullTopicSender implements TopicSender {
    @Override
    public void sendMessagesToTopic(List<String> messages) {

    }
}
