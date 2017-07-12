package CommonFunction;

import AQ_test.AQ_1;

public class  RFQ_SUB {
	
	public static final String MASTER_FILE = "C:\\Users\\045313\\Desktop\\Webtesting\\data\\AutoTest\\Setup_Data\\WebQuote_Data.xlsx";
	public static final String USERFILE = "C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\0609WebTest\\src\\WQ-Users.xlsx";
	public static final String LOGIN_SHEETNAME_SALES = "User_sales";
	public static final String LOGIN_SHEETNAME_CS = "User_CS";
	public static final String LOGIN_SHEETNAME_PM = "User_PM";
	public static final String LOGIN_SHEETNAME_QC = "User_QC";
	public static final String LOGIN_SHEETNAME_MM = "User_MM";
	public static final String LOGIN_SHEETNAME_BMT = "User_BMT";


	public static final String QUOTEFILE = "C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\0609WebTest\\src\\Quotes.xlsx";
	public static final String QUOTE_SHEETNAME = "QUOTE";
	public static final String TESTFILE = "test.xlsx";
	public static final String TESTPATH = "C:\\Users\\045313\\Desktop\\Webtesting\\workspace\\0609WebTest\\src";
	public static final String TEST_SHEETNAME = "Test_Results";

	public static final String PRICER = "C:\\Users\\045313\\Desktop\\Webtesting\\data\\AutoTest\\GTS_PR_UPLOAD\\pricer_15_records.xlsx";
	
	public static final String DOWNLOADFILE = "C:\\Users\\045313\\Downloads\\Download.xlsx";
	public static final String DOWNLOADSHEET = "Sheet0";
	public String[] USERInfo = new String[2];
	public int quote_count;
	public String[][] QUOTE;
	public String[][] QUOTE_DL;
	public int quote_count_DL;

	//RFQ submission HEADER page xpath,ID  
	public static final String SOLD_TO_CODE_ID = "form_rfqSubmission:basicDetails_soldToCustomerNumber";
	public static final String SOLD_TO_PARTY_ID = "form_rfqSubmission:basicDetails_soldToCustomerName";
	public static final String CUSTOMER_TYPE_ID = "form_rfqSubmission:basicDetails_customerType";	
	public static final String SEGMENT_ID = "form_rfqSubmission:basicDetails_enquirySegment";
	public static final String QUOTE_TYPE = "form_rfqSubmission:basicDetails_quoteType";
	public static final String DESIGN_IN_CAT = "form_rfqSubmission:basicDetails_designInCat";
	
	//RFQ submission details , ID, xpath
	//buttons
	public static final String ADD_10_ITEMS_XP = "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission:j_idt294']";
	public final String NEXT_PAGE_XP = "//*[@id='form_rfqSubmission:datatable_basicDetails_rfqSubmission_paginator_top']/a[3]";
	public final String NEXT_BUTTON_ID = "form_rfqSubmission:nextButton";
	
	

}
