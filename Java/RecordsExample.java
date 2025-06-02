import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Requires Java 16 or later
public class RecordsExample {
    public static void main(String[] args) {
        // Create instances of the Person record
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        Person person3 = new Person("Charlie", 17);
        Person person4 = new Person("David", 40);

        // Print record instances (uses auto-generated toString())
        System.out.println("Person 1: " + person1);
        System.out.println("Person 2: " + person2);

        // Access components using accessor methods (auto-generated)
        System.out.println("Person 1 Name: " + person1.name());
        System.out.println("Person 1 Age: " + person1.age());

        // Use custom method
        System.out.println("Is Person 1 adult? " + person1.isAdult());
        System.out.println("Is Person 3 adult? " + person3.isAdult());

        // Use records in a List and filter using Streams
        List<Person> people = Arrays.asList(person1, person2, person3, person4);

        System.out.println("\nAll people: " + people);

        // Filter people older than 20
        List<Person> olderPeople = people.stream()
                                         .filter(p -> p.age() > 20)
                                         .collect(Collectors.toList());
        System.out.println("People older than 20: " + olderPeople);

        // Filter adults
        List<Person> adults = people.stream()
                                    .filter(Person::isAdult)
                                    .collect(Collectors.toList());
        System.out.println("Adults: " + adults);
    }
}