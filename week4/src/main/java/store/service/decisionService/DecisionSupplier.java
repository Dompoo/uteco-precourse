package store.service.decisionService;

@FunctionalInterface
public interface DecisionSupplier<T> {
    T get(String name, Integer count);
}
