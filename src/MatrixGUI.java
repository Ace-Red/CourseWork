import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
public class MatrixGUI extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        ToggleGroup howToEnterMatrix = new ToggleGroup();
        //Кнопка генерации
        RadioButton generateMatrixRB = new RadioButton();
        generateMatrixRB.setToggleGroup(howToEnterMatrix);
        //Введение матрицы в ячейки
        RadioButton enterMatrixRB = new RadioButton();
        enterMatrixRB.setToggleGroup(howToEnterMatrix);
        //Чтение матрицы с файла
        RadioButton readMatrixRB = new RadioButton();
        readMatrixRB.setToggleGroup(howToEnterMatrix);
        enterMatrixRB.setSelected(true);
        //Надписи к кнопкам(вверхнем поле) и отступы
        final HBox hBox = new HBox(new Label(" - Генерация", generateMatrixRB), new Label(" - Ввод в ячейки", enterMatrixRB), new Label(" - Чтение с файла", readMatrixRB));
        hBox.setSpacing(10);
        pane.setTop(hBox);
        //Место для кнопок упорядоченых сверху-вниз
        VBox calculatingArea = new VBox();
        pane.setBottom(calculatingArea);
        calculatingArea.setSpacing(5);
        calculatingArea.setAlignment(Pos.CENTER);
        Button calculate = new Button("Найти обратную матрицу");
        Text outputPath = new Text("Выберете папку для вывода результата");
        Button chooseFolder = new Button("Выбор папки");
        //Кнопки выбора метода нахождения обратной(Жордана-Гаусса,Шульца) и записи результата
        ToggleGroup matrixMultiplication = new ToggleGroup();
        RadioButton gordanGaus = new RadioButton();
        gordanGaus.setToggleGroup(matrixMultiplication);
        RadioButton shulc = new RadioButton();
        shulc.setToggleGroup(matrixMultiplication);
        shulc.setSelected(true);
        final HBox downHBox = new HBox(new Label(" - Жордана-Гаусса", gordanGaus), new Label(" - Шульца", shulc));
        downHBox.setSpacing(10);
        final HBox outputFolderHBox = new HBox(new Label("Путь вывода: "), outputPath, chooseFolder);
        outputFolderHBox.setSpacing(10);
        calculatingArea.getChildren().addAll(outputFolderHBox, downHBox, calculate);
        //

        //
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Нахождение обратной матрицы");
        primaryStage.show();


    }
}
