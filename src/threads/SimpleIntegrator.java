package threads;

public class SimpleIntegrator implements Runnable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private final Task tasks;

    public SimpleIntegrator(Task tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        double result = tasks.integral();
        System.out.println(ANSI_GREEN + "Result = " + result + " " + ANSI_RESET);
    }
}
