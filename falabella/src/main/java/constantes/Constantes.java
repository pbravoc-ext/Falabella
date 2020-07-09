package constantes;

import utils.Resource;

public class Constantes {

	public static final String FINAL_HTML = ".html";
	public static final String FINAL_PNG = ".png";
	
	//propiedades del archivo de configuracion para tienpos de espera en los wait para recuperar WebElement o esperas explicitas
	public static final String WAIT_PAGEFACTORY								= Resource.get("wait.pagefactory");
	public static final String WAIT_FLUENTWAIT								= Resource.get("wait.fluentwait");
	public static final String WAIT_FINDINPUTNUMBER 						= Resource.get("wait.findInputNumber");
	public static final String WAIT_ISCONTAINSATTRIBUTE_TIMEOUTINSECONDS 	= Resource.get("wait.iscontainsattribute.timeOutInSeconds");	
	public static final String WAIT_ISCONTAINSATTRIBUTE_SLEEPINMILLIS		= Resource.get("wait.iscontainsattribute.sleepInMillis");
	public static final String WAIT_ELEMENTNOTEXIST_TIMEOUTINSECONDS		= Resource.get("wait.elementNotExist.timeOutInSeconds");	
	public static final String WAIT_ELEMENTNOTEXIST_SLEEPINMILLIS			= Resource.get("wait.elementNotExist.sleepInMillis"); 	
}
