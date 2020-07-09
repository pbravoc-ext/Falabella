package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilesSelenium {
	private static final Logger LOGGER = LogManager.getLogger(UtilesSelenium.class);

	/**
	 * 
	 * @param tag
	 * @param xpath
	 */
	public static void moveToElement(WebDriver D, String tag, String xpath) {
		try {
			WebElement webElement = D.findElement(By.xpath("//" + tag + "[text()='" + xpath + "']"));
			Coordinates coordinates = (Coordinates) ((Locatable) webElement).getCoordinates();
			coordinates.inViewPort();
			waitForLoad(D);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String getDateFormatt() {
		Date date = new Date();
		SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
		return parser.format(date);
	}

	/**
	 * 
	 * @param element
	 */
	public static void moveByElement(WebElement element) {
		try {
			Coordinates cordinates = (Coordinates) ((Locatable) element).getCoordinates();
			cordinates.inViewPort();
		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	/**
	 * 
	 * @param listaIonic
	 * @param tagName
	 * @param key
	 * @return
	 */
	public static WebElement getElementoFromIonicItem(List<WebElement> listaIonic, String tagName, String key) {
		WebElement elementoEncontrado = null;
		for (WebElement elemento : listaIonic) {
			List<WebElement> divElementos = elemento.findElements(By.tagName(tagName));
			for (WebElement elementos : divElementos) {
				if (key.equals(elementos.getText())) {
					elementoEncontrado = elementos;
				}
			}
		}
		return elementoEncontrado;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public static WebElement findElementById(WebDriver D, By id) {
		return D.findElement(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static List<WebElement> findElementsById(WebDriver D, By id) {
		return D.findElements(id);
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */
	public static List<WebElement> findElementsByTag(WebDriver D, By tag) {
		return D.findElements(tag);
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */

	public static List<WebElement> getElementsByClassName(WebDriver D, String tag) {
		return D.findElements(By.className(tag));
	}

	/**
	 * 
	 * @param findElements
	 * @param tag
	 * @return
	 */
	public static WebElement getWebElementByTag(List<WebElement> findElements, String tag) {
		WebElement elementoWeb = null;
		for (int i = 0; i < findElements.size(); i++) {
			WebElement elemento = findElements.get(i).findElement(By.className(tag));
			if (elemento.isDisplayed()) {
				elementoWeb = elemento;
			}
		}
		return elementoWeb;
	}

	/**
	 * 
	 * @param D
	 */
	public static void openPage(WebDriver D) {
		String URL = Resource.getProperty("url.optimus");
		LOGGER.debug("Redireccionando a URL --> " + URL);
		try {
			D.get(URL);
		} catch (Exception e) {
			LOGGER.error("Error al Redireccionar la URL, " + e.getStackTrace());
		}
	}

	/**
	 * 
	 * @param D
	 */
	public void destroyDriver(WebDriver D) {
		D.quit();
	}

	/**
	 * 
	 * @param D
	 */
	public void refresh(WebDriver D) {
		if (D != null) {
			D.navigate().refresh();
			waitForLoad(D);
		}
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String validaData(String data) {
		if (data == null) {
			LOGGER.error("Campo Nulo");
			return null;
		}
		if (data.trim().isEmpty()) {
			LOGGER.error("Campo Vacio");
			return null;
		}
		return data;

	}

	/**
	 * 
	 * @param D
	 */
	public static void waitForLoad(WebDriver D) {
		waitForLoad(D, Integer.parseInt(Resource.getProperty("select.config.time")));
	}

	/**
	 * 
	 * @param D
	 * @param segundos
	 */
	public static void waitForLoad(WebDriver D, int segundos) {
		try {
			Thread.sleep(segundos*1000);
//			WebDriverWait wait = new WebDriverWait(D, segundos);
//			wait.until(ExpectedConditions.invisibilityOf(D.findElement(By.id("header"))));
		} catch (Exception error) {
		}
	}

	/**
	 * 
	 * @param driver WebDriver
	 * @param x      WebElement
	 */
	public static void waitForLoadForElement(WebDriver driver, String attribute, WebElement webElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30, 2000);
			wait.until(ExpectedConditions.attributeToBeNotEmpty(webElement, attribute));
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				driver.switchTo().defaultContent();
			}
		}
	}

	/**
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 */
	public static boolean waitForLoadForElementDisplay(WebDriver driver, String webElement) {
		if (waitForLoadForElementDisplay(driver, driver.findElement(By.xpath(webElement)))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 */
	public static boolean waitForLoadForElementDisplay(WebDriver driver, WebElement webElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60, 1);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			return true;
		} catch (Exception error) {
			LOGGER.error(error);
			return false;
		}
	}

	/**
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 */
	public static boolean waitForLoadForElementNotDisplay(WebDriver driver, WebElement webElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30, 05);

			wait.until(ExpectedConditions.invisibilityOf(webElement));
			return true;
		} catch (Exception error) {
			LOGGER.error(error);
			return false;
		}
	}

	/**
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 */
	public static boolean waitForLoadForElementEnabled(WebDriver driver, WebElement webElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30, 05);

			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			return true;
		} catch (Exception error) {
			LOGGER.error(error);
			return false;
		}
	}

	/**
	 * 
	 * @param D
	 * @param element
	 * @param attribute
	 * @param cambio
	 */
	public static void waitForLoadForContains(WebDriver D, WebElement element, String attribute, String cambio) {
		try {
			WebDriverWait wait = new WebDriverWait(D, 30, 1);
			wait.until(ExpectedConditions.attributeToBe(element, attribute, cambio));
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				D.switchTo().defaultContent();
			}
		}
	}
}