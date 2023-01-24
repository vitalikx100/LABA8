package functions.meta;

import functions.Function;

public class Mult implements Function{

    private Function Function1, Function2;

    public Mult(Function Function1, Function Function2){
        this.Function1=Function1;
        this.Function2=Function2;
    }

    public double getRightDomainBorder(){
        if(Function1.getRightDomainBorder()<= Function2.getRightDomainBorder()){
            return Function1.getRightDomainBorder();
        }
        else return Function2.getRightDomainBorder();

    }

    public double getLeftDomainBorder(){
        if (Function1.getLeftDomainBorder()<=Function2.getLeftDomainBorder()){
            return Function1.getLeftDomainBorder();
        }
        else return Function2.getLeftDomainBorder();
    }

    public double getFunctionValue(double x){
        return Function1.getFunctionValue(x)*Function2.getFunctionValue(x);
    }
}
