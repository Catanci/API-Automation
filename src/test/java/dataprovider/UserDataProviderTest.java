package dataprovider;

import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pet.store.User;
import pet.store.api.users.XlsxPostMethod;

import java.io.IOException;

public class UserDataProviderTest {

    @Test(dataProvider = "DP1")
    @MethodOwner(owner = "abuda")
    public void testCreateUserFromObject(String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        User user = new User();

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);


        XlsxPostMethod xlsxPostMethod = new XlsxPostMethod();
        xlsxPostMethod.setRequestBody(user);
        xlsxPostMethod.callAPIExpectSuccess();
    }

    @DataProvider(name = "DP1")
    public static Object[][] provideUserData() {
        return new Object[][]{
                {"bogdanoff", "John", "Doe", "example@example.com", "password", "123456789", 0},
        };
    }

    @Test
    @MethodOwner(owner = "abuda")
    public void testCreateUsersFromExcel() throws IOException {
        String filePath = "src/test/resources/data_source/UserTestData.xlsx";

        XlsxPostMethod xlsxPostMethod = new XlsxPostMethod();
        xlsxPostMethod.sendRequestsFromExcel(filePath);
        xlsxPostMethod.callAPIExpectSuccess();
        xlsxPostMethod.validateResponse();
    }
}
