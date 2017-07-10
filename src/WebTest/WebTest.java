package WebTest;
import org.testng.annotations.Test;

import AQ_test.AQ_v01;
import AQ_test.Excel_read;
public class WebTest {

	@Test
	public static void main(String[] args) throws Exception{

		Excel_read reader = new Excel_read();
		
		AQ_v01 test_launcher = new AQ_v01();
		
		//int login_count = reader.readloginCount();
		int quote_count = reader.readQuoteCount();
		
		int i = 0;
		
		String[] USERID = new String[2];
		String[][] QUOTE = new String[quote_count][42];
		
		USERID = reader.readLoginInfo(1); // please reference to user excel file to get the corresponding row

		QUOTE = reader.read_Quote();
		
		/*
		for(i=0;i<quote_count;i++){
		for(int j=0;j<42;j++)
			System.out.print(QUOTE[i][j]+"||");
		System.out.println(" ");
		}
		System.exit(1);
		*/
		
		if (quote_count==1)
			test_launcher.run(USERID,quote_count,QUOTE,0);
		else if(quote_count >1 )
			test_launcher.run(USERID,quote_count,QUOTE,1);
		
		
	}

}
