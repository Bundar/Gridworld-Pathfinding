import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellsTest {


    @Test
    void shouldTestAGreaterThanBWithDiffFCost(){
        Cells a = new Cells(0,0, Cells.States.OPEN);

        Cells b = new Cells(0,0, Cells.States.OPEN);

        a.calcGCost();
        a.calcHCost(new Cells(1,1, Cells.States.OPEN));
        a.calcFCost();

        b.calcGCost();
        b.calcHCost(new Cells(1,0, Cells.States.OPEN));
        b.calcFCost();

        if(a.compareTo(b) > 0)
            System.out.println("a is greater than b");

        if(a.compareTo(b) < 0)
            System.out.println("a is less than b");
        if(a.compareTo(b) == 0)
            System.out.println("a is equal to b");
    }
    @Test
    void shouldTestRandomTieBreak(){
        Cells a = new Cells(0,0, Cells.States.OPEN);

        Cells b = new Cells(0,0, Cells.States.OPEN);

        a.calcGCost();
        a.calcHCost(new Cells(1,1, Cells.States.OPEN));
        a.calcFCost();

        b.calcGCost();
        b.calcHCost(new Cells(1,1, Cells.States.OPEN));
        b.calcFCost();

        int bool = a.compareTo(b);
        if( bool > 0)
            System.out.println("2. a is greater than b");

        if(bool < 0)
            System.out.println("2. a is less than b");

        if(bool == 0)
            System.out.println("2. a is equal to b");
    }

    @Test
    void shouldTestAGreaterThanBWithSameFCost(){
        Cells a = new Cells(0,0, Cells.States.OPEN);

        Cells b = new Cells(0,0, Cells.States.OPEN);

        a.calcGCost();
        a.calcHCost(new Cells(1,1, Cells.States.OPEN));
        a.calcFCost();

        b.calcGCost();
        b.calcHCost(new Cells(1,1, Cells.States.OPEN));
        b.calcFCost();

        int bool = a.compareTo(b);
        if( bool > 0)
            System.out.println("2. a is greater than b");

        if(bool < 0)
            System.out.println("2. a is less than b");

        if(bool == 0)
            System.out.println("2. a is equal to b");
    }
}