package vikbur;

import java.util.ArrayList;
import java.util.List;

public class Node extends Thread{

    private static List<String> events = new ArrayList<>();
    private boolean isActive;

    public Node(){
        super();
        isActive = true;
    }

    public void disable(){
        isActive = false;
    }

    @Override
    public void run() {
        //иммитируем постоянно работающий узел
        System.out.println("Node started");

        while (isActive) {
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

    public static String getLog(){
        return events.toString();
    }


}
