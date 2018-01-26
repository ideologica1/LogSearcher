package ru.siblion.util;

import java.util.Comparator;

public class ErrorsComparator implements Comparator<Errors> {
    @Override
    public int compare(Errors o1, Errors o2) {
        return (int)(o1.getErrorCode() - o2.getErrorCode());
    }
}
