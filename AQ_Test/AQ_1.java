package AQ_test;

import static org.testng.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import CommonFunction.Operation;
import CommonFunction.RFQ_SUB;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

//@Listeners(CommonFunction.ScreenshotListener.class)
@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
MethodListener.class })
public class AQ_1{
	
	 {
         System.setProperty("atu.reporter.config", "lib\\atu.properties");

       } 	

		public static WebDriver driver;
		
		RFQ_SUB RFQ = new RFQ_SUB();
		Operation operation = new Operation();
		
		@BeforeClass (description = "initialize chrome browser")
		public void setup() throws IOException{
			
			Excel_read reader = new Excel_read();

			
			RFQ.quote_count = reader.readQuoteCount(); // read the number of quote items
						
		
			
			RFQ.QUOTE = new String[RFQ.quote_count][42]; // set the size of QUOTE array 
			
			RFQ.USERInfo = reader.readLoginInfo(1); // 1=sales, 2=QC, 3=...
			
			RFQ.QUOTE = reader.read_Quote(); // read all quote data
			
			//for(int i=0;i<RFQ.quote_count;i++)
			//System.out.println(RFQ.QUOTE[i][36]+" || ");
			
			//System.exit(1);
			
			Init_browser("https://emasiaweb-test.avnet.com/webquote3"); //type in expected link, start the browser

			setAuthorInfoForReports();

		}
		
		@Test (description = "upload AQ pricer")
		public void NPR_1_1() throws IOException, InterruptedException{
			
			Excel_read reader = new Excel_read();

			String [] QC_INFO = new String [2];
			
			QC_INFO = reader.readLoginInfo(2); // 1=sales, 2=QC, 3=...
			
			System.out.println("============================== Executing NPR/1/1 ==============================");

			operation.Login(driver, QC_INFO);
			
			ATUReports.add("Login to QC account", false);
			
			//mouse hover to span menu
			
			Actions actions = new Actions(driver);
			
			WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt29']/ul/li[3]/a"));
			actions.moveToElement(mainMenu);

			WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt29']/ul/li[3]/ul/li[1]/a"));
			actions.moveToElement(subMenu);
			actions.click().build().perform();
			
			ATUReports.add("Go to upload pricer page", true);
			
			//dropdown menu	
			
			Select dropdown = new Select(driver.findElement(By.id("uploadProgramItem:action")));
			
			dropdown.selectByVisibleText("ADD/UPDATE");
			
			//upload button
			
			driver.findElement(By.id("uploadProgramItem:j_idt86")).sendKeys(RFQ_SUB.PRICER);
			
			driver.findElement(By.id("uploadProgramItem:j_idt87")).click();
			
			Thread.sleep(2000);
			
			operation.Logout(driver);
		}
		
		
		
	
		@SuppressWarnings("deprecation")
		@Test	(priority =1, description= "Logon WebQuote as Sales/CS")
		public void AQ_1_1() throws InterruptedException {
		
		System.out.println("============================== Executing AQ/1/1 ==============================");

		operation.Login(driver, RFQ.USERInfo);
		
		ATUReports.add("Login","ID: "+RFQ.USERInfo[0]+"\nPW: "+RFQ.USERInfo[1], false);
		
		String currenturl = driver.getCurrentUrl();

		//match the link
		
		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf", currenturl,true);

		Assert.assertEquals(currenturl, "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf");
	}
		
		@Test (priority =1, description = "Select RFQ submission page, check auto-filled region")
		public void AQ_1_2(){
			
			System.out.println("============================== Executing AQ/1/2 ==============================");

			//operation.HoverMouse(driver, "//*[@id='menu_form:j_idt33']/ul/li[2]/a","//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a", 1); //move mouse to quote -> rfq submission

			Actions actions = new Actions(driver);
			
			WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/a"));
			
			WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a"));

			actions.moveToElement(mainMenu);
			
			actions.moveToElement(subMenu);

			actions.click().build().perform();
			
			//verify link
			
			String currenturl = driver.getCurrentUrl();
			

			Assert.assertEquals(currenturl,"https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1"); 

			ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1", currenturl,true);

		}
		
		@Test (priority =1, description = "IN RFQ header, enter mandatory field")
		public void AQ_1_3() throws InterruptedException{
			
			System.out.println("============================== Executing AQ/1/3 to AQ/1/6 ==============================");

			
			//sold to code
			
			operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", RFQ.QUOTE[1][6]);
			operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", "ENTER");
			
			ATUReports.add("Input Sold to Code", false);
			
			//wait until text come out in STP
			
			while(driver.findElement(By.id("form_rfqSubmission:basicDetails_soldToCustomerName")).getAttribute("value").length() == 0)
					Thread.sleep(1000);
						
			//customer type-- drop down menu
			
			operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_customerType", RFQ.QUOTE[1][19]);
			 
			ATUReports.add("Input Customer type (drop down menu)", RFQ.QUOTE[1][19], false);
 
			 //segment 
			 
			operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_enquirySegment", RFQ.QUOTE[1][21]);

			ATUReports.add("Input Segment (drop down menu)", RFQ.QUOTE[1][21], false);

			
			 //quote type
			 
			operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_quoteType", RFQ.QUOTE[1][31]);
			
			ATUReports.add("Input Quote type", RFQ.QUOTE[1][31], false); 
			
			 //design in cat
			
			operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_designInCat", RFQ.QUOTE[1][26]);
			
			ATUReports.add("Input Design in Cat", RFQ.QUOTE[1][26], true);
		}
		
		@Test (priority =1, description = "It enter mandatory field in RFQ detail")
		public void AQ_1_4_to_1_6() throws InterruptedException{
			
			WebDriverWait wait = new WebDriverWait(driver, 40);

			int s;
			 //add RFQ deatils items row
			 if (RFQ.quote_count>5){
				  s = (int)Math.ceil((double)RFQ.quote_count/10);
				 if(RFQ.quote_count<=10){
					 
					 operation.ClickElementXP(driver, RFQ_SUB.ADD_10_ITEMS_XP, 2);
					 
				 }else {
					 for(int i=0; i < s ; i++)
						 operation.ClickElementXP(driver, RFQ_SUB.ADD_10_ITEMS_XP, 2);
				 		}			 
				 }
			
			//RFQ Details part
			 int i=0;
			 int j=1;
			 //MRF
				 for (i =0 ; i < RFQ.quote_count ; i++){
					 
					 if(i !=0){
				if(i%5==0){
					 
					operation.ClickElementXP(driver, RFQ_SUB.ADD_10_ITEMS_XP, 2);

					operation.ClickElementXP(driver, RFQ.NEXT_PAGE_XP, 1);
					j=0;
					}	 
				}
					 
				if (i==0){
					
				//click on magnifier
				 operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":j_idt203",4);
				 
				 ATUReports.add("Click on magnifier", false);
				 
				// trigger web element in sub window
				 WebElement triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
				 
				 triggerDropDown.click();
				 
				 WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
				 
				 Select select = new Select(selectElement);
				 
				 select.selectByVisibleText(RFQ.QUOTE[i][0]);
				 
				 ATUReports.add("Select MRF dropdown list",RFQ.QUOTE[i][0], false);
				 
				 //fill in text field in sub window
				 
				 operation.InputTextID(driver, "form_rfqSubmission_partNumber:j_idt685",RFQ.QUOTE[i][1]);
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']", 0);

				 ATUReports.add("Input P/N and click search button",RFQ.QUOTE[i][0], false);
				 
				//checkbox in sub window
					
				 WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission_partNumber:datatable_search_alternate_data']/tr[1]/td[2]/div/div[2]/span")));
				 
				 checkbox.click();
				 
				 ATUReports.add("Click Checkbox", true);
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SelectButton']/span", 3);
					
				 //required quantity
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[12]", 0);

				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", RFQ.QUOTE[i][36]);
				 
				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", "ENTER");
				
				 Thread.sleep(2000);
				 
				 //ATUReports.add("Input required quantity | end of first item", false);
				}
				 else{
					 //wait for visibility of dropdown menu
					 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":mfr_in")));
					 //MRF 
					 operation.DropMenuID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":mfr_in", RFQ.QUOTE[i][0]);
					 
					 //ATUReports.add("Select MRF", RFQ.QUOTE[i][0], false);
					 
		      // P/N
					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[5]", 3);
					 				 
					 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":requiredPartNumber_in_input']", RFQ.QUOTE[i][1]);
					 
					 //ATUReports.add("Input P/N", RFQ.QUOTE[i][1], false);
 
					 //wait for dropdown menu displayed
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]")));
					 System.out.println("Begin selecting correct P/N");
					 //choose dropdown	, search for correct row
					 String searchText = RFQ.QUOTE[i][1]+" || "+RFQ.QUOTE[i][0];
					 WebElement dropdown = driver.findElement(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel"));
					 List<WebElement> options = dropdown.findElements(By.tagName("li"));
					 for (WebElement option : options)
					 {
					     if (option.getText().equals(searchText))
					     {
							 //wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span"), RFQ.QUOTE[i][1]));
					    	 Actions builder = new Actions(driver);
					    	 builder.moveToElement(option).build().perform();
							 //ATUReports.add("Click on corresponding P/N in dropdown menu", RFQ.QUOTE[i][1], true);

					     }
					 }
					 System.out.println("Selected ");

					 // wait until drop down menu displayed again
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]")));
					 System.out.println("awaiting auto complete jquery");

					 // wait for auto-fill function within dropdown menu
					 try{
						 
						 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[5]", 3);
						 
						 wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span"), RFQ.QUOTE[i][1]));
					 
						 //ATUReports.add("Wait for autofill query", RFQ.QUOTE[i][1],driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span")).getText(), true);
					 						 
					 }catch (TimeoutException e) {
		                    String message="time out!";
							 ATUReports.add("Wait for autofill query", RFQ.QUOTE[i][1],driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span")).getText(), false);
							 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[12]", 0);
							 throw new TimeoutException(message, e);
							
		                }
					 
					 System.out.println("P/N "+i+" matching succeeded");

					 					 
					//required quantity
					 
					 Thread.sleep(3000);

					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[12]", 0);
					 
					 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":basicDetails_input_requiredQty_in']", RFQ.QUOTE[i][36]);
					 
					 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":basicDetails_input_requiredQty_in']", "ENTER");
				     
					 Thread.sleep(1000);

					 //ATUReports.add("Input required quantity", RFQ.QUOTE[i][36], false);
					
					 Thread.sleep(3000);

					 if((driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":requiredPartNumber_in_input']")).isDisplayed())==true)
					 {
						 i--;
						 j--;
					 }
						 	 j++;
					
				 }
					//if program can run until here, it means AQ/1/4, AQ/1/5 is passed
				 }
		}
		
		@Test (priority =1, description = "Move to DRMS Match")
		public void AQ_1_7() throws InterruptedException{
			
			
			 System.out.println("============================== Executing AQ/1/7 ==============================");
			
			 operation.ClickElementID(driver, RFQ.NEXT_BUTTON_ID, 3);

			 assertEquals(driver.findElement(By.id("form_rfqSubmission:drmsPendingEntering_popup")).isDisplayed(),true);
				
			 Thread.sleep(1000);

			 ATUReports.add("Moved to DRMS Match Page", true);
				
			 Thread.sleep(1000);

		}
		
		@Test (priority =1, description = "Move to [confirmation] page")
		public void AQ_1_8() throws InterruptedException{
			
			System.out.println("============================== Executing AQ/1/8 ==============================");


			 operation.ClickElementID(driver, RFQ.NEXT_BUTTON_ID, 4);
			 
			 assertEquals(driver.findElement(By.id("form_rfqSubmission:datatable_confirmation_rfqSubmission:j_idt520")).getText(),"Confirm To Submit RFQ");

			 Thread.sleep(1000);

			 ATUReports.add("Move to Confirmation Page", true);
				
			 Thread.sleep(1000);

		}
		
		@Test (priority =2, description = "Confirm results of submitted items")
		public void AQ_1_9() throws InterruptedException{
			
			System.out.println("============================== Executing AQ/1/9 ==============================");
			
			WebDriverWait wait = new WebDriverWait(driver, 25);

			String AQ_status = new String();

			String f_status = new String();
			
			String PN = new String();
			
			String Form_No = new String();
			
			String Quote_No = new String();
			
			Excel_read reader = new  Excel_read();
				
			RFQ.QUOTE_DL = new String[RFQ.quote_count][12]; // set the size of QUOTE array 
				
					 
			boolean displayed;
			
			Thread.sleep(2000);
			
			operation.ClickElementID(driver, "form_rfqSubmission:datatable_confirmation_rfqSubmission:j_idt520", 2);
			 
			operation.ClickElementID(driver, "form_rfqSubmission:confirm_submit_dialog_yes", 2);
			
			//check AQ
			int j=1;
			 for(int i=0;i<RFQ.quote_count;i++){
				 if (i%5==0 && i!=0){
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]")));
					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]", 1);
					 j=1;
					 
				 }
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")));
				 AQ_status = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")).getText();
				 f_status = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[13]")).getText();
				 displayed = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:j_idt81_content']/table[2]/tbody/tr/td[6]/h3")).isDisplayed();
				 PN = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[5]")).getText();	 					 
				 Form_No = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[2]")).getText();
				 Quote_No = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[3]")).getText();
				 RFQ.QUOTE_DL[i][0] = Form_No;
				 RFQ.QUOTE_DL[i][1] = Quote_No;
				 
				 assertEquals(AQ_status,"AQ");
				 
				 assertEquals(f_status,"FINISH");
					 
				 assertEquals(displayed, true);
				 

					Thread.sleep(1000);

					 ATUReports.add("Check RFQ status and Quote stage", "AQ+FINISH", "P/N: "+PN+" AQ staus: "+AQ_status+" Finish status: "+f_status, false);
						
					 Thread.sleep(1000);

					 if(i==0 || (i)%5==0){
						 //scroll to bottom
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
					
					Thread.sleep(1000);
					 ATUReports.add("Screenshotof results", true);
					Thread.sleep(1000);

					 }
					 j++;
					 
				 }
			 
			 driver.close();
			 }
			 
			@Test (priority=3, description = "download and read excel file")
			 public void AQ_1_10() throws InterruptedException, IOException{
								 				 
				 System.out.println("===================Executing AQ/1/10========================");
						
				 Init_browser("https://emasiaweb-test.avnet.com/webquote3"); //type in expected link, start the browser
				 
				 WebDriverWait wait = new WebDriverWait(driver, 40);
				 
				 operation.Login(driver, RFQ.USERInfo);
				 
				//mouse hover to span menu
					
					Actions actions = new Actions(driver);
					
					WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/a"));
					actions.moveToElement(mainMenu);

					WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[3]/a/span"));
					actions.moveToElement(subMenu);
					actions.click().build().perform();
				 
				 assertEquals(driver.getCurrentUrl(), "https://emasiaweb-test.avnet.com/webquote3/RFQ/MyQuoteListForSales.jsf");
				 
				 ATUReports.add("Go to my quote search page", false);

				 //enter Avnetquote no. and form no.
				 
				 for(int i = 0; i < RFQ.quote_count; i++){
					operation.InputTextID(driver, "form:j_idt84:criteriaQuoteNumber", RFQ.QUOTE_DL[i][1]); //quote no.
					 
					 operation.InputTextID(driver, "form:j_idt84:criteriaQuoteNumber", "ENTER");
					 
					 //operation.InputTextID(driver, "form:j_idt84:j_idt89", RFQ.QUOTE_DL[i][1]); //quote no.
					 
					 //operation.InputTextID(driver, "form:j_idt84:j_idt89", "ENTER"); //form no.
				 }
				 
				 //click button
				 
				 operation.ClickElementID(driver, "form:j_idt84:j_idt141", 4);
				 
				 while(driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr[1]/td[4]")).getText()=="");
				 
				 for(int i=0; i<RFQ.quote_count;i++){
					 if(i==0){
					 
						 ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

						 Thread.sleep(1000);
						 
						 ATUReports.add("1st screenshot  on 1st page", true);
						 
						 Thread.sleep(1000);
						 
						 
						//scroll right to see details
						 ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr/td[51]")));

						 Thread.sleep(1000);
						 
						 ATUReports.add("2nd screenshot  on 1st page", true);

						 Thread.sleep(1000);
						 
						 if(RFQ.quote_count>6){
							 
						 ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr[7]/td[2]")));
						
						 Thread.sleep(1000);
						 
						 ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr[9]")));
						 
						 Thread.sleep(1000);
						 
						 ATUReports.add("3rd screenshot  on 1st page", true);
						 
						 Thread.sleep(1000);
						 
						 //scroll right to see details
						 ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr/td[51]")));
						 
						 Thread.sleep(1000);
						 
						 ATUReports.add("4th screenshot on 1st page", true);

						 Thread.sleep(1000);
						 
						 }
					 }
						 if(i%10==0 && i!=0){
							 
							 operation.ClickElementXP(driver, "//*[@id='form:datatable_myquotelist_paginator_top']/a[3]", 1);
							 
							 Thread.sleep(1000);
							 
							 ATUReports.add(" screenshot on next page", true);

							 Thread.sleep(1000);
							 
						 }
							 
					 
				 
					 System.out.println("===================Matching row "+(i+1)+" data ===================");
					 System.out.println("Matching quote number ...............");
					 System.out.println("Matching part number ...............");
					 assertEquals(RFQ.QUOTE_DL[RFQ.quote_count-i-1][1],driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr["+(i+1)+"]/td[4]")).getText());
					 assertEquals(RFQ.QUOTE[RFQ.quote_count-i-1][1],driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr["+(i+1)+"]/td[10]")).getText());
					 System.out.println("Successful !");

				 if(i+1 >RFQ.quote_count){
					 
					 ((JavascriptExecutor) driver)
				     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
					 
					 ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr["+i+"]/td[4]")));

					 Thread.sleep(1000);
					 
					 ATUReports.add("screenshot", true);
					 
					 Thread.sleep(1000);
					 
					 ((JavascriptExecutor) driver).executeScript(
					            "arguments[0].scrollIntoView();", driver.findElement(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr/td[51]")));
					 
					 Thread.sleep(1000);
					 
					 ATUReports.add("screenshot 2", true);

					 Thread.sleep(1000);
					 
				 }
				 
				 }
				 //savepage(driver);
				 operation.ClickElementXP(driver, "//*[@id='form:j_idt162']", 2);
				 
			}
			
		/*
		
		@AfterClass (description = "close chrome browser")
		public void close_browser(){
			
			driver.close();
			
		}
	*/
		public void savepage(WebDriver driver){
			
			try (FileWriter fstream = new FileWriter("C:\\Report\\test.html");
				     BufferedWriter out = new BufferedWriter(fstream)) {
				    String content = (String)((JavascriptExecutor)driver).executeScript("return $('html').html();");
				    out.write(content);
				} 
				catch (Exception e) {
					
					
				}
			
		}
		
		
		public void Init_browser(String link){
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\driver\\chromedriver.exe");
			
			driver= new ChromeDriver();
			
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;

			ATUReports.setWebDriver(driver);
			
		    driver.get(link);
			
		    driver.manage().window().maximize();
		}
		
		//set author name
		private void setAuthorInfoForReports() {
			   ATUReports.setAuthorInfo("Charles Chung", Utils.getCurrentTime(),"1.0");
			}

}
