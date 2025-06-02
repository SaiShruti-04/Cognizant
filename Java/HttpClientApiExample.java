import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

// Optional: For JSON parsing (add Jackson or Gson to classpath)
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientApiExample {

    private static final String API_URL = "https://api.github.com/users/octocat"; // A public GitHub API endpoint

    public static void main(String[] args) {
        // Create an HttpClient instance
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // Use HTTP/2 where available
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10)) // Connection timeout
                .build();

        // Build an HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET() // Or .POST(HttpRequest.BodyPublishers.ofString("body")) for POST
                .header("Accept", "application/json") // Request JSON response
                .build();

        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print response details
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Headers: " + response.headers().map());
            System.out.println("\nResponse Body:\n" + response.body());

            // Optional: Parse JSON response (Requires Jackson/Gson)
            // parseJson(response.body());

        } catch (IOException | InterruptedException e) {
            System.err.println("HTTP request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Optional: Method to parse JSON response using Jackson
    /*
    private static void parseJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonString);
            System.out.println("\n--- Parsed JSON ---");
            System.out.println("Login: " + rootNode.get("login").asText());
            System.out.println("ID: " + rootNode.get("id").asInt());
            System.out.println("Public Repos: " + rootNode.get("public_repos").asInt());
            System.out.println("Followers: " + rootNode.get("followers").asInt());
        } catch (IOException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }
    }
    */
}