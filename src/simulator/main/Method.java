package simulator.main;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulator.tools.Block;
import simulator.tools.Table;

public abstract class Method {
    protected int line;
    protected int row;
    protected Pane root;
    protected Rectangle[][] rtgs;
    protected String[][] instrTable;
    protected TextArea instructions;
    protected TextArea fileInfo;
    protected Table table;
    protected int instrNo;
    /**
     * Initialize with root, rtgs, instructions, fileInfo, table, line, row, instrNo, and instrTable.
     */
    public Method(Pane p, Rectangle[][] r, TextArea ta, TextArea tb, int line, int row) {
        this.root = p;
        this.rtgs = r;
        this.instructions = ta;
        this.fileInfo = tb;
        this.table = new Table(line, row);
        this.line = line;
        this.row = row;
        this.instrNo = 0;
        this.instrTable = new String[100][4];
        //System.out.println("method initialized");
        // Alert testInfo = new Alert(Alert.AlertType.INFORMATION, "Contiguous
        // allocation activated!");
        // testInfo.show();
    }
    /**
     * Set up by loading instrTable and set fileInfo.
     */
    public void setUp() {
        //System.out.println("setUp start");
        //updateBlock(0, 0, "MapTable");
        fileInfo.setText("");
        //fileInfo.appendText("(0,0): Directory maptable\n");
        getInstructionTable();
        //instructions.setText("");
        //printInstructions();
        //*/
        //System.out.println("setUp finished");
    }
    /**
     * Load instrTable from instructions.
     */
    public void getInstructionTable(){
        String temp = instructions.getText();
        for(int i = 0; i < 100; i++)
            for(int j = 0; j < 4; j++)
                instrTable[i][j] = "";
        int instructionNo = 0;
        for(int i = 0; i < temp.length(); ){
            for(int j = 0; j < 4; j++){
                while((temp.charAt(i) >= 'A' && temp.charAt(i) <= 'Z') ||
                    (temp.charAt(i) >= 'a' && temp.charAt(i) <= 'z') ||
                    (temp.charAt(i) >= '0' && temp.charAt(i) <= '9') ||
                    temp.charAt(i) == '_'
                ){
                    //System.out.println(i + ":" + temp.substring(i, i + 1));
                    instrTable[instructionNo][j] += String.valueOf(temp.charAt(i));
                    ////System.out.print(String.valueOf(temp.charAt(i)));
                    i++;
                    if(i >= temp.length()) break;
                }
                i++;
                ////System.out.print(",");
            }
            ////System.out.println("instruction number: " + instructionNo);
            instructionNo++;
            ////System.out.print("\n");
        }
    }
    /**
     * Return the Block updated with the given params.
     */
    public Block updateBlock(Block directory, Block nextBlock, Block lastBlock, String fileName, int line, int row, int fileLength, int blockNumber, boolean isDirectory){
        rtgs[line][row].setFill(Color.BLACK);
        //table.updateTable(line, row, fileName);
        return table.updateTable(true, directory, null, lastBlock, fileName, line, row, fileLength, blockNumber, isDirectory);
    }
    /**
     * Write off given block.
     */
    public void writeOffBlock(Block temp){
        rtgs[temp.getLine()][temp.getRow()].setFill(Color.WHITE);
        table.updateTable(false, null, null, null, "", temp.getLine(), temp.getRow(), 0, 0, false);
    }
    /**
     * Write off given block.
     */
    public void writeOffBlock(int line, int row){
        rtgs[line][row].setFill(Color.WHITE);
        table.updateTable(false, null, null, null, "", line, row, 0, 0, false);
    }
    /**
     * Swap given blocks and alter rtgs.
     */
    public void swapBlocks(int l1, int r1, int l2, int r2){
        table.exchangeBlocks(l1, r1, l2, r2);
        if(table.getBlock(l1, r1).getIsOccupied()){
            rtgs[l1][r1].setFill(Color.BLACK);
        }else{
            rtgs[l1][r1].setFill(Color.WHITE);
        }
        if(table.getBlock(l2, r2).getIsOccupied()){
            rtgs[l2][r2].setFill(Color.BLACK);
        }else{
            rtgs[l2][r2].setFill(Color.WHITE);
        }
    }
    /**
     * Swap given blocks and alter rtgs.
     */
    public void swapBlocks(Block b1, Block b2){
        swapBlocks(b1.getLine(), b1.getRow(), b2.getLine(), b2.getRow());
    }
    /**
     * Return whether instrTable has more instructions.
     */
    public boolean hasNextInstruction(){
        if(instrTable[instrNo][0] == "" || instrTable[instrNo][0] == "\n"){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * Print instructions for tests.
     */
    public void printInstructions() {
        //System.out.println("instrTable.length = " + instrTable.length);
        //System.out.println("instrTable[0].length = " + instrTable[0].length);
        for(int i = 0; instrTable[i][0] != ""; i++){
            //System.out.println("i = " + i);
            for(int j = 0; j < 3; j++){
                //System.out.println("i = " + i + ", j = " + j);
                instructions.appendText(instrTable[i][j] + ",");
            }
            instructions.appendText(instrTable[i][3] + "\n");
            //System.out.println("eol: i = " + i);
        }
    }
    /**
     * Perform next instruction.
     */
    public void next(){
        if(!hasNextInstruction()){
            new Alert(Alert.AlertType.INFORMATION, "No more instructions!").showAndWait();
            return;
        }
        switch(instrTable[instrNo][3]){
            case "C":
            createBlocks();
            break;
            case "R":
            readBlock();
            break;
            case "W":
            writeBlock();
            break;
            case "D":
            deleteBlocks();
            break;
            default:
        }
        instrNo++;
    }
    /**
     * Perform instruction when instrTable[][3] is "C".
     */
    protected abstract void createBlocks();
    /** 
     * Perform instruction when instrTable[][3] is "R".
     */
    protected abstract void readBlock();
    /**
     * Perform instruction when instrTable[][3] is "W".
     */
    protected abstract void writeBlock();
    /**
     * Perform instruction when instrTable[][3] is "D".
     */
    protected abstract void deleteBlocks();
    /**
     * Return number of remaining instruction.
     */
    public int getInstructionNumber(){
        int i = 0;
        for(i = 0; instrTable[i][0] != ""; i++){}
        return i - instrNo;
    }
    /**
     * Perform one defragmentation between an occupied and a free block.
     */
    public void fragmentation(int[] block) {
        int[] temp = table.findNextOccupied(block);
        if(temp[0] == -1 && temp[1] == -1){
            fileInfo.appendText("No fragmentation" + "\n");
            return;
        }
        //System.out.println("find: " + temp[0] + temp[1]);
        swapBlocks(block[0], block[1], temp[0], temp[1]);
        //System.out.println("" + block[0] + block[1] + table.getBlock(block[0], block[1]).getIsOccupied());
        //System.out.println("" + temp[0] + temp[1] + table.getBlock(temp[0], temp[1]).getIsOccupied());
    }
    /**
     * Perform one defragmentation between an occupied and a free block(deprecated).
     */
    public int[] hasFragmentation(){
        int[] head = new int[2];
        head[0] = 0;
        head[1] = 0;
        head = table.findNextFree(head);
        if(head[0] == -1 && head[1] == -1){
            fileInfo.appendText("No free space for defragmentation" + "\n");
            return head;
        }
        int[] tail = head;
        tail = table.findNextOccupied(tail);
        if(tail[0] == -1 && tail[1] == -1){
            fileInfo.appendText("No fragmentation" + "\n");
            return head;
        }
        return head;
    }
    /**
     * Perform one defragmentation between an occupied and a free block(deprecated).
     */
    public boolean fragmentation(){
        for(int i = 0; i < line; i ++){
            for(int j = 0; j < row; j++){
                //System.out.print("" + i + j + !table.getBlock(i, j).getIsOccupied() + ",");
            }
            //System.out.println("");
        }
        int[] head = new int[2];
        head[0] = 0;
        head[1] = 0;
        head = table.findNextFree(head);
        if(head[0] == -1 && head[1] == -1){
            fileInfo.appendText("No free space for defragmentation" + "\n");
            return false;
        }
        int[] tail = head;
        tail = table.findNextOccupied(tail);
        if(tail[0] == -1 && tail[1] == -1){
            fileInfo.appendText("No fragmentation" + "\n");
            return false;
        }
        swapBlocks(head[0], head[1], tail[0], tail[1]);
        return true;
    }
}