import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server IP address
        int port = 12345; // Server port

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server: " + serverAddress + ":" + port);

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Auto-flush

            Scanner scanner = new Scanner(System.in);
            String clientMessage;
            String serverMessage;

            System.out.println("Start chatting (type 'bye' to exit):");

            // Client sends messages and reads from server
            while (true) {
                // Send to server
                System.out.print("Client: ");
                clientMessage = scanner.nextLine();
                out.println(clientMessage);

                if (clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client disconnecting.");
                    break;
                }

                // Read from server
                if (in.ready()) { // Check if there's data to read
                    serverMessage = in.readLine();
                    if (serverMessage == null || serverMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    System.out.println("Server: " + serverMessage);
                }
            }

            scanner.close();
            System.out.println("Client shut down.");

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}