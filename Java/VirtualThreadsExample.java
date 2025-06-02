import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

public class VirtualThreadsExample {

    private static final int NUM_THREADS = 100_000; // A large number to demonstrate scalability

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Running with Virtual Threads ---");
        runWithVirtualThreads();

        // Introduce a small delay to ensure virtual thread output finishes before platform thread
        Thread.sleep(100);

        System.out.println("\n--- Running with Traditional (Platform) Threads ---");
        // Warning: Running 100,000 platform threads might crash your system or take a very long time.
        // Reduce NUM_THREADS significantly for platform threads (e.g., to 1000 or 5000) for comparison.
        // runWithPlatformThreads(); // Uncomment with caution and reduced NUM_THREADS
    }

    private static void runWithVirtualThreads() throws InterruptedException {
        Instant start = Instant.now();

        // Using ExecutorService with newVirtualThreadPerTaskExecutor()
        // This creates a new virtual thread for each submitted task
        ExecutorService executor = newVirtualThreadPerTaskExecutor();
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < NUM_THREADS; i++) {
                final int threadId = i;
                futures.add(executor.submit(() -> {
                    // System.out.println("Virtual Thread " + threadId + " says hello!"); // Too much output
                    try {
                        Thread.sleep(1); // Simulate some short work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
            }

            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    System.err.println("Error in virtual thread task: " + e.getMessage());
                }
            }
        } finally {
            executor.shutdown();
        }

        Instant end = Instant.now();
        System.out.println("Virtual threads finished in: " + Duration.between(start, end).toMillis() + " ms");
    }

    private static void runWithPlatformThreads() throws InterruptedException {
        // WARNING: This can be resource-intensive and potentially crash your system
        // for a large number of threads. Reduce NUM_THREADS before uncommenting.
        System.out.println("Caution: Running a large number of platform threads can be resource-intensive.");
        System.out.println("Consider reducing NUM_THREADS for platform thread test.");

        Instant start = Instant.now();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < NUM_THREADS; i++) {
                final int threadId = i;
                futures.add(executor.submit(() -> {
                    // System.out.println("Platform Thread " + threadId + " says hello!"); // Too much output
                    try {
                        Thread.sleep(1); // Simulate some short work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    System.err.println("Error in platform thread task: " + e.getMessage());
                }
            }
        } finally {
            executor.shutdown();
        }

        Instant end = Instant.now();
        System.out.println("Platform threads finished in: " + Duration.between(start, end).toMillis() + " ms");
    }
}