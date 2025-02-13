import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDataApi {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private final Map<Integer, User> userMap = new HashMap<>();
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UserDataApi(HttpClient httpClient, ObjectMapper objectMapper){
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public void fetchUsers() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed to fetch users");
                return;
            }
            // parse using ObjectMapper
            JsonNode rootNode = objectMapper.readTree(response.body());
            for (JsonNode userNode : rootNode) {
                User user = new User();
                user.setId(userNode.get("id").asInt());
                user.setName(userNode.get("name").asText());
                user.setUserName(userNode.get("username").asText());
                user.setEmail(userNode.get("email").asText());
                user.setCity(userNode.get("address").get("city").asText());

                userMap.put(user.getId(), user);
            }
            System.out.println("Fetched " + userMap.size() + " users.");

        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to fetch users: " + e.getMessage());
        }
    }

    public List<User> getUsersByCity(String city) {
        List<User> usersInCity = new ArrayList<>();
        for (User user : userMap.values()) {
            if (user.getCity().equalsIgnoreCase(city)) {
                usersInCity.add(user);
            }
        }
        return usersInCity;
    }

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

}
