public class Matrix {
    private int size;
    private int[][] matrix;
    private double[][] inverseMatrix;

    //Конструктор
    public Matrix(int size) {
        this.size = size;
        matrix = new int[size][size];
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
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void methodGaussJordan() {
        //Копируем глобально начальную матрицу
        double[][] matrixA = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = matrix[i][j];
            }
        }
        //Заполняем в обратной матрице главную диагональ 1
        for (int i = 0; i < size; i++) {
            inverseMatrix[i][i] = 1;
        }
        //Обнуление вверхнего левого угла
        for (int k = 0; k < size; k++) {
            double r = 1 / matrixA[k][k];
            for (int j = 0; j < size; j++) {
                matrixA[k][j] = matrixA[k][j] * r;
                inverseMatrix[k][j] = inverseMatrix[k][j] * r;
            }
            for (int i = k + 1; i < size; i++) {
                double res = matrixA[i][k];
                for (int z = 0; z < size; z++) {
                    matrixA[i][z] = matrixA[i][z] - matrixA[k][z] * res;
                    inverseMatrix[i][z] = inverseMatrix[i][z] - inverseMatrix[k][z] * res;
                }
            }
        }
        //Обнуление нижнего правого угла
        for (int k = size - 1; k > -1; k--) {
            for (int i = k - 1; i > -1; i--) {
                double res = matrixA[i][k];
                for (int z = size - 1; z > -1; z--) {
                    matrixA[i][z] = matrixA[i][z] - matrixA[k][z] * res;
                    inverseMatrix[i][z] = inverseMatrix[i][z] - inverseMatrix[k][z] * res;
                }
            }
        }
    }

    //Вывод обратной матрицы
    public void outInverseMatrix() {
        System.out.println("Ваша обратная матрица:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf(" %7.4f", inverseMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public void checkInverseMatrix() {
        System.out.println("Проверка!Результат умножения обратной матрицы на начальную");
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrix[i][k] * inverseMatrix[k][j];
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf(" %4.1f", Math.abs(result[i][j]));
            }
            System.out.println();
        }
    }

    //метод Шульца(иттерационный метод)
    public void methodIteration() {
        //копирование матрицы глобально
        double[][] matrixA = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = matrix[i][j];
            }
        }
        //транспонировка матрицы
        double[][] matrixAT = transponirovkaMatrix(matrixA);
        //поиск прібліженія
        double[][] U0 = multiplicationNumberOnMatrix((1/searchKoef(matrixMultiplication(matrixA,matrixAT))),matrixAT);
        //точность
        double e = 0.01;
        //матрица фи для проверки точности на каждой итерации
        double[][] fi;
        //точность матрицы фи
        double norma = 1;
        //эдиничная матрицы
        double[][] im = unitMatrix();
        while(norma>e){
            fi = diffMatrix(im,matrixMultiplication(matrixA,U0));
            norma = searchKoef(fi);
            if(norma<=e){
                break;
            }
            U0 = matrixMultiplication(U0,sumMatrix(im,fi));
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                inverseMatrix[i][j] = U0[i][j];
            }
        }

    }

    //Транспонировка матрицы
    private double[][] transponirovkaMatrix(double[][] matrix) {
        double[][] matrixT = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrixT[i][j] = matrix[j][i];
            }
        }
        return matrixT;
    }

    //Умножение матриц
    private double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                for (int k = 0; k < matrix1.length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
    //Умножение матрицы на число
    private double[][] multiplicationNumberOnMatrix(double numb, double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = numb * matrix[i][j];
            }
        }
        return matrix;
    }

    //эдиничная матрица
    private double[][] unitMatrix() {
        double[][] unitMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            unitMatrix[i][i] = 1;
        }
        return unitMatrix;
    }
    //Разница 2 матриц
    private double[][] diffMatrix(double[][] matrix1,double[][] matrix2){
        double[][]result = new double[size][size];
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return result;
    }
    //Сумма 2 матриц
    private double[][] sumMatrix(double[][] matrix1,double[][] matrix2){
        double[][]result = new double[size][size];
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }
    //Нахождение коефициента для U0
    private double searchKoef(double[][] matrix){
        double res = 0;
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                res+=(matrix[i][j]*matrix[i][j]);
            }
        }
        return Math.sqrt(res);
    }


    //Инкапсуляция
    public double[][] getInverseMatrix() {
        return inverseMatrix;
    }

    public void setInverseMatrix(double[][] inverseMatrix) {
        this.inverseMatrix = inverseMatrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
