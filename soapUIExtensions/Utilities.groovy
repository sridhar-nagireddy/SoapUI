import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext;
class Utilities {

	def log;
	def testRunner;
	def project_level;
	def groovyUtils;
	
	
	def RetailLogin(String strLoginType) {
		
		log.info "Login type" + strLoginType;
		def testStep_Login =project_level.testSuites["TestSuiteA"].testCases["Login"].testSteps["LoginRequest"];
		def testStep_Context = new WsdlTestRunContext(testStep_Login);
		testStep_Context.setProperty("ExpectedLoginStatus","loggedin");
		
		if(strLoginType.equals("User"))
		{
			
		
			if (project_level.getPropertyValue("RetailUserSessionID").equals("") || project_level.getPropertyValue("RetailUserSessionID")==null )
				{
				
				
				testStep_Context.setProperty("Username",project_level.getPropertyValue("RetailUser"));
				testStep_Context.setProperty("Password",project_level.getPropertyValue("RetailUserPswd"));
				
				testStep_Login.run(testRunner,testStep_Context);
				
			def Login_Response = testStep_Context.getProperty("Response");
			def XmlHolderObj = groovyUtils.getXmlHolder( Login_Response );
			
			
			def Login_status = XmlHolderObj.getNodeValue('//loginstatus/text()');
			if (Login_status.equals(testStep_Context.getProperty("ExpectedLoginStatus")))
					{
						def RetailUserSessionID = XmlHolderObj.getNodeValue('//sessionid/text()');
						project_level.setPropertyValue("RetailUserSessionID",RetailUserSessionID);
						log.info "Login session ID" + RetailUserSessionID;
					}
			else
			{
				project_level.setPropertyValue("RetailUserSessionID","");
				log.info "Login was not successful as Login_status is " + Login_status;
			}
			
			}
		
		}
		
		if(strLoginType.equals("Admin"))
		{
			
		
		
		
		if (project_level.getPropertyValue("AdminsessionID").equals("") || project_level.getPropertyValue("sessionID")==null ) 
			{
			
			testStep_Context.setProperty("Username",project_level.getPropertyValue("AdminUser"));
			testStep_Context.setProperty("Password",project_level.getPropertyValue("AdminUsrPswd"));
			
			
			testStep_Login.run(testRunner,testStep_Context);
			
		def Login_Response = testStep_Context.getProperty("Response");
		def XmlHolderObj = groovyUtils.getXmlHolder( Login_Response );
		
		
		def Login_status = XmlHolderObj.getNodeValue('//loginstatus/text()');
		if (Login_status.equals(testStep_Context.getProperty("ExpectedLoginStatus")))
				{
					def sessionId = XmlHolderObj.getNodeValue('//sessionid/text()');
					project_level.setPropertyValue("AdminsessionID",sessionId);
					log.info "Login session ID" + sessionId;
				}
		else
		{
			project_level.setPropertyValue("AdminsessionID","");
			log.info "Login was not successful as Login_status is " + Login_status;
		}
		
	}
 }
}

}
