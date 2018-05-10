package generators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class NumberGenerator {
    @NonNull
    private final ThreadLocalRandom random;

    public int nextInt(int toInclusive) {
        return random.nextInt(toInclusive + 1);
    }

    public int nextInt(int fromInclusive, int toInclusive) {
        return random.nextInt(fromInclusive, toInclusive + 1);
    }

    public long nextLong(long fromInclusive, long toInclusive) {
        return random.nextLong(fromInclusive, toInclusive + 1);
    }
}
