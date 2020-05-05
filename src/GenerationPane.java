import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GenerationPane extends VBox{
    private final TextField a = new TextField();
    public GenerationPane() {
        getChildren().add(new Text("Введите количество рядков и столбцов генерируемой матрицы"));
        getChildren().add(new HBox(new Label("Рядки,Столбцы: "), a));
    }
    public int getI() {
        return Integer.parseInt(a.getText());
    }


}
