package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable, Cloneable {
    public double x,y;

    public FunctionPoint(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public FunctionPoint(FunctionPoint point){
        this.x=point.x;
        this.y=point.y;
    }
    public FunctionPoint(){
        this.x=0;
        this.y=0;
    }

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x=x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y=y;
    }

    public String toString(){
        return "(" + this.x + ";" + this.y + ")";
    }

    public boolean equals(Object object){
        return object instanceof FunctionPoint && this.getX() == ((FunctionPoint) object).getX() && this.getY() == ((FunctionPoint) object).getY();
    }

    public int hashCode(){
        return Objects.hash(x, y);
    }

    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}





