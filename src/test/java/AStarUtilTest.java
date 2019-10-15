import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

class AStarUtilTest {
    GridWorld gridWorld;
    AStarUtil aStarUtil;

    private int dim = 101;

    @BeforeEach
    void init() {
        Random random = new Random();
        aStarUtil = new AStarUtil();
        try {
            do {
                gridWorld = new GridWorld(dim, random);
                aStarUtil.setGridWorld(gridWorld);
                aStarUtil.setStart(gridWorld.getCell(0,0));
                aStarUtil.setTarget(gridWorld.getCell(dim -1,dim -1));
                gridWorld.generateGridMap();
            } while (!aStarUtil.repeatedForwardAStar());
            System.out.println("Generated Gridworld:\n"+ gridWorld);
            gridWorld.setCellOpen(0,0);
            gridWorld.setCellOpen(dim -1,dim -1);
            gridWorld.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldRunAStarForward(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedForwardAStar()) {
            List<Cells> path = aStarUtil.getTreePath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1));
            path.forEach(System.out::println);
        }
    }
    @Test
    void shouldRunAStarBackward(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedBackwardAStar()) {
            List<Cells> path = aStarUtil.getTreePath(gridWorld.getCell(dim -1, dim -1), gridWorld.getCell(0, 0));
            path.forEach(System.out::println);
        }
    }

    @Test
    void shouldRunAdaptiveAStar(){
        System.out.println(gridWorld);
        if(aStarUtil.adaptiveAStar()) {
            List<Cells> path = aStarUtil.getTreePath(gridWorld.getCell(0,0), gridWorld.getCell(dim -1, dim -1));
            path.forEach(System.out::println);
        }else
            System.out.println("Adaptive Failed.");
    }

    @Test
    void shouldTimeFStar(){
        GridWorld gridWorld = new GridWorld(101, new Random());
        aStarUtil = new AStarUtil(gridWorld, gridWorld.getCell(0,0), gridWorld.getCell(100,100));
//        System.out.println(gridWorld);
        Instant start = Instant.now();
        aStarUtil.repeatedForwardAStar();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + " ms");
    }

    @Test
    void shouldCompareFandB(){
        int fstartime = 0;
        int bstartime = 0;
        GridWorld worstdiff = null;
        int maxdiff = 0;
        String fpath = "";
        String bpath = "";
        String worstFPath = null, worstBPath = null;
        for(int i = 0; i < 50; i ++){
            Instant start = Instant.now();
            aStarUtil.repeatedForwardAStar();
            Instant mid = Instant.now();
            fpath = aStarUtil.storeableCellPath(gridWorld.getCell(0,0), gridWorld.getCell(dim -1, dim-1));
            gridWorld.reset();
            Instant mid2 = Instant.now();
            aStarUtil.repeatedBackwardAStar();
            Instant end = Instant.now();
            bpath = aStarUtil.storeableCellPath(gridWorld.getCell(dim -1, dim-1), gridWorld.getCell(0,0));
            init();
            fstartime += Duration.between(start, mid).toMillis();
            bstartime += Duration.between(mid2, end).toMillis();

            if(Math.abs(fstartime - bstartime) > maxdiff){
                maxdiff = Math.abs(fstartime - bstartime);
                worstdiff = gridWorld;
                worstBPath = bpath;
                worstFPath = fpath;
            }
        }
        try{
            FileWriter fw=new FileWriter("src/test/worstDiffMap.txt");
            fw.write(worstdiff.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/worstDiffFPath.txt");
            fw.write(worstFPath);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/worstDiffBPath.txt");
            fw.write(worstBPath);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Forward: " + fstartime/50 + " ms");
        System.out.println("Backward: " + bstartime/50 + " ms");

    }
    @Test
    void shouldPrintOutThePathAsIfItWasAFfile(){
        System.out.println(gridWorld);
        if(aStarUtil.repeatedForwardAStar()) {
            System.out.println(aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1)));
        }
    }

    @Test
    void shouldSaveForwardPathToFile(){
        System.out.println(gridWorld);
        String path = "";
        if(aStarUtil.repeatedForwardAStar()) {
            path = aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1));
        }else{
            System.out.println("failed");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/ftest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/ftestpath.txt");
            fw.write(path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...");
    }
    @Test
    void shouldSaveBackwardPathToFile(){
        System.out.println(gridWorld);
        String path = "";
        if(aStarUtil.repeatedBackwardAStar()) {
            path = aStarUtil.storeableCellPath(gridWorld.getCell(dim -1, dim -1), gridWorld.getCell(0, 0));
        }else{
            System.out.println("failed");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/btest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/btestpath.txt");
            fw.write(path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...");
    }

    @Test
    void shouldSaveAdaptivePathToFile(){
        System.out.println(gridWorld);
        String path = "";
        if(aStarUtil.adaptiveAStar()) {
            path = aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1));
        }else{
            System.out.println("failed");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/atest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/atestpath.txt");
            fw.write(path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...");
    }

    @Test
    void shouldCompareFandBandAAStar(){
        String f_path = "";
        if(aStarUtil.repeatedForwardAStar()) {
            f_path = aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1));
        }else{
            System.out.println("failed to find path with forward");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/ftest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/ftestpath.txt");
            fw.write(f_path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...(Fpath)");
        System.out.println(aStarUtil.getTarget().getGCost());

        String b_path = "";
        gridWorld.reset();
        if(aStarUtil.repeatedBackwardAStar()) {
            b_path = aStarUtil.storeableCellPath(gridWorld.getCell(dim -1, dim -1), gridWorld.getCell(0, 0));
        }else{
            System.out.println("failed to find back path");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/btest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/btestpath.txt");
            fw.write(b_path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...(Bpath)");
        System.out.println(aStarUtil.getStart().getGCost());

        String a_path = "";
        gridWorld.reset();
        if(aStarUtil.adaptiveAStar()) {
            a_path = aStarUtil.storeableCellPath(gridWorld.getCell(0, 0), gridWorld.getCell(dim -1, dim -1));
        }else{
            System.out.println("failed to find adaptive path");
            return;
        }
        try{
            FileWriter fw=new FileWriter("src/test/atest.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            FileWriter fw=new FileWriter("src/test/atestpath.txt");
            fw.write(b_path);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...(Apath)");
        System.out.println(aStarUtil.getTarget().getGCost());
    }

    @Test
    void shouldShowImprovementOverRunsWithAdaptiveH(){
        Instant start = Instant.now();
        aStarUtil.adaptiveAStar();
        Instant firstDone = Instant.now();
        aStarUtil.adaptiveAStar();
        Instant secondDone = Instant.now();
        aStarUtil.adaptiveAStar();
        Instant thirdDone = Instant.now();

        System.out.println("First Execution: "+ Duration.between(start, firstDone).toMillis() + " ms");
        System.out.println("Second Execution: "+ Duration.between(firstDone, secondDone).toMillis()+ " ms");
        System.out.println("Third Execution: "+ Duration.between(secondDone, thirdDone).toMillis()+ " ms");
    }
}