package threads;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private final Task tasks;
    private final Semaphore semaphore;

    public Integrator(Task tasks, Semaphore semaphore) {
        this.tasks = tasks;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        double result = tasks.integral();
        System.out.println(ANSI_GREEN + "Result = " + result + " " + ANSI_RESET);
        semaphore.release();
    }
}
