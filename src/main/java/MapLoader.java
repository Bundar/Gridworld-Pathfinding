import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("").append(gridWorlds.size()).append("\n");

        gridWorlds.forEach(gw -> {
            stringBuilder.append(serializeGridWorld(gw, gson)).append("\n");
        });

        FileOutputStream outputStream = new FileOutputStream(gridWorldsPath);
        byte[] bytes = stringBuilder.toString().getBytes();
        outputStream.write(bytes);
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
