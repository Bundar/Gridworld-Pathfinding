import java.util.Random;

public class Cells implements Comparable {
    public void visit() {
        visited = true;
    }

    public enum States{
        UNKNOWN,
        BLOCKED,
        OPEN
    }

    private States state;
    private Cells prev = this;
    private final int j;
    private final int i;
    private boolean visited;
    private int g = 0, h = 0, f = 0;
    private Random random;

    Cells(int i, int j, States state) {
        this.i = i;
        this.j = j;
        this.state = state;
        setVisited(false);
        random = new Random();
    }

    void explore() {
        double rand = random.nextDouble();
        if(rand <= 0.7){
            state = States.OPEN;
        }else{
            state = States.BLOCKED;
        }
    }

    States getState() {
        return state;
    }

    int getJ() {
        return j;
    }

    int getI() {
        return i;
    }

    public void setPrev(Cells prev) {
        this.prev = prev;
    }

    @Override
    public int compareTo(Object o) {
        Cells c;
        try {
            c = (Cells) o;
        }catch(Exception e){
            System.out.println(e);
            return 0;
        }
        if(this.getFCost() == c.getFCost()){
            if(this.getHCost() == c.getHCost()){
                if(this.getGCost() == c.getGCost()){
                    int random_tie_break = random.nextInt(2);//random tie breaking
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    int getGCost() {
        return g;
    }

    int getHCost() {
        return h;
    }

    int getFCost() {
        return f;
    }

    void calcFCost(){
        f =  getGCost() + getHCost();
    }
    void calcGCost(){
        g = prev.getGCost()+1;
    }
    void calcHCost(Cells target){
        h =  Math.abs(this.getI() - target.getI()) + Math.abs(this.getJ() - target.getJ());
    }

    @Override
    public String toString() {
        return "Cells{" +
                "state=" + state +
                ", prevCell=" + prev +
                ", i=" + i +
                ", j=" + j +
                ", cost=" + f +
                '}';
    }
}