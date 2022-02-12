package vikbur;

import java.util.ArrayList;
import java.util.List;

public class Node implements Runnable{

    private static List<String> events = new ArrayList<>();

    @Override
    public void run() {
        //иммитируем постоянно работающий узел
        System.out.println("Node started");

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Node stopped");
                break;
            }
        }

    }

    public static void updateEvents(List<String> newEvents){

        for (String newEvent: newEvents){
            if (!events.contains(newEvent)){
                events.add(newEvent);
            }
        }
    }


}
