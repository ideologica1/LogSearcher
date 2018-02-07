package ru.siblion.logsearcher.service.model.request;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.siblion.logsearcher.util.FileExtension;

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

    private FileExtension fileExtension;

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

    public boolean getRealization() {
        return realization;
    }

    public void setRealization(boolean realization) {
        this.realization = realization;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

}
