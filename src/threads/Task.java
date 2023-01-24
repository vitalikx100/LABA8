package threads;

import functions.Function;
import functions.Functions;

public class Task {
    private Function function;
    private double left;
    private double right;
    private double stepSampling;
    private int numberOfTasks;

    public Task(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public Task(Function function, double leftBorder, double rightBorder, double samplingStep, int numberOfTasks) {
        this.function = function;
        this.left = leftBorder;
        this.right = rightBorder;
        this.stepSampling = samplingStep;
        this.numberOfTasks = numberOfTasks;
    }

    public double integral() {
        return Functions.Integral(function, left, right, stepSampling);
    }

    public Function getFunction() {
        return function;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getStepSampling() {
        return stepSampling;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setStepSampling(double stepSampling) {
        this.stepSampling = stepSampling;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
}
