import java.util.ArrayList;
import java.util.List;

public class AStarUtil {

    private GridWorld gridWorld;
    private Cells start;
    private Cells target;

    AStarUtil(){}

    AStarUtil(GridWorld gridWorld, Cells startCell, Cells target){
        this.gridWorld = gridWorld;
        this.start = startCell;
        this.target = target;
    }

    boolean repeatedForwardAStar(){
        CellHeap open = new CellHeap();
        calcCosts(start, target);
        open.insert(start);
        while(open.notEmpty()){
            Cells curr = open.deleteMin();
            if(curr.equals(target)){
//                System.out.println(curr.getGCost());
                return true;
            }
            curr.visit();
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(open.isNotContained(c)){
                        c.setPrev(curr);
                        calcCosts(c, target);
                        open.insert(c);
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            calcCosts(c, target);
                            open.buildHeap();
                        }
                    }
                }
            });
        }
        return false;
    }

    private void calcCosts(Cells c, Cells target) {
        c.calcGCost();
        c.calcHCost(target);
        c.calcFCost();
    }


    boolean repeatedBackwardAStar(){
        CellHeap open = new CellHeap();
        //calc g, h, f for start
        calcCosts(target,start);
        open.insert(target);
        while(open.notEmpty()){
            Cells curr = open.deleteMin();
            if(curr.equals(start)){
//                System.out.println(curr.getGCost());
                return true;
            }
            curr.visit();
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(open.isNotContained(c)){
                        c.setPrev(curr);
                        calcCosts(c, start);
                        open.insert(c);
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            calcCosts(c, start);
                            open.buildHeap();
                        }
                    }
                }
            });
        }
        return false;
    }

    public boolean adaptiveAStar(){
        CellHeap open = new CellHeap();
        start.calcGCost();
        if(start.getHCost() == 0) start.calcHCost(target);
        start.calcFCost();
        open.insert(start);
        boolean pathFound = false;
        while(open.notEmpty()){
            Cells curr = open.deleteMin();
            if(curr.equals(target)){
                pathFound = true;
                break;
            }
            curr.visit();
            gridWorld.getNeighborsOf(curr.getI(), curr.getJ()).forEach(c -> {
                if(c.getState() != Cells.States.BLOCKED && !c.isVisited()){
                    if(open.isNotContained(c)){
                        c.setPrev(curr);
                        c.calcGCost();
                        if(c.getHCost() == 0) c.calcHCost(target);
                        c.calcFCost();
                        open.insert(c);
                    }else{
                        if(curr.getGCost() + 1 < c.getGCost()){
                            c.setPrev(curr);
                            c.calcGCost();
                            if(c.getHCost() == 0) c.calcHCost(target);
                            c.calcFCost();
                            open.buildHeap();
                        }
                    }
                }
            });
        }
        gridWorld.resetAllUnVisited();//so we can run adaptive a star multiple times
        if(!pathFound){
            return false;
        }
        int g = target.getGCost();
        List<Cells> path = getTreePath(start, target);
        path.forEach(c -> {
            c.setH(g - c.getGCost());
        });
        return true;
    }

    public void setGridWorld(GridWorld g){
        this.gridWorld = g;
    }
    public void setTarget(Cells target) {
        this.target = target;
    }
    public void setStart(Cells start) {
        this.start = start;
    }
    public Cells getTarget() {
        return target;
    }
    public Cells getStart() {
        return start;
    }

    List<Cells> getTreePath(Cells startCell, Cells endCell){
        List<Cells> cellPath = new ArrayList<>();
        Cells tmp = endCell;
        while(tmp != startCell){
            if(tmp.getPrev() == null || tmp.getPrev().equals(tmp)){
                break;
            }
            cellPath.add(tmp);
            tmp = tmp.getPrev();
        }
        cellPath.add(tmp);
        return cellPath;
    }

    String storeableCellPath(Cells startCell, Cells endCell){
        StringBuilder fileOut = new StringBuilder("" + startCell.getI() + " " + startCell.getJ() + "\n" +
                endCell.getI() + " " + endCell.getJ() + "\n");

        List<Cells> cellsPath = getTreePath(startCell, endCell);

        for(int i = 0 ; i < cellsPath.size() ; i++){
            Cells c = cellsPath.get(i);
            fileOut.append(c.getI()).append(" ").append(c.getJ());
            if(i != cellsPath.size()-1){
                fileOut.append("\n");
            }
        }
        return fileOut.toString();
    }
}
