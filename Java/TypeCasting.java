public class TypeCasting {
    public static void main(String[] args) {
        double d = 9.78;
        int i = (int) d; // narrowing
        System.out.println("Double to int: " + i);

        int j = 10;
        double d2 = j; // widening
        System.out.println("Int to double: " + d2);
    }
}
