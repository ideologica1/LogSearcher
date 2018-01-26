package ru.siblion.service.entity;

import org.springframework.stereotype.Component;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.response.SearchInfoResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Component
public class XMLModel implements Serializable {

    @XmlElement
    private final String author = "Андрей Иванов";

    @XmlElement
    private final String applicationName = "Logs Finder";

    private SearchInfo searchInfo;

    private SearchInfoResult searchInfoResult;

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public SearchInfoResult getSearchInfoResult() {
        return searchInfoResult;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public void setSearchInfoResult(SearchInfoResult searchInfoResult) {
        this.searchInfoResult = searchInfoResult;
    }

    public String getAuthor() {
        return author;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
