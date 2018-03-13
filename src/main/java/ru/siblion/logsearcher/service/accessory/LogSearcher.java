package ru.siblion.logsearcher.service.accessory;

import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

public interface LogSearcher {

    /**
     * Search logs by specific parameters, if no single log is found where would be empty message that contain
     * information about this
     * @param searchInfo
     * @return logs sorted ascending by time creation, no duplicates are allowed
     */

    SearchInfoResult searchLogs(SearchInfo searchInfo);
}
