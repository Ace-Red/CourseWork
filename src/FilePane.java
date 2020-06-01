import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class FilePane extends VBox {
    private final Text matrixPath = new Text("Выбере путь к матрице:");

    public FilePane() {
        HBox mHBox = new HBox();
        mHBox.setSpacing(5);
        Button setPathA = new Button("-Путь-");
        setPathA.setOnAction(e -> {
            try{
            FileChooser directoryChooser = new FileChooser();
            File selectedDirectory = directoryChooser.showOpenDialog(new Stage());
            matrixPath.setText(selectedDirectory.getAbsolutePath());}
            catch (NullPointerException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("!ОШИБКА!");
                alert.setHeaderText("Некоректно введенные данные!");
                alert.setContentText("Вы не указали путь к файлу с матрицей!");
                alert.showAndWait();
            }
        });
        mHBox.getChildren().addAll(matrixPath, setPathA);
        getChildren().addAll(mHBox);
        mHBox.setAlignment(Pos.BASELINE_CENTER);
    }

    public String getMatrixPath() {
        return matrixPath.getText();
    }
}
