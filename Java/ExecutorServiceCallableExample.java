import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceCallableExample {
    public static void main(String[] args) {
        // Create an ExecutorService with a fixed thread pool size
        // This pool will reuse a fixed number of threads
        int corePoolSize = 3; // Number of threads in the pool
        ExecutorService executor = Executors.newFixedThreadPool(corePoolSize);

        List<CallableTask> tasks = new ArrayList<>();
        tasks.add(new CallableTask("Task-A", 2000)); // 2 seconds
        tasks.add(new CallableTask("Task-B", 1000)); // 1 second
        tasks.add(new CallableTask("Task-C", 3000)); // 3 seconds
        tasks.add(new CallableTask("Task-D", 500));  // 0.5 seconds
        tasks.add(new CallableTask("Task-E", 2500)); // 2.5 seconds

        List<Future<String>> results = new ArrayList<>();

        System.out.println("Submitting tasks to the ExecutorService...");
        // Submit each Callable task to the executor
        for (CallableTask task : tasks) {
            Future<String> future = executor.submit(task);
            results.add(future);
        }

        System.out.println("\nRetrieving results...");
        // Retrieve the results from the Future objects
        for (int i = 0; i < results.size(); i++) {
            Future<String> future = results.get(i);
            try {
                // .get() is a blocking call. It waits until the task completes and returns its result.
                String taskResult = future.get();
                System.out.println("Result of Task " + (char)('A' + i) + ": " + taskResult);
            } catch (InterruptedException e) {
                System.err.println("Task was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupt status
            } catch (ExecutionException e) {
                System.err.println("Task execution failed for Task " + (char)('A' + i) + ": " + e.getCause().getMessage());
            }
        }

        // Shut down the executor service gracefully
        executor.shutdown();
        System.out.println("\nExecutorService shutdown initiated.");

        try {
            // Wait for all submitted tasks to complete within a timeout
            if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time. Forcing shutdown.");
                executor.shutdownNow(); // Force shutdown if tasks are still running
            }
        } catch (InterruptedException e) {
            System.err.println("Termination interrupted: " + e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("All tasks completed and ExecutorService terminated.");
    }
}