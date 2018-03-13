package ru.siblion.logsearcher.service.model.response;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultLogs that = (ResultLogs) o;

        if (timeMoment != null ? !timeMoment.equals(that.timeMoment) : that.timeMoment != null) return false;
        if (FileName != null ? !FileName.equals(that.FileName) : that.FileName != null) return false;
        return Content != null ? Content.equals(that.Content) : that.Content == null;
    }

    @Override
    public int hashCode() {
        int result = timeMoment != null ? timeMoment.hashCode() : 0;
        result = 31 * result + (FileName != null ? FileName.hashCode() : 0);
        result = 31 * result + (Content != null ? Content.hashCode() : 0);
        return result;
    }

    public ResultLogs() {
    }
}
