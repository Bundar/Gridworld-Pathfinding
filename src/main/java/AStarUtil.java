public class AStarUtil {

    private GridWorld gridWorld;
    private final Cells start;
    private final Cells end;

    public AStarUtil(GridWorld gridWorld, Cells startCell, Cells endCell){
        this.gridWorld = gridWorld;
        this.start = startCell;
        this.end = endCell;
    }

    public void repeatedForwardAStar(){
        CellHeap open = new CellHeap();
        CellHeap closed = new CellHeap();


    }
    public void repeatedBackwardAStar(){

    }
    public void adaptiveAStar(){

    }
}
