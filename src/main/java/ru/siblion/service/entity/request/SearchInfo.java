package ru.siblion.service.entity.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Component
@RequestScope
@XmlRootElement
public class SearchInfo implements Serializable {

    private String RegularExpression;

    private List<SignificantDateInterval> DateInterval;

    private String Location;

    private boolean realization;

    private String fileExtention;


    @Autowired
    private SignificantDateInterval significantDateInterval;

    public SearchInfo() {
    }

    public String getRegularExpression() {
        return RegularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        RegularExpression = regularExpression;
    }

    public List<SignificantDateInterval> getDateInterval() {
        return DateInterval;
    }

    public void setDateInterval(List<SignificantDateInterval> dateInterval) {
        DateInterval = dateInterval;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public SignificantDateInterval getSignificantDateInterval() {
        return significantDateInterval;
    }

    public void setSignificantDateInterval(SignificantDateInterval significantDateInterval) {
        this.significantDateInterval = significantDateInterval;
    }

    public boolean getRealization() {
        return realization;
    }

    public void setRealization(boolean realization) {
        this.realization = realization;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public boolean isRealization() {
        return realization;
    }

}
