import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWritingExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string to write to the file: ");
        String userInput = scanner.nextLine();

        String fileName = "output.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(userInput);
            System.out.println("Data successfully written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}