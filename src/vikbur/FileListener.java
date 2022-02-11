package vikbur;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileListener {

    private static long countLines;

    public static void main(String[] args) {

        initNode();
        startListener();

    }

    private static void initNode(){
        Node node = new Node();
        node.run();
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
                    System.out.println(file.getPath().toString() + " change at " + new Date());
                }
            });

            //Запускаем мониторинг файла с интервалом в 5 сек
            FileAlterationMonitor monitor = new FileAlterationMonitor(5000, observer);
            monitor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
