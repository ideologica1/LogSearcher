package ru.siblion.service.accessory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siblion.service.entity.response.CorrectionCheckResult;
import ru.siblion.service.entity.response.LogSearchResult;
import ru.siblion.util.Errors;

@Component
public class LogSearchResultService {

    @Autowired
    private LogSearchResult logSearchResult;

    public void setLogSearchResultResponse(CorrectionCheckResult correctionCheckResult) {
        long errorCode = correctionCheckResult.getErrorCode();
        if (errorCode != 0)
            logSearchResult.setResponse(Errors.getDescriptionByCode(errorCode));
    }
}
