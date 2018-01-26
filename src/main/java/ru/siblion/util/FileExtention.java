package ru.siblion.util;

public enum FileExtention { PDF("PDF"), TXT("TXT"), LOG("LOG"), DOC("DOC"), XML("XML"), HTML("HTML");

    private final String fileExtention;

    FileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public String getFileExtention() {
        return fileExtention;
    }
}
