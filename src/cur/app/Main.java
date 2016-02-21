package cur.app;

import cur.auto.base.FrameLoad;
import cur.auto.ram.RAM_Auto;

public class Main {
    public static void main(String[] args) {
        System.out.println("New app");
        RAM_Auto ar = new RAM_Auto();
        new FrameLoad(ar);
    }
}
