package simulator.tools;

public class Block {
    private Block directory;        // file directory
    private Block nextBlock;        // next block in logic address
    private Block lastBlock;        // last block in logic address
    private String fileName;        // file name
    private int myLine;             // block line
    private int myRow;              // block row
    private int fileLength;         // file length
    private int blockNumber;        // position in file
    private boolean occupied;       // whether occupied
    private boolean isDirectory;    // whether directory
    /**
     * Initialize a free block.
     * @param myLine
     * @param myRow
     */
    public Block(int myLine, int myRow){
        this.directory = null;
        this.nextBlock = null;
        this.lastBlock = null;
        this.fileName = "";
        this.myLine = myLine;
        this.myRow = myRow;
        this.fileLength = 0;
        this.blockNumber = 0;
        this.occupied = false;
        this.isDirectory = false;
    }
    // set param functions
    public void setDirectory(Block directory){this.directory = directory;}
    public void setNextBlock(Block nextBlock){this.nextBlock = nextBlock;}
    public void setLastBlock(Block lastBlock){this.lastBlock = lastBlock;}
    public void setFileName(String fileName){this.fileName = fileName;}
    public void setLine(int line){this.myLine = line;}
    public void setRow(int row){this.myRow = row;}
    public void setfileLength(int fileLength){this.fileLength = fileLength;}
    public void setBlockNumber(int blockNumber){this.blockNumber = blockNumber;}
    public void setOccupied(boolean occupied){this.occupied = occupied;}
    public void setIsDirectory(boolean isDirectory){this.isDirectory = isDirectory;}
    // get param functions
    public Block getDirectory(){return directory;}
    public Block getNextBlock(){return nextBlock;}
    public Block getLastBlock(){return lastBlock;}
    public String getFileName(){return fileName;}
    public int getLine(){return myLine;}
    public int getRow(){return myRow;}
    public int getFileLength(){return fileLength;}
    public int getBlockNumber(){return blockNumber;}
    public boolean getIsOccupied(){return occupied;}
    public boolean getIsDirectory(){return isDirectory;}
}