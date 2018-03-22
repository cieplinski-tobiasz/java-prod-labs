package utils.ranges;

public interface Range<T> {
    T getMinimum();
    T getMaximum();
    boolean in(T e);
}
