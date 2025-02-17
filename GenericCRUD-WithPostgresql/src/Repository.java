import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    void save(T entity);  // Now returns the saved entity with an auto-generated ID
    T findById(Integer id);
    List<T> findAll();
    void delete(Integer id);
    void update(Integer id, T entity);
    List<T> filter(Predicate<T> condition);
}

