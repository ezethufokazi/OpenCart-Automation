package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name="dp")
    public String[][] getData() throws IOException {
        // path to Excel file
        String path = ".\\testData\\LoginData.xlsx";
        ExcelUtility xlutil = new ExcelUtility(path);

        int rows = xlutil.getRowCount("Sheet1");
        int cols = xlutil.getCellCount("Sheet1", 0); // use header row to count columns

        String[][] loginData = new String[rows][cols];

        for(int i = 1; i <= rows; i++) {       // start from 1 to skip header
            for(int j = 0; j < cols; j++) {
                loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        return loginData;
    }
}
