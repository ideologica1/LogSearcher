package ru.siblion.logsearcher.service.dao;

import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;

import java.util.List;

public interface DaoService {

    void saveCreatedFile(SearchInfo searchInfo, String name);

    void removeCreatedFile(String fileName);

    List<String> getFilteredExistingFiles(SearchInfo searchInfo);

    List<SignificantDateInterval> getDateIntervals(String fileName);
}
