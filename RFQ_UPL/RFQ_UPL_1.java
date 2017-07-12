package RFQ_UPL;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
MethodListener.class })
public class RFQ_UPL_1 {
	
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
	
	@Test (description = "Select RFQ submission page ")
	public void RFQ_UPL_1_1(){
		//Select RFQ submission page  1/1
		//CS QCO QCP
		// can switch region 
		// user info cannot be modified
		
		System.out.println("============================== Executing RFQ_UPL/1/1 ==============================");

		operation.Login(driver, RFQ.USERInfo);
		
		ATUReports.add("Login","ID: "+RFQ.USERInfo[0]+"\nPW: "+RFQ.USERInfo[1], false);
		
		String currenturl = driver.getCurrentUrl();

		//match the link
		
		ATUReports.add("Verify Link", "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf", currenturl,true);

		Assert.assertEquals(currenturl, "https://emasiaweb-test.avnet.com/webquote3/WebPromo/WebPromo.jsf");
		
		System.out.println("------------Web link matched-----------");
		
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
	
	@Test (description = "RFQ header, enter mandatory field")
	public void RFQ_UPL_1_2() throws InterruptedException{
		
		System.out.println("================Executing RFQ_UPL_1_2======================");
		
		//sold to code
		
		operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", RFQ.QUOTE[1][6]);
		operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", "ENTER");
		
		ATUReports.add("Input Sold to Code", false);
		
		//wait until text come out in STP
		
		while(driver.findElement(By.id("form_rfqSubmission:basicDetails_soldToCustomerName")).getAttribute("value").length() == 0)
				Thread.sleep(1000);
					
		//End Customer Code & Name
		
		
		
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
	
	@Test (description="download RFQ form template")
	public void RFQ_UPL_1_3(){
		
		driver.findElement(By.id("form_rfqSubmission:j_idt195")).click();
		
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
