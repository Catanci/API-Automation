package petstore.test;

import com.zebrunner.carina.api.apitools.validation.JsonCompareKeywords;

import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;

import pet.store.api.pets.PostPetImageMethods;
import pet.store.api.pets.GetPetMethods;
import pet.store.api.pets.PostPetMethods;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pet.store.api.pets.PutPetMethods;

import java.lang.invoke.MethodHandles;


public class PetTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test()
    @MethodOwner(owner = "abuda")
    public void testCreatePet() {
        LOGGER.info("Test is starting...");
        PostPetMethods api = new PostPetMethods();
        api.expectResponseStatus(HttpResponseStatusType.OK_200);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testUploadPetImage() {
        LOGGER.info("Test is starting...");
        PostPetImageMethods api = new PostPetImageMethods();
        String imagePath = "src/test/resources/files/icon.jpg";
        api.uploadImage(imagePath);
        api.expectResponseStatus(HttpResponseStatusType.OK_200);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testCreatePetMissingSomeFields() {
        PostPetMethods api = new PostPetMethods();
        api.setProperties("api/users/user.properties");
        api.getProperties().remove("id");
        api.getProperties().remove("name");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testGetPets() {
        GetPetMethods getPetMethods = new GetPetMethods();
        getPetMethods.callAPIExpectSuccess();
        getPetMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getPetMethods.validateResponseAgainstSchema("api/users/_get/rs.schema");
    }

    @Test()
    @MethodOwner(owner = "abuda")
    @TestPriority(Priority.P1)
    public void testDeletePets() {
        PostPetImageMethods deletePetMethods = new PostPetImageMethods();
        deletePetMethods.setProperties("api/users/user.properties");
        deletePetMethods.callAPIExpectSuccess();
        deletePetMethods.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testPutPets() {
        PostPetMethods postPetMethods = new PostPetMethods();
        postPetMethods.setProperties("api/users/user.properties");
        postPetMethods.callAPIExpectSuccess();
        postPetMethods.validateResponse();
        String jsonPostId = postPetMethods.getProperties().getProperty("id");

        PutPetMethods putPetMethods = new PutPetMethods();
        putPetMethods.setProperties("api/users/user.properties");
        putPetMethods.getProperties().put("put_id", jsonPostId);
        putPetMethods.callAPIExpectSuccess();
        putPetMethods.validateResponse();
    }
}

