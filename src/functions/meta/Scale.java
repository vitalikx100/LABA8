package functions.meta;

import functions.Function;

public class Scale implements Function {

    private Function Func;
    private double ScaleX, ScaleY;

    public Scale(Function Func, double ScaleX, double ScaleY){
        this.Func=Func;
        this.ScaleX=ScaleX;
        this.ScaleY=ScaleY;
    }

    public double getRightDomainBorder(){
        return Func.getRightDomainBorder() * ScaleX;
    }

    public double getLeftDomainBorder(){
        return Func.getLeftDomainBorder() * ScaleX;
    }

    public double getFunctionValue(double x){
        return Func.getFunctionValue(x*ScaleX) * ScaleY;
    }
}
