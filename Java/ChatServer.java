import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        int port = 12345; // Port to listen on

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Auto-flush

            Scanner scanner = new Scanner(System.in);
            String clientMessage;
            String serverMessage;

            System.out.println("Start chatting (type 'bye' to exit):");

            // Server reads from client and sends messages
            while (true) {
                // Read from client
                if (in.ready()) { // Check if there's data to read
                    clientMessage = in.readLine();
                    if (clientMessage == null || clientMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                    System.out.println("Client: " + clientMessage);
                }

                // Send to client
                System.out.print("Server: ");
                serverMessage = scanner.nextLine();
                out.println(serverMessage);

                if (serverMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Server disconnecting.");
                    break;
                }
            }

            scanner.close();
            clientSocket.close();
            System.out.println("Server shut down.");

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}