import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class FilePane extends VBox {
    private final Text matrixPath = new Text("Выбере путь к матрице:");
    public FilePane(){
        HBox mHBox = new HBox();
        mHBox.setSpacing(5);
        Button setPathA = new Button("-Путь-");
        setPathA.setOnAction(e -> {
            FileChooser directoryChooser = new FileChooser();
            File selectedDirectory = directoryChooser.showOpenDialog(new Stage());
            matrixPath.setText(selectedDirectory.getAbsolutePath());
        });
        mHBox.getChildren().addAll(matrixPath, setPathA);
        getChildren().addAll(mHBox);
    }

    public String getMatrixPath() {
        return matrixPath.getText();
    }
}
