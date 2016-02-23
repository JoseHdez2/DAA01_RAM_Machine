package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import auto.base.RAM_Frame;
import auto.ram.RAM_Sim;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("New app");
        RAM_Sim ar = new RAM_Sim();
        String content = new Scanner(new File("example_files/ram/test2.ram")).useDelimiter("\\Z").next();
        ar.loadTransitions(content);
        new RAM_Frame(ar);
    }
}
