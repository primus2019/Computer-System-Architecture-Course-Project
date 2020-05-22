package simulator.tools;

public class Table {
    private Block[][] table;            // information stores in block
    private int line;                   // overall maximum line of space
    private int row;                    // overall maximum row of space

    /**
     * Initialize with specific line and row, no larger than 10.
     * @param line
     * @param row
     */
    public Table(int line, int row) {
        this.line = line;
        this.row = row;
        table = new Block[line][row];
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                table[i][j] = new Block(i, j);
            }
        }
    }

    /**
     * Update specific block in table, tagged as occupied.
     * @param isOccupied
     * @param directory
     * @param nextBlock
     * @param lastBlock
     * @param fileName
     * @param myLine
     * @param myRow
     * @param fileLength
     * @param blockNumber
     * @param isDirectory
     * @return
     */
    public Block updateTable(boolean isOccupied, Block directory, Block nextBlock, Block lastBlock, String fileName, int myLine, int myRow, int fileLength, int blockNumber, boolean isDirectory){
        Block temp = table[myLine][myRow];
        temp.setOccupied(isOccupied);
        temp.setDirectory(directory);
        temp.setNextBlock(nextBlock);
        temp.setLastBlock(lastBlock);
        temp.setFileName(fileName);
        temp.setLine(myLine);
        temp.setRow(myRow);
        temp.setfileLength(fileLength);
        temp.setBlockNumber(blockNumber);
        temp.setIsDirectory(isDirectory);
        return temp;
    }
    /**
     * Return whether given block is free.
     * @param line
     * @param row
     * @return
     */
    public boolean isFree(int line, int row){return !table[line][row].getIsOccupied();}
    
    /**
     * Return whether given block is free.
     * @param line
     * @param row
     * @return
     */
    public boolean isFree(int[] block){return isFree(block[0], block[1]);}

    /**
     * Return whether consistent free blocks exists in table with given length.
     * @param length
     * @return
     */
    public boolean isConsistentFree(int length){
        int[] temp = new int[2];
        temp = findRandomFree();
        if(temp[0] == -1 && temp[1] == -1){
            return false;
        }
        int count = 0;
        for(int i = 0; i < line * row; i++){
            if(isFree(temp)){
                count++;
            }else{
                count = 0;
            }
            if(count == length){
                return true;
            }
            temp = next(temp);
        }
        return false;
    }
    /**
     * Return head of the first group of consistent free blocks exists in table with given length.
     * @param length
     * @return
     */
    public int[] findConsistentFree(int length){
        int[] head = new int[2];
        head = findRandomFree();
        if(head[0] == -1 && head[1] == -1){
            return head;
        }
        int[] temp = head;
        for(int i = 0; i < line * row; i++){
            for(int j = 0; j < length; j++){
                if(!isFree(temp)){
                    break;
                }
                if(j == length - 1){
                    return head;
                }
                temp = next(temp);
            }
            head = next(head);
            temp = head;
        }
        head[0] = -1;
        head[1] = -1;
        return head;
    }
    /**
     * Return whether discrete free blocks exists in table with given length.
     * @param length
     * @return
     */
    public boolean isDiscreteFree(int length){
        int count = 0;
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                if(isFree(i, j)) count++;
            }
        }
        if(count >= length) return true;
        return false;
    }
    /**
     * Return isDirectory of given block.
     * Return false if not found.
     * @param fileName
     * @return
     */
    public boolean isDirectory(String fileName){
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                if(table[i][j].getDirectory() != null){
                    ////System.out.println("check directory: " + (row * table[i][j].getLine() + table[i][j].getRow()));
                    if(table[i][j].getDirectory().getFileName().equals(fileName))
                        return true;
                }
            }
        }
        return false;
    }    
    /**
     * Return next block of given block in phisical address.
     * Return the first if the last is given.
     * @param block
     * @return
     */
    public int[] next(int line, int row){
        int[] temp = new int[2];
        if(row < this.row - 1){
            temp[0] = line;
            temp[1] = row + 1;
        }else if(row == this.row - 1 && line < this.line - 1){
            temp[0] = line + 1;
            temp[1] = 0;
        }else if(row == this.row - 1 && line == this.line - 1){
            temp[0] = 0;
            temp[1] = 0;
        }
        return temp;
    }
    /**
     * Return next block of given block in phisical address.
     * Return the first if the last is given.
     * @param block
     * @return
     */
    public int[] next(int[] block){return next(block[0], block[1]);}
    /**
     * Return next block of given block and given distance in phisical address.
     * @param block
     * @return
     */
    public int[] next(int[] block, int distance){
        for(int i = 0; i < distance - 1; i++) block = next(block);
        return block;
    }
    /**
     * Return next block of given block in phisical address.
     * Return the first if the last is given.
     * @param block
     * @return
     */
    public Block next(Block block){
        int[] temp = next(block.getLine(), block.getRow());
        return getBlock(temp[0], temp[1]);
    }
    /**
     * Return random free block.
     * Return [-1, -1] if not found.
     * @return
     */
    public int[] findRandomFree(){
        int[] block = new int[2];
        block[0] = (int)(Math.random() * 100) % line;
        block[1] = (int)(Math.random() * 100) % row;
        for(int i = 0; i < line * row; i++){
            if(isFree(block)) return block;
            block = next(block);
        }
        block[0] = -1;
        block[1] = -1;
        return block;
    }
    /**
     * Return first block with given fileName, starting from [0, 0].
     * Return null if not found.
     * @param fileName
     * @return
     */
    public Block findByfileName(String fileName){
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                if(table[i][j].getFileName().equals(fileName)){
                    return table[i][j];
                }
            }
        }
        return null;
    }
    /**
     * Return isDirectory of given block.
     * @param line
     * @param row
     * @return
     */
    public boolean isDirectory(int line, int row){return table[line][row].getIsDirectory();}
    /**
     * Return isDirectory of given block.
     * @param block
     * @return
     */
    public boolean isDirectory(int[] block){return isDirectory(block[0], block[1]);}
    /**
     * Get given block in table.
     * @param line
     * @param row
     * @return
     */
    public Block getBlock(int line, int row){return table[line][row];}
    /**
     * Find first block with given fileName and directory, starting from [0, 0].
     * Return null if not found.
     * @param fileName
     * @param directory
     * @return
     */
    public Block findByfileNameAndDirectory(String fileName, String directory){
        for(int i = 0; i < line; i++){
            for(int j = 0; j < row; j++){
                if(table[i][j].getDirectory() != null){
                    if(table[i][j].getFileName().equals(fileName) && 
                    table[i][j].getDirectory().getFileName().equals(directory)){
                        return table[i][j];
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Find last block of given block in physical address.
     * Return the last in table if the first is given.
     * @param block
     * @return
     */
    public int[] forward(int line, int row){
        int[] temp = new int[2];
        temp[0] = line;
        temp[1] = row;
        if(row > 0){
            temp[1]--;
            return temp;
        }
        if(row == 0 && line > 0){
            temp[1] = this.row - 1;
            temp[0]--;
            return temp;
        }
        if(row == 0 && line == 0){
            temp[1] = this.row - 1;
            temp[0] = this.line - 1;
            return temp;
        }
        return null;
    }

    /**
     * Find last block of given block in physical address.
     * Return the last in table if the first is given.
     * @param block
     * @return
     */
    public int[] forward(int[] block){
        return forward(block[0], block[1]);
    }
    /**
     * Find last block of given block in physical address.
     * Return the last in table if the first is given.
     * @param block
     * @return
     */
    public Block forward(Block block){
        int[] temp = forward(block.getLine(), block.getRow());
        return getBlock(temp[0], temp[1]);
    }
    /**
     * Exchange given blocks
     * @param l1
     * @param r1
     * @param l2
     * @param r2
     */
    public void exchangeBlocks(int l1, int r1, int l2, int r2){
        Block temp = getBlock(l1, r1);
        table[l1][r1] = getBlock(l2, r2);
        table[l2][r2] = temp;
        table[l1][r1].setLine(l2);
        table[l1][r1].setRow(r2);
        table[l2][r2].setLine(l1);
        table[l2][r2].setRow(r1);
    }
    /**
     * Exchange given blocks.
     * @param b1
     * @param b2
     */
    public void exchangeBlocks(int[] b1, int[] b2){
        exchangeBlocks(b1[0], b1[1], b2[0], b2[1]);
    }
    /**
     * Exchange given blocks.
     * @param b1
     * @param b2
     */
    public void exchangeBlocks(Block b1, Block b2){
        exchangeBlocks(b1.getLine(), b1.getRow(), b2.getLine(), b2.getRow());
    }
    /**
     * Find next occupied block in table, ranging from given block to the last in table.
     * Return [-1, -1] if not found.
     * @param block
     * @return
     */
    public int[] findNextOccupied(int[] block){
        int[] temp = new int[2];
        temp[0] = block[0];
        temp[1] = block[1];
        while(temp[0] != line - 1 || temp[1] != row - 1){
            if(table[temp[0]][temp[1]].getIsOccupied()){
                return temp;
            }
            temp = next(temp);
        }
        if(table[temp[0]][temp[1]].getIsOccupied()){
            return temp;
        }
        temp[0] = -1;
        temp[1] = -1;
        return temp;
    }
    /**
     * Find next free block in table, ranging from given block to the last in table.
     * Return [-1, -1] if not found.
     * @param block
     * @return
     */
    public int[] findNextFree(int[] block){
        int[] temp = new int[2];
        temp[0] = block[0];
        temp[1] = block[1];
        while(temp[0] != line - 1 || temp[1] != row - 1){
            if(!table[temp[0]][temp[1]].getIsOccupied()){
                return temp;
            }
            temp = next(temp);
        }
        if(!table[temp[0]][temp[1]].getIsOccupied()){
            return temp;
        }
        temp[0] = -1;
        temp[1] = -1;
        return temp;
    }
}