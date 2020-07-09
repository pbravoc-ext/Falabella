package base;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import constantes.Constantes;
import utils.JSWaiter;

public class PageObject {
	Logger log = LogManager.getLogger(PageObject.class);

	protected WebDriver driver;
	protected JSWaiter  jswaiter;
	protected JavascriptExecutor js;
	
	public PageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver,  Integer.valueOf(Constantes.WAIT_PAGEFACTORY.trim()) ), this);
		this.jswaiter = new JSWaiter();
		jswaiter.setDriver(driver);
		this.js  = (JavascriptExecutor) driver;

	}
	


	public JSWaiter getJswaiter() {
		return jswaiter;
	}


	public boolean isElementDisplayed(String idElemento) {
		return loadElementById(idElemento).isDisplayed();
	}

	protected WebElement getFluidWait(WebDriver driver, By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds( Integer.valueOf(Constantes.WAIT_FLUENTWAIT.trim())))
				.pollingEvery(Duration.ofSeconds(20)).ignoring(Exception.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
		return element;
	}

	public WebElement loadElementById(String idElemento) {
		return getFluidWait(driver, By.id(idElemento));
	}

	public WebElement loadElementByXpath(String xpath) {
		return getFluidWait(driver, By.xpath(xpath));
	}

	public WebElement loadElementByCss(String selector) {
		return getFluidWait(driver, By.cssSelector(selector));
	}

	/**
	 * 
	 * @param elementBig
	 * @param xpathInput
	 * @return
	 */
	public WebElement findInputNumber(String elementBig, String xpathInput) {
		driver.findElement(By.xpath(elementBig)).click();
		new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_FINDINPUTNUMBER.trim())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathInput)));
		return driver.findElement(By.xpath(xpathInput));
	}

	/**
	 * 
	 * @param label
	 * @return solo numeros de la cadena
	 */
	public String onlyNumbers(String label) {
		return label.replaceAll("[^0-9]", "");
	}

	/**
	 * 
	 * @param valor
	 * @return Lista con agrupacion de numeros
	 */
	public String numeroGrilla(String valor) {
		String[] mov = valor.split(",");
		return this.onlyNumbers(mov[0]);
	}

	protected Boolean isContainsAttribute(WebDriver driver, By by, String attribute, String attribute_value) {
		Boolean retorno = false;
		try {
			retorno = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_SLEEPINMILLIS.trim()))
					.until(ExpectedConditions.attributeContains(by, attribute, attribute_value));
		} catch (org.openqa.selenium.TimeoutException e) {
			log.debug(by.toString() + " " + attribute + " " + attribute_value + " Timeout exceeded");
		}
		return retorno;
	}
	
	protected Boolean elementNotExist(WebDriver driver, By by) {
		Boolean retorno = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_SLEEPINMILLIS.trim()));
			retorno = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
//			retorno = true;
//			retorno = driver.findElements(by).size() == 0;
		} catch (Exception e) {
			log.debug(by.toString() + "Timeout exceeded");
		}
		return retorno;
	}

	protected Boolean elementExist(WebDriver driver, By by) {
		Boolean retorno = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_SLEEPINMILLIS.trim()));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			retorno = driver.findElements(by).size() > 0;
		} catch (Exception e) {
			log.debug(by.toString() + " Timeout exceeded");
		}
		return retorno;
	}

	protected Boolean isVisibility(By by) {
		Boolean retorno = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ELEMENTNOTEXIST_SLEEPINMILLIS.trim()));
			WebElement obWElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			retorno = (obWElement != null);
		} catch (Exception e) {
			log.debug(by.toString() + " Timeout exceeded");
		}
		return retorno;
	}

	
	//comprueba si un elemento exista pero espera un corto tiempo, esto se hace en casos que un formularios por ambiente de cliente usa un xpath u otro y es necesario comprobar
	//rapidamente si un webelement esta presente o no
	protected Boolean elementExistShortTime(By by) {
		Boolean retorno = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 3, 500);
			WebElement obWElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			retorno = (obWElement != null);
		} catch (Exception e) {
			log.debug(by.toString() + " Timeout exceeded");
		}
		return retorno;
	}

	//retorna el primer WebElement que Exista 
	protected WebElement loadFirstElementThatExist(By byFirst, By bySecond) {
		WebElement wReturn;
		
		if(this.elementExistShortTime(byFirst)) {
			wReturn = this.getFluidWait(this.driver, byFirst);
		}
		else 
		{
			wReturn = this.getFluidWait(this.driver, bySecond);
		}
			
		return wReturn;
	}
	public Boolean xpathExist(String xpath) {
		return elementExist(driver, By.xpath(xpath));
	}

	public Boolean idExist(String idElemento) {
		return elementExist(driver, By.id(idElemento));
	}
	
	public Boolean cssExist(String selector) {
		return elementExist(driver, By.cssSelector(selector));
	}

	public Boolean xpathNotExist(String xpath) {
		return elementNotExist(driver, By.xpath(xpath));
	}

	public Boolean idNotExist(String idElemento) {
		return elementNotExist(driver, By.id(idElemento));
	}
	
	public Boolean cssNotExist(String selector) {
		return elementNotExist(driver, By.cssSelector(selector));
	}

	protected Boolean isNotContainsAttribute(WebDriver driver, By by, String attribute, String attribute_value) {
		Boolean retorno = false;
		try {
			retorno = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_SLEEPINMILLIS.trim())).until(
					ExpectedConditions.not(ExpectedConditions.attributeContains(by, attribute, attribute_value)));

		} catch (org.openqa.selenium.TimeoutException e) {
			log.debug(by.toString() + " " + attribute + " " + attribute_value + " Timeout exceeded");
		}
		return retorno;
	}
	
	public Boolean isContainsAttributeById(String idElemento, String attribute, String attribute_value) {
		return isContainsAttribute(driver, By.id(idElemento), attribute, attribute_value);
	}

	public Boolean isContainsAttributeByXpath(String xpath, String attribute, String attribute_value) {
		return isContainsAttribute(driver, By.xpath(xpath), attribute, attribute_value);
	}

	public Boolean isContainsAttributeByCss(String selector, String attribute, String attribute_value) {
		return isContainsAttribute(driver, By.cssSelector(selector), attribute, attribute_value);
	}

	public Boolean isNotContainsAttributeById(String idElemento, String attribute, String attribute_value) {
		return isNotContainsAttribute(driver, By.id(idElemento), attribute, attribute_value);
	}

	public Boolean isNotContainsAttributeByXpath(String xpath, String attribute, String attribute_value) {
		return isNotContainsAttribute(driver, By.xpath(xpath), attribute, attribute_value);
	}

	public Boolean isNotContainsAttributeByCss(String selector, String attribute, String attribute_value) {
		return isNotContainsAttribute(driver, By.cssSelector(selector), attribute, attribute_value);
	}

	public void waitSec(WebDriver driver, Long segundos) {
		driver.manage().timeouts().implicitlyWait(segundos, TimeUnit.SECONDS);
	}

	public void waitPageLoad(WebDriver driver, Long segundos) {
		driver.manage().timeouts().pageLoadTimeout(segundos, TimeUnit.SECONDS);
	}
	
	public Boolean isAttributeToBeNotEmpty(WebElement element, String attribute) {
		Boolean retorno = false;
		try {
			retorno = new WebDriverWait(driver, Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_TIMEOUTINSECONDS.trim()), Integer.valueOf(Constantes.WAIT_ISCONTAINSATTRIBUTE_SLEEPINMILLIS.trim())).until(
					ExpectedConditions.attributeToBeNotEmpty(element, attribute));

		} catch (org.openqa.selenium.TimeoutException e) {
			log.debug(element.toString() + " " + attribute +" Timeout exceeded");
		}
		return retorno;
	}
	
	public WebElement loadFirstElementThatExistByXPath(String xpath1, String xpath2) {
		return this.loadFirstElementThatExist(By.xpath(xpath1), By.xpath(xpath2));
	}
	public WebElement loadFirstElementThatExistByCss(String selector1, String selector2) {
		return this.loadFirstElementThatExist(By.cssSelector(selector1), By.cssSelector(selector2));
	}
	public WebElement loadFirstElementThatExistById(String idElemento1, String idElemento2) {
		return this.loadFirstElementThatExist(By.id(idElemento1), By.id(idElemento2));
	}
	public Boolean isVisibilityByXpath(String xpath) {
		return this.isVisibility(By.xpath(xpath));
	}
	public Boolean isVisibilityByCss(String selector) {
		return this.isVisibility(By.cssSelector(selector));
	}
	public Boolean isVisibilityById(String idElemento) {
		return this.isVisibility(By.id(idElemento));
	}
	public Boolean elementExistShortTimeByXpath(String xpath) {
		 return this.elementExistShortTime(By.xpath(xpath));
	}
	public Boolean elementExistShortTimeByCss(String selector1) {
		 return this.elementExistShortTime(By.cssSelector(selector1));
	}
	public Boolean elementExistShortTimeById(String id) {
		 return this.elementExistShortTime(By.id(id));
	}
}
