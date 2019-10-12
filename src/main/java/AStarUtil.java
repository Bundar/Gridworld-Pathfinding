public class AStarUtil {

    private GridWorld gridWorld;
    private final Cells start;
    private final Cells target;

    public AStarUtil(GridWorld gridWorld, Cells startCell, Cells target){
        this.gridWorld = gridWorld;
        this.start = startCell;
        this.target = target;
    }

    public void repeatedForwardAStar(){
        CellHeap open = new CellHeap();
        CellHeap closed = new CellHeap();

        open.insert(start);
        while(true){
            Cells curr = open.deleteMin();
            closed.insert(curr);

            if(curr.equals(target)){
                return;
            }

            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED){
                    if(c.fcost() < curr.fcost()){
                        
                    }
                }
            });

        }
    }
    public void repeatedBackwardAStar(){

    }
    public void adaptiveAStar(){

    }
}
