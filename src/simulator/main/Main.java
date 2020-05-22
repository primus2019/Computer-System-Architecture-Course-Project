package simulator.main;

import static simulator.tools.Status.CONTIGUOUS;
import static simulator.tools.Status.INDEXED;
import static simulator.tools.Status.LINKED;
import static simulator.tools.Status.WAITING;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import simulator.main.Init;
import simulator.main.FrameSetting;
import simulator.tools.Status;

public class Main extends Application {
    Status status = new Status(Status.WAITING);
    // status of method chosen
    String fileAddress = "";
    Method method;
    // vary from contiguous, linked, and indexed

    @Override
    public void start(Stage stg) {

        Pane root = new Pane();
        Scene scn = new Scene(root, 1150, 500);
        Init init = new Init(root);
        // initialization of controllers
        FrameSetting setting = new FrameSetting(stg);
        // initialization of listners and others
        int line = 10;
        // pre-set maximum line of space
        int row = 10;
        // pre-set maximum row of space

        Label[][] lbs = new Label[10][10];
        Rectangle[][] rtgs = new Rectangle[10][10];
        TextArea instructions = new TextArea();
        TextArea addressArea = new TextArea();
        TextArea fileInfo = new TextArea();
        Label enterDirectory = new Label();
        Button auto = new Button();
        Button next = new Button();
        Button pause = new Button();
        Button exit = new Button();
        Button contiguous = new Button();
        Button linked = new Button();
        Button indexed = new Button();
        Button fragmentation = new Button();
        Label currentAllocation = new Label();
        Label allocationStatus = new Label();

        Thread setUp = new Thread() {
            boolean ThreadFlag = true;
            // listner the status and initialize method
            @Override
            public void run() {
                while (ThreadFlag) {
                    if (status.showStatus() != WAITING) {
                        switch (status.showStatus()) {
                        case CONTIGUOUS:
                            method = new Contiguous(root, rtgs, instructions, fileInfo, line, row);
                            break;
                        case LINKED:
                            method = new Linked(root, rtgs, instructions, fileInfo, line, row);
                            break;
                        case INDEXED:
                            method = new Indexed(root, rtgs, instructions, fileInfo, line, row);
                            break;
                        }
                        method.setUp();
                        try {
                            sleep(1000);
                        } catch (InterruptedException e1) {
                            //System.out.println("sleep error???");
                        }
                        ThreadFlag = false;
                        //System.out.println("joined");
                        try {
                            join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        sleep(500);
                    } catch (Exception e) {
                        //System.out.println("sleep error???");
                        e.printStackTrace();
                    }
                }
            }
        };
        setUp.start();

        addressArea.setText("src\\simulator\\main\\test.txt");  // default testing file
        init.initial(lbs, rtgs, instructions, addressArea, enterDirectory, currentAllocation, allocationStatus, auto,
                next, exit, contiguous, linked, indexed, fileInfo, pause, fragmentation);
        setting.set(exit, contiguous, linked, indexed, allocationStatus, status, instructions, addressArea, fileInfo,
                next, auto, method, pause, fragmentation);

        Timeline autoPlayer = new Timeline();       // animation of automatic operations
        autoPlayer.setCycleCount(Timeline.INDEFINITE);
        KeyFrame autoNext = new KeyFrame(Duration.millis(500), e -> {
            if(method.hasNextInstruction())         // do remaining instructions, or disable buttons
                method.next();
            else{
                fragmentation.setDisable(false);
                pause.setDisable(true);
            }
        });
        autoPlayer.getKeyFrames().add(autoNext);

        Timeline autoFrag = new Timeline();         // animation of automatic defragmentation
        autoFrag.setCycleCount(Timeline.INDEFINITE);
        KeyFrame autoFragmentation = new KeyFrame(Duration.millis(100), e -> {
            if(!method.fragmentation()){            // do remaining instructions, or disable buttons
                autoFrag.stop();
            }
        });
        autoFrag.getKeyFrames().add(autoFragmentation);

        // exclusive running of auto and next for safety
        auto.setOnMouseClicked(e -> {
            autoPlayer.play();
            auto.setDisable(true);
            next.setDisable(true);
            pause.setDisable(false);
        });
        pause.setOnMouseClicked(e -> {
            autoPlayer.pause();
            auto.setDisable(false);
            next.setDisable(false);
            pause.setDisable(true);
        });
        next.setOnMouseClicked(e -> {
            if(method.hasNextInstruction()){            // do remaining instructions
                method.next();
            }
            else{                                       // when done, disable buttons
                fragmentation.setDisable(false);
                next.setDisable(true);
                auto.setDisable(true);
                pause.setDisable(true);
            }
        });

        fragmentation.setOnMouseClicked(e -> {
            autoFrag.play();
        });

		stg.initStyle(StageStyle.TRANSPARENT);          // transparent style
		//root.setStyle("-fx-background:transparent");    // transparent style
		//scn.setFill(null);                              // transparent system buttons
        scn.getStylesheets().add(getClass().getResource("fancy.css").toExternalForm());
        // get external css
        stg.setScene(scn);
        stg.setTitle("Simulator 季林成 2017012775 经71");
        stg.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}