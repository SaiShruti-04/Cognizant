public class ThreadCreationExample {
    public static void main(String[] args) {
        // Using Thread class
        MyThreadClass thread1 = new MyThreadClass("Thread-1");
        thread1.start();

        // Using Runnable interface
        Thread thread2 = new Thread(new MyRunnableThread("Thread-2"));
        thread2.start();

        System.out.println("Main thread finished.");
    }
}