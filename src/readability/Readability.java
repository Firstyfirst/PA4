package src.readability;

import java.io.File;

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

import src.readability.strategy.*;
/**
 * 
 * the main class for make GUI and run
 * 
 */
public class Readability extends Application {
    /** the area that can enter file path or url link */
    TextField textField;
    /** file from file chooser */
    File selectedFile;
    /** the area for shownig text output */
    TextArea textArea;

    ReadStrategy rd = new ReadStrategy();

    /**
     * the main method for running program
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** 
     * overide the start method in application
     */
    @Override
    public void start(Stage primaryStage) {
        FlowPane flowPane = initComponent(primaryStage);
        primaryStage.setTitle("Flesh Readability Index");
        Scene scene = new Scene(flowPane, 550, 250);
        scene.getStylesheets().add("src/readability/css/styleSheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * determine the initial component of flow-pane 
     * @param primaryStage stage for determine paramiter in filechooser
     * @return the flowpane that there are all component of this application
     */
    public FlowPane initComponent(Stage primaryStage) {
        FlowPane root = new FlowPane();
        // set flowpane apperence
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Label label = new Label("file or url : ");
        label.setId("box2");
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
        // add all component in flowpane.
        root.getChildren().addAll(label, textField, calButton, selectButton, clearButton, textArea);
        return root;
    }

    /**
     * make the action when calculation button was clicked
     * to show in text area.
     * @param event the event when make the action.
     */
    private void calculationHandle(ActionEvent event) {
        String filePaths = textField.getText();
        if (filePaths.isBlank()) {
            textField.setPromptText("Please enter URL of File");
        }
        // read count and calculate from text file or url from text field
        rd.read(filePaths);
        
        Calculation calculation = new Calculation();
        Double index = calculation.indexFlesch(rd);
        String level = calculation.level(index);
        String name = rd.getName();

        textArea.setText(String.format("%-32s:  %s\n%-24s:  %.0f\n%-24s:  %.0f\n%-22s:  %.0f\n%-26s:  %.0f\n%-28s :  %.3f\n%-31s:  %s", 
            name, filePaths, "Number of Syllabels", rd.getSyllables(),
            "Number of Words", rd.getWords(), "Number of Sentences", rd.getSentences(),
            "Number of Lines", rd.getLines(), "Flesch Index", index, 
            "Readability", level));
    }

    /**
     * clear text area and text flied when click clear button
     * @param event the event when make the action
     */
    private void clearHandle(ActionEvent event) {
        textField.clear();
        textArea.clear();
    }
}