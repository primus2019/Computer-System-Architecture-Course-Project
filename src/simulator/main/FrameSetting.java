package simulator.main;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import simulator.tools.EasierIO;
import simulator.tools.Status;

public class FrameSetting {
    Stage stg;

    public FrameSetting(Stage s) {
        stg = s;
    }
    /**
     * Set the given controllers in specific orders.
     */
    public void set(Button exit, Button contiguous, Button linked, Button indexed, Label allocationStatus,
            Status status, TextArea instructions, TextArea addressArea, TextArea fileInfo, Button next, 
            Button auto, Method method, Button pause, Button fragmentation) {
                // disable all buttons before loading the file
                contiguous.setDisable(true);
                linked.setDisable(true);
                indexed.setDisable(true);
                auto.setDisable(true);
                next.setDisable(true);
                pause.setDisable(true);
                fragmentation.setDisable(true);
                // disable fragmentation on click
                fragmentation.setOnMouseClicked(e -> {
                    fragmentation.setDisable(true);
                });
                // exit on click
                exit.setOnMouseClicked(e -> {
                    stg.close();
                    System.exit(0);
                });
                // set contiguous
                contiguous.setOnMouseClicked(e -> {
                    allocationStatus.setText("contiguous");
                    status.setStatus(Status.CONTIGUOUS);
                    contiguous.setDisable(true);
                    linked.setDisable(true);
                    indexed.setDisable(true);
                    auto.setDisable(false);
                    next.setDisable(false);
                });
                // set linked
                linked.setOnMouseClicked(e -> {
                    allocationStatus.setText("linked");
                    status.setStatus(Status.LINKED);
                    contiguous.setDisable(true);
                    linked.setDisable(true);
                    indexed.setDisable(true);
                    auto.setDisable(false);
                    next.setDisable(false);
                });
                // set indexed
                indexed.setOnMouseClicked(e -> {
                    allocationStatus.setText("indexed");
                    status.setStatus(Status.INDEXED);
                    contiguous.setDisable(true);
                    linked.setDisable(true);
                    indexed.setDisable(true);
                    auto.setDisable(false);
                    next.setDisable(false);
                });
                // set instructions
                instructions.setWrapText(true);
                instructions.setEditable(false);
                // set addressArea
                addressArea.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        String fileAddress = "";
                        fileAddress = fileAddress.concat(addressArea.getText());
                        instructions.setText(EasierIO.fileRead(fileAddress));
                    }
                });
                addressArea.setOnKeyReleased(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        addressArea.clear();
                        contiguous.setDisable(false);
                        linked.setDisable(false);
                        indexed.setDisable(false);
                        addressArea.setEditable(false);
                    }
                });
                fileInfo.setEditable(false);
            }
}