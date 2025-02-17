import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryRepository<T> implements Repository<T> {
    private final Map<Integer, T> storage = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);


    @Override
    public void save(T entity) {
        int id = idCounter.getAndIncrement();
        storage.put(id, entity);
    }


    @Override
    public T findById(Integer id) {
        return storage.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(Integer id) {
        storage.remove(id);
        System.out.println("Deleted entity with ID: " + id);
    }

    @Override
    public T update(Integer id, T entity) {
        if (storage.containsKey(id)) {
            storage.put(id, entity);
            return entity;
        }
        return null;
    }

    @Override
    public List<T> filter(Predicate<T> condition) {
        return storage.values().stream().filter(condition).collect(Collectors.toList());
    }
}

