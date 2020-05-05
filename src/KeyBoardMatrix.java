import javafx.scene.layout.HBox;

public class KeyBoardMatrix extends HBox {
    private final MatrixSpecialSizeFromKeyboard matrix = new MatrixSpecialSizeFromKeyboard();
    public KeyBoardMatrix() {
        getChildren().addAll(matrix);
        setSpacing(10);
    }

    public int[][] getMatrix() {
        return matrix.getMatrix();
    }
}
