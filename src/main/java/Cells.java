import java.util.Random;

public class Cells implements Comparable {
        public enum States{
        UNKNOWN,
        BLOCKED,
        OPEN
    }

    private boolean visited;
    private States state;
    private Cells prev;
    private final int j;
    private final int i;
    private int g, h, f;
    private Random random;

    Cells(int i, int j, States state) {
        this.i = i;
        this.j = j;
        this.state = state;
        visited = false;
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

    boolean isVisited(){ return visited; }

    @Override
    public int compareTo(Object o) {
        Cells c;
        try {
            c = (Cells) o;
        }catch(Exception e){
            System.out.println(e);
            return 0;
        }
        if(this.fcost() == c.fcost()){
            if(this.hcost() == c.hcost()){
                if(this.gcost() == c.gcost()){
                    int random_tie_break = random.nextInt(2);//random tie breaking
                    if(random_tie_break == 0)
                        return -1;
                    else
                        return 1;
                }
                else
                    return this.gcost() - c.gcost();
            }
            else
                return this.hcost() - c.hcost();
        }
        else
            return this.fcost() - c.fcost();
    }

    private int gcost() {
        return g;
    }

    private int hcost() {
        return h;
    }

    private int fcost() {
        return f;
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