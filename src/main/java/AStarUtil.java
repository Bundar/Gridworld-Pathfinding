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
        //calc g, h, f for start
        calcCosts(start);
        open.insert(start);
        while(!open.isEmpty()){
            Cells curr = open.deleteMin();//remove from open list the lowest f value cell
            if(curr.equals(target)){
                System.out.println("Path Found");
                return;
            }
            curr.visit();//add to closed list
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(!open.contains(c)){
                        open.insert(c);
                        c.setPrev(curr);
                        calcCosts(c);
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            calcCosts(c);//may need to reorder heap after this...
                        }
                    }
                }
            });
        }
        System.out.println("No Path...");
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
