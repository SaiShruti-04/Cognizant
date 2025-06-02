import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

public class ReflectionExample {

    public void sayHello() {
        System.out.println("Hello from sayHello() method!");
    }

    private String getPrivateMessage(String user) {
        return "Shh! This is a secret message for " + user + ".";
    }

    public int addNumbers(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        String className = "ReflectionExample"; // The class to load dynamically

        try {
            // 1. Load the class dynamically
            Class<?> myClass = Class.forName(className);
            System.out.println("Class loaded: " + myClass.getName());

            // 2. Create an instance of the class
            Object instance = myClass.getDeclaredConstructor().newInstance();
            System.out.println("Instance created: " + instance.getClass().getName());

            System.out.println("\n--- Public Method Invocation ---");
            // 3. Get a public method by name and invoke it
            Method sayHelloMethod = myClass.getMethod("sayHello");
            System.out.println("Invoking method: " + sayHelloMethod.getName());
            sayHelloMethod.invoke(instance); // No arguments

            System.out.println("\n--- Method with Parameters and Return Value ---");
            // 4. Get a method with parameters and invoke it
            Method addNumbersMethod = myClass.getMethod("addNumbers", int.class, int.class);
            System.out.println("Invoking method: " + addNumbersMethod.getName() + " with parameters: " + addNumbersMethod.getParameterTypes()[0].getName() + ", " + addNumbersMethod.getParameterTypes()[1].getName());
            Object result = addNumbersMethod.invoke(instance, 10, 20); // Pass arguments
            System.out.println("Result of addNumbers: " + result);

            System.out.println("\n--- Accessing Private Method ---");
            // 5. Get a private method and make it accessible
            Method getPrivateMessageMethod = myClass.getDeclaredMethod("getPrivateMessage", String.class);
            getPrivateMessageMethod.setAccessible(true); // Allow access to private method
            System.out.println("Invoking private method: " + getPrivateMessageMethod.getName());
            Object privateResult = getPrivateMessageMethod.invoke(instance, "Alice");
            System.out.println("Result of getPrivateMessage: " + privateResult);


            System.out.println("\n--- Listing All Methods ---");
            // 6. Get all declared methods of the class
            Method[] methods = myClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("Method Name: " + method.getName());
                System.out.println("  Return Type: " + method.getReturnType().getName());
                System.out.print("  Parameters: ");
                if (method.getParameterCount() == 0) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < method.getParameterTypes().length; i++) {
                        System.out.print(method.getParameterTypes()[i].getName());
                        if (i < method.getParameterTypes().length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("  Modifiers: " + Modifier.toString(method.getModifiers()));
                System.out.println("---");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found - " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Error: Method not found - " + e.getMessage());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Error during instantiation or invocation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}