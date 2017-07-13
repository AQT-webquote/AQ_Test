package AQ_test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Verification.Veriguide;

public class Excel_write {

	public boolean isEmpty(Row row) 
	{
		int fcell=row.getFirstCellNum();
		int lcell=row.getLastCellNum();
	    boolean flag = true;
	    for (int i = fcell; i < lcell; i++) {
	    if (StringUtils.isEmpty(String.valueOf(row.getCell(i))) == true || 
	        StringUtils.isWhitespace(String.valueOf(row.getCell(i))) == true || 
	        StringUtils.isBlank(String.valueOf(row.getCell(i))) == true || 
	        String.valueOf(row.getCell(i)).length() == 0 || 
	        row.getCell(i) == null) {} 
	    else {
	                flag = false;
	        }
	    }
	        return flag;
	}
	
	
		
	public void writeExcel(String filePath,String sheetName, String[] dataToWrite) throws IOException{
		Veriguide veri = new Veriguide();
		
		dataToWrite[2] = veri.getDate(dataToWrite[0]);
		
		//Create an object of File class to open xlsx file
        File file =    new File(filePath);
        
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        
		//Create an object of FileOutputStream class to create write data in excel file

	    Workbook MyWorkbook = null;


        MyWorkbook = new XSSFWorkbook(inputStream);


    //Read excel sheet by sheet name    
    Sheet sheet = MyWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file
    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

    //Get the first row from the sheet
    Row row = sheet.getRow(0);
/*
    //remove blank row
    for(int i = 0; i < sheet.getLastRowNum(); i++){
        if(sheet.getRow(i).getFirstCellNum() > 0 && sheet.getRow(i).getLastCellNum() >0 && isEmpty(sheet.getRow(i))){
            sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            System.out.println("deleted one row");
            i--;//Adjusts the sweep in accordance to a row removal
        }
    }
*/
    
    
    //Create a new row and append it at last of sheet
    Row newRow = sheet.createRow(rowCount+1);
    
    //Create a loop over the cell of newly created Row
    for(int j = 0; j < row.getLastCellNum(); j++){
    	
        //Fill data in row

        Cell cell = newRow.createCell(j);

        cell.setCellValue(dataToWrite[j]);

    }

    	//Close input stream
    	inputStream.close();
    	
	    FileOutputStream outputStream = new FileOutputStream(file);

	    //write data in the excel file
	    MyWorkbook.write(outputStream);

	    //close output stream
	    outputStream.close();
		
	}
	
}
