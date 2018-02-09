package ru.siblion.logsearcher.service.model.request;


import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequestScope
public class SignificantDateInterval {

    private Date dateFrom;

    private Date dateTo;

    private String dateFromString;

    private String dateToString;

    public SignificantDateInterval() {
    }


    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(Date dateFrom) {

        this.dateFrom = dateFrom;
    }


    public Date getDateTo() {
        return dateTo;
    }


    public void setDateTo(String dateTo)  {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.dateTo = simpleDateFormat.parse(dateTo);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDateFrom(String dateFrom) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.dateFrom = simpleDateFormat.parse(dateFrom);
        }

        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateFromString() {
        return dateFromString;
    }

    public void setDateFromString(String dateFromString) {
        if (dateFromString == null || dateFromString.isEmpty()) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.dateFromString = df.format(new Date(0L));
        }
        else
            this.dateFromString = dateFromString;
    }

    public String getDateToString() {
        return dateToString;
    }

    public void setDateToString(String dateToString) {
        if (dateToString == null || dateToString.isEmpty()) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.dateToString = df.format(new Date());
        }
        else
            this.dateToString = dateToString;
    }


    @Override
    public String toString() {
        return "Date from: "+ dateFromString+" Date to: "+ dateToString;
    }
}
