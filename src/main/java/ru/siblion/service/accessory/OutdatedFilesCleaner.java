package ru.siblion.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

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
    private DataBaseManager dataBaseManager;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            configureCleaningOutdatedFilesSchedule();
        } catch (SQLException | ConfigurationException e) {
            e.printStackTrace();
        }
    }



    public void configureCleaningOutdatedFilesSchedule() throws SQLException, ConfigurationException {

     //   this.run();
        PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
        long delayOfSchedulingExecution = (conf.getLong("CleaningIntervalInHours") * 3600000);
        taskScheduler.scheduleWithFixedDelay(new OutdatedFilesCleaner(), delayOfSchedulingExecution);
    }

    public OutdatedFilesCleaner() {
    }

    @Override
    public void run() {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
            String filesDirectory = conf.getString("created_files");
            long availableLifeTime = (conf.getLong("AvailableLifeTimeInHours") * 3600000);
            String[] fileList = new File(filesDirectory).list();
            if (fileList != null) {
                for (String fileName : fileList) {
                    System.out.println(fileName);
                    Path filePath = FileSystems.getDefault().getPath(filesDirectory + fileName);
                    BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                    long creationTime = basicFileAttributes.creationTime().toMillis();
                    if ((new Date().getTime() - creationTime > availableLifeTime)) {
                        Files.delete(filePath);
                        dataBaseManager.removeCreatedFile(fileName);
                        System.out.println("outdated");
                    } else System.out.println("up to date");
                }
            }
        } catch (ConfigurationException | IOException | SQLException ignored) {

        }
    }

    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public void setTaskScheduler(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }
}
