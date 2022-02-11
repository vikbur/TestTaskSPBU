package vikbur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node implements Runnable{

    private static List<String> events = new ArrayList<>();

    @Override
    public void run() {

        try {
            System.out.println("I'm Node, i'm alive! :)");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Node stopped");
        }

    }
}
