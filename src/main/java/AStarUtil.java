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

        //calc g, h, f for start
        calcCosts(start);

        open.insert(start);
        while(true){
            Cells curr = open.deleteMin();

            closed.insert(curr);

            if(curr.equals(target)){
                return;
            }

            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED){
                    c.setPrev(curr);
                    calcCosts(c);
                    if(curr.getGCost() + 1 < c.getGCost()){

                    }
                }
            });

        }
    }

    private void calcCosts(Cells c) {
        c.calcGCost();
        c.calcHCost(target);
        c.calcFCost();
    }

    public void repeatedBackwardAStar(){

    }
    public void adaptiveAStar(){

    }
}
