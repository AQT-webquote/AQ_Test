package AQ_test;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import CommonFunction.RFQ_SUB;

public class Excel_read {
		
	
	public int readloginCount() throws IOException{
		
		//Variables global = new Variables();
		
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.MASTER_FILE);
	    
	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
	    
	    Workbook MyWorkbook = null;

	   	MyWorkbook = new XSSFWorkbook(inputStream);

	    //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.LOGIN_SHEETNAME_SALES);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();
			    
	    
	    inputStream.close();
	    
	    return rowCount; 
	}
	  
	public int readQuoteCount() throws IOException{
		
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.MASTER_FILE);
	    
	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
	    
	    Workbook MyWorkbook = null;

	   	MyWorkbook = new XSSFWorkbook(inputStream);

	    //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.QUOTE_SHEETNAME);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();
			    
	    inputStream.close();
		
		return rowCount;
		
	}
	
	
	public String[] readLoginInfo(int row) throws IOException{
				
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.MASTER_FILE);
	    
	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
	    
	    Workbook MyWorkbook = null;

	   	MyWorkbook = new XSSFWorkbook(inputStream);

	   	Sheet MySheet = null;
	   	
	    //Read sheet inside the workbook by its name
	   	if(row==1){
	   		
	    MySheet = MyWorkbook.getSheet(RFQ_SUB.LOGIN_SHEETNAME_SALES);
	    
	   	}
	   	else if(row==2){
	   		
		MySheet = MyWorkbook.getSheet(RFQ_SUB.LOGIN_SHEETNAME_QC);	
	   		
	   	}else if(row==3){
	   		
	    MySheet = MyWorkbook.getSheet(RFQ_SUB.LOGIN_SHEETNAME_SALES);
	   		
	   		
	   	}else if(row==4){
	   		
	    MySheet = MyWorkbook.getSheet(RFQ_SUB.LOGIN_SHEETNAME_SALES);  		
	   		
	   	}

	    
	    String[] results = new String[2];
	    results[0] = MySheet.getRow(2).getCell(0).toString();
	    results[1] = MySheet.getRow(2).getCell(3).toString();
	    return results;
		
	}
	
	public String[][] read_Quote() throws IOException{
		
		
		
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.MASTER_FILE);

	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
		
	    Workbook MyWorkbook = null;
	    
	    MyWorkbook = new XSSFWorkbook(inputStream);
	    
	  //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.QUOTE_SHEETNAME);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();

	    
		String[][] Quote = new String[rowCount][42];

	    	//choose the corresponding row
	    
	        Row row = MySheet.getRow(1);


	        //Create a loop to print cell values in a row

	        double temp =0 ;
	        DecimalFormat df = new DecimalFormat("###.#");
	        for (int i =0 ; i < rowCount; i++){
	        	
	        	if(i!=0)
	        	row = MySheet.getRow(1+i);
	
	        for (int j = 0; j < row.getLastCellNum(); j++) {

	        	Cell cell = row.getCell(j);
	        	if(cell.getCellTypeEnum()==CellType.NUMERIC){
	        		
	        		temp = cell.getNumericCellValue();
	        		Quote[i][j] = df.format(temp);
	        		
	        	}
	        	else if(cell.getCellTypeEnum()==CellType.BLANK)
	        		break;
	        	else
	            //Print Excel data in console
	        		Quote[i][j] = cell.getStringCellValue();
	        }
        	

	        }
	        
	        MyWorkbook.close();
	        
	        inputStream.close();
	        
	        
	    return Quote;
	    		
    	//Close input stream
    	
	    }
	    
public String[][] read_Downloaded_Quote() throws IOException{
		
		
		
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.DOWNLOADFILE);

	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
		
	    Workbook MyWorkbook = null;
	    
	    MyWorkbook = new XSSFWorkbook(inputStream);
	    
	  //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.DOWNLOADSHEET);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();

	    
		String[][] Quote = new String[rowCount][12];

	    	//choose the corresponding row
	    
	        Row row = MySheet.getRow(1);


	        //Create a loop to print cell values in a row

	        double temp =0 ;
	        DecimalFormat df = new DecimalFormat("###.#");
	        for (int i =0 ; i < rowCount; i++){
	        	
	        	if(i!=0)
	        	row = MySheet.getRow(1+i);
	
	        for (int j = 0; j < row.getLastCellNum(); j++) {

	        	Cell cell = row.getCell(j);
	        	if(cell.getCellTypeEnum()==CellType.NUMERIC){
	        		
	        		temp = cell.getNumericCellValue();
	        		Quote[i][j] = df.format(temp);
	        		
	        	}
	        	else if(cell.getCellTypeEnum()==CellType.BLANK)
	        		break;
	        	else
	            //Print Excel data in console
	        		Quote[i][j] = cell.getStringCellValue();
	        }
        	

	        }
	        
	        MyWorkbook.close();
	        
	        inputStream.close();
	        
	        
	    return Quote;
	    		
    	//Close input stream
    	
	    }
	
	public String[][] read_Excel() throws IOException{
		
		
		
		//Create an object of File class to open xlsx file
	    File file =    new File(RFQ_SUB.DOWNLOADFILE);

	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
		
	    Workbook MyWorkbook = null;
	    
	    MyWorkbook = new XSSFWorkbook(inputStream);
	    
	  //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.DOWNLOADSHEET);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();

	    
		String[][] Quote = new String[rowCount][14];

	    	//choose the corresponding row
	    
	        Row row = MySheet.getRow(1);


	        //Create a loop to print cell values in a row

	        double temp =0 ;
	        DecimalFormat df = new DecimalFormat("###.#");
	        for (int i =0 ; i < rowCount; i++){
	        	
	        	if(i!=0)
	        	row = MySheet.getRow(1+i);
	
	        for (int j = 0; j < row.getLastCellNum(); j++) {

	        	Cell cell = row.getCell(j);
	        	if(cell.getCellTypeEnum()==CellType.NUMERIC){
	        		
	        		temp = cell.getNumericCellValue();
	        		Quote[i][j] = df.format(temp);
	        		
	        	}
	        	else if(cell.getCellTypeEnum()==CellType.BLANK)
	        		break;
	        	else
	            //Print Excel data in console
	        		Quote[i][j] = cell.getStringCellValue();
	        }
        	

	        }
	        
	        MyWorkbook.close();
	        
	        inputStream.close();
	        
	        
	    return Quote;
	    		
    	//Close input stream
    	
	    }

	
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

	    //Create an object of File class to open xlsx file
	    File file =    new File(filePath+"\\"+fileName);

	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook MyWorkbook = null;

	    //Find the file extension by splitting file name in substring  and getting only extension name
	    String fileExtensionName = fileName.substring(fileName.indexOf("."));

	    //Check condition if the file is xlsx file
	    if(fileExtensionName.equals(".xlsx")){

	    	MyWorkbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file
	    else if(fileExtensionName.equals(".xls")){

	    	MyWorkbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name
	    Sheet MySheet = MyWorkbook.getSheet(RFQ_SUB.QUOTE_SHEETNAME);

	    //Find number of rows in excel file
	    int rowCount = MySheet.getLastRowNum()-MySheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it
	    for (int i = 0; i < rowCount+1; i++) {

	        Row row = MySheet.getRow(i);

	        //Create a loop to print cell values in a row

	        for (int j = 0; j < row.getLastCellNum(); j++) {

	        	Cell cell = row.getCell(j);
	        	if(cell.getCellTypeEnum()==CellType.NUMERIC)
	        		System.out.print(cell.getNumericCellValue()+"||");
	        	else if(cell.getCellTypeEnum()==CellType.BLANK)
	        		break;
	        	else
	            //Print Excel data in console
	            System.out.print(row.getCell(j).getStringCellValue()+"|| ");

	        }

	        System.out.println();

	    }
	    
    	//Close input stream
    	inputStream.close();
	    }
}
