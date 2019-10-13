import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarUtilTest {
    GridWorld gridWorld;
    AStarUtil aStarUtil;
    @BeforeEach
    void init() {
        gridWorld = new GridWorld(101);
        try {
            gridWorld.generateGridMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gridWorld.setCell(0,0, Cells.States.OPEN);
        gridWorld.setCell(100,100, Cells.States.OPEN);
        aStarUtil = new AStarUtil(gridWorld, gridWorld.getCell(0,0), gridWorld.getCell(100,100));
    }

    @Test
    void shouldRunAStarForward(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedForwardAStar()) {
            List<Cells> path = aStarUtil.getTreePath(gridWorld.getCell(0, 0), gridWorld.getCell(9, 9));
            path.forEach(System.out::println);
        }
    }
    @Test
    void shouldRunAStarBackward(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedBackwardAStar()) {
            List<Cells> path = aStarUtil.getTreePath(gridWorld.getCell(9, 9), gridWorld.getCell(0, 0));
            path.forEach(System.out::println);
        }
    }

    @Test
    void shouldPrintOutThePathAsIfItWasAFfile(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedForwardAStar()) {
            System.out.println(aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(9, 9)));
        }
    }

    @Test
    void shouldSavePathToFile(){
        System.out.println(gridWorld);
        String path = "";
        if(aStarUtil.repeatedForwardAStar()) {
            path = aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(100, 100));
        }else{
            System.out.println("failed");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/testGridWithPath.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/testGridPath.txt");
            fw.write(path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...");
    }
}