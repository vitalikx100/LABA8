package threads;

import functions.basic.Log;
import java.util.Random;

public class SimpleGenerator implements Runnable {
    private final Task tasks;

    public SimpleGenerator(Task tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int i = 0; i < tasks.getNumberOfTasks(); ++i) {
            double base = rand.nextDouble() * 9 + 1;
            tasks.setFunction(new Log(base));
            tasks.setLeft(rand.nextDouble() * 100);
            tasks.setRight(rand.nextDouble() * 100 + 100);
            tasks.setStepSampling(rand.nextDouble());
            System.out.println("Left = " + tasks.getLeft() + ", Right = " + tasks.getRight() + ", Step = " + tasks.getStepSampling() + ", base = " + base);

            SimpleIntegrator integrator = new SimpleIntegrator(tasks);
            Thread thread = new Thread(integrator);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
