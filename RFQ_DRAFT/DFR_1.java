package RFQ_DRAFT;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class DFR_1 {

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
		
		RFQ.QUOTE_DL = new String[2][2];
		
		RFQ.USERInfo = reader.readLoginInfo(1); // 1=sales, 2=QC, 3=...
		
		RFQ.QUOTE = reader.read_Quote(); // read all quote data
		
		//for(int i=0;i<RFQ.quote_count;i++)
		//System.out.println(RFQ.QUOTE[i][36]+" || ");
		
		//System.exit(1);
		
		Init_browser("https://emasiaweb-test.avnet.com/webquote3"); //type in expected link, start the browser

		setAuthorInfoForReports();

	}
	
	@Test (description = "Enter RFQ header")
	public void DRF_1_1(){
		
		System.out.println("============================== Executing DRF/1/1 ==============================");

		operation.Login(driver, RFQ.USERInfo);
		
		ATUReports.add("Login","ID: "+RFQ.USERInfo[0]+"\nPW: "+RFQ.USERInfo[1], false);
		
		String currenturl = driver.getCurrentUrl();

		//match the link
		
		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf", currenturl,true);

		Assert.assertEquals(currenturl, "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf");
		
		System.out.println("------------Homepage Web link matched-----------");
		
		
		Actions actions = new Actions(driver);
		
		WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/a"));
		
		WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a"));

		actions.moveToElement(mainMenu);
		
		actions.moveToElement(subMenu);

		actions.click().build().perform();
		
		//verify link
		
		currenturl = driver.getCurrentUrl();
		

		Assert.assertEquals(currenturl,"https://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1"); 

		System.out.println("------------RFQ submission page web link matched-----------");
		
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
	
	@Test
	public void DRF_1_2() throws InterruptedException{
		
		System.out.println("================Executing DRF_1_2======================");

		WebDriverWait wait = new WebDriverWait(driver, 40);

		
		//sold to code
		
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", RFQ.QUOTE[1][6]);
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", "ENTER");
				
				ATUReports.add("Input Sold to Code", false);
				
				//wait until text come out in STP
				
				while(driver.findElement(By.id("form_rfqSubmission:basicDetails_soldToCustomerName")).getAttribute("value").length() == 0)
						Thread.sleep(1000);
							
				//End Customer Code & Name
				
				operation.ClickElementID(driver,  "form_rfqSubmission:basicDetails_endCustomerNumber_input", 1);
				
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_endCustomerNumber_input", RFQ.QUOTE[1][8]);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission:basicDetails_endCustomerNumber_panel")));
				
				Thread.sleep(2000);
				
				//store end customer into arrary
				String temp = driver.findElement(By.xpath("//*[@id='form_rfqSubmission:basicDetails_endCustomerNumber_panel']/ul/li[1]")).getAttribute("data-item-value");
				
				String[] temp2 = new String[2];
				temp2 = temp.split(" , ");
				
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_endCustomerNumber_input", "ENTER");
				 				
				Thread.sleep(1000);
				
				RFQ.QUOTE_DL[0][0]= temp2[1];
								
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
				
				//Project name
				
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_projectName_input", RFQ.QUOTE[1][22]);
				
				Thread.sleep(3000);
				
				operation.InputTextID(driver, "form_rfqSubmission:basicDetails_projectName_input", "ENTER");

				
	}
	
	@Test (description = "select one or multiple CS in copy to cs field")
	public void DRF_1_3() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver, 40);
		
		System.out.println("=====================Executing DRF_1_3====================");
		
		operation.ClickElementID(driver, "form_rfqSubmission:j_idt176", 1);
		
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_cs_data']/tr[1]/td[2]/div/div[2]/span")));
	
		checkbox.click();
		 
		 ATUReports.add("Click Checkbox", true);
		 
		 operation.ClickElementID(driver, "form_rfqSubmission:cs_YesButton", 1);
		 
		 while(driver.findElement(By.id("form_rfqSubmission:basicDetails_copyToCs")).getAttribute("value").length()==0)
			 Thread.sleep(1000);
		 
		//click on magnifier
		 operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+0+":j_idt203",4);
		 
		 ATUReports.add("Click on magnifier", false);
		 
		// trigger web element in sub window
		 WebElement triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
		 
		 triggerDropDown.click();
		 
		 WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
		 
		 Select select = new Select(selectElement);
		 
		 select.selectByVisibleText(RFQ.QUOTE[0][0]);
		 
		 ATUReports.add("Select MRF dropdown list",RFQ.QUOTE[0][0], false);
		 
		 //fill in text field in sub window
		 
		 operation.InputTextID(driver, "form_rfqSubmission_partNumber:j_idt685",RFQ.QUOTE[0][1]);
		 
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']", 0);
		 
		//checkbox in sub window
			
		 checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission_partNumber:datatable_search_alternate_data']/tr[1]/td[2]/div/div[2]/span")));
		 
		 checkbox.click();
		 
		 ATUReports.add("Click Checkbox", true);
		 
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SelectButton']/span", 3);
			
		 //required quantity
		 
		 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[12]", 0);

		 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", RFQ.QUOTE[0][36]);
		 
		 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", "ENTER");
		
		 Thread.sleep(2000);
		 
	}
	
	@Test
	public void DRF_1_4() throws InterruptedException{
		
		System.out.println("=============Executing DRF_1_4================");
		
		Thread.sleep(5000);
		
		operation.ClickElementID(driver, "form_rfqSubmission:basicDetails_save", 2);
		
		
		
		
	}
	
	@Test
	public void DRF_1_5() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver, 40);

		System.out.println("==============Executing DRF_1_5=================");

		Actions actions = new Actions(driver);
		
		WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt27']/ul/li[2]/a"));
		
		WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt27']/ul/li[2]/ul/li[3]/a"));

		actions.moveToElement(mainMenu);
		
		actions.moveToElement(subMenu);

		actions.click().build().perform();
		
		Thread.sleep(2000);
		
		operation.InputTextID(driver, "form:j_idt84:j_idt99", RFQ.QUOTE[1][6]);
		
		operation.InputTextID(driver, "form:j_idt84:j_idt101_input", RFQ.QUOTE_DL[0][0]);
		
		Thread.sleep(2000);
		
		operation.InputTextID(driver, "form:j_idt84:j_idt101_input", "ENTER");
		
		operation.InputTextID(driver, "form:j_idt84:j_idt105_input", RFQ.QUOTE[0][2]); // P/N
		
		Thread.sleep(2000);
		
		operation.InputTextID(driver, "form:j_idt84:j_idt105_input", "ENTER"); 
		
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form:j_idt84:j_idt119']/tbody/tr/td[2]/div/div[2]/span")));
		
		checkbox.click();
		
		checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form:j_idt84:j_idt119']/tbody/tr/td[3]/div/div[2]/span")));
		
		checkbox.click();
		
		checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form:j_idt84:j_idt119']/tbody/tr/td[4]/div/div[2]/span")));
		
		checkbox.click();
		
		operation.ClickElementID(driver, "form:j_idt84:j_idt141", 2);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form:datatable_myquotelist_data']/tr/td[1]/div/div[2]/span")));
		
		operation.ClickElementXP(driver, "//*[@id='form:datatable_myquotelist_data']/tr/td[1]/div/div[2]/span", 1);
	
		operation.ClickElementID(driver, "form:j_idt158", 5);
	}
	
	@Test
	public void DRF_1_6(){
		
		System.out.println("=================Executing DRF_1_6====================");
		
		if((driver.findElement(By.id("form_rfqSubmission:basicDetails_soldToCustomerNumber")).getText().equals(RFQ.QUOTE[1][6]))==true)
		
		ATUReports.add("After returning to RFQ submission page", true);
		
		else{
		assertFalse(1==2);
		ATUReports.add("After returning to RFQ submission page", true);
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
