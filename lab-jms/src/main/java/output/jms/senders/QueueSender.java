package output.jms.senders;

import java.util.List;

public interface QueueSender {
    void sendMessagesToQueue(List<String> messages);
}
