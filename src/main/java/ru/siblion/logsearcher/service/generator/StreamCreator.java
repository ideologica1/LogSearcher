package ru.siblion.logsearcher.service.generator;

import ru.siblion.logsearcher.service.model.XMLModel;

import java.io.ByteArrayOutputStream;

public interface StreamCreator {
    ByteArrayOutputStream getModelAsStream(XMLModel xmlModel);
}
