package output.jms.senders;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class NullQueueSender implements QueueSender {
    @Override
    public void sendMessagesToQueue(List<String> messages) {

    }
}
