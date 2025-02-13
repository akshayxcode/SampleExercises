import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherDataTest {
    @Mock
    private HttpClient mockClient;
    @Mock
    private HttpResponse<String> mockResponse;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        WeatherData.setHttpClient(mockClient);
    }
    @Test
    public void testGetTemperature_CityNotFound() throws IOException, InterruptedException {
        String city = "Invalid";
        when(mockClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(404);
        double result = WeatherData.getTemperature(city);
        assertEquals(Double.MIN_VALUE, result,  0.0);
    }

    @Test
    public void testGetTemperature_SuccessResponse() throws IOException, InterruptedException {
        String city = "London";
        double expectedTemperature = 3.58;
        when(mockClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"name\": \"London\", \"main\": {\"temp\": 3.58}}");
        double result = WeatherData.getTemperature(city);
        assertEquals(expectedTemperature,result,0.0);
    }


}