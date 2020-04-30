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
        double[][] matrixA = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = matrix[i][j];
            }
        }
        double[][] matrixAT = transponirovkaMatrix(matrixA);
        double deter = Math.abs(searchDeterm(matrixMultiplication(matrixA, matrixAT)));
        if(deter==0){
            System.out.println("Детерминант матрицы равен 0!");
            System.exit(0);
        }
        double[][] U = multiplicationNumberOnMatrix(1 / deter, matrixAT);
        double[][] Im = unitMatrix();
        double E = 0.01;
        double norma=0;
        int k = 0;
        double[][]fMatrix = fillMatrix();
        while (Math.abs(searchDeterm(fMatrix))>E){
            fMatrix = diffMatrix(Im,matrixMultiplication(matrixA,U));
            if(Math.abs(searchDeterm(fMatrix))<E){
                break;
            }
            U = matrixMultiplication(U,sumMatrix(Im,fMatrix));
            k++;
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

    //Нахождение детерминанта
    private double searchDeterm(double[][] matrix) {
        int size = matrix.length;
        if (size == 1) {
            return matrix[0][0];
        }
        double determ = 0;
        double[][] B = new double[size - 1][size - 1];
        int l = 1;
        for (int i = 0; i < size; ++i) {
            int x = 0, y = 0;
            for (int j = 1; j < size; ++j) {
                for (int k = 0; k < size; ++k) {
                    if (i == k) continue;
                    B[x][y] = matrix[j][k];
                    ++y;
                    if (y == size - 1) {
                        y = 0;
                        ++x;
                    }
                }
            }
            determ += l * matrix[0][i] * searchDeterm(B);
            l *= (-1);
        }
        return determ;
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
    //Заполнение матрицы 1
    private double[][] fillMatrix(){
        double[][] matrix = new double[size][size];
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                matrix[i][j]=1.0;
            }
        }
        return matrix;
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
