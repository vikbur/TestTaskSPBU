package vikbur;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileListener {

    private static long countLines;
    private static FileAlterationMonitor monitor;
    private static Node node;
    private static final String FILE_READ_ERROR = "File read error";
    private static final String CMD_MENU = "Enter getLog or exit";
    private static final String UNKNOWN_COMMAND = "Unknown command";

    public static void main(String[] args) {

        initNode();
        startListener();

        try (Scanner console = new Scanner(System.in)){

            System.out.println(CMD_MENU);

            String cmd;

            while (!(cmd = console.next()).toLowerCase().equals("exit")){

                if (cmd.toLowerCase().equals("getlog")){
                    System.out.println(Node.getLog());
                } else {
                    System.out.println(UNKNOWN_COMMAND);
                }

                System.out.println(CMD_MENU);
            }

            node.disable();
            monitor.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void initNode(){
        node = new Node();
        node.start();
    }

    private static void startListener(){

        try {
            File file = new File("D://ForJava//files//test1.txt");

            //записываем текущее количество строк, чтобы понимать с какой строки записывать изменения

            countLines = Files.lines(file.toPath()).count();

            File dir = file.getParentFile();

            //Создаем просмоторщик файлов по директории с фильтром по нашему файлу
            FileAlterationObserver observer = new FileAlterationObserver(dir,
                    FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                            FileFilterUtils.nameFileFilter(file.getName())));

            //Задаем прослушивателя на изменение файла
            observer.addListener(new FileAlterationListenerAdaptor(){
                @Override
                public void onFileChange(File file) {

                    try {

                        List<String> lines = Files.readAllLines(file.toPath());

                        //обновляем данные в Node, если изначально файл был пустой, то отправляем все строки
                        //если нет, то только новые строки

                        if (countLines == 0){
                            Node.updateEvents(lines);
                        } else {
                            Node.updateEvents(lines.stream().filter(x -> lines.indexOf(x) > countLines).collect(Collectors.toList()));
                        }

                        countLines = lines.size();

                    } catch (IOException e) {
                        System.out.println(FILE_READ_ERROR);
                        e.printStackTrace();
                    }

                }
            });

            //Запускаем мониторинг файла с интервалом в 5 сек
            monitor = new FileAlterationMonitor(5000, observer);
            monitor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
