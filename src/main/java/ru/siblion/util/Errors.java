package ru.siblion.util;

public enum Errors {
    FROM_EXCEED_TO(1, "Beginning of the date interval exceed end."),

    FROM_EXCEED_PRESENT(18, "Beginning of the date interval exceed present time."),

    TIME_FORMAT(19, "Incorrect time format."),

    INPUT_PARAMETERS(37, "Missed mandatory parameters."),

    EXTENSION_ABSENCE(3701, "Missed asynchronous method file extension."),

    LOGS_LOCATION(44, "Incorrect resource name.");


    private final long errorCode;
    private final String errorDescription;

    Errors (long errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public static String getDescriptionByCode(long errorCode) {
        String temp = null;
        for (Errors errors : Errors.values()) {
            if (errors.getErrorCode() == errorCode) {
                temp = errors.getErrorDescription();
            }

        }
        return temp;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
