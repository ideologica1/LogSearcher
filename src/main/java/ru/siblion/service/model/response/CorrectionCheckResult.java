package ru.siblion.service.model.response;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

@RequestScope
@Component
public class CorrectionCheckResult implements Serializable{
    private long errorCode = 0;

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }
}
