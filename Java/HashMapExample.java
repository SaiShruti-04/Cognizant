import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HashMapExample {
    public static void main(String[] args) {
        Map<Integer, String> studentMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int studentId;
        String studentName;

        System.out.println("Enter student IDs and names (Enter ID 0 to finish adding):");
        while (true) {
            System.out.print("Enter student ID: ");
            studentId = scanner.nextInt();
            if (studentId == 0) {
                break;
            }
            scanner.nextLine(); // Consume newline
            System.out.print("Enter student name for ID " + studentId + ": ");
            studentName = scanner.nextLine();
            studentMap.put(studentId, studentName);
        }

        System.out.println("\nRetrieving student names:");
        System.out.print("Enter a student ID to retrieve their name: ");
        int searchId = scanner.nextInt();

        if (studentMap.containsKey(searchId)) {
            System.out.println("Student ID: " + searchId + ", Name: " + studentMap.get(searchId));
        } else {
            System.out.println("Student with ID " + searchId + " not found.");
        }
        scanner.close();
    }
}