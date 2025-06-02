public class DecompileMe {
    private String secretMessage = "This is a secret!";

    public void printSecret() {
        System.out.println(secretMessage);
    }

    public int calculate(int x, int y) {
        int result = (x * y) + 10;
        return result;
    }

    public static void main(String[] args) {
        DecompileMe obj = new DecompileMe();
        obj.printSecret();
        System.out.println("Calculation result: " + obj.calculate(5, 3));
    }
}