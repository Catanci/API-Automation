package pet.store.test;


import com.zebrunner.carina.api.apitools.validation.JsonCompareKeywords;

import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;

import pet.store.api.DeletePetMethods;
import pet.store.api.GetPetMethods;
import pet.store.api.PostPetMethods;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class PetTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test()
    @MethodOwner(owner = "abuda")
    public void testCreatePet() {
        LOGGER.info("Test is starting...");
        PostPetMethods api = new PostPetMethods();
        api.setProperties("api/users/user.properties");
        api.expectResponseStatus(HttpResponseStatusType.OK_200);
        api.callAPI();
        api.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testCreatePetMissingSomeFields() throws Exception {
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
        DeletePetMethods deletePetMethods = new DeletePetMethods();
        deletePetMethods.setProperties("api/users/user.properties");
        deletePetMethods.callAPIExpectSuccess();
        deletePetMethods.validateResponse();
    }
}
