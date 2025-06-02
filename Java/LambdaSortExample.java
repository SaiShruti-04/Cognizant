import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaSortExample {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");
        names.add("Eve");

        System.out.println("Original List: " + names);

        // Sort the list using a lambda expression
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
        // Or for reverse order: Collections.sort(names, (s1, s2) -> s2.compareTo(s1));

        System.out.println("Sorted List (Ascending): " + names);

        // Example with a custom sorting logic (e.g., sort by length)
        Collections.sort(names, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.println("Sorted List (by Length): " + names);
    }
}