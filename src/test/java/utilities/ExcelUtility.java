package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	// class level variables
    FileInputStream fi;
    FileOutputStream fo;
    Workbook workbook;
    Sheet sheet;
    Row row;
    Cell cell;
    String path;

    // constructor - receives file path
    public ExcelUtility(String path) {
        this.path = path;
    }

    // counts rows in a sheet
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowcount;
    }

    // counts cells in a specific row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellcount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellcount;
    }

    // reads data from a specific cell
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);
        } catch(Exception e) {
            data = "";
        }
        workbook.close();
        fi.close();
        return data;
    }

    // writes data to a specific cell
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {

        // if file doesnt exist create it
        File xlfile = new File(path);
        if(!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        // if sheet doesnt exist create it
        if(workbook.getSheetIndex(sheetName) == -1)
            workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);

        // if row doesnt exist create it
        if(sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        row = sheet.getRow(rownum);

        // create cell and write value
        cell = row.createCell(colnum);
        cell.setCellValue(data);

        // save to file
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }

}
