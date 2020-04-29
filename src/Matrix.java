public class Matrix {
    private int size;
    private int[][] matrix;

    public Matrix (int size){
        this.size = size;
        matrix = new int[size][size];
    }
    public void generateRandomMatrix(int start,int finish){
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (int)(Math.random()*(finish-start+1))+start;
            }
        }
    }
    public void outMatrix(){
        System.out.println("Ваша матрица:");
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%4d",matrix[i][j]);
            }
            System.out.println();
        }
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
