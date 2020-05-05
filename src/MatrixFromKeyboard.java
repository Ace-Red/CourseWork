import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
//Класс матрицы ячеек
public class MatrixFromKeyboard extends GridPane {

    private final TextField[][] textFields;

    public MatrixFromKeyboard(int i) {
        int j = i;
        textFields = new TextField[i][j];
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                textFields[k][l] = new TextField();
                textFields[k][l].setPrefColumnCount(3);
                add(textFields[k][l], l, k);
            }
        }
    }
    public int [][] getMatrix() {
        int [][] matrix = new int[textFields.length][textFields[0].length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = Integer.parseInt(textFields[i][j].getText());
        return matrix;
    }
}
