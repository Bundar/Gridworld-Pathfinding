import java.util.*;

public class GridWorld {
    private int dimensions;
    private Cells[][] gridWorld;
    private Random random;
    GridWorld(int dim){
        dimensions = dim;
        random = new Random();
        gridWorld = new Cells[dimensions][dimensions];
        initializeGridWorld();
    }

    private void initializeGridWorld(){
        for( int i = 0 ; i < dimensions ; i ++){
            for ( int j = 0 ; j < dimensions ; j ++){
                gridWorld[i][j] = new Cells(i, j, Cells.States.UNKNOWN);
            }
        }
    }

    void generateGridMap() throws Exception {
        int i, j;
        Random random = new Random();
        i = random.nextInt(dimensions);
        j = random.nextInt(dimensions);
//        System.out.println("(i,j) = ("+i+", "+j+")");
        depthFirstCartographer(gridWorld[i][j]);
    }

    private void depthFirstCartographer(Cells rootCell){
//        System.out.println("Starting from ("+rootCell.getI()+", "+rootCell.getJ()+")");
        dfsUtil(rootCell);
        for( int i = 0 ; i < dimensions ; i ++){
            for ( int j = 0 ; j < dimensions ; j ++){
                if(gridWorld[i][j].getState() == Cells.States.UNKNOWN){
                    dfsUtil(gridWorld[i][j]);
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

    public List<Cells> getNeighborsOf(int i, int j) {
        return new ArrayList<Cells>(Arrays.asList(
                getCell(i - 1, j),
                getCell(i + 1, j),
                getCell(i, j - 1),
                getCell(i, j + 1)));
    }

    private Cells getCell(int i, int j) {
        if(0 <= i && i < dimensions && 0 <= j && j < dimensions) {
            return gridWorld[i][j];
        }
        else
            return new Cells(i, j, Cells.States.BLOCKED);
    }

    @Override
    public String toString() {
        String title = ""+dimensions+"\n" + dimensions + "\n";
        for ( Cells[] cellRow: gridWorld ) {
            for ( int i = 0 ; i < cellRow.length ; i++ ) {
                Cells.States state = cellRow[i].getState();
                title += (state == Cells.States.OPEN || state == Cells.States.UNKNOWN) ? "0" : "1";
                if(i != cellRow.length-1)
                    title+=" ";
            }
            title += "\n";
        }
        return title;
    }
}


