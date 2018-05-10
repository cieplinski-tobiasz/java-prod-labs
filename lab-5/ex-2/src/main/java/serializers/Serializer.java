package serializers;

public interface Serializer<T> {
    String serialize(T obj);
}
