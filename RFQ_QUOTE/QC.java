package RFQ_QUOTE;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import AQ_test.Excel_read;
import CommonFunction.Operation;
import CommonFunction.RFQ_SUB;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

//@Listeners(CommonFunction.ScreenshotListener.class)
@Listeners({ ATUReportsListener.class, ConfigurationListener.class,MethodListener.class })
public class QC {

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
		

	@Test (description = "go to RFQ submission page")
	public void QC_1_1() {
		
		System.out.println("============================== Executing QC/1/1 ==============================");

		System.out.println("==============================Login as sales ==============================");

		operation.Login(driver, RFQ.USERInfo);
		
		ATUReports.add("Login","ID: "+RFQ.USERInfo[0]+"\nPW: "+RFQ.USERInfo[1], false);
		
		String currenturl = driver.getCurrentUrl();

		//match the link
		
		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf", currenturl,true);

		Assert.assertEquals(currenturl, "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf");
		
		System.out.println("==============================Login as sales ============================OK");

		System.out.println("==============================Go to RFQ submission ==============================");

		Actions actions = new Actions(driver);
		
		WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/a"));
		
		WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a"));

		actions.moveToElement(mainMenu);
		
		actions.moveToElement(subMenu);

		actions.click().build().perform();
		
		//verify link
		
		currenturl = driver.getCurrentUrl();
		

		Assert.assertEquals(currenturl,"https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1"); 

		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1", currenturl,true);

		System.out.println("==============================Go to RFQ submission ============================OK");

		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1", currenturl,true);
		
		//Expected outcome : default region is auto filled
		
		Assert.assertEquals(driver.findElement(By.id("form_rfqSubmission:j_idt93")).getAttribute("value").length()>0, true);
		
		ATUReports.add("Salesman code displayed", false);
		
		Assert.assertEquals(driver.findElement(By.id("form_rfqSubmission:j_idt97")).getAttribute("value").length()>0,true);
		
		ATUReports.add("Salesman name displayed", false);
		
		Assert.assertEquals(driver.findElement(By.id("form_rfqSubmission:j_idt99")).getAttribute("value").length()>0, true);
		
		ATUReports.add("Team displayed", false);
		
		Assert.assertEquals(driver.findElement(By.id("form_rfqSubmission:basicDetails_bizUnit")).isEnabled(),true);
		
		ATUReports.add("Region can be modified", false);
		
	}
	
	@Test (description = "able to enter mandatory field")
	public void QC_1_2() throws InterruptedException {
		System.out.println("============================== Executing QC/1/2 ==============================");

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
	
	@Test (description="")
	public void QC_1_3() throws InterruptedException {
		
		System.out.println("============================== Executing QC/1/3 ==============================");
		
		WebDriverWait wait = new WebDriverWait(driver, 40);

		
		//upload function
	     operation.InputTextID(driver, "form_rfqSubmission:j_idt194", RFQ.BATCH_RFQ_FILE);
	     operation.ClickElementID(driver, "form_rfqSubmission:uploadButton", 1);
		
	     /*
		//wait for visibility of dropdown menu
		 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:mfr_in")));
		 //MRF 
		 operation.DropMenuID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:mfr_in", RFQ.QUOTE[0][0]);
		
		 //P/N
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[5]", 3);
		 				 
		 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_input']", RFQ.QUOTE[0][1]);
		 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_panel']/ul/li[1]")));
		 System.out.println("Begin selecting correct P/N");
		 //choose dropdown	, search for correct row
		 String searchText = RFQ.QUOTE[0][1]+" || "+RFQ.QUOTE[0][0];
		 WebElement dropdown = driver.findElement(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_panel"));
		 List<WebElement> options = dropdown.findElements(By.tagName("li"));
		 for (WebElement option : options)
		 {
		     if (option.getText().equals(searchText))
		     {
				 //wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span"), RFQ.QUOTE[i][1]));
		    	 Actions builder = new Actions(driver);
		    	 builder.moveToElement(option).build().perform();
		    	 builder.moveToElement(option).build().perform();

				 //ATUReports.add("Click on corresponding P/N in dropdown menu", RFQ.QUOTE[i][1], true);

		     }
		 }
		 System.out.println("Selected ");

		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_panel']/ul/li[1]")));
		 System.out.println("awaiting auto complete jquery");

		 // wait for auto-fill function within dropdown menu
		
			 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[5]", 3);
			 
			 wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_panel']/ul/li/span"), RFQ.QUOTE[0][1]));
		 
			 //ATUReports.add("Wait for autofill query", RFQ.QUOTE[i][1],driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span")).getText(), true);
	
			//required quantity
			 
			 Thread.sleep(3000);

			 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:j_idt229\']", 0);
			 
			 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", RFQ.QUOTE[0][36]);
			 
			 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", "ENTER");
		     
			 Thread.sleep(3000);
			 
			 //target resale
			 
			 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[1]/td[12]", 1);
			 
			 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_targetResale_in\']", RFQ.QUOTE[0][38]);
			 
			 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_targetResale_in\']", "ENTER");
			 
			 //application
			 
			 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[1]/td[33]", 1);
			 
			 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_application_input\']", RFQ.QUOTE[0][23]);

			 //EAU
			 
			 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[1]/td[13]", 1);
			 
			 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_eau_in\']", RFQ.QUOTE[0][37]);
			 
			 */
			 Thread.sleep(2000);
	}
	/*
	@Test (description= "click magnifer to search for pricer info")
	public void QC_1_4() throws InterruptedException {
		
		System.out.println("============================== Executing QC/1/4 ==============================");

		WebDriverWait wait = new WebDriverWait(driver, 40);

		//click on magnifier
		 operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:j_idt203",4);
		 
		 ATUReports.add("Click on magnifier", false);
		 
		// trigger web element in sub window
		 WebElement triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
		 
		 triggerDropDown.click();
		 
		 WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
		 
		 Select select = new Select(selectElement);
		 
		 select.selectByVisibleText(RFQ.QUOTE[1][0]);
		 
		 ATUReports.add("Select MRF dropdown list",RFQ.QUOTE[1][0], false);
		 
		 //fill in text field in sub window
		 
		 operation.InputTextID(driver, "form_rfqSubmission_partNumber:j_idt685",RFQ.QUOTE[1][1]);
		 
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']", 0);

		 ATUReports.add("Input P/N and click search button",RFQ.QUOTE[1][0], false);
		 
		//required quantity
		 
		 Thread.sleep(3000);

		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[2]/td[12]", 0);
		 
		 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_requiredQty_in']", RFQ.QUOTE[1][36]);
		 
		 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_requiredQty_in']", "ENTER");
	     
		 Thread.sleep(1000);
		 
		 //target resale
		 
		 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[2]/td[12]", 1);
		 
		 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_targetResale_in\']", "0.000001");
		 
		 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_targetResale_in\']", "ENTER");
		 //application
		 
		 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[2]/td[33]", 1);
		 
		 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_application_input\']", RFQ.QUOTE[1][23]);

		 //EAU
		 
		 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[2]/td[13]", 1);
		 
		 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:basicDetails_input_eau_in\']", RFQ.QUOTE[1][37]);
		 
		 //3rd item Tick SFQ
		 
		 	operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:select_id_basicDetails_specialPriceIndicator\']/div[2]/span", 1);
		 
		 	operation.InputTextID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:mfr_in", RFQ.QUOTE[2][1]);
			
			operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in", 1);
			
			operation.InputTextID(driver, "id=\"form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in_input\"", RFQ.QUOTE[2][2]);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in_panel']/ul/li[1]")));
			 System.out.println("Begin selecting correct P/N");
			 //choose dropdown	, search for correct row
			 String searchText = RFQ.QUOTE[2][1]+" || "+RFQ.QUOTE[2][0];
			 WebElement dropdown = driver.findElement(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in_panel"));
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

			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in_panel']/ul/li[1]")));
			 System.out.println("awaiting auto complete jquery");

			 // wait for auto-fill function within dropdown menu
			
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[3]/td[5]", 3);
				 
				 wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:requiredPartNumber_in_panel']/ul/li/span"), RFQ.QUOTE[2][1]));
			 
				 //ATUReports.add("Wait for autofill query", RFQ.QUOTE[i][1],driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span")).getText(), true);
		
				//required quantity
				 
				 Thread.sleep(3000);

				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[3]/td[12]", 0);
				 
				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_requiredQty_in']", RFQ.QUOTE[2][36]);
				 
				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_requiredQty_in']", "ENTER");
			     
				 Thread.sleep(1000);
		 
				//target resale
				 
				 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[3]/td[12]", 1);
				 
				 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_targetResale_in\']", RFQ.QUOTE[2][38]);
				 
				 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_targetResale_in\']", "ENTER");
				 
				 //application
				 
				 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[3]/td[33]", 1);
				 
				 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_application_input\']", RFQ.QUOTE[2][23]);

				 //EAU
				 
				 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr[3]/td[13]", 1);
				 
				 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:2:basicDetails_input_eau_in\']", RFQ.QUOTE[2][37]);
				 
				 
				 Thread.sleep(2000);
				 
				 if(RFQ.quote_count>3) {
					 
					 for(int i=3;i<RFQ.quote_count;i++) {
						 
						//click on magnifier
						 operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":j_idt203",4);
						 
						 ATUReports.add("Click on magnifier", false);
						 
						// trigger web element in sub window
						  triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
						 
						 triggerDropDown.click();
						 
						  selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
						 
						  select = new Select(selectElement);
						 
						 select.selectByVisibleText(RFQ.QUOTE[i][0]);
						 
						 ATUReports.add("Select MRF dropdown list",RFQ.QUOTE[i][0], false);
						 
						 //fill in text field in sub window
						 
						 operation.InputTextID(driver, "form_rfqSubmission_partNumber:j_idt685",RFQ.QUOTE[i][1]);
						 
						 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']", 0);

						 ATUReports.add("Input P/N and click search button",RFQ.QUOTE[i][0], false);
						 
						//required quantity
						 
						 Thread.sleep(3000);

						 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(i+1)+"]/td[12]", 0);
						 
						 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":basicDetails_input_requiredQty_in']", RFQ.QUOTE[i][36]);
						 
						 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":basicDetails_input_requiredQty_in']", "ENTER");
					     
						 Thread.sleep(1000);
						 
						 //target resale
						 
						 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr["+(i+1)+"]/td[12]", 1);
						 
						 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":basicDetails_input_targetResale_in\']", "0.00001");
						 
						 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":basicDetails_input_targetResale_in\']", "ENTER");
						 
						 //application
						 
						 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr["+(i+1)+"]/td[33]", 1);
						 
						 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i+1)+":basicDetails_input_application_input\']", RFQ.QUOTE[i][23]);

						 //EAU
						 
						 operation.ClickElementXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission_data\']/tr["+(i+1)+"]/td[13]", 1);
						 
						 operation.InputTextXP(driver, "//*[@id=\'form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i+1)+":basicDetails_input_eau_in\']", RFQ.QUOTE[i][37]);
						 
					 }
					 
				 }
	}
	
	@Test(description="")
	public void QC_1_5() throws InterruptedException {
		
		System.out.println("============================== Executing QC/1/5 ==============================");

		 WebDriverWait wait = new WebDriverWait(driver, 40);

		//checkbox in sub window
		
		 WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission_partNumber:datatable_search_alternate_data']/tr[1]/td[2]/div/div[2]/span")));
		 
		 checkbox.click();
		 
		 ATUReports.add("Click Checkbox", true);
		 
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SelectButton']/span", 3);
			
	}
	*/
	
	@Test (description = "Move to DRMS Match")
	public void QC_1_6() throws InterruptedException{
		
		
		 System.out.println("============================== Executing QC/1/6 ==============================");
		
		 operation.ClickElementID(driver, RFQ.NEXT_BUTTON_ID, 3);

		 assertEquals(driver.findElement(By.id("form_rfqSubmission:drmsPendingEntering_popup")).isDisplayed(),true);
			
		 Thread.sleep(1000);

		 ATUReports.add("Moved to DRMS Match Page", true);
			
		 Thread.sleep(1000);

	}
	
	@Test ( description = "Move to [confirmation] page")
	public void QC_1_8() throws InterruptedException{
		
		System.out.println("============================== Executing QC/1/8 ==============================");


		 operation.ClickElementID(driver, RFQ.NEXT_BUTTON_ID, 4);
		 
		 assertEquals(driver.findElement(By.id("form_rfqSubmission:datatable_confirmation_rfqSubmission:j_idt520")).getText(),"Confirm To Submit RFQ");

		 Thread.sleep(1000);

		 ATUReports.add("Move to Confirmation Page", true);
			
		 Thread.sleep(1000);
		 
		 //////////////////////////////////////////////////////
		 
		 WebDriverWait wait = new WebDriverWait(driver, 25);

			String QC_STATUS = new String();

			String QUOTE_STAGE = new String();
			
			String PN = new String();
			
			String Form_No = new String();
			
			String Quote_No = new String();
			
			Excel_read reader = new  Excel_read();
				
			RFQ.QUOTE_DL = new String[RFQ.quote_count][12]; // set the size of QUOTE array 
				
			RFQ.quote_count_DL=0;
					 
			boolean displayed;
			
			Thread.sleep(2000);
			
			operation.ClickElementID(driver, "form_rfqSubmission:datatable_confirmation_rfqSubmission:j_idt520", 2);
			 
			operation.ClickElementID(driver, "form_rfqSubmission:confirm_submit_dialog_yes", 2);
			
			//check QC
			int j=1;
			 for(int i=0;i<RFQ.quote_count;i++){
				 if (i%5==0 && i!=0){
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]")));
					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]", 1);
					 j=1;
					 
				 }
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")));
				 QC_STATUS = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")).getText();
				 QUOTE_STAGE = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[13]")).getText();
				 displayed = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:j_idt81_content']/table[2]/tbody/tr/td[6]/h3")).isDisplayed();
				 PN = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[5]")).getText();	 					 
				 Form_No = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[2]")).getText();
				 Quote_No = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[3]")).getText();
				 RFQ.QUOTE_DL[i][0] = Form_No;
				 RFQ.QUOTE_DL[i][1] = Quote_No;
				 RFQ.quote_count_DL++;
				 assertEquals(QC_STATUS,"QC");
				 
				 assertEquals(QUOTE_STAGE,"PENDING");
					 
				 assertEquals(displayed, true);
				 

					Thread.sleep(1000);

					 ATUReports.add("Check RFQ status and Quote stage", "QC+PENDING", "P/N: "+PN+" AQ staus: "+QC_STATUS+" Finish status: "+QUOTE_STAGE, false);
						
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
			 
			// driver.close();
		 

	}
	
	@Test (description = "Login as QC and go to working platform")
	public void QC_2_1() throws IOException, InterruptedException {
		

		 System.out.println("===================Executing QC/2/1========================");

		 Init_browser("https://emasiaweb-test.avnet.com/webquote3"); //type in expected link, start the browser

		 Excel_read reader = new Excel_read();

			String [] QC_INFO = new String [2];
			
			QC_INFO = reader.readLoginInfo(2); // 1=sales, 2=QC, 3=...
			
			operation.Login(driver, QC_INFO);
			
			ATUReports.add("Login to QC account", false);
			
			//mouse hover to span menu
			
			Actions actions = new Actions(driver);
			
			WebElement mainMenu = driver.findElement(By.xpath("//*[@id=\'menu_form:j_idt29\']/ul/li[2]/a"));
			actions.moveToElement(mainMenu);

			WebElement subMenu = driver.findElement(By.xpath("//*[@id=\'menu_form:j_idt29\']/ul/li[2]/ul/li[5]/a"));
			actions.moveToElement(subMenu);
			actions.click().build().perform();
		 
		 assertEquals(driver.getCurrentUrl(), "https://emasiaweb-test.avnet.com/webquote3/WorkingPlatform/WorkingPlatformLayout.jsf");
		 
		 ATUReports.add("Go to working platform", false);
	}
	
	
	@Test (description = "find the quote in qc/1/8 by form # and quote # ")
	public void QC_2_2() throws InterruptedException {
		
		 WebDriverWait wait = new WebDriverWait(driver, 70);

		 

		 operation.DropMenuXP(driver, "//*[@id=\"form_pendinglist:SearchCriteriaPanel:j_idt78\"]/table/tbody/tr/td[8]/select", "ALL");
		 
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id( "form_pendinglist:j_idt455")));
		 
		 operation.ClickElementID(driver, "form_pendinglist:j_idt455", 2);
		 
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist_dialog:j_idt629\']")));

		 //form no
		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:j_idt108:filter", RFQ.QUOTE_DL[0][0]);
		 
		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:j_idt108:filter", "ENTER");
		 
		 Thread.sleep(2000);

		 //quote no
		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:j_idt111:filter", RFQ.QUOTE_DL[0][1]);

		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:j_idt111:filter", "ENTER");
		 
		 Thread.sleep(4000);
		 
		 WebElement textbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist:datatable_pendinglist_data\']/tr[1]/td[2]")));
		 
		 wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\'form_pendinglist:datatable_pendinglist_data\']/tr[1]/td[2]"), RFQ.QUOTE_DL[0][0]));
			 
		
		// while((driver.findElement(By.xpath("//*[@id=\'form_pendinglist:datatable_pendinglist_data\']/tr[1]/td[2]")).getText())!=RFQ.QUOTE_DL[0][0])
		 
		 WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist:datatable_pendinglist:j_idt107\']/div/div[2]/span")));
		 
		 checkbox.click();		 
		 
		 Thread.sleep(3000);
		 
		 for(int i=0; i <RFQ.quote_count_DL-1;i++) {
			 
		 //operation.ClickElementID(driver, "form_pendinglist:datatable_pendinglist:"+i+":p_quotedPrice", 1);
		 
		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:"+i+":p_quotedPrice", "1");
		 
		 operation.InputTextID(driver, "form_pendinglist:datatable_pendinglist:"+i+":p_quotedPrice", "ENTER");
		 
		 Thread.sleep(3000);
		 
		 }
		 //proceed quote
		 operation.ClickElementID(driver, "form_pendinglist:j_idt459", 4);
		 
		 for(int i=0; i <RFQ.quote_count_DL-1;i++) {

		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist_dialog:datatable_quotation_warning:"+i+":j_idt544\']/div[2]/span")));

		 operation.ClickElementXP(driver, "//*[@id=\'form_pendinglist_dialog:datatable_quotation_warning:"+i+":j_idt544\']/div[2]/span", 1);
		 }
		 
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist_dialog:quotaton_validation_submitButton\']/span")));
		 
		 operation.ClickElementXP(driver,"//*[@id=\'form_pendinglist_dialog:quotaton_validation_submitButton\']/span",1);
		 
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'form_pendinglist_dialog:datatable_proceed_quotation_data\']/tr/td[2]")));
		
		 ATUReports.add("final screenshot", true);
		 
		 assertEquals(driver.findElement(By.xpath("//*[@id=\'form_pendinglist_dialog:datatable_proceed_quotation_data\']/tr/td[2]")).getText(), RFQ.QUOTE_DL[0][0]);
		 
		 
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
