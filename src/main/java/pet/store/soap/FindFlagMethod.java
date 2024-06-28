package pet.store.soap;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/websamples.countryinfo/CountryInfoService.wso", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@RequestTemplatePath(path = "api/soap/returnflag/rq.xml")
@ResponseTemplatePath(path = "api/soap/returnflag/rs.xml")
@ContentType(type = "text/xml")
public class FindFlagMethod extends AbstractApiMethodV2 {

    public FindFlagMethod() {
        replaceUrlPlaceholder("base_url",Configuration.getRequired("soap_url"));
    }
}
