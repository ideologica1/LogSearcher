package ru.siblion.logsearcher.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.siblion.logsearcher.service.dao.DaoService;
import ru.siblion.logsearcher.service.dao.DataBaseManager;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.util.Date;

@ApplicationScope
@Component
public class OutdatedFilesCleaner implements ApplicationListener<ContextRefreshedEvent>, Runnable {


    @Autowired
    private DaoService dataBaseManager;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private static long AVAILABLE_LIFETIME;

    private static long DELAY_OF_EXECUTION;


    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
            configureCleaningOutdatedFilesSchedule();
    }


    private void configureCleaningOutdatedFilesSchedule() {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("application.properties");
            AVAILABLE_LIFETIME = (conf.getLong("AvailableLifeTimeInHours") * 3600000);
            DELAY_OF_EXECUTION = (conf.getLong("CleaningIntervalInHours") * 3600000);
            taskScheduler.scheduleWithFixedDelay(this, DELAY_OF_EXECUTION);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OutdatedFilesCleaner() {
    }

    @Override
    public void run() {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("application.properties");
            String filesDirectory = conf.getString("created_files_location");
            String[] fileList = new File(filesDirectory).list();
            if (fileList != null) {
                for (String fileName : fileList) {
                    System.out.println(fileName);
                    Path filePath = FileSystems.getDefault().getPath(filesDirectory + fileName);
                    BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                    long creationTime = basicFileAttributes.creationTime().toMillis();
                    if ((new Date().getTime() - creationTime > AVAILABLE_LIFETIME)) {
                        System.out.println("Status: outdated - this file would be removed");
                        Files.delete(filePath);
                        dataBaseManager.removeCreatedFile(fileName);
                    } else System.out.println("Status: OK - this file is up to date");
                }
            }
        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }


    public void setDataBaseManager(DaoService dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public void setTaskScheduler(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }
}
