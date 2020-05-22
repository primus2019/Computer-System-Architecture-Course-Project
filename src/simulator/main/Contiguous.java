package simulator.main;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import simulator.tools.Block;

public class Contiguous extends Method {
    /**
     * Initialize with given params.
     */
    public Contiguous(Pane p, Rectangle[][] r, TextArea ta, TextArea tb, int line, int row) {
        super(p, r, ta, tb, line, row);
    }

    @Override
    public void setUp() {
        super.setUp();
    }
    /**
     * Create blocks with given instructions.
     */
    @Override
    protected void createBlocks() {
        int[] rand = table.findRandomFree();
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int length = Integer.parseInt(instrTable[instrNo][2]);
        if (!table.isConsistentFree(length) || !table.isDiscreteFree(length + 1)) {
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No enough blocks!" + "\n");
            return;
        }
        System.out.println("is consistent free: " + length);
        Block directory = table.getBlock(0, 0);
        if (!table.isDirectory(directoryName)) {
            int[] temp = table.findRandomFree();
            // System.out.println("directory: " + temp[0] + temp[1]);
            directory = updateBlock(null, null, null, directoryName, temp[0], temp[1], 1, 1, true);
        } else {
            directory = table.findByfileName(directoryName);
        }
        rand = table.findConsistentFree(length);
        if(rand[0] == -1 && rand[1] == -1){
            fileInfo.appendText("Not enough blocks!" + "\n");
            return;
        }
        fileInfo.appendText(
                directoryName + "(" + (row * directory.getLine() + directory.getRow()) + ")" + "->" + fileName + ":");
        for (int i = 0; i < length; i++) {
            // System.out.println("block" + (i + 1) + ":" + rand[0] + rand[1]);
            updateBlock(directory, null, null, fileName, rand[0], rand[1], length, i + 1, false);
            fileInfo.appendText((row * rand[0] + rand[1]) + ",");
            rand = table.next(rand);
        }
        fileInfo.appendText("CREATE\n");
    }
    /**
     * Read blocks with given instructions.
     */
    @Override
    protected void readBlock() {
        // table.printFileTable();

        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int blockNumber = Integer.parseInt(instrTable[instrNo][2]);
        Block head = findBlock(directoryName, fileName, blockNumber);
        if (head == null) {
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such block!" + "\n");
            return;
        }
        fileInfo.appendText(
                directoryName + "->" + fileName + ":" + (row * head.getLine() + head.getRow()) + "READ" + "\n");
        // */
    }
    /**
     * Write blocks with given instructions.
     */
    @Override
    protected void writeBlock() {

        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int blockNumber = Integer.parseInt(instrTable[instrNo][2]);
        Block head = findBlock(directoryName, fileName, blockNumber);
        if (head == null) {
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such block!" + "\n");
            return;
        }
        fileInfo.appendText(
                directoryName + "->" + fileName + ":" + (row * head.getLine() + head.getRow()) + "WRITE" + "\n");
        // */
    }
    /**
     * Delete blocks with given instructions.
     */
    @Override
    protected void deleteBlocks() {
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int length = Integer.parseInt(instrTable[instrNo][2]);
        Block head = findBlock(directoryName, fileName, 1);
        if (head == null) {
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such file!" + "\n");
            return;
        }
        ////////////////// countnumber check
        Block temp = head;
        int count = 0;
        while (temp.getFileName().equals(fileName) && temp.getDirectory().getFileName().equals(directoryName)) {
            count++;
            temp = table.next(temp);
        } /////////////////////////// and deletion
        if (count != length) {
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "file length error!" + "\n");
            return;
        }
        for (int i = 0; i < length; i++) {
            temp = table.next(head);
            writeOffBlock(head);
            head = temp;
        }
        fileInfo.appendText(directoryName + "->" + fileName + "" + "deleted" + "\n");
    }
    /**
     * Return first block with given directoryName, fileName, blockNumber.
     */
    private Block findBlock(String directoryName, String fileName, int blockNumber) {
        Block temp = table.getBlock(0, 0);
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < row; j++) {
                temp = table.getBlock(i, j);
                if (temp.getDirectory() != null) {
                    if (temp.getDirectory().getFileName().equals(directoryName) && temp.getFileName().equals(fileName)
                            && temp.getBlockNumber() == blockNumber) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }
}