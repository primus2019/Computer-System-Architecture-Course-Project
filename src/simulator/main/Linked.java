package simulator.main;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import simulator.tools.Block;

public class Linked extends Method {
    /**
     * Initialize with given params.
     */
    public Linked(Pane p, Rectangle[][] r, TextArea ta, TextArea tb, int line, int row) {
        super(p, r, ta, tb, line, row);

    }

    @Override
    public void setUp(){
        super.setUp();
    }
    /**
     * Create blocks with given instructions.
     */
    @Override
    protected void createBlocks() {
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int fileLength = Integer.parseInt(instrTable[instrNo][2]);
        Block directory = null;
        if(table.findByfileNameAndDirectory(fileName, directoryName) != null){
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "duplicate file!\n");
            return;
        }
        ////////////////////////////////////////
        if(!table.isDiscreteFree(fileLength + 1)){
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "Not enough blocks!" + "\n");
            return;
        }
        int[] temp = table.findRandomFree();
        
        if(!table.isDirectory(directoryName)){
            directory = updateBlock(null, null, null, directoryName, temp[0], temp[1], 1, 1, true);
            //System.out.println("update directory: " + (row * temp[0] + temp[1]));
        } else {
            directory = table.findByfileName(directoryName);
            //System.out.println("old directory: " + (row * directory.getLine() + directory.getRow()));
        }
        fileInfo.appendText(directoryName + "(" + (row * directory.getLine() + directory.getRow()) + ")" + "->" + fileName + ": ");
        Block[] whole = new Block[fileLength];

        int[] block = table.findRandomFree();
        //System.out.println("update table: " + (row * block[0] + block[1]));
        boolean isDirectory = table.isDirectory(fileName);
        whole[0] = updateBlock(directory, null, null, fileName, block[0], block[1], fileLength, 1, isDirectory);
        fileInfo.appendText((row * whole[0].getLine() + whole[0].getRow()) + "");
        for(int i = 1; i < fileLength; i++){
            block = table.findRandomFree();
            //System.out.println("update table: " + (row * block[0] + block[1]));
            isDirectory = table.isDirectory(fileName);
            whole[i] = updateBlock(directory, null, whole[i - 1], fileName, block[0], block[1], fileLength, (i + 1), isDirectory);
            fileInfo.appendText("->" + (row * whole[i].getLine() + whole[i].getRow()));
        }
        //System.out.print(whole[0].getFileName());
        for(int i = 0; i < fileLength - 1; i++){
            whole[i].setNextBlock(whole[i + 1]);
            //System.out.print("(" + (row * whole[i].getLine() + whole[i].getRow()) + ")" + "->");
        }
        fileInfo.appendText("CREATE\n");
    }
    /**
     * Read blocks with given instructions.
     */
    @Override
    protected void readBlock() {
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int blockNumber = Integer.parseInt(instrTable[instrNo][2]);
        Block temp = findBlock(directoryName, fileName, blockNumber);
        if(temp != null)
            fileInfo.appendText(directoryName + "->" + fileName + ":" + (row * temp.getLine() + temp.getRow()) + "READ\n");
        else
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such block!\n");
    }
    /**
     * Write blocks with given instructions.
     */
    @Override
    protected void writeBlock() {
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int blockNumber = Integer.parseInt(instrTable[instrNo][2]);
        Block temp = findBlock(directoryName, fileName, blockNumber);
        if(temp != null)
            fileInfo.appendText(directoryName + "->" + fileName + ":" + (row * temp.getLine() + temp.getRow()) + "WRITE\n");
        else
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such block!\n");
    }
    /**
     * Delete blocks with given instructions.
     */
    @Override
    protected void deleteBlocks() {
        String directoryName = instrTable[instrNo][0];
        String fileName = instrTable[instrNo][1];
        int fileLength = Integer.parseInt(instrTable[instrNo][2]);
        Block head = findBlock(directoryName, fileName, 1);
        int count = 1;
        Block temp = head;
        if(temp == null){
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "No such file!" + "\n");
            return;
        }
        while(temp.getNextBlock() != null){
            count++;
            temp = temp.getNextBlock();
        }
        if(count != fileLength){
            fileInfo.appendText(directoryName + "->" + fileName + ":" + "wrong file length on deletion!!!" + "\n");
            return;
        }
        temp = head;
        for(int i = 0; i < fileLength - 1; i++){
            temp = head.getNextBlock();
            writeOffBlock(head);
            head = temp;
        }
        writeOffBlock(head);
        fileInfo.appendText(directoryName + "->" + fileName + ":" + "file deleted" + "\n");
    }
    /**
     * Return first block with given directoryName, fileName, blockNumber, starting from [0, 0].
     */
    private Block findBlock(String directoryName, String fileName, int blockNumber){
        Block temp = table.getBlock(0, 0);
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                temp = table.getBlock(i, j);
                if(temp.getDirectory() != null){
                    if(temp.getDirectory().getFileName().equals(directoryName) &&
                    temp.getFileName().equals(fileName) &&
                    temp.getBlockNumber() == blockNumber){
                        return temp;
                    }
                }
            }
        }
        return null;
    }
}