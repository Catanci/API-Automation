//package booker_test;
//
//import com.zebrunner.carina.api.apitools.validation.XmlCompareMode;
//import com.zebrunner.carina.core.IAbstractTest;
//import booker.soap.AddIntegerMethod;
//import booker.soap.LookupCityMethod;
//import io.restassured.path.xml.XmlPath;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//public class SoapSampleTest implements IAbstractTest {
//
//    @Test
//    public void testAddInteger() {
//        AddIntegerMethod soap = new AddIntegerMethod();
//        soap.setProperties("api/soap/soap.properties");
//
//        Response response = soap.callAPIExpectSuccess();
//        XmlPath rsBody = XmlPath.given(response.asString());
//        Integer actualResult = rsBody.getInt("AddIntegerResult");
//        Integer expectedResult = Integer.valueOf(soap.getProperties().getProperty("result"));
//        Assert.assertEquals(actualResult, expectedResult);
//    }
//
//    @Test
//    public void testLookupCity() {
//        LookupCityMethod soap = new LookupCityMethod();
//        soap.setProperties("api/soap/soap.properties");
//
//        soap.callAPIExpectSuccess();
//        soap.validateXmlResponse(XmlCompareMode.STRICT);
//    }
//
//}
