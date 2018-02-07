package ru.siblion.logsearcher.service.accessory;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.util.FileExtension;

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
        try {
            searchInfo.setFileExtension(FileExtension.valueOf(request.getParameter("extension")));
        }
        catch (IllegalArgumentException e) {
            searchInfo.setFileExtension(null);
        }
        searchInfo.setRegularExpression(request.getParameter("regex"));
        if (request.getParameter("realization") != null) {
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
