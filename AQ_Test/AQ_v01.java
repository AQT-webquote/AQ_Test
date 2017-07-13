package AQ_test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import CommonFunction.Operation;
import Verification.Veriguide;
import WebTest.WebTest;

public class AQ_v01 extends WebTest{

	public  void run(String[] UserInfo,int rowcount, String[][] QUOTE, int quote_case) throws Exception{
		
		Veriguide verification = new Veriguide();
		Operation operation = new Operation();
		String returnpath="";
		
		//initialize chrome browser
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\driver\\chromedriver.exe");
		
		WebDriver driver= new ChromeDriver();
		
		driver.get("http://emasiaweb-test.avnet.com/webquote3");
		
		WebDriverWait wait = new WebDriverWait(driver, 25);
		//login
		
		operation.Login(driver, UserInfo);
		
		//returnpath=operation.captureScreen(driver, "AQ_1_1",returnpath);
		
		verification.Veri_Common("AQ/1/1","",returnpath);
		
		//System.exit(1);
		
		//login verification  ---------VeriLogin( <TESTCASE> )

		verification.VeriLogin("AQ/1/1", QUOTE, rowcount);
        
		//mouse hover to span menu
		//HoverMouse(driver, target_x_path, click=1 or don't click = 0)
		
		/*
		Actions actions = new Actions(driver);
		
		WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/a"));
		
		WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a"));

		actions.moveToElement(mainMenu);
		
		actions.moveToElement(subMenu);

		actions.click().build().perform();*/
		operation.HoverMouse(driver, "//*[@id='menu_form:j_idt33']/ul/li[2]/a","//*[@id='menu_form:j_idt33']/ul/li[2]/ul/li[1]/a", 1);
		
 
		//Verification 
		// verilink(driver, <the target link>, <test case code>) 
		
		verification.VeriLink(driver, "http://emasiaweb-test.avnet.com/webquote3/RFQ/RFQSubmissionLayout.jsf?clear=1","AQ/1/2"); 
		
		
		//input info
		//driver.type("id=form_rfqSubmission:basicDetails_soldToCustomerNumber", "852");
				//driver.select("id=form_rfqSubmission:basicDetails_customerType", "label=EMS");
		
		//sold to code
		
		operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", QUOTE[1][6]);
		operation.InputTextID(driver, "form_rfqSubmission:basicDetails_soldToCustomerNumber", "ENTER");
		
		//wait until text come out in STP
		
		while(driver.findElement(By.id("form_rfqSubmission:basicDetails_soldToCustomerName")).getAttribute("value").length() == 0){
			
				Thread.sleep(1000);
		}
		
		//customer type-- drop down menu
		
		operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_customerType", QUOTE[1][19]);
		 
		 
		 //segment 
		 
		operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_enquirySegment", QUOTE[1][21]);

		 //quote type
		 
		operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_quoteType", QUOTE[1][31]);
		 
		 //design in cat
		
		operation.DropMenuID(driver, "form_rfqSubmission:basicDetails_designInCat", QUOTE[1][26]);
		 
		//if program can run until here, it means AQ/1/3 is completed
		 
		 verification.Veri_Common("AQ/1/3","",returnpath);
		 int s;
		 //add RFQ deatils items row
		 if (rowcount>5){
			  s = (int)Math.ceil((double)rowcount/10);
			 if(rowcount<=10){
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:j_idt294']", 2);
				 
			 }else {
				 for(int i=0; i < s ; i++){
					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:j_idt294']", 2);
					 	}
			 }
		 
		 }
		 
		 
		 //RFQ Details part
		 int i=0;
		 int j=1;
		 //MRF
			 for (i =0 ; i < rowcount ; i++){
				 
				 if(i !=0){
			if(i%5==0){
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission_paginator_top")));
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_paginator_to']/span/a["+page+1+"]")));
				operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_paginator_top']/a[3]", 1);
				j=0;
			}	 
			}
				 
			if (i==0){
			 operation.ClickElementID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":j_idt203",4);

			 //clickelementid (driver, id/xpath, script idle time in second after action)
			 
			// trigger web element in sub window
			 
			 //WebDriverWait wait = new WebDriverWait(driver, 300);
			 
			 
			 WebElement triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
			 
			 triggerDropDown.click();
			 
			 WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
			 
			 Select select = new Select(selectElement);
			 
			 select.selectByVisibleText(QUOTE[i][0]);
			 
			 //fill in text field
			 
			 operation.InputTextID(driver, "form_rfqSubmission_partNumber:j_idt685", QUOTE[i][1]);
			 
			 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']", 0);

			//checkbox
				
			 WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission_partNumber:datatable_search_alternate_data']/tr[1]/td[2]/div/div[2]/span")));
			 
			 checkbox.click();
			 
			 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission_partNumber:validation_expansion_SelectButton']/span", 3);
				
			 //required quantity
			 
			 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[12]", 0);

			 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", QUOTE[i][36]);
			 
			 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']", "ENTER");
			}
			 else{
				 //if (i%5==0)
					// operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_paginator_top']/span/a[2]", 1);
				 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":mfr_in")));
				 
				 operation.DropMenuID(driver, "form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":mfr_in", QUOTE[i][0]);
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[5]", 1);
				 				 
				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":requiredPartNumber_in_input']", QUOTE[i][1]);
				 
				 //wait until seeing //*[@id="form_rfqSubmission:datatable_basicDetails_rfqSubmission:1:requiredPartNumber_in_panel"]/ul/li/span
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]")));
				 
				 //choose dropdown	
				 String searchText = QUOTE[i][1]+" || "+QUOTE[i][0];
				 WebElement dropdown = driver.findElement(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel"));
				 
				 List<WebElement> options = dropdown.findElements(By.tagName("li"));
				 for (WebElement option : options)
				 {
				     if (option.getText().equals(searchText))
				     {
				         option.click();
						 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(i+1)+"]/td[12]", 0);// click the desired option
				         //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(i+1)+"]/td[12]", 0);// click the desired option
				         break;
				     }
				 }
				//*[@id="form_rfqSubmission:datatable_basicDetails_rfqSubmission:5:requiredPartNumber_in_panel"]/ul/li[1]/span
				//*[@id="form_rfqSubmission:datatable_basicDetails_rfqSubmission:5:requiredPartNumber_in_panel"]/ul/li[2]/span
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]")));

				 try{
				 wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li/span"), QUOTE[i][1]));
				 }catch (TimeoutException e) {
	                    String message="time out!";
	   				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[12]", 0);
						throw new TimeoutException(message, e);
						
	                }
				 
				 
				 Thread.sleep(2000);
				 
				 //operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":requiredPartNumber_in_input']", "ENTER");
				 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":requiredPartNumber_in_input']", 5);

				 
				//required quantity
				 
				 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(i+1)+"]/td[12]", 0);
				 
				 
				 
				 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]", 0);
				 
				 
				 //driver.findElement(By.xpath("//li[contains(text(), QUOTE[i][2]+' || '+QUOTE[i][1])]"));
				 
				 
				 //while(!(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]")))));
				 //{
				//	 Thread.sleep(1000);				 }
				 
				 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(j+1)+"]/td[12]", 0);
				 
				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":basicDetails_input_requiredQty_in']", QUOTE[i][36]);
				 
				 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr["+(i+2)+"]/td[12]", 3);

				 operation.InputTextXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+(i)+":basicDetails_input_requiredQty_in']", "ENTER");
			     j++;
				 //WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]").equals(QUOTE[i][2])));
				 
				 //menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]").equals(QUOTE[i][2])));
				
				 //menu.click();
				 
				 //operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[2]/td[8]", 0);


				 //operation.ClickElementXP(driver,"//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:"+i+":requiredPartNumber_in_panel']/ul/li[1]",2);
				 
			 
			 }
				//if program can run until here, it means AQ/1/4, AQ/1/5 is passed
			 }
			 
			 verification.Veri_Common("AQ/1/4","",returnpath);

			 verification.Veri_Common("AQ/1/5","",returnpath);
			 
			 verification.Veri_Common("AQ/1/6","",returnpath);
			 
			 operation.ClickElementID(driver, "form_rfqSubmission:nextButton", 2);
			 
			 verification.Veri_Common("AQ/1/7","",returnpath);
			 
			 Thread.sleep(1500);
			 
			 operation.ClickElementID(driver, "form_rfqSubmission:nextButton", 2);
			 
			 verification.Veri_Common("AQ/1/8","",returnpath);
			 
			 operation.ClickElementID(driver, "form_rfqSubmission:datatable_confirmation_rfqSubmission:j_idt520", 2);
			 
			 operation.ClickElementID(driver, "form_rfqSubmission:confirm_submit_dialog_yes", 2);
			 
			//check AQ
			 j=1;
			 for(i=0;i<rowcount;i++){
				 if (i%5==0){
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]")));
					 operation.ClickElementXP(driver, "//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_paginator_top']/a[3]", 1);
					 j=1;
					 
				 }
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")));
				 if((driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[11]")).getText().equals("AQ")) && (driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_confirmation_rfqSubmission_data']/tr["+j+"]/td[13]")).getText().equals("FINISH")) && (driver.findElement(By.xpath("//*[@id='form_rfqSubmission:j_idt81_content']/table[2]/tbody/tr/td[6]/h3")).isDisplayed())){
					 					 
				 	verification.Veri_Common("AQ/1/9",QUOTE[i][2],"");
				 	j++;
				 }
				 else {
					 
				     returnpath = Operation.captureScreen(driver, "AQ_1_9", returnpath);

					 verification.Veri_Fail("AQ/1/9",QUOTE[i][2],returnpath);
					 j++;
				 }
				 
			 }
			 
			 //download xls
			 
			 operation.ClickElementID(driver, "form_rfqSubmission:j_idt153", 1);
			 
			 //read data 
			 
			 String[][] newdata  = new String[rowcount][14];
			 
			 //operation.Logout(driver);

	}
		
		 
			 
		/* // case 1: requested P/N
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[5]")).click();
		 
		 Thread.sleep(2000);
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_input']")).sendKeys(QUOTE[1][1]);
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:requiredPartNumber_in_input']")).click();
		 
		 
		 Thread.sleep(2000);
		 
		 verification.Veri_Common("AQ/1/4");
		 
		 // case 2: magnifier icon

		 driver.findElement(By.id("form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:j_idt203")).click();
		 
		 Thread.sleep(4000);

		 // trigger web element in sub window
		 
		 WebDriverWait wait = new WebDriverWait(driver, 300);

		 
		 WebElement triggerDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_rfqSubmission_partNumber:alternatePartSearch_panel_content")));
		 
		 triggerDropDown.click();
		 
		 WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("form_rfqSubmission_partNumber:j_idt680")));
		 
		 Select select = new Select(selectElement);
		 
		 select.selectByVisibleText(QUOTE[1][0]);
		 
		 
		 //fill in text field
		 
		 driver.findElement(By.id("form_rfqSubmission_partNumber:j_idt685")).sendKeys(QUOTE[1]);
		 
		 //MRF dropdown menu
		 //dropdown = new Select(driver.findElement(By.name("form_rfqSubmission_partNumber:j_idt680")));
		 //dropdown.selectByVisibleText("FSC");
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission_partNumber:validation_expansion_SearchButton']")).click();

		 //checkbox
		
		 WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form_rfqSubmission_partNumber:datatable_search_alternate_data']/tr[1]/td[2]/div/div[2]/span")));
		 
		 checkbox.click();
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission_partNumber:validation_expansion_SelectButton']/span")).click();
		 
		 Thread.sleep(3000);

		 //required quantity
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_data']/tr[1]/td[12]")).click();
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']")).sendKeys(QUOTE[36]);
		 
		 driver.findElement(By.xpath("//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:0:basicDetails_input_requiredQty_in']")).sendKeys(Keys.ENTER);

		 //if program can run until here, it means AQ/1/4, AQ/1/5 is passed
		 
		 
		 verification.Veri_Common("AQ/1/5");
		 
		 verification.Veri_Common("AQ/1/6");
		 */
		 
		 		 
	}

	

