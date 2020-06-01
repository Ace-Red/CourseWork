import javafx.geometry.Pos;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GenerationPane extends VBox{
    private final TextField a = new TextField();
    public GenerationPane() {
        HBox mHBox = new HBox();
        Text text = new Text("Введите размер генерируемой матрицы: ");
        HBox hBox = new HBox(a);
        mHBox.getChildren().addAll(text, hBox);
        getChildren().addAll(mHBox);
        mHBox.setAlignment(Pos.CENTER);

    }
    public int getI() {
        try {
            return Integer.parseInt(a.getText());
        }catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!ОШИБКА!");
            alert.setHeaderText("Некоректно введенные данные!");
            alert.setContentText("Введенные данные не есть целочисленными!\nПрограмма не может продолжить работу!");
            alert.showAndWait();
            return 0;
        }
    }


}
