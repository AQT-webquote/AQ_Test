package CommonFunction;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Operation {

	public void HoverMouse(WebDriver driver, String targetxpath, String targetxpath2, int click){
		
		//mouse hover to span menu
		
				Actions actions = new Actions(driver);
				
				WebElement mainMenu = driver.findElement(By.xpath(targetxpath));
				
				WebElement subMenu = driver.findElement(By.xpath(targetxpath2));

				actions.moveToElement(mainMenu);
				
				actions.moveToElement(subMenu);
				
				if(click==1)
				actions.click().build().perform();
	}
	
	
	public void Login(WebDriver driver, String[] UserInfo){
		
		driver.findElement(By.id("avnetid")).sendKeys(UserInfo[0]); 
		
		driver.findElement(By.xpath("/html/body/div[1]/form/table[1]/tbody/tr[2]/td[3]/input")).sendKeys(UserInfo[1]); 
		
		driver.findElement(By.id("Submit")).click();
		
	}
	
	public void Logout (WebDriver driver){
		
		driver.findElement(By.xpath("//*[@id='menu_form:j_idt28']/ul/li[5]/a")).click();
		
		
	}
	
	
	
	public void InputTextID(WebDriver driver, String targetid, String content){
		if(content.equals("ENTER"))
			driver.findElement(By.id(targetid)).sendKeys(Keys.ENTER);
		else
		driver.findElement(By.id(targetid)).sendKeys(content);  

	}
	
	public void InputTextXP(WebDriver driver, String targetxp, String content){
if(content.equals("ENTER"))
	driver.findElement(By.xpath(targetxp)).sendKeys(Keys.ENTER);
else
	driver.findElement(By.xpath(targetxp)).sendKeys(content);  

	}
	
	public void DropMenuID(WebDriver driver, String targetid, String content){
		
		Select dropdown = new Select(driver.findElement(By.id(targetid)));
		 
		 dropdown.selectByVisibleText(content);
		
	}
	
	public void ClickElementID(WebDriver driver, String targetid, int idle) throws InterruptedException{
		
		 idle=idle*1000;
		 driver.findElement(By.id(targetid)).click();
		 Thread.sleep(idle);

	}
	
	public void ClickElementXP(WebDriver driver, String targetid, int idle) throws InterruptedException{
		 idle=idle*1000;
		 driver.findElement(By.xpath(targetid)).click();
		 Thread.sleep(idle);

	}
	
	public static String captureScreen(WebDriver driver, String testcase,String path) {
	    //get your driver instance        
	    try {
	        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        Calendar currentDate = Calendar.getInstance();
	        SimpleDateFormat formatter = new SimpleDateFormat(
	                "yyyy/MMM/dd HH:mm:ss");
	        String dateN = formatter.format(currentDate.getTime()).replace("/","_");
	        String dateNow = dateN.replace(":","_");
	        String snapShotDirectory =  "C:\\Users\\045313\\Desktop\\Webtesting\\data\\AutoTest\\Setup_Data\\ScreenShot\\"+ dateNow;

	        File f = new File(snapShotDirectory);
	        if(f.mkdir()){
	        path = f.getAbsolutePath() + "/" + testcase+".jpg";
	        FileUtils.copyFile(source, new File(path)); 

	        }
	        
	    }
	    catch(IOException e) {
	        path = "Failed to capture screenshot: " + e.getMessage();
	    }
	    return path; 
	}
	
	
	public void DataCheck(WebDriver driver, String[][] quote){
		
		
		
	}
}
