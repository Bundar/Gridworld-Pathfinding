import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MapLoader {
    private static String gridWorldsPath = "src/test/fiftyGridWorlds.ser";

    public static void main(String args[]){
    }
    public ArrayList<GridWorld> loadAllMapsFromFile() throws IOException {
        ArrayList<GridWorld> gridWorlds = new ArrayList<>();
        Gson gson = new Gson();
        Scanner scanner = new Scanner(new File(gridWorldsPath));
        while(scanner.hasNextLine()){
            gridWorlds.add(deserializeGridWorld(scanner.nextLine(), gson));
        }
        return gridWorlds;
    }
    public void saveGridWorldsToFile(ArrayList<GridWorld> gridWorlds) throws IOException {
        Gson gson = new Gson();
        FileOutputStream outputStream = new FileOutputStream(gridWorldsPath);
        AtomicInteger i = new AtomicInteger();
        gridWorlds.forEach(gw -> {
            System.out.println("writing out gw # " + i);
            i.getAndIncrement();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(serializeGridWorld(gw, gson)).append("\n");
            byte[] bytes = stringBuilder.toString().getBytes();
            try {
                outputStream.write(bytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        outputStream.close();
    }

    ArrayList<GridWorld> generateAllGridMaps(int n){
        ArrayList<GridWorld> gridWorlds = new ArrayList<>();
        Random random = new Random();
        AStarUtil aStarUtil = new AStarUtil();
        IntStream.range(0,n).forEach(i -> {
            System.out.println(i);
            GridWorld gridWorld;
            do {
                gridWorld = new GridWorld(101, random);
                aStarUtil.setGridWorld(gridWorld);
                aStarUtil.setStart(gridWorld.getCell(0,0));
                aStarUtil.setTarget(gridWorld.getCell(100,100));
                gridWorld.generateGridMap();
            } while (!aStarUtil.repeatedForwardAStar());
            gridWorlds.add(gridWorld);
        });
        return gridWorlds;
    }
    public static String serializeGridWorld(GridWorld g, Gson gson){
        return gson.toJson(g);
    }

    public static GridWorld deserializeGridWorld(String gridSon, Gson gson){
        return gson.fromJson(gridSon, GridWorld.class);
    }




}
