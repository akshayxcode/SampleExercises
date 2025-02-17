public class User {
    private static int idCounter = 1;
    private final Integer id;
    private String name;
    private String email;

    public User(Integer id,String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        idCounter = Math.max(idCounter, id + 1);
    }
    public User(String name, String email) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

