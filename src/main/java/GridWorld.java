import java.beans.Transient;
import java.util.*;
import static java.util.Arrays.deepEquals;

public class GridWorld {
    private int dimensions;
    private ArrayList<ArrayList<Cells>> gridWorld;
    private transient Random random;

    GridWorld(int dim, Random random){
        dimensions = dim;
        this.random = random;
        gridWorld = new ArrayList<ArrayList<Cells>>(dimensions);
        initializeGridWorld();
    }

    void initializeGridWorld(){
        for( int i = 0 ; i < dimensions ; i ++){
            gridWorld.add(new ArrayList<Cells>(dimensions));
            for ( int j = 0 ; j < dimensions ; j ++){
                gridWorld.get(i).add(new Cells(i, j, Cells.States.UNKNOWN, random));
            }
        }
    }

    void generateGridMap() {
        int i, j;
        Random random = new Random();
        i = random.nextInt(dimensions);
        j = random.nextInt(dimensions);
        depthFirstCartographer(gridWorld.get(i).get(j));
    }


    public void reset() {
        gridWorld.forEach(cellArr -> {
            cellArr.forEach(c -> {
                c.setVisited(false);
                c.setPrev(null);
                c.resetFGH();
            });
        });
    }

    private void depthFirstCartographer(Cells rootCell){
        dfsUtil(rootCell);
        for( int i = 0 ; i < dimensions ; i ++){
            for ( int j = 0 ; j < dimensions ; j ++){
                if(gridWorld.get(i).get(j).getState() == Cells.States.UNKNOWN){
                    dfsUtil(gridWorld.get(i).get(j));
                }
            }
        }
    }

    private void dfsUtil(Cells rootCell) {
        Stack<Cells> cellStack = new Stack<>();
        cellStack.push(rootCell);
        while(!cellStack.empty()){
            Cells cell = cellStack.pop();
            int i = cell.getI();
            int j = cell.getJ();
            List<Cells> cells = getNeighborsOf(i, j);
            while(!cells.isEmpty()){
                int randomIndex = random.nextInt(cells.size());
                Cells c = cells.get(randomIndex);
                if (c.getState() == Cells.States.UNKNOWN) {
                    c.explore();
                    if (c.getState() == Cells.States.OPEN) {
                        cellStack.push(c);
                    }
                }
                cells.remove(randomIndex);
            }
        }
    }

    List<Cells> getNeighborsOf(int i, int j) {
        return new ArrayList<>(Arrays.asList(
                getCell(i - 1, j),
                getCell(i + 1, j),
                getCell(i, j - 1),
                getCell(i, j + 1)));
    }

    Cells getCell(int i, int j) {
        if(0 <= i && i < dimensions && 0 <= j && j < dimensions) {
            return gridWorld.get(i).get(j);
        }
        else
            return new Cells(i, j, Cells.States.BLOCKED, random);
    }

    void setCellOpen(int i, int j) {
        gridWorld.get(i).get(j).setStateOpen();
    }

    @Override
    public String toString() {
        StringBuilder title = new StringBuilder("" + dimensions + "\n" + dimensions + "\n");
        for ( ArrayList<Cells> cellRow: gridWorld ) {
            for ( int i = 0 ; i < cellRow.size() ; i++ ) {
                Cells.States state = cellRow.get(i).getState();
                title.append((state == Cells.States.OPEN || state == Cells.States.UNKNOWN) ? "0" : "1");
                if(i != cellRow.size()-1)
                    title.append(" ");
            }
            title.append("\n");
        }
        return title.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridWorld)) return false;
        GridWorld gridWorld1 = (GridWorld) o;
        return dimensions == gridWorld1.dimensions &&
                gridWorld.equals(gridWorld1.gridWorld);
    }
}


