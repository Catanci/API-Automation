package pet.store.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/v2/user", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/users/_post/urq.json")
@ResponseTemplatePath(path = "api/users/_post/urs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PostUserMethods extends AbstractApiMethodV2 {

    public PostUserMethods() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}

