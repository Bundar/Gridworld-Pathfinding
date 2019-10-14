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

    @Test
    void shouldSaveGridWorldsToFile() throws IOException {
        mapLoader.saveGridWorldsToFile(mapLoader.generateAllGridMaps(5));
    }

    @Test
    void shouldReadGridWorldsFromFile(){

    }
}