// Requires Java 16 or later
public record Person(String name, int age) {
    // Records automatically generate constructor, getters, equals(), hashCode(), and toString()
    // You can add custom methods if needed
    public boolean isAdult() {
        return age >= 18;
    }
}