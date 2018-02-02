package ru.siblion.service.model.response;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.siblion.util.Errors;

import javax.xml.bind.annotation.XmlRootElement;

@Component
@RequestScope
@XmlRootElement
public class LogSearchResult {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = "Путь до интересующего файла: " + response;
    }

    public void setResponse(CorrectionCheckResult correctionCheckResult) {
        long errorCode = correctionCheckResult.getErrorCode();
        if (errorCode != 0)
            this.response = "Ошибка: " + Errors.getErrorDescriptionByCode(errorCode);
    }

    public LogSearchResult() {
    }
}
