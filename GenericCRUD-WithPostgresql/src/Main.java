import java.sql.SQLException;

public class Main {
    public static <T> void main(String[] args) throws SQLException {

        DbFunction<T> db = new DbFunction<>();
        db.connect_to_db("generic-crud","postgres","0000");
        DbFunction<User> userRepository = new DbFunction<>();
        userRepository.save(new User("Alice","alice@gmail.com"));
        userRepository.save(new User("Bob","bob@gmail.com"));

        System.out.println(userRepository.findAll());
    }
}