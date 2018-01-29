package ru.siblion.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@ApplicationScope
@Component
public class OutdatedFilesCleaner implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private DataBaseManager dataBaseManager;


    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
            String filesDirectory = conf.getString("created_files");
            long availableLifeTime = (conf.getLong("AvailableLifeTimeInHours") * 3600000);
            String[] fileList = new File(filesDirectory).list();
            System.out.println(true);
            System.out.println(true);
            System.out.println(true);
            System.out.println(true);
            if (fileList != null) {
                for (String fileName : fileList) {
                    System.out.println(fileName);
                    Path filePath = FileSystems.getDefault().getPath(filesDirectory + fileName);
                    BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                    long creationTime = basicFileAttributes.creationTime().toMillis();
                    if ((new Date().getTime() - creationTime > availableLifeTime)) {
                        Files.delete(filePath);
                  //      dataBaseManager.removeCreatedFile(fileName);
                        System.out.println(true);
                    } else System.out.println(false);
                }
            }
        } catch (ConfigurationException | IOException  ignored) {

        }
    }

    @PostConstruct
    public void setsms() {
        System.out.println(true);
        System.out.println(true);
        System.out.println(true);
        System.out.println(true);
        System.out.println(true);
    }

}
