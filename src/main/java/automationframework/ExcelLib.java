package automationframework;

/**
 * @author Shubham Jain
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLib {

	String xlpath = Configuration.getExcelFile();
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
}