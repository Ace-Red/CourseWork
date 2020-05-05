import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class MatrixSpecialSizeFromKeyboard extends BorderPane {
    public MatrixSpecialSizeFromKeyboard() {
        HBox comboBoxes = new HBox();
        setTop(comboBoxes);
        comboBoxes.setAlignment(Pos.CENTER);
        comboBoxes.setSpacing(5);
        ComboBox<Integer> row = new ComboBox<>();
        for (int i = 1; i <= 15; i++)
            row.getItems().add(i);
        row.setValue(5);
        comboBoxes.getChildren().addAll(new Label("Row: "), row);

        resetCenter(row);

        row.setOnAction(e -> resetCenter(row));

    }

    private void resetCenter(ComboBox<Integer> row) {
        setCenter(new MatrixFromKeyboard(row.getValue()));
    }

    public int[][] getMatrix() {
        return ((MatrixFromKeyboard) getCenter()).getMatrix();
    }
}
