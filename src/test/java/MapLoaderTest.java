import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class MapLoaderTest {
    private MapLoader mapLoader = new MapLoader();
    @Test
    void shouldTestGeneratingMaps(){
        ArrayList<GridWorld> gridWorlds = mapLoader.generateAllGridMaps(5);
        gridWorlds.forEach(System.out::println);
    }

    @Ignore
    @Test
    void manualGenerateGridFile() throws IOException {
        mapLoader.saveGridWorldsToFile(mapLoader.generateAllGridMaps(50));
    }

    @Test
    void shouldReadGridWorldsFromFile() throws IOException {
        ArrayList<GridWorld> gridWorlds = mapLoader.loadAllMapsFromFile();
        gridWorlds.forEach(gw -> {
            System.out.println(gw.hashCode());
        });
    }

    @Test
    void shouldTestThatDeserializedIsEqualToUnSerialized() throws IOException {
        ArrayList<GridWorld> gridWorlds_pre = mapLoader.generateAllGridMaps(5);
        mapLoader.saveGridWorldsToFile(gridWorlds_pre);
        ArrayList<GridWorld> gridWorlds_de = mapLoader.loadAllMapsFromFile();

        System.out.println(gridWorlds_de.equals(gridWorlds_pre));
    }

    @Test
    void shouldLaunchViewerFromJava(){
        int dim = 101;
        Random random = new Random();
        AStarUtil aStarUtil = new AStarUtil();
        GridWorld gridWorld = null;
        try {
            do {
                gridWorld = new GridWorld(dim, random);
                aStarUtil.setGridWorld(gridWorld);
                aStarUtil.setStart(gridWorld.getCell(0,0));
                aStarUtil.setTarget(gridWorld.getCell(dim -1,dim -1));
                gridWorld.generateGridMap();
            } while (!aStarUtil.repeatedForwardAStar());
//            System.out.println("Generated Gridworld:\n"+ gridWorld);
            gridWorld.setCellOpen(0,0);
            gridWorld.setCellOpen(dim -1,dim -1);
            mapLoader.displayGridWorld(gridWorld.toString(), aStarUtil.storeableCellPath(gridWorld.getCell(0,0), gridWorld.getCell(dim -1,dim -1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}