package input.parsers;

import org.springframework.stereotype.Component;

@Component
public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String value) {
        return Integer.valueOf(value);
    }
}
