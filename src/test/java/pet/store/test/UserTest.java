package pet.store.test;

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
import pet.store.api.DeleteUserMethods;
import pet.store.api.GetUserMethods;
import pet.store.api.PostUserMethods;

import java.lang.invoke.MethodHandles;

public class UserTest implements IAbstractTest  {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test()
    @TestPriority(Priority.P1)
    @MethodOwner(owner = "abuda")
    public void testCreateUser() {
        LOGGER.info("Test is starting...");
        PostUserMethods api = new PostUserMethods();
        api.setProperties("api/users/user.properties");
        api.expectResponseStatus(HttpResponseStatusType.OK_200);
        api.callAPI();
        api.validateResponse();
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
        GetUserMethods getUserMethods = new GetUserMethods();
        getUserMethods.callAPIExpectSuccess();
        getUserMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getUserMethods.validateResponseAgainstSchema("api/users/_get/rs.schema");
    }
}
