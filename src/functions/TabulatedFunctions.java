package functions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {

    private static TabulatedFunctionFactory TFF = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory){
        TFF = functionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points, int NumberOfPoints){
        return TFF.createTabulatedFunction(points, NumberOfPoints);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
        return TFF.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
        return TFF.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException{
        if (pointsCount <2 || leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) throw new IllegalArgumentException();
        double size = (rightX-leftX)/(pointsCount-1);
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + size * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(points, pointsCount);
    }


    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out){
        try (DataOutputStream stream = new DataOutputStream(out)) {
            int pointsCount = function.getNumberOfPoints();
            stream.writeInt(pointsCount);
            for (int i = 0; i < pointsCount; ++i) {
                stream.writeDouble(function.getPointX(i));
                stream.writeDouble(function.getPointY(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        try (DataInputStream stream = new DataInputStream(in)) {
            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return createTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        try (BufferedWriter stream = new BufferedWriter(out)) {
            int pointsCount = function.getNumberOfPoints();
            stream.write(String.valueOf(pointsCount) + " ");
            for (int i = 0; i < pointsCount; i++) {
                stream.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in){
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointsCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; stream.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return  createTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clas, FunctionPoint[] points){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction > constructor = clas.getConstructor(FunctionPoint[].class);
            function = constructor.newInstance((Object) points);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clas, double leftX, double rightX, int pointsCount){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> constructor = clas.getConstructor(double.class, double.class, int.class);
            function = constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clas, double leftX, double rightX, double[] values){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> constructor = clas.getConstructor(double.class, double.class, double[].class);
            function = constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
        return function;
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> classTabFunc, Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double length = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + length * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(points, pointsCount);
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> classTabFunc, InputStream in) {
        try (DataInputStream dis = new DataInputStream(in)) {
            int pointsCount = dis.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(dis.readDouble(), dis.readDouble());
            }
            return  createTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> classTabFunc, Reader in) {
        try {
            StreamTokenizer st = new StreamTokenizer(in);
            st.nextToken();
            int pointsCount = (int) st.nval;
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; st.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = st.nval;
                st.nextToken();
                double y = st.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return  createTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }




}
