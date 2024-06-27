package pet.store.api.pets;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;
import io.restassured.specification.RequestSpecification;

import java.io.File;

@Endpoint(url = "${base_url}/v2/pet/123/uploadImage", methodType = HttpMethodType.POST)
@ResponseTemplatePath(path = "api/users/_post/image_rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@Header(key = "Content-Type", value = "multipart/form-data")
public class PostPetImageMethods extends AbstractApiMethodV2 {

    public PostPetImageMethods() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }

    public void uploadImage(String imagePath) {
        File imageFile = new File(imagePath);
        RequestSpecification request = this.getRequest();

        request.multiPart("file", imageFile);
    }
}
