import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;

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
}