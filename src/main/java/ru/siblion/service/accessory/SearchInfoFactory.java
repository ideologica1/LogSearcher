package ru.siblion.service.accessory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.siblion.service.model.request.SearchInfo;
import ru.siblion.service.model.request.SignificantDateInterval;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class SearchInfoFactory implements Serializable {

    public SearchInfo createSearchInfo (HttpServletRequest request) {
        SearchInfo searchInfo = new SearchInfo();
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();

        searchInfo.setLocation(request.getParameter("location"));
        searchInfo.setFileExtention(request.getParameter("extension"));
        searchInfo.setRegularExpression(request.getParameter("regex"));
        if (request.getParameter("asyncrealization") != null) {
            searchInfo.setRealization(true);
        }
        else searchInfo.setRealization(false);
        String[] datesFrom = request.getParameterValues("dateFrom");
        String[] datesTo = request.getParameterValues("dateTo");
        for (int i = 0; i < datesFrom.length; i++) {
            SignificantDateInterval significantDateInterval = new SignificantDateInterval();
            significantDateInterval.setDateFromString(datesFrom[i]);
            significantDateInterval.setDateToString(datesTo[i]);
            dateIntervals.add(significantDateInterval);
        }
        searchInfo.setDateInterval(dateIntervals);

        return searchInfo;
    }
}
