package ru.siblion.logsearcher.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.util.FileExtension;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreatorFactory {

    @Autowired
    private List<FileCreator> fileCreators;

    private Map<FileExtension, FileCreator> cachedFileCreators = new HashMap<>();


    @PostConstruct
    public void init() {
        for (FileCreator fileCreator : fileCreators) {
            cachedFileCreators.put(fileCreator.getFileExtension(), fileCreator);
        }
    }

    public FileCreator getFileGenerator(FileExtension fileExtension) throws UnsupportedFileExtensionException {
        FileCreator fileCreator = cachedFileCreators.get(fileExtension);
        if (fileCreator == null) throw new UnsupportedFileExtensionException();
        else return fileCreator;
    }
}
