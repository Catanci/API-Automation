package soap.test;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pet.store.soap.FindCurrencyMethod;
import pet.store.soap.FindFlagMethod;

public class SoapTests implements IAbstractTest {

    @Test
    @MethodOwner(owner = "abuda")
    public void testLookupCurrency() {
        FindCurrencyMethod soap = new FindCurrencyMethod();
        soap.setProperties("api/soap/soap.properties");
        Response response = soap.callAPIExpectSuccess();
        XmlPath rsBody = XmlPath.given(response.asString());
        String actualResult = rsBody.getString("CurrencyNameResult");
        String expectedResult = String.valueOf(soap.getProperties().getProperty("actualCurrencyName"));
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    @MethodOwner(owner = "abuda")
    public void testLookupFlag() {
        FindFlagMethod soap = new FindFlagMethod();
        soap.setProperties("api/soap/soap.properties");
        Response response = soap.callAPIExpectSuccess();
        XmlPath rsBody = XmlPath.given(response.asString());
        String actualResult = rsBody.getString("countryFlagValue");
        String expectedResult = String.valueOf(soap.getProperties().getProperty("countryFlagResult"));
        Assert.assertEquals(actualResult, expectedResult);
    }
}
