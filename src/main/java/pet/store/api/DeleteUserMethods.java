
package pet.store.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/v2/user/bogdanoff", methodType = HttpMethodType.DELETE)
//@RequestTemplatePath(path = "api/users/_delete/rq.json")
@ResponseTemplatePath(path = "api/users/_delete/urs.json")
@Header(key = "api-key", value = "special-key")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class DeleteUserMethods extends AbstractApiMethodV2 {

    public DeleteUserMethods() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
