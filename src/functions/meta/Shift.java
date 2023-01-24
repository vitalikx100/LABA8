package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function Func;
    private double ShiftX, ShiftY;

    public Shift(Function Func, double ShiftX, double ShiftY){
        this.Func=Func;
        this.ShiftX=ShiftX;
        this.ShiftY=ShiftY;
    }

    public double getRightDomainBorder(){
        return Func.getRightDomainBorder() + ShiftX;
    }

    public double getLeftDomainBorder(){
        return Func.getLeftDomainBorder() + ShiftX;
    }

    public double getFunctionValue(double x){
        return getFunctionValue(x-ShiftX)+ ShiftY;
    }
}
