import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.NoSuchElementException;

public class MatrixGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(10, 10, 10, 10));
        ToggleGroup chooseEnterMatrix = new ToggleGroup();
        //Кнопка генерации
        RadioButton generateMatrix = new RadioButton();
        generateMatrix.setToggleGroup(chooseEnterMatrix);
        //Введение матрицы в ячейки
        RadioButton enterMatrix = new RadioButton();
        enterMatrix.setToggleGroup(chooseEnterMatrix);
        //Чтение матрицы с файла
        RadioButton readMatrix = new RadioButton();
        readMatrix.setToggleGroup(chooseEnterMatrix);
        enterMatrix.setSelected(true);
        //Надписи к кнопкам(вверхнем поле) и отступы
        final HBox hBox = new HBox(new Label(" - Генерация", generateMatrix), new Label(" - Ввод в ячейки", enterMatrix), new Label(" - Чтение с файла", readMatrix));
        hBox.setSpacing(10);
        pane.setLeft(hBox);
        //Место для кнопок упорядоченых сверху-вниз
        VBox chooseFileArea = new VBox();
        pane.setBottom(chooseFileArea);
        chooseFileArea.setSpacing(5);
        chooseFileArea.setAlignment(Pos.CENTER);
        Button start = new Button("Найти обратную матрицу");
        Text outputPath = new Text("Выберете папку для вывода результата");
        Button chooseFolder = new Button("Выбор папки");
        //Кнопки выбора метода нахождения обратной(Жордана-Гаусса,Шульца) и записи результата
        ToggleGroup matrixInvertion = new ToggleGroup();
        RadioButton gordanGaus = new RadioButton();
        gordanGaus.setToggleGroup(matrixInvertion);
        RadioButton shulc = new RadioButton();
        shulc.setToggleGroup(matrixInvertion);
        shulc.setSelected(true);
        final VBox leftHBox = new VBox(new Label(" - Жордана-Гаусса", gordanGaus), new Label(" - Шульца", shulc));
        leftHBox.setSpacing(10);
        pane.setLeft(leftHBox);
        final HBox outputFolderHBox = new HBox(new Label("Путь вывода: "), outputPath, chooseFolder);
        outputFolderHBox.setSpacing(10);
        chooseFileArea.getChildren().addAll(hBox, outputFolderHBox, start);
        //Добавление в сцену ячеек для матрицы
        final KeyBoardMatrix keyboardMatrices = new KeyBoardMatrix();
        pane.setCenter(keyboardMatrices);
        final FilePane filePane = new FilePane();
        final GenerationPane generatorPane = new GenerationPane();
        //Сама сцена
        Scene scene = new Scene(pane, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Нахождение обратной матрицы");
        primaryStage.show();
        //Функционал кнопки выбора папки в который будет занесен результат
        chooseFolder.setOnMouseClicked(e -> {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                outputPath.setText(selectedDirectory.getAbsolutePath());
            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("!ОШИБКА!");
                alert.setHeaderText("Некоректно введенные данные!");
                alert.setContentText("Вы не указали путь к файлу вывода!");
                alert.showAndWait();
            }
        });
        //Перемещение между вариантами матрицы
        generateMatrix.setOnMouseClicked(e -> pane.setCenter(generatorPane));
        enterMatrix.setOnAction(e -> pane.setCenter(keyboardMatrices));
        readMatrix.setOnAction(e -> pane.setCenter(filePane));

        start.setOnAction(e -> {
            if (generateMatrix.isSelected()) {
                int i = generatorPane.getI();
                if (i < 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("!ОШИБКА!");
                    alert.setHeaderText("Размер матрицы недопустим!");
                    alert.setContentText("Введите коректный размер матрицы!");
                    alert.showAndWait();
                } else {
                    Matrix matrix = new Matrix(i);
                    if (!outputPath.getText().equals("Выберете папку для вывода результата")) {
                        matrix.generateRandomMatrix(0, 100);
                        if (matrix.checkDeterminant()) {
                            makeCalculation(shulc, gordanGaus, matrix);
                            if (matrix.checkInverseMatrix()) {
                                matrix.writeMatrix(outputPath.getText() + "/matrix.txt");
                                matrix.writeInverseMatrix(outputPath.getText() + "/matrixInverse.txt");
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("!ОШИБКА!");
                                alert.setHeaderText("У данной матрицы нет обратной!");
                                alert.setContentText("Введите матрицу у которой есть обратная!");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("!ОШИБКА!");
                            alert.setHeaderText("У данной матрицы нет обратной!");
                            alert.setContentText("Введите матрицу у которой есть обратная!");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("!ОШИБКА!");
                        alert.setHeaderText("Не выбран путь записи!");
                        alert.setContentText("Введите путь записи!");
                        alert.showAndWait();
                    }
                }
            } else if (enterMatrix.isSelected()) {
                Matrix matrix = new Matrix(keyboardMatrices.getMatrix());
                if (!outputPath.getText().equals("Выберете папку для вывода результата")) {
                    if (matrix.checkDeterminant()) {
                        makeCalculation(shulc, gordanGaus, matrix);
                        if (matrix.checkInverseMatrix()) {
                            matrix.writeMatrix(outputPath.getText() + "/matrix.txt");
                            matrix.writeInverseMatrix(outputPath.getText() + "/matrixInverse.txt");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("!ОШИБКА!");
                            alert.setHeaderText("У данной матрицы нет обратной!");
                            alert.setContentText("Введите матрицу у которой есть обратная!");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("!ОШИБКА!");
                        alert.setHeaderText("У данной матрицы нет обратной!");
                        alert.setContentText("Введите матрицу у которой есть обратная!");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("!ОШИБКА!");
                    alert.setHeaderText("Не выбран путь записи!");
                    alert.setContentText("Введите путь записи!");
                    alert.showAndWait();
                }
            } else {
                try {
                    if(!filePane.getMatrixPath().equals("Выбере путь к матрице:")){
                    Matrix matrix = new Matrix(filePane.getMatrixPath());
                    if (!outputPath.getText().equals("Выберете папку для вывода результата")) {
                        if (matrix.checkDeterminant()) {
                            makeCalculation(shulc, gordanGaus, matrix);
                            if (matrix.checkInverseMatrix()) {
                                matrix.writeInverseMatrix(outputPath.getText() + "/matrixInverse.txt");
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("!ОШИБКА!");
                                alert.setHeaderText("У данной матрицы нет обратной!");
                                alert.setContentText("Введите матрицу у которой есть обратная!");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("!ОШИБКА!");
                            alert.setHeaderText("У данной матрицы нет обратной!");
                            alert.setContentText("Введите матрицу у которой есть обратная!");
                            alert.showAndWait();
                        }}
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("!ОШИБКА!");
                        alert.setHeaderText("Не выбран путь записи!");
                        alert.setContentText("Введите путь записи!");
                        alert.showAndWait();
                    }}
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("!ОШИБКА!");
                        alert.setHeaderText("Не выбран файл чтения!");
                        alert.setContentText("Введите файл чтения!");
                        alert.showAndWait();
                    }
                    } catch(NoSuchElementException ex){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("!ОШИБКА!");
                        alert.setHeaderText("Некоректно введенные данные!");
                        alert.setContentText("В данном файле не содержиться матрицы!\nПрограмма не может продолжить работу!");
                        alert.showAndWait();
                    }

            }
            pane.setRight(new VBox(new Text("Добавлений: " + Matrix.getAddingOperation() + ";"), new Text("Умножений: " + Matrix.getMultiplicationOperation() + ";"), new Text("Отниманий: " + Matrix.getMinusOperation() + ";"), new Text("Делений: " + Matrix.getDevisionOperation() + ";")));
            Matrix.clearOperation();

        });

    }

    private static void makeCalculation(RadioButton shulc, RadioButton gordanGaus, Matrix matrix) {

        if (shulc.isSelected())
            matrix.methodIteration();
        else
            matrix.methodGaussJordan();
    }
}