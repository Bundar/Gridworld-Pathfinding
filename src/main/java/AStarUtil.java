import java.util.ArrayList;
import java.util.List;

public class AStarUtil {

    private GridWorld gridWorld;
    private final Cells start;
    private final Cells target;

    AStarUtil(GridWorld gridWorld, Cells startCell, Cells target){
        this.gridWorld = gridWorld;
        this.start = startCell;
        this.target = target;
    }

    boolean repeatedForwardAStar(){
        CellHeap open = new CellHeap();
        //calc g, h, f for start
        calcCosts(start, target);
        open.insert(start);
        while(!open.isEmpty()){
            Cells curr = open.deleteMin();//remove from open list the lowest f value cell
            if(curr.equals(target)){
                System.out.println("Path Found");
                return true;
            }
            curr.visit();//add to closed list
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(!open.contains(c)){
                        open.insert(c);
                        c.setPrev(curr);
                        calcCosts(c, target);
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            calcCosts(c, target);//may need to reorder heap after this...
                        }
                    }
                }
            });
        }
        System.out.println("No Path...");
        return false;
    }

    private void calcCosts(Cells c, Cells searchtarget) {
        c.calcGCost();
        c.calcHCost(searchtarget);
        c.calcFCost();
    }


    boolean repeatedBackwardAStar(){
        CellHeap open = new CellHeap();
        //calc g, h, f for start
        calcCosts(target,start);
        open.insert(target);
        while(!open.isEmpty()){
            Cells curr = open.deleteMin();
            if(curr.equals(start)){
                System.out.println("Backward Path Found");
                return true;
            }
            curr.visit();
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(!open.contains(c)){
                        open.insert(c);
                        c.setPrev(curr);
                        calcCosts(c, start);//might need to chage to fix the calc cost thing
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            calcCosts(c, start);//may need to reorder heap after this...
                        }
                    }
                }
            });
        }
        System.out.println("No Path...");
        return false;
    }

    public void adaptiveAStar(){

    }

    List<Cells> getTreePath(Cells startCell, Cells endCell){
        List<Cells> cellPath = new ArrayList<>();
        Cells tmp = endCell;
        while(tmp != startCell){
            if(tmp.getPrev().equals(tmp)){
                break;
            }
            cellPath.add(tmp);
            tmp = tmp.getPrev();
        }
        cellPath.add(tmp);
        return cellPath;
    }

    String storeableCellPath(Cells startCell, Cells endCell){
        String fileOut = "" + startCell.getI() + " " + startCell.getJ() + "\n" +
                endCell.getI() + " " + endCell.getJ() + "\n";

        List<Cells> cellsPath = getTreePath(startCell, endCell);

        for(int i = 0 ; i < cellsPath.size() ; i++){
            Cells c = cellsPath.get(i);
            fileOut += c.getI() + " " + c.getJ();
            if(i != cellsPath.size()-1){
                fileOut+= "\n";
            }
        }
        return fileOut;
    }
}
