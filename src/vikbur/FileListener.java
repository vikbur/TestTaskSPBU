package vikbur;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileListener {

    public static void main(String[] args) {

        startListener();
    
    }

    private static void startListener(){

        try {
            File file = new File("D://ForJava//files//test1.txt");

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
