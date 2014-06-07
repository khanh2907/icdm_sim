package tests;

import static org.junit.Assert.*;

import icdm_sim.Heart;
import icdm_sim.ICD;

import org.junit.Test;

public class TestICD {
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
		ICD thisICD = new ICD(thisHeart);
		
		assertNotNull(thisICD);
	}
	
	@Test
	public void testAccessors() {
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
		ICD thisICD = new ICD(thisHeart);
		
		assertEquals(patient_id, thisICD.getPatientId());
		assertEquals(thisHeart, thisICD.getHeart());
		assertEquals(80, thisICD.getCurrentHeartrate(), 0);
		assertEquals(false, thisICD.getFast());
		assertEquals(false, thisICD.getSlow());
		assertEquals(false, thisICD.isDead());
		assertEquals(false, thisICD.getFlag("fast"));
		assertEquals(false, thisICD.getFlag("slow"));
		assertEquals(false, thisICD.getFlag("dead"));
		assertEquals(false, thisICD.getPFlag());
		assertEquals(false, thisICD.getQRSFlag());
	}
	
	@Test
	public void testMutators() {
		Heart thisHeart = new Heart(patient_id, age, bmi, dietRating, smokes, drinksAlcohol, heartDisease);
		ICD thisICD = new ICD(thisHeart);
		
		thisICD.setDead(true);
		assertTrue(thisICD.isDead());
		
		thisICD.setDeadFlag(false);
		assertFalse(thisICD.isDead());
		
		thisICD.setFastFlag(true);
		assertTrue(thisICD.getFast());
		
		thisICD.setSlowFlag(true);
		assertTrue(thisICD.getSlow());
		
		thisICD.setPFlag(true);
		assertTrue(thisICD.getPFlag());
		
		thisICD.setQRSFlag(true);
		assertTrue(thisICD.getQRSFlag());
		
		
	}

}
