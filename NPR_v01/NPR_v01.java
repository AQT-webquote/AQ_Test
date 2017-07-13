package NPR_v01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import CommonFunction.RFQ_SUB;

public class NPR_v01 {

	public static void main(String[] args) throws Exception{

		//initialize chrome browser
				System.setProperty("webdriver.chrome.driver","C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\driver\\chromedriver.exe");
				WebDriver driver= new ChromeDriver();
				driver.get("http://emasiaweb-test.avnet.com/webquote3");
				
				RFQ_SUB RFQ = new RFQ_SUB();
				
				//login QC operation account
				driver.findElement(By.id("avnetid")).sendKeys("043044"); 
				driver.findElement(By.xpath("/html/body/div[1]/form/table[1]/tbody/tr[2]/td[3]/input")).sendKeys("Tivoli01"); 
				driver.findElement(By.id("Submit")).click();
						
				//mouse hover to span menu
				
				Actions actions = new Actions(driver);
				
				WebElement mainMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt27']/ul/li[3]/a"));
				actions.moveToElement(mainMenu);

				WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu_form:j_idt27']/ul/li[3]/ul/li[1]/a"));
				actions.moveToElement(subMenu);
				actions.click().build().perform();
				
				//dropdown menu
				
				Select dropdown = new Select(driver.findElement(By.id("uploadProgramItem:action")));
				
				dropdown.selectByVisibleText("ADD/UPDATE");
				
				//upload button
				
				driver.findElement(By.id("uploadProgramItem:j_idt86")).sendKeys(RFQ_SUB.PRICER);
				
				driver.findElement(By.id("uploadProgramItem:j_idt87")).click();
				
	}

}
