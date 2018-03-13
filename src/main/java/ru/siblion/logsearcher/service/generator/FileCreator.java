package ru.siblion.logsearcher.service.generator;

import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.util.FileExtension;

public interface FileCreator {
    void generateFile(String filePath, XMLModel xmlModel);

    FileExtension getFileExtension();
}
