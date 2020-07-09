package utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import constantes.Constantes;

public class UtilesExtentReport {

	public static void captura(WebDriver D, String variacion, ExtentTest loggerER, String descripcion) {
		String fecha = DateUtil.fecha();
		try {
			getScreenshot(D, variacion, descripcion + "-" + fecha );
			String destino = "testsScreenshots/"+ variacion + " - " + descripcion+ "-" + fecha+ Constantes.FINAL_PNG;
			loggerER.log(LogStatus.PASS, descripcion, loggerER.addScreenCapture(destino));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void capturaError(WebDriver D, String variacion, ExtentTest loggerER, String descripcion) {
		String fecha = DateUtil.fecha();
		try {
			getScreenshot(D, variacion, descripcion + "-" + fecha );
			String destino = "testsScreenshots/"+ variacion + " - " + descripcion+ "-" + fecha+ Constantes.FINAL_PNG;
			loggerER.log(LogStatus.ERROR, descripcion, loggerER.addScreenCapture(destino));
		} catch (Exception e) {
			e.getMessage();
		}
	}
	/**
	 * 
	 * @param driver
	 * @param test
	 * @param screenshotName
	 * @return
	 * @throws Exception
	 */
	public static String getScreenshot(WebDriver driver, String variacion, String screenshotName) throws Exception {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + Resource.getProperty("path.screenshots") + variacion + " - " + screenshotName
				+ Constantes.FINAL_PNG;
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
