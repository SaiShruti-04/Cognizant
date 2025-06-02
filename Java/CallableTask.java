import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableTask implements Callable<String> {
    private String taskName;
    private long sleepTimeMillis;

    public CallableTask(String taskName, long sleepTimeMillis) {
        this.taskName = taskName;
        this.sleepTimeMillis = sleepTimeMillis;
    }

    @Override
    public String call() throws Exception {
        System.out.println(taskName + " started. Will sleep for " + sleepTimeMillis + "ms.");
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTimeMillis); // Simulate work
        } catch (InterruptedException e) {
            System.out.println(taskName + " was interrupted.");
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
        String result = taskName + " completed after " + sleepTimeMillis + "ms.";
        System.out.println(result);
        return result;
    }
}