import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MapLoader {
    private static String gridWorldsPath = "src/test/fiftyGridWorlds.ser";

    public static void main(String args[]){
        AStarUtil aStarUtil;
        ArrayList<GridWorld> gridWorlds = null;
        try {
            gridWorlds = loadAllMapsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter Command:\n");
            String input = scanner.nextLine();
            String[] commandAndArgs = input.split(" ");
            switch (commandAndArgs[0]) {
                case "exit":
                    break;
                case "show":
                    displayGridWorldCommand();
                case "run":
                    switch (commandAndArgs[1]) {
                        case "a":
                            //run adaptive
                            GridWorld g = gridWorlds.get(Integer.parseInt(commandAndArgs[2]));
                            g.reset();
                            aStarUtil = new AStarUtil(g, g.getCell(0, 0), g.getCell(g.getDim() - 1, g.getDim() - 1));
                            aStarUtil.adaptiveAStar();
                            saveGridToFile(g.toString(), aStarUtil.storeableCellPath(g.getCell(0, 0), g.getCell(g.getDim() - 1, g.getDim() - 1)));
                        case "f":
                            //run forward
                            GridWorld g2 = gridWorlds.get(Integer.parseInt(commandAndArgs[2]));
                            g2.reset();
                            aStarUtil = new AStarUtil(g2, g2.getCell(0, 0), g2.getCell(g2.getDim() - 1, g2.getDim() - 1));
                            aStarUtil.repeatedForwardAStar();
                            saveGridToFile(g2.toString(), aStarUtil.storeableCellPath(g2.getCell(g2.getDim() - 1, g2.getDim() - 1), g2.getCell(0, 0)));
                        case "b":
                            //run backward
                            GridWorld g3 = gridWorlds.get(Integer.parseInt(commandAndArgs[2]));
                            g3.reset();
                            aStarUtil = new AStarUtil(g3, g3.getCell(0, 0), g3.getCell(g3.getDim() - 1, g3.getDim() - 1));
                            aStarUtil.repeatedBackwardAStar();
                            saveGridToFile(g3.toString(), aStarUtil.storeableCellPath(g3.getCell(0, 0), g3.getCell(g3.getDim() - 1, g3.getDim() - 1)));
                        default:
                            System.out.println("Command must be \'a\', \'f\', or \'b\'...");
                    }
            }
        }
    }
    public static ArrayList<GridWorld> loadAllMapsFromFile() throws IOException {
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

    public static void saveGridToFile(String gridWorld, String path){
        try{
            FileWriter fw=new FileWriter("src/test/currentGrid.txt");
            fw.write(gridWorld);
            fw.close();

            FileWriter fw2=new FileWriter("src/test/currentGridPath.txt");
            fw2.write(path);
            fw2.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void displayGridWorld(String gridWorld, String path){
        try{
            FileWriter fw=new FileWriter("src/test/currentGrid.txt");
            fw.write(gridWorld);
            fw.close();

            FileWriter fw2=new FileWriter("src/test/currentGridPath.txt");
            fw2.write(path);
            fw2.close();
        }catch(Exception e){
            System.out.println(e);
        }

        try {
            String[] callAndArgs= {"python","/home/dubar/IdeaProjects/IntroToAIProj1/src/test/showGrid.py","src/test/currentGrid.txt","src/test/currentGridPath.txt"};
            Process p = Runtime.getRuntime().exec(callAndArgs);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // read the output
            String s = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            // read any errors
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.exit(0);
        }
        catch (IOException e) {
                System.out.println("exception occured");
                e.printStackTrace();
                System.exit(-1);
        }
    }

    public static void displayGridWorldCommand(){
        try {
            String[] callAndArgs= {"python","/home/dubar/IdeaProjects/IntroToAIProj1/src/test/showGrid.py","src/test/currentGrid.txt","src/test/currentGridPath.txt"};
            Process p = Runtime.getRuntime().exec(callAndArgs);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // read the output
            String s = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            // read any errors
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception occured");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
