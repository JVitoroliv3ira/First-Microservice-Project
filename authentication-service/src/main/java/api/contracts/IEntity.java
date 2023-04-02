package api.contracts;

public interface IEntity<T> {
    T getId();

    void setId(T id);
}
