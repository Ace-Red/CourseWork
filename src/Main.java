public class Main {
    public static void main(String[] args) {
        Matrix matrix2 = new Matrix(3);
        matrix2.generateRandomMatrix(0,2);
        matrix2.outMatrix();
        matrix2.methodIteration();
        matrix2.outInverseMatrix();
        matrix2.checkInverseMatrix();
    }
}
