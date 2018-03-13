package ru.siblion.logsearcher.util;

/**
 * errors that could occur if input data is incorrect
 */
public enum Error {
    FROM_EXCEED_TO(1, "Начало временного интервала превышает конец."),

    FROM_EXCEED_PRESENT(18, "Начало временного интервала превышает текущий момент времени."),

    TIME_FORMAT(19, "Неверный формат даты"),

    INPUT_PARAMETERS(37, "Не все обязательные поля заполнены."),

    EXTENSION_ABSENCE(3701, "Не указано расширение запрошенного файла."),

    LOGS_LOCATION(44, "Неверно указано название сервера, домена или кластера.");


    private final long errorCode;
    private final String errorDescription;

    Error(long errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public static String getErrorDescriptionByCode(long errorCode) {
        String temp = null;
        for (Error error : Error.values()) {
            if (error.getErrorCode() == errorCode) {
                temp = error.getErrorDescription();
            }

        }
        return temp;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
