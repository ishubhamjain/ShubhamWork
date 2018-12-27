package Utilities;

/**
 * @author Shubham Jain
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLib {

	static String xlpath = Configuration.getExcelFile();
	public String getXLcellValue(String sheetName, int rowNum, int cellNum)
	{
		try{
			FileInputStream fis=new FileInputStream(xlpath);
			Workbook wb=WorkbookFactory.create(fis);
			
			return wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return "";
	}
	
	//get xl row count
	
	public int getXLRowCount(String sheetName)
	{
		try{
			FileInputStream fis=new FileInputStream(xlpath);
			
			Workbook wb=WorkbookFactory.create(fis);
			
			return wb.getSheet(sheetName).getLastRowNum();
		}
		catch(Exception ex)
		{
			
		}
		return -1;
	}
	
		//set the value of the cell present in specific sheet
	
	public void setXLCellValue(String sheetName,int rowNum,int cellNum, String input)
	{
		try{
			FileInputStream fis=new FileInputStream(xlpath);
			
			Workbook wb=WorkbookFactory.create(fis);
			
			wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(input);
			
			FileOutputStream fos=new FileOutputStream(xlpath);
			
			wb.write(fos);
			
			fos.close();
			
		}
		catch(Exception ex)
		{
			
		}
		
	}
	
	public static List<String> getRowValues(String sheetName, String scenario) throws IOException {
		List<String> sheetval = null;
		HSSFWorkbook workbook = null;
		try {
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(xlpath));
			workbook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workbook.getSheet(sheetName); // Get data as per sheet name
			sheetval = new ArrayList<String>();
			for (Row row : sheet) { // For each Row.
				Cell cell = row.getCell(0); // Get the Cell at the Index / Column you want.
				if (cell.getStringCellValue().equalsIgnoreCase(scenario)) {
					for (int i = 0; i <= cell.getRow().getLastCellNum() - 1; i++)
						sheetval.add(cell.getRow().getCell(i).toString());
				}
			}
			return sheetval;
		} catch (Exception ex) {
			//AutomationLog.error(ex.getMessage(), ex);
		}
		finally {
			workbook.close();
		}
		return sheetval;
	}
}