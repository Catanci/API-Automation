package pet.store.api.users;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pet.store.User;

import java.io.FileInputStream;
import java.io.IOException;

@Endpoint(url = "${base_url}/v2/user", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@ResponseTemplatePath(path = "api/users/_post/user_xlsx_rs.json")

public class XlsxPostMethod extends AbstractApiMethodV2 {

    public XlsxPostMethod() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }

    private Object[][] readExcelData(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum()+1;
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                data[i][j] = cell.getStringCellValue();
            }
        }
        workbook.close();
        fileInputStream.close();
        return data;
    }

    public void sendRequestsFromExcel(String filePath) throws IOException {
        Object[][] excelData = readExcelData(filePath);
        for (Object[] rowData : excelData) {
            String username = (String) rowData[1];
            String firstName = (String) rowData[2];
            String lastName = (String) rowData[3];
            String email = (String) rowData[4];
            String password = (String) rowData[5];
            String phone = (String) rowData[6];

            User user = new User();
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);

            setRequestBody(user);
        }
    }
}
