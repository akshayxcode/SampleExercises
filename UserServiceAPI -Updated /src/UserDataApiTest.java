import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class UserDataApiTest {

    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;
    private UserDataApi userDataApi;


    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();

        userDataApi = new UserDataApi(mockHttpClient, objectMapper);
    }

    @Test
    public void testFetchUsers_SuccessfulResponse() throws IOException, InterruptedException {
        // Sample JSON response from the API
        String jsonResponse = """
        [
            {
                "id": 1,
                "name": "Leanne Graham",
                "username": "Bret",
                "email": "Sincere@april.biz",
                "address": {
                    "city": "Gwenborough"
                }
            },
            {
                "id": 2,
                "name": "Ervin Howell",
                "username": "Antonette",
                "email": "Shanna@melissa.tv",
                "address": {
                    "city": "Wisokyburgh"
                }
            }
        ]
        """;

        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpResponse.statusCode()).thenReturn(200);

        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockHttpResponse);
        when(mockHttpResponse.statusCode()).thenReturn(200);


        userDataApi.fetchUsers();

        Map<Integer, User> userMap = userDataApi.getUserMap();
        assertEquals(2, userMap.size());
        assertEquals("Leanne Graham", userMap.get(1).getName());
        assertEquals("Ervin Howell", userMap.get(2).getName());


        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

}