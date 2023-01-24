package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function Func1, Func2;

    public Composition(Function Func1, Function Func2){
        this.Func1=Func1;
        this.Func2=Func2;
    }


    public double getRightDomainBorder(){
        return Func1.getRightDomainBorder();
    }

    public double getLeftDomainBorder(){
        return Func1.getLeftDomainBorder();
    }

    public double getFunctionValue(double x){
        return Func1.getFunctionValue(Func2.getFunctionValue(x));
    }
}
