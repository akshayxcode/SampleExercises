
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class WeatherData {
    static String apiKey = System.getenv("API_KEY");
    static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    static final Map<String, Double> temperatureCache = new HashMap<>();
    private static HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setHttpClient(HttpClient mockClient) {
        client = mockClient;
    }
    public static void main(String[] args) {
        if (apiKey != null) {
            System.out.println("API Key : " + apiKey);
        } else {
            System.out.println("API Key is not set.");
        }
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.print("Enter city name (or type 'exit' to quit): ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("exit")) {
                System.out.println("Exiting WeatherApp...");
                break;
            }

            double temperature = getTemperature(city);
            if (temperature == Double.MIN_VALUE) {
                System.out.println("City not found.");
            } else {
                System.out.println("Temperature in " + city + " is " + temperature + "°C");
            }
        }

        scanner.close();
    }

    static double getTemperature(String city) {
        if (temperatureCache.containsKey(city.toLowerCase())) {
            System.out.println("(Cache hit) Returning stored temperature for " + city);
            return temperatureCache.get(city.toLowerCase());
        }

        try {
            String url = BASE_URL + city + "&appid=" + apiKey + "&units=metric";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return Double.MIN_VALUE;
            }


            JsonNode rootNode = objectMapper.readTree(response.body());
            String cityName = rootNode.get("name").asText();
            double temperature = rootNode.get("main").get("temp").asDouble();


            temperatureCache.put(cityName.toLowerCase(), temperature);

            return temperature;
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching data: " + e.getMessage());
            return Double.MIN_VALUE;
        }

    }


}
