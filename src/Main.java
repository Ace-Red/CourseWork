public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(5);
        matrix.generateRandomMatrix(0,100);
        matrix.outMatrix();
        matrix.methodGaussJordan();
        matrix.outInverseMatrix();
        matrix.checkInverseMatrix();
    }
}
