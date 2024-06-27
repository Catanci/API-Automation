package pet.store.soap;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;


@Endpoint(url = "${base_url}/websamples.countryinfo/CountryInfoService.wso", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@RequestTemplatePath(path = "api/soap/returncurrency/rq.xml")
@ResponseTemplatePath(path = "api/soap/returncurrency/rs.xml")
@ContentType(type = "text/xml")
public class FindCurrencyMethod extends AbstractApiMethodV2 {

    public FindCurrencyMethod() {
        replaceUrlPlaceholder("base_url",Configuration.getRequired("soap_url"));
    }
}
