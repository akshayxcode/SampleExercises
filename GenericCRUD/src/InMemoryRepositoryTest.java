import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<User> userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryRepository<>();
    }

    @Test
    public void testSaveAndFindById() {
        User user = new User("John Doe", "john@example.com");
        userRepository.save(user);

        User retrievedUser = userRepository.findById(1);
        assertNotNull(retrievedUser);
        assertEquals("John Doe", retrievedUser.getName());
        assertEquals("john@example.com", retrievedUser.getEmail());
    }

    @Test
    public void testFindAll() {
        userRepository.save(new User("John Doe", "john@example.com"));
        userRepository.save(new User("Steve", "steve@example.com"));
        userRepository.save(new User("Adam", "adam@example.com"));
        List<User> users = userRepository.findAll();
        assertEquals(3, users.size());


    }

    @Test
    public void testDelete() {
        userRepository.save(new User("John Doe", "john@example.com"));
        assertNotNull(userRepository.findById(1));

        userRepository.delete(1);
        assertNull(userRepository.findById(1));
    }

    @Test
    void testUpdate() {
        User user = new User("David", "david@example.com");
        userRepository.save(user);

        User updatedUser = new User("David Updated", "david.new@example.com");
        userRepository.update(1, updatedUser);

        User retrievedUser = userRepository.findById(1);
        assertNotNull(retrievedUser);
        assertEquals("David Updated", retrievedUser.getName());
        assertEquals("david.new@example.com", retrievedUser.getEmail());
    }







}