public class Main {
    public static void main(String[] args) {
        Repository<User> userRepository = new InMemoryRepository<>();
        Repository<Product> productRepository = new InMemoryRepository<>();

        userRepository.save(new User("Akshay","akss@gmail.com"));
        userRepository.save(new User("Steve","steve@gmail.com"));
        userRepository.save(new User("Charlie", "charlie@example.com"));
        userRepository.save(new User("David", "david@yahoo.com"));

        productRepository.save(new Product("Laptop", 1200.99));
        productRepository.save(new Product("Phone", 799.49));
        productRepository.save(new Product("Tablet", 299.99));
        productRepository.save(new Product("Monitor", 199.99));



        userRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);
        System.out.println("\nFind User with ID 2: " + userRepository.findById(2));


        productRepository.delete(2);
        System.out.println("\nAfter deleting Product with ID 2:");
        productRepository.findAll().forEach(System.out::println);

        System.out.println("-- Updating --");
        userRepository.update(3, new User(3,"riya","riya@inline.com"));
        userRepository.findAll().forEach(System.out::println);


        System.out.println("-- Filtering --");
        userRepository.filter(user -> user.getEmail().endsWith("@gmail.com")).forEach(System.out::println);
        productRepository.filter(product -> product.getPrice() < 500).forEach(System.out::println);
    }
}