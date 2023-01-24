package functions;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Arrays;

public class ArrayTabulatedFunction implements Serializable, TabulatedFunction, Function, Cloneable{
    private FunctionPoint[] MassOfValues;
    private int NumberOfPoints;

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points, int NumberOfPoints) {
            return new ArrayTabulatedFunction(points, NumberOfPoints);
        }


        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            try {
                return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
            } catch (InappropriateFunctionPointException e) {
                throw new RuntimeException(e);
            }
        }


        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            try {
                return new ArrayTabulatedFunction(leftX, rightX, values);
            } catch (InappropriateFunctionPointException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points, int NumberOfPoints) {
        if (points.length < 2) throw new IllegalArgumentException();

        this.NumberOfPoints=NumberOfPoints;
        this.MassOfValues = new FunctionPoint[NumberOfPoints];
        for (int i = 0; i < NumberOfPoints; ++i) {
            MassOfValues[i]=points[i];
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount)  throws InappropriateFunctionPointException, IllegalArgumentException {
        if((leftX>=rightX) || (pointsCount<2)) throw new IllegalArgumentException();

        double size = ((rightX - leftX) / (pointsCount));
        this.MassOfValues = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            this.MassOfValues[i] = new FunctionPoint(leftX + i * size, 0);
        }
        NumberOfPoints = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException, IllegalArgumentException {
        if((leftX>=rightX) || (values.length<2)) throw new IllegalArgumentException();

        this.MassOfValues = new FunctionPoint[values.length];
        double size = ((rightX - leftX) / (values.length ));
        for (int i = 0; i < values.length; i++) {
            this.MassOfValues[i] = new FunctionPoint(leftX + i * size, values[i]);
        }
        NumberOfPoints = values.length;
    }



    public double getLeftDomainBorder() {
        return this.MassOfValues[0].getX();
    }

    public double getRightDomainBorder() {
        return this.MassOfValues[NumberOfPoints - 1].getX();
    }

    public double getFunctionValue(double x) {
        if ((this.MassOfValues[0].getX() > x) || (this.MassOfValues[MassOfValues.length - 1].getX() < x)) {
            return Double.NaN;
        }

        for (int i = 0; i < getNumberOfPoints(); ++i) {
            if (MassOfValues[i].getX() == x) {
                return MassOfValues[i].getY();
            }
        }
        if (getLeftDomainBorder() <= x && getRightDomainBorder() >= x) {
            double leftY = getPointY(0);
            double rightY = getPointY(getNumberOfPoints() - 1);
            double k = (rightY - leftY) / (getRightDomainBorder() - getLeftDomainBorder()) ;
            double b = rightY - k * getRightDomainBorder();
            return k * x + b;
        }

        return Double.NaN;
    }

    public int getNumberOfPoints() {
        return NumberOfPoints;
    }
    public void setNumberOfPoints(int count){

    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index];
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if ((index < 0) || (index >= NumberOfPoints) || (point.getX() > this.getRightDomainBorder() || point.getX() < this.getLeftDomainBorder()))
            throw new FunctionPointIndexOutOfBoundsException();

        if (((index == 0) ? false : (this.MassOfValues[index - 1].getX() >= point.getX())) || ((index == (NumberOfPoints - 1)) ? false : (this.MassOfValues[index + 1].getX() <= point.getX())))
            throw new InappropriateFunctionPointException();

        this.MassOfValues[index] = point;
    }

    public double getPointX(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if ((index < 0) || (index >= NumberOfPoints) || (x > this.getRightDomainBorder() || x < this.getLeftDomainBorder()))
            throw new FunctionPointIndexOutOfBoundsException();

        if (((index == 0) ? false : (this.MassOfValues[index - 1].getX() >= x)) || ((index == (NumberOfPoints - 1)) ? false : (this.MassOfValues[index + 1].getX() <= x)))
            throw new InappropriateFunctionPointException();

        this.MassOfValues[index].setX(x);
    }


    public double getPointY(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        this.MassOfValues[index].setY(y);
    }

    public void deletePoint(int index) {
        if ((index < 0) || (index >= NumberOfPoints)) throw new FunctionPointIndexOutOfBoundsException();
        if (NumberOfPoints <= 3) throw new  IllegalStateException();
        System.arraycopy(this.MassOfValues, index + 1, this.MassOfValues, index, MassOfValues.length - index - 1);
        NumberOfPoints--;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (NumberOfPoints<3) throw new InappropriateFunctionPointException();
        else {
            int index = 0;
            if (point.getX() > this.MassOfValues[NumberOfPoints - 1].getX()) {
                index = NumberOfPoints;
            } else {
                while (MassOfValues[index].getX() < point.getX()) index++;
            }

            if (index < NumberOfPoints) {
                if (NumberOfPoints <= this.MassOfValues.length) {
                    FunctionPoint[] tmp = new FunctionPoint[getNumberOfPoints()];
                    System.arraycopy(MassOfValues, 0, tmp, 0, tmp.length);
                    this.MassOfValues = new FunctionPoint[getNumberOfPoints() + 1];
                    NumberOfPoints++;
                    System.arraycopy(tmp, 0, MassOfValues, 0, index);
                    this.MassOfValues[index] = point;
                    System.arraycopy(tmp, index, MassOfValues, index + 1, tmp.length - index);
                } else {
                    System.arraycopy(MassOfValues, index, MassOfValues, index + 1, getNumberOfPoints() - index);
                    this.MassOfValues[index] = point;
                }

            } else {
                FunctionPoint[] tmp = new FunctionPoint[getNumberOfPoints()];
                System.arraycopy(MassOfValues, 0, tmp, 0, tmp.length);
                this.MassOfValues = new FunctionPoint[getNumberOfPoints() + 1];
                NumberOfPoints++;
                System.arraycopy(tmp, 0, MassOfValues, 0, tmp.length);
                this.MassOfValues[NumberOfPoints - 1] = point;
            }
        }
    }

    public String toString(){
        StringBuilder b=new StringBuilder();
        b.append("{").append(MassOfValues[0].toString());
        for (int i = 1; i < this.NumberOfPoints; i++) {
            b.append(", ").append(MassOfValues[i].toString());
        }
        b.append("}");
        return b.toString();
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TabulatedFunction)) return false;
        if (object instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction f = (ArrayTabulatedFunction) object;
            if (f.NumberOfPoints != this.NumberOfPoints) return false;
            for (int i = 0; i < this.NumberOfPoints; ++i) {
                if (!f.MassOfValues[i].equals(this.MassOfValues[i])) return false;
            }
            return true;
        } else {
            if (((TabulatedFunction) object).getNumberOfPoints() != this.getNumberOfPoints()) return false;
            for (int i = 0; i < this.NumberOfPoints; ++i) {
                if (!((TabulatedFunction) object).getPoint(i).equals(this.MassOfValues[i])) return false;
            }
            return true;
        }
    }

    public int hashCode(){
        int res = NumberOfPoints;
        for (int i=0;i<NumberOfPoints;++i){
            res^=MassOfValues[i].hashCode()*i;
        }
        return res;
    }

    public Object clone() throws CloneNotSupportedException {
        //return super.clone();
        int size=this.getNumberOfPoints();
        FunctionPoint[] a=new FunctionPoint[this.getNumberOfPoints()];
        for(int i=0;i<size;i++){
            a[i]=new FunctionPoint(this.getPoint(i));
        }
        return super.clone();
    }

    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int index = 0;

            public boolean hasNext() {
                return (index >= NumberOfPoints);
            }

            public FunctionPoint next() {
                if (hasNext()){
                    throw new NoSuchElementException();
                }
                return new FunctionPoint(MassOfValues[index++]);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void print() {
        for (int i = 0; i < NumberOfPoints; i++) {
            System.out.println("(" + MassOfValues[i].getX() + ";" + MassOfValues[i].getY() + ")");
        }
    }





}





