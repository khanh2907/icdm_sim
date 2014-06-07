package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import icdm_sim.Heart;
import icdm_sim.ICD;

public class BlackBoxTest {

	private WebDriver driver;  
	
	int patient_id = 5;
	int age = 10;
	float bmi = 20;
	int dietRating = 5;
	boolean smokes = true;
	boolean drinksAlcohol = true;
	boolean heartDisease = false;
	
	@Before
	public void setUp() {
		// Create a new instance of the html unit driver
		driver = new FirefoxDriver();

		//Navigate to desired web page
		driver.get("http://130.56.248.71/elec5614/");
	}

	
	@Test
	public void testHeartrateMonitor() throws InterruptedException {
		// create heart and icd objects
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
		ICD thisICD = new ICD(thisHeart);
		
		// make sure that it's not null
		assertNotNull(thisICD);
		
		// check that it's arrived at the login page
		String actualTitle = driver.getTitle();
		assertEquals("ICDM - Login", actualTitle);
		
		// login and go to patient 5
	    driver.findElement(By.name("user")).clear();
	    driver.findElement(By.name("user")).sendKeys("admin");
	    driver.findElement(By.name("pass")).clear();
	    driver.findElement(By.name("pass")).sendKeys("Pass.123");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Maximo Sobol")).click();
		
	    // test if the heart rate updates properly
	    for (int second = 0;; second++) {
	    	if (second >= 30) fail("timeout");
	    	String expected = Integer.toString((int) thisICD.getCurrentHeartrate()) + " bpm";
	    	Thread.sleep(1000);
	    	System.out.println(expected);
	    	System.out.println(driver.findElement(By.id("current-hr")).getText());
	    	if (expected.equals(driver.findElement(By.id("current-hr")).getText())) return;
	    	
	    	Thread.sleep(1000);
	    }
	}
	
	  @After
	  public void tearDown() throws Exception {
		driver.quit();
	  }
}
