package input.parsers;

public interface Parser<T> {
    T parse(String value);
}
