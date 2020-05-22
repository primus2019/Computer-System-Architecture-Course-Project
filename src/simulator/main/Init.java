package simulator.main;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Init {
    public Pane root;

    public Init (Pane p) {
        root = p;
    }
    /**
     * Initialize rtgs.
     */
    public void initRectangle(Rectangle[][] rtgs) {
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                rtgs[i][j] = new Rectangle(20, 20);
                rtgs[i][j].setFill(Color.WHITE);
                rtgs[i][j].setStroke(Color.BLACK);
                rtgs[i][j].setLayoutX(j * 40 + 25);
                rtgs[i][j].setLayoutY(i * 40 + 10);
                root.getChildren().add(rtgs[i][j]);
            }
        }
    }
    /**
     * Initialize lbs.
     */
    public void initLabels(Label[][] lbs) {
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                lbs[i][j] = new Label(String.valueOf(10 * i + j));
                lbs[i][j].setMinSize(20, 20);
                lbs[i][j].setMaxSize(20, 20);
                lbs[i][j].setLayoutX(j * 40 + 10);
                lbs[i][j].setLayoutY(i * 40 + 10);
                root.getChildren().add(lbs[i][j]);
            }
        }
    }
    /**
     * Initialize btn.
     */
    public void initButton(Button btn, String text, int length, int height, int layoutX, int layoutY) {
        btn.setText(text);
        btn.setMaxSize(length, height);
        btn.setMinSize(length, height);
        btn.setLayoutX(layoutX);
        btn.setLayoutY(layoutY);
        this.root.getChildren().add(btn);
    }
    /**
     * Initialize btn.
     */
    public static void initButton(Pane root, Button btn, String text, int length, int height, int layoutX, int layoutY) {
        btn.setText(text);
        btn.setMaxSize(length, height);
        btn.setMinSize(length, height);
        btn.setLayoutX(layoutX);
        btn.setLayoutY(layoutY);
        root.getChildren().add(btn);
    }
    /**
     * Initialize label with text, size and layout.
     */
    public void initLabel(Label lb, String text, int layoutX, int layoutY) {
        lb.setText(text);
        lb.setLayoutX(layoutX);
        lb.setLayoutY(layoutY);
        this.root.getChildren().add(lb);
    }
    /**
     * Initialize label with text, size and layout.
     */
    public static void initLabel(Pane root, Label lb, String text, int layoutX, int layoutY) {
        lb.setText(text);
        lb.setLayoutX(layoutX);
        lb.setLayoutY(layoutY);
        root.getChildren().add(lb);
    }
    /**
     * Initialize TextArea with size and layout.
     */
    public void initTextArea(TextArea ta, int width, int height, int layoutX, int layoutY) {
        ta.setMaxSize(width, height);
        ta.setMinSize(width, height);
        ta.setLayoutX(layoutX);
        ta.setLayoutY(layoutY);
        this.root.getChildren().add(ta);
    }
    /**
     * Initialize TextArea with size and layout.
     */
    public static void initTextArea(Pane root, TextArea ta, int width, int height, int layoutX, int layoutY) {
        ta.setMaxSize(width, height);
        ta.setMinSize(width, height);
        ta.setLayoutX(layoutX);
        ta.setLayoutY(layoutY);
        root.getChildren().add(ta);
    }
    /**
     * Initialize all controllers with specific text, size, and layout.
     */
    public void initial(Label[][] lbs, Rectangle[][] rtgs, TextArea instructions, TextArea addressArea, 
        Label enterDirectory, Label currentAllocation, Label allocationStatus, 
        Button auto, Button next, Button exit, Button contiguous, Button linked, 
        Button indexed, TextArea fileInfo, Button pause, Button fragmentation){
            initLabels     (lbs);
            initRectangle  (rtgs);
            initTextArea   (instructions,                        290, 400,             420, 95);
            initTextArea   (addressArea,                         250, 25,              420, 50);
            initTextArea   (fileInfo,                            290, 400,             720, 95);
            initLabel      (enterDirectory,      "Please enter your file directory",   420, 10);
            initLabel      (currentAllocation,   "Current allocation",                 720, 10);
            initLabel      (allocationStatus,    "waiting",                            720, 53);
            initButton     (auto,                "auto",         90, 25,               1050, 200);
            initButton     (next,                "step",         90, 25,               1050, 230);
            initButton     (exit,                "exit",         90, 25,               1050, 260);
            initButton     (pause,               "pause",        90, 25,               1050, 140);
            initButton     (fragmentation,       "defragmentation",90, 25,             1050, 170);
            initButton     (contiguous,          "contiguous",   90, 25,              1050, 50);
            initButton     (linked,              "linked",       90, 25,              1050, 80);
            initButton     (indexed,             "indexed",      90, 25,              1050, 110);
    }
}