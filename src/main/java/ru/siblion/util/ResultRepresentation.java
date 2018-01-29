package ru.siblion.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.siblion.service.entity.response.CorrectionCheckResult;
import ru.siblion.service.entity.response.LogSearchResult;

import java.io.Serializable;

@RequestScope
@Component
public class ResultRepresentation implements Serializable{

    @Autowired
    private CorrectionCheckResult correctionCheckResult;

    public void showMessage(LogSearchResult logSearchResult) {
     /*   if (correctionCheckResult.getErrorCode() != 0)
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: "+logSearchResult.getResponse(), "Error"));
        else FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Путь до интересующих логов: "+logSearchResult.getResponse())); */
    }
}
