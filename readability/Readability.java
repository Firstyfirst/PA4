package readability;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Readability extends Application {

    TextField textField;

    File selectedFile;

    URL objUrl;

    TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane flowPane = initComponent(primaryStage);
        primaryStage.setTitle("Flesh Readability Index");
        primaryStage.setScene(new Scene(flowPane, 500, 200));
        primaryStage.show();
    }

    public FlowPane initComponent(Stage primaryStage) {
        FlowPane root = new FlowPane();

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Label fileOrUrl = new Label("File or URL name: ");
        // apperence of textfield
        textField = new TextField();
        textField.setPrefWidth(160);
        // browse button
        Button calButton = new Button("Calculate !");
        EventHandler<ActionEvent> handler = this::handleCalculation;
        calButton.setOnAction(handler);
        // file chooser for choose file from your computer
        FileChooser fileChooser = new FileChooser();
        Button selectButton = new Button("select file");
        selectButton.setOnAction(event -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            textField.setText(selectedFile.getAbsolutePath());
        });
        // clear button
        Button clearButton = new Button("clear");
        EventHandler<ActionEvent> handler2 = this::handleClear;
        clearButton.setOnAction(handler2);
        // text area
        textArea = new TextArea();

        root.getChildren().addAll(fileOrUrl, textField, calButton, selectButton, clearButton, textArea);

        return root;
    }

    private void handleCalculation(ActionEvent event) {
        String filePaths = textField.getText();
        // first index is line
        // second index is sentence
        // third index is syllabel
        // fourth index is word
        ArrayList<Double> number = IOTask.readFile(filePaths);
        Calculation calculation = new Calculation(number.get(0), number.get(1), number.get(2), number.get(3));
        Double index = calculation.IndexFlesch();
        String level = calculation.Level(index);

        String name;
        if (IOTask.isUrlValid(filePaths)) {
            name = "URL path";
        }
        else {
            name = "File name";
        }

        textArea.setText(String.format("%-32s:  %s\n%-25s:  %.0f\n%-24s:  %.0f\n%-22s:  %.0f\n%-26s:  %.0f\n%-29s :  %.3f\n%-31s:  %s", 
            name, filePaths, "Number of Syllabels", number.get(2),
            "Number of Words", number.get(3), "Number of Sentences", number.get(1), 
            "Number of Lines",number.get(0), "Flesch Index", index, 
            "Readability", level));
    }

    private void handleClear(ActionEvent event) {
        textField.clear();
        textArea.clear();
    }
}