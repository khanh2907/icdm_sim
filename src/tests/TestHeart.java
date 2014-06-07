package tests;

import static org.junit.Assert.*;
import icdm_sim.Heart;

import java.util.concurrent.locks.Lock;

import org.junit.Test;

public class TestHeart {
	int patient_id = 5;
	int age = 10;
	float bmi = 20;
	int dietRating = 5;
	boolean smokes = true;
	boolean drinksAlcohol = true;
	boolean heartDisease = false;
	
	@Test
	public void testConstructor() {
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
		assertNotNull(thisHeart);
	}
	
	@Test
	public void testAccessors() {
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
		assertEquals(patient_id, thisHeart.getPatientId());
		assertNotNull(thisHeart.getHeartrate());
		assertNotNull(thisHeart.getPWave());
		assertNotNull(thisHeart.getQRSWave());
		assertNotNull(thisHeart.getFib());
		assertNotNull(thisHeart.getLockHeartRate());
		assertNotNull(thisHeart.getLockQRSWave());
		assertNotNull(thisHeart.getLockQRSWave());
		assertNotNull(thisHeart.getLockVentricFib());
		assertFalse(thisHeart.isDead());
	}
	
	@Test
	public void testMutators() {
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
			
		thisHeart.setHeartrate(100);
		assertEquals(100, thisHeart.getHeartrate(), 0);
		
		thisHeart.setDead(false);
		assertFalse(thisHeart.isDead());
		
		thisHeart.setFib(true);
		assertTrue(thisHeart.getFib());
		
		thisHeart.setPWave(true);
		assertTrue(thisHeart.getPWave());
		
		thisHeart.setQRSWave(false);
		assertFalse(thisHeart.getQRSWave());
		
	}

}
