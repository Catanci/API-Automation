package petstore.test;

import com.zebrunner.carina.api.apitools.validation.JsonCompareKeywords;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pet.store.api.pets.PostPetMethods;
import pet.store.api.users.DeleteUserMethods;
import pet.store.api.users.GetUserMethods;
import pet.store.api.users.PostUserMethods;
import pet.store.api.users.PutUserMethods;

import java.lang.invoke.MethodHandles;

public class UserTest implements IAbstractTest  {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test()
    @TestPriority(Priority.P1)
    @MethodOwner(owner = "abuda")
    public void testCreateUser() {
        LOGGER.info("Test is starting...");
        PostUserMethods postUserMethods = new PostUserMethods();
        postUserMethods.setProperties("api/users/user.properties");
        postUserMethods.expectResponseStatus(HttpResponseStatusType.OK_200);
        postUserMethods.callAPI();
        postUserMethods.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    @TestPriority(Priority.P2)
    public void testDeleteUser() {
        DeleteUserMethods deleteUserMethods = new DeleteUserMethods();
        deleteUserMethods.setProperties("api/users/user.properties");
        deleteUserMethods.callAPIExpectSuccess();
        deleteUserMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        deleteUserMethods.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "abuda")
    public void testGetUser() {
        LOGGER.info("Test is starting...");
        PostUserMethods postUserMethods = new PostUserMethods();
        postUserMethods.setProperties("api/users/user.properties");
        postUserMethods.callAPIExpectSuccess();
        postUserMethods.validateResponse();

        GetUserMethods getUserMethods = new GetUserMethods();
        getUserMethods.setProperties("api/users/user.properties");
        getUserMethods.callAPIExpectSuccess();
        getUserMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getUserMethods.validateResponseAgainstSchema("api/users/_get/rs.schema");
    }


    @Test()
    @MethodOwner(owner = "abuda")
    public void testPutUser() {
        PutUserMethods putUserMethods = new PutUserMethods();

        putUserMethods.setProperties("api/users/user.properties");
        putUserMethods.callAPIExpectSuccess();
        putUserMethods.validateResponse();
    }
}
