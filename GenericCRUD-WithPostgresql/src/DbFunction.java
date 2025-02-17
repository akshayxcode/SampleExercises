import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DbFunction<T> implements Repository<T> {
    private static Connection connection;
    //private String tableName;

//    public DbFunction(Class<T> entityClass) {
//        this.tableName = getTableName(entityClass);
//    }

    public void connect_to_db(String dbname, String user, String password ) {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,password);
            if (connection != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getTableName(Class<T> entityClass) {
        if (entityClass == User.class) {
            return "users";
        } else if (entityClass == Product.class) {
            return "products";
        }
        throw new IllegalArgumentException("Unsupported entity type: " + entityClass.getSimpleName());
    }

    @Override
    public void save(T entity) {
        if (entity instanceof User user) {
            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (entity instanceof Product product) {
            String sql = "INSERT INTO products (name,price) VALUES (?, ?)";
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setDouble(2, product.getPrice());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public T findById(Integer id) {
        if (id < 0) return null;
        if (User.class.isAssignableFrom(getClass())) {
            String sql = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return (T) new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> results = new ArrayList<>();
//        if (User.class.isAssignableFrom(getClass())) {
            String sql = "SELECT * FROM users";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    results.add((T) new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //}
        return results;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Integer id, T entity) {
        if (entity instanceof User user) {
            String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setInt(3, user.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<T> filter(Predicate<T> condition) {
        return List.of();
    }
}
