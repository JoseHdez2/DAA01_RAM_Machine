package cur.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cur.auto.ram.RAM_Auto;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("New app");
        RAM_Auto ar = new RAM_Auto();
        String content = new Scanner(new File("example_files/ram/test2.ram")).useDelimiter("\\Z").next();
        ar.loadTransitions(content);
//        new FrameLoad(ar);
    }
}
