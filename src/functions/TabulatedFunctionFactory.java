package functions;

interface TabulatedFunctionFactory {

    TabulatedFunction createTabulatedFunction(FunctionPoint[] points, int NumberOfPoints);

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount);

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values);
}
