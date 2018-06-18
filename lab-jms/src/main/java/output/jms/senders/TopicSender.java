package output.jms.senders;

import java.util.List;

public interface TopicSender {
    void sendMessagesToTopic(List<String> messages);
}
