import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class UserApiCall {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) {
        UserDataApi userData = new UserDataApi(client,objectMapper);
        userData.fetchUsers();

        var userMap = userData.getUserMap();
        System.out.println(userMap);

        var userByCity = userData.getUsersByCity("McKenziehaven");
        System.out.println(userByCity);

    }
}
