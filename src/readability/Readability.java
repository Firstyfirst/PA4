package src.readability;

import java.io.File;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import src.readability.stategy.*;

public class Readability extends Application {

    ComboBox<String> fileOrUrl;

    TextField textField;

    File selectedFile;

    URL objUrl;

    TextArea textArea;

    ReadStategy rd = new ReadStategy();

    String type;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane flowPane = initComponent(primaryStage);
        primaryStage.setTitle("Flesh Readability Index");
        Scene scene = new Scene(flowPane, 500, 250);
        // scene.getStylesheets().add("readability/css/styleSheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public FlowPane initComponent(Stage primaryStage) {
        FlowPane root = new FlowPane();
        // set flowpane apperence
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Label label = new Label("file or url : ");
        // apperence of textfield
        textField = new TextField();
        textField.setPrefWidth(160);
        textField.setId("box");
        // browse button
        Button calButton = new Button("Calculate !");
        calButton.setId("box");
        EventHandler<ActionEvent> handler2 = this::calculationHandle;
        calButton.setOnAction(handler2);
        // file chooser for choose file from your computer
        FileChooser fileChooser = new FileChooser();
        Button selectButton = new Button("select file");
        selectButton.setId("box");
        selectButton.setOnAction(event -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            textField.setText(selectedFile.getAbsolutePath());
        });
        // clear button
        Button clearButton = new Button("clear");
        clearButton.setId("box");
        EventHandler<ActionEvent> handler3 = this::clearHandle;
        clearButton.setOnAction(handler3);
        // text area
        textArea = new TextArea();
        textArea.setId("box");

        root.getChildren().addAll(label, textField, calButton, selectButton, clearButton, textArea);

        return root;
    }

    private void calculationHandle(ActionEvent event) {
        String filePaths = textField.getText();
        if (filePaths.isBlank()) {
            textField.setPromptText("Please enter URL of File");
        }

        rd.read(filePaths);
        
        Calculation calculation = new Calculation();
        Double index = calculation.indexFlesch(rd);
        String level = calculation.level(index);
        String name = rd.getName();

        textArea.setText(String.format("%-32s:  %s\n%-25s:  %.0f\n%-24s:  %.0f\n%-22s:  %.0f\n%-26s:  %.0f\n%-29s :  %.3f\n%-31s:  %s", 
            name, filePaths, "Number of Syllabels", rd.getSyllables(),
            "Number of Words", rd.getWords(), "Number of Sentences", rd.getSentences(),
            "Number of Lines", rd.getLines(), "Flesch Index", index, 
            "Readability", level));
    }

    private void clearHandle(ActionEvent event) {
        textField.clear();
        textArea.clear();
    }
}