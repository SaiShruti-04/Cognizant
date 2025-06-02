import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListExample {
    public static void main(String[] args) {
        List<String> studentNames = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String name;

        System.out.println("Enter student names (type 'done' to finish):");
        while (true) {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break;
            }
            studentNames.add(name);
        }

        System.out.println("\nAll entered student names:");
        if (studentNames.isEmpty()) {
            System.out.println("No names entered.");
        } else {
            for (String studentName : studentNames) {
                System.out.println("- " + studentName);
            }
        }
        scanner.close();
    }
}