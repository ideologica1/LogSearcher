package ru.siblion.util;

import ru.siblion.service.model.response.ResultLogs;

import java.util.Comparator;

public class LogsComparator implements Comparator<ResultLogs> {
    @Override
    public int compare(ResultLogs o1, ResultLogs o2) {
        if (o1.getTimeMoment().after(o2.getTimeMoment())) return 1;
        else if (o2.getTimeMoment().after(o1.getTimeMoment())) return -1;
        else return 0;
    }
}
