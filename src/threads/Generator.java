package threads;

import functions.basic.Log;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private final Semaphore semaphore;
    private final Task tasks;

    public Generator(Task tasks, Semaphore semaphore) {
        this.tasks = tasks;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int i = 0; i < this.tasks.getNumberOfTasks(); ++i) {
            try {
                semaphore.acquire();
                double base = rand.nextDouble() * 9 + 1;
                tasks.setFunction(new Log(base));
                tasks.setLeft(rand.nextDouble() * 100);
                tasks.setRight(rand.nextDouble() * 100 + 100);
                tasks.setStepSampling(rand.nextDouble());
                System.out.println(ANSI_BLUE + "Test #" + (i + 1) + "" + ANSI_RESET);
                System.out.println("Left = " + tasks.getLeft() + ", Right = " + tasks.getRight() + ", Step = " + tasks.getStepSampling() + ", base = " + base);
                Integrator integrator = new Integrator(tasks, semaphore);
                integrator.start();
            } catch (InterruptedException e) {
                System.err.println("Interrupting Threads");
            }
        }
    }
}
