package ru.siblion.logsearcher.service.accessory;

import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.util.Error;

public interface Validator {

    /**
     * Check input data passed as SearchInfo object, if some mandatory parameters are empty or contain incorrect data
     * there will be returned the corresponding error. If multiple error occurs the method will return the error with
     * greatest code error value
     * @param searchInfo provides all information that needed to find curtain logs in curtain location
     * @return valid ru.siblion.logsearcher.util.Error - the error with greatest code error value. If all parameters are correct
     * then return null.
     */

    Error correctionCheck(SearchInfo searchInfo);

}
