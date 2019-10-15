import java.util.Objects;
import java.util.Random;

public class Cells implements Comparable {
    private States state;
    private Cells prev = null;
    private final int j;
    private final int i;
    private boolean visited;
    private int g = -1, h = 0, f = 0;
    private transient Random random;

    public enum States{
        UNKNOWN,
        BLOCKED,
        OPEN
    }

    Cells(int i, int j, States state, Random random) {
        this.i = i;
        this.j = j;
        this.state = state;
        setVisited(false);
        this.random = random;
    }

    void explore() {
        double rand = random.nextDouble();
        if(rand <= 0.7){
            state = States.OPEN;
        }else{
            state = States.BLOCKED;
        }
    }
    // Getters
    States getState() {
        return state;
    }
    int getJ() {
        return j;
    }
    int getI() {
        return i;
    }
    boolean isVisited() {
        return visited;
    }
    void visit() {
        visited = true;
    }
    Cells getPrev() {
        return prev;
    }
    int getGCost() {
        return g;
    }
    int getHCost() {
        return h;
    }
    private int getFCost() {
        return f;
    }

    //Setters
    void resetFGH(){
        g = -1;
        h = 0;
        f = 0;
    }
    void setStateOpen() {
        this.state = States.OPEN;
    }
    void setPrev(Cells prev) {
        this.prev = prev;
    }
    void setVisited(boolean v) {
        this.visited = v;
    }
    void calcFCost(){
        f =  getGCost() + getHCost();
    }
    void calcGCost(){
        if(prev == null)
            g = 1;
        else
            g = prev.getGCost()+1;
    }
    public void setH(int i) {
        this.h = i;
    }
    void calcHCost(Cells target){
        h =  Math.abs(this.getI() - target.getI()) + Math.abs(this.getJ() - target.getJ());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cells)) return false;
        Cells cells = (Cells) o;
        return getJ() == cells.getJ() &&
                getI() == cells.getI() &&
                isVisited() == cells.isVisited() &&
                g == cells.g &&
                h == cells.h &&
                f == cells.f &&
                getState() == cells.getState() &&
                (getPrev() == null && cells.getPrev() == null) || (getPrev() != null && cells.getPrev() != null && getPrev().equals(cells.getPrev()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState(), getPrev(), getJ(), getI(), isVisited(), g, h, f);
    }

    @Override
    public int compareTo(Object o) {
        Cells c;
        try {
            c = (Cells) o;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
        if(this.getFCost() == c.getFCost()){
            if(this.getHCost() == c.getHCost()){
                if(this.getGCost() == c.getGCost()){
                    int random_tie_break = random.nextInt(2);
                    if(random_tie_break == 0)
                        return -1;
                    else
                        return 1;
                }
                else
                    return this.getGCost() - c.getGCost();
            }
            else
                return this.getHCost() - c.getHCost();
        }
        else
            return this.getFCost() - c.getFCost();
    }

    @Override
    public String toString() {
        return "Cells{" +
                "state=" + state +
                ", prevCell= (" + (prev != null ? ""+prev.getI() + ", " + prev.getJ() : "X, X") + ")" +
                ", i=" + i +
                ", j=" + j +
                ", cost=" + f +
                '}';
    }
}