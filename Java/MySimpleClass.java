public class MySimpleClass {
    public int add(int a, int b) {
        return a + b;
    }

    public String greet(String name) {
        String message = "Hello, " + name + "!";
        return message;
    }

    public static void main(String[] args) {
        MySimpleClass obj = new MySimpleClass();
        int sum = obj.add(5, 7);
        System.out.println("Sum: " + sum);
        obj.greet("World");
    }
}