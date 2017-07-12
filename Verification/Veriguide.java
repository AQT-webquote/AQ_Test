package Verification;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import AQ_test.Excel_write;
import CommonFunction.RFQ_SUB;

public class Veriguide {
	//define constant

	public String getDate(String Test){
		
		// Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 
		 //get current date time with Date()
		 Date date = new Date();
		 
		 // Now format the date
		 String date1= "Tested at : "+dateFormat.format(date);
		 
		 // Print the Date
		 System.out.println(Test + " tested at : " +date1);
		
		 return date1;
	}
	
	public void Veri_Common(String testcase, String PN, String path) throws IOException{
		
		 //Create an array with the data in the same order in which you expect to be filled in excel file
		
		String[] valueToWrite = {testcase,"Pass",getDate(testcase),PN,path};
        Excel_write objExcelFile_write = new Excel_write();
		objExcelFile_write.writeExcel(RFQ_SUB.MASTER_FILE,RFQ_SUB.TEST_SHEETNAME,valueToWrite);
		System.out.println("Status : "+ testcase +" test passed.");
		
		
	}
	
	public void Veri_Fail(String testcase, String PN,String path) throws IOException{
		
		 //Create an array with the data in the same order in which you expect to be filled in excel file
		String[] valueToWrite = {testcase,"Fail",getDate(testcase),PN,path};
        Excel_write objExcelFile_write = new Excel_write();
		objExcelFile_write.writeExcel(RFQ_SUB.MASTER_FILE,RFQ_SUB.TEST_SHEETNAME,valueToWrite);
		System.out.println("Status : "+ testcase +" test passed.");
		
		
		
	}
	
	public void VeriLink(WebDriver driver,String link,String testcase) throws Exception{
		
		Thread.sleep(2000);
		
		String url = driver.getCurrentUrl();
		
		if(url.equals(link))
		{        
			  //Create an array with the data in the same order in which you expect to be filled in excel file
			String[] valueToWrite = {testcase,"Pass",getDate(testcase),"",""};
	        Excel_write objExcelFile_write = new Excel_write();
			objExcelFile_write.writeExcel(RFQ_SUB.MASTER_FILE,RFQ_SUB.TEST_SHEETNAME,valueToWrite);
			getDate("AQ/1/2");
			System.out.println("Status : "+ testcase +" test passed.");


		}else{
			String[] valueToWrite = {testcase,"Fail",getDate(testcase),"",""};
			Excel_write objExcelFile_write = new Excel_write();
			objExcelFile_write.writeExcel(RFQ_SUB.MASTER_FILE,RFQ_SUB.TEST_SHEETNAME,valueToWrite);
			getDate("AQ/1/2");
			System.out.println("Status : "+ testcase +" test failed.");
			System.out.println(url);
		}
		
	}

	public void VeriLogin(String testcase, String[][] quote, int rowcount){
		
		String PN = new String();
		
		for (int i =1; i < rowcount; i++ ){
			
			PN = PN + quote[i][2] + " ,\n"; 

		}
		
		String[] valueToWrite = {testcase,"Pass",getDate(testcase),PN,""};
		
		System.out.println("Status : "+valueToWrite[0]+" test passed.");
		
		//Create an object of current class
        Excel_write objExcelFile_write = new Excel_write();

        //Write the file using file name, sheet name and the data to be filled
        try {
			objExcelFile_write.writeExcel(RFQ_SUB.MASTER_FILE,RFQ_SUB.TEST_SHEETNAME,valueToWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
