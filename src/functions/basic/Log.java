package functions.basic;

import functions.Function;

public class Log implements Function {

    private double logarithm_base;

    public Log(double logarithm_base){ this.logarithm_base=logarithm_base;}

    public double getRightDomainBorder(){
        return Double.POSITIVE_INFINITY;
    }

    public double getLeftDomainBorder(){
        return 0;
    }

    public double getFunctionValue(double x){
        return Math.log(x)/Math.log(logarithm_base);
    }
}
