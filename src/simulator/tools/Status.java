package simulator.tools;

public class Status {
    // static for system allocation status
    public final static int     WAITING     = 0;
    public final static int     CONTIGUOUS  = 1;
    public final static int     LINKED      = 2;
    public final static int     INDEXED     = 3;

    private int status;
    
    public Status(int status){
        this.status = status;
    }
    /**
     * Set status.
     * @param status
     */
    public void setStatus(int status){
        this.status = status;
    }

    /**
     * Get status.
     * @return
     */
    public int showStatus(){
        return this.status;
    }
}