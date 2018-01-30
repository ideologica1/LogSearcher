package ru.siblion.service.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResultLogs {

    @XmlTransient
    private Date timeMoment;

    private String FileName;
    private String Content;

    public Date getTimeMoment() {
        return timeMoment;
    }

    public void setTimeMoment(Date timeMoment) {
        this.timeMoment = timeMoment;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @XmlElement(name = "TimeMoment")
    public String getDateStringRepr() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String reportDate = df.format(timeMoment);
        return reportDate;
    }

    @Override
    public String toString() {
        return Content;
    }


    public ResultLogs() {
    }
}
