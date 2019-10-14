import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.Random;
import java.util.stream.IntStream;

class GridWorldTest {
    GridWorld gridWorld;

    @BeforeEach
    void init(){
        gridWorld = new GridWorld(101, new Random());
    }

    @Test
    void shouldGenerateGridWorld(){
        try {
            gridWorld.generateGridMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(gridWorld);
    }

    @Test
    void shouldWriteGridMapToFile(){
        try {
            gridWorld.generateGridMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            FileWriter fw=new FileWriter("src/test/testOutputFile101.txt");
            fw.write(gridWorld.toString());
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Success...");
        System.out.println(gridWorld);
    }

    @Test
    void shouldGenerate50GridWorlds(){
        IntStream.range(1,51).forEach(i ->{
            GridWorld gridWorldi = null;
            try {
                gridWorldi = new GridWorld(101, new Random());
                gridWorldi.generateGridMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                FileWriter fw=new FileWriter("src/test/gridworlds/test"+i+".txt");
                fw.write(gridWorldi.toString());
                fw.close();
            }catch(Exception e){
                System.out.println(e);
            }
        });
    }
    @Test
    void shouldPrintOutGridWorld(){
        System.out.println(gridWorld);
    }
}