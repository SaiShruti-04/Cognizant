// Requires Java 21 or later
public class PatternMatchingSwitch {

    public static void processObject(Object obj) {
        String message;
        if (obj == null) {
            message = "It's a null object.";
        } else if (obj instanceof Integer) {
            message = "It's an Integer with value: " + obj;
        } else if (obj instanceof String) {
            message = "It's a String with length: " + ((String) obj).length();
        } else if (obj instanceof Double) {
            message = "It's a Double with value: " + obj;
        } else {
            message = "It's an object of unknown type: " + obj.getClass().getName();
        }
        System.out.println(message);
    }

    public static void main(String[] args) {
        processObject(10);
        processObject("Hello Java 21");
        processObject(3.14);
        processObject(new StringBuilder("Some text"));
        processObject(true);
        processObject(null);
    }
}