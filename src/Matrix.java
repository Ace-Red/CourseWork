import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;

public class Matrix {
    private static long addingOperation;
    private static long multiplicationOperation;
    private static long minusOperation;
    private static long devisionOperation;
    private int size;
    private double[][] matrix;
    private double[][] inverseMatrix;

    //Конструктор
    public Matrix(int size) {

        this.size = size;
        matrix = new double[size][size];
        inverseMatrix = new double[size][size];
    }

    //Генерация матрицы
    public void generateRandomMatrix(int start, int finish) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (int) (Math.random() * (finish - start + 1)) + start;
            }
        }
    }

    //Выводит матрицу
    public void outMatrix() {
        System.out.println("Ваша матрица:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%7.3f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //Метод Жордана-Гаусса
    public void methodGaussJordan() {
        double tempe;
        double[][] matrixA = new double[size][2 * size];
        double[][] unitMatrix = unitMatrix();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = matrix[i][j];
                matrixA[i][j + size] = unitMatrix[i][j];
            }
        }
        for (int i = size - 1; i > 0; i--) {
            if (matrixA[i - 1][0] < matrixA[i][0]) {
                double[] temp = matrixA[i];
                matrixA[i] = matrixA[i - 1];
                matrixA[i - 1] = temp;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j != i) {
                    tempe = matrixA[j][i] / matrixA[i][i];
                    devisionOperation++;
                    for (int k = 0; k < 2 * size; k++) {

                        matrixA[j][k] -= matrixA[i][k] * tempe;
                        multiplicationOperation++;
                        minusOperation++;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            tempe = matrixA[i][i];
            for (int j = 0; j < 2 * size; j++) {
                matrixA[i][j] = matrixA[i][j] / tempe;
                devisionOperation++;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                inverseMatrix[i][j] = matrixA[i][j + size];
            }
        }
    }


    //Вывод обратной матрицы
    public void outInverseMatrix() {
        System.out.println("Ваша обратная матрица:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf(" %7.3f", inverseMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public boolean checkInverseMatrix() {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += (inverseMatrix[i][k] * matrix[k][j]);
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (Math.round(Math.abs(result[i][i])) != 1) {
                return false;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j && (int) (Math.abs(result[i][j])) != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    //метод Шульца(иттерационный метод)
    public void methodIteration() {
        //копирование матрицы глобально
        int counter = 0;
        double[][] matrixA = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = matrix[i][j];
            }
        }
        //транспонировка матрицы
        double[][] matrixAT = transponirovkaMatrix(matrixA);
        //поиск приближения
        double[][] U0 = multiplicationNumberOnMatrix((1 / searchKoef(matrixMultiplication(matrixA, matrixAT))), matrixAT);
        //точность
        double e = 0.0001;
        //матрица фи для проверки точности на каждой итерации
        double[][] fi;
        //точность матрицы фи
        double norma = 1;
        //эдиничная матрица
        double[][] im = unitMatrix();
        while (norma > e) {
            if (counter > 10000) {
                return;
            }
            fi = diffMatrix(im, matrixMultiplication(matrixA, U0));
            norma = searchKoef(fi);
            if (norma <= e) {
                break;
            }
            counter++;
            U0 = matrixMultiplication(U0, sumMatrix(im, fi));
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                inverseMatrix[i][j] = U0[i][j];
            }
        }

    }

    //Поиск детерминанта
    private double searchDeterminant(double[][] A) {
        int n = A.length;
        if(n == 1) return A[0][0];
        double ans = 0;
        double B[][] = new double[n-1][n-1];
        int l = 1;
        for(int i = 0; i < n; ++i){

            int x = 0, y = 0;
            for(int j = 1; j < n; ++j){
                for(int k = 0; k < n; ++k){
                    if(i == k) continue;
                    B[x][y] = A[j][k];
                    ++y;
                    if(y == n - 1){
                        y = 0;
                        ++x;
                    }
                }
            }
            ans += l * A[0][i] * searchDeterminant(B);
            l *= (-1);
        }
        return ans;
    }
    public boolean checkDeterminant(){
        return searchDeterminant(matrix) != 0;
    }



        //Транспонировка матрицы
        private double[][] transponirovkaMatrix ( double[][] matrix){
            double[][] matrixT = new double[matrix.length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrixT[i][j] = matrix[j][i];
                }
            }
            return matrixT;
        }

        //Умножение матриц
        private double[][] matrixMultiplication ( double[][] matrix1, double[][] matrix2){
            double[][] result = new double[matrix1.length][matrix1.length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1.length; j++) {
                    for (int k = 0; k < matrix1.length; k++) {
                        result[i][j] += matrix1[i][k] * matrix2[k][j];
                        multiplicationOperation++;
                        addingOperation++;
                    }
                }
            }
            return result;
        }

        //Умножение матрицы на число
        private double[][] multiplicationNumberOnMatrix ( double numb, double[][] matrix){
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[i][j] = numb * matrix[i][j];
                    multiplicationOperation++;
                }
            }
            return matrix;
        }

        //эдиничная матрица
        private double[][] unitMatrix () {
            double[][] unitMatrix = new double[size][size];
            for (int i = 0; i < size; i++) {
                unitMatrix[i][i] = 1;
            }
            return unitMatrix;
        }

        //Разница 2 матриц
        private double[][] diffMatrix ( double[][] matrix1, double[][] matrix2){
            double[][] result = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result[i][j] = matrix1[i][j] - matrix2[i][j];
                    minusOperation++;
                }
            }
            return result;
        }

        //Сумма 2 матриц
        private double[][] sumMatrix ( double[][] matrix1, double[][] matrix2){
            double[][] result = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result[i][j] = matrix1[i][j] + matrix2[i][j];
                    addingOperation++;
                }
            }
            return result;
        }

        //Нахождение коефициента для U0
        private double searchKoef ( double[][] matrix){
            double res = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    res += (matrix[i][j] * matrix[i][j]);
                    addingOperation++;
                    multiplicationOperation++;
                }
            }
            return Math.sqrt(res);
        }

        //Создание начальной матрицы с файла
    public Matrix(String fileName) {

            File file = new File(fileName);
            if (!file.exists())
                System.out.println("Файл не найден!");
            try (Scanner input = new Scanner(file)) {
                this.size = Integer.parseInt(input.nextLine());
                matrix = new double[size][size];
                inverseMatrix = new double[size][size];
                for (int i = 0; i < size; i++) {
                    String[] numbers = input.nextLine().split(" ");
                    for (int j = 0; j < size; j++) {
                        matrix[i][j] = Integer.parseInt(numbers[j]);
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Файл не найден!");
            }
        }

        //Создание матрицы с чисел в ячейках
    public Matrix( double[][] matrixEnter){
            matrix = new double[matrixEnter.length][matrixEnter.length];
            this.size = matrixEnter.length;
            inverseMatrix = new double[size][size];
            for (int i = 0; i < matrixEnter.length; i++) {
                for (int j = 0; j < matrixEnter.length; j++) {
                    matrix[i][j] = matrixEnter[i][j];
                }
            }
        }

        //Вывод в файл начальной матрицы
        public void writeMatrix (String fileName){
            File file = new File(fileName);
            try (PrintWriter outPut = new PrintWriter(file)) {
                outPut.println("Ваша матрица:");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        outPut.printf("%7.3f", matrix[i][j]);
                    }
                    outPut.println();
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Файл не найден");
            }
        }

        //Вывод в файл конечной матрицы
        public void writeInverseMatrix (String fileName){
            File file = new File(fileName);
            try (PrintWriter outPut = new PrintWriter(file)) {
                outPut.println("Ваша обратная матрица:");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        outPut.printf(" %7.3f", inverseMatrix[i][j]);
                    }
                    outPut.println();
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Файл не найден");
            }
        }

        public static void clearOperation () {
            Matrix.addingOperation = 0;
            Matrix.multiplicationOperation = 0;
            Matrix.devisionOperation = 0;
            Matrix.minusOperation = 0;
        }

        //Инкапсуляция
        public double[][] getInverseMatrix () {
            return inverseMatrix;
        }

        public void setInverseMatrix ( double[][] inverseMatrix){
            this.inverseMatrix = inverseMatrix;
        }

        public double[][] getMatrix () {
            return matrix;
        }

        public void setMatrix ( double[][] matrix){
            this.matrix = matrix;
        }

        public int getSize () {
            return size;
        }

        public void setSize ( int size){
            this.size = size;
        }

        public static long getAddingOperation () {
            return addingOperation;
        }

        public static long getDevisionOperation () {
            return devisionOperation;
        }

        public static long getMinusOperation () {
            return minusOperation;
        }

        public static long getMultiplicationOperation () {
            return multiplicationOperation;
        }


    }
