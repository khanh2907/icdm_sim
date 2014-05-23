package icdm_sim;

import java.util.Random;

public class HeartPThread extends Thread {

	private Heart m_heart;
	private float m_chance;
	
	public HeartPThread(Heart heart) {
		m_heart = heart;
		
		float heartSeed = heart.m_dietRating;
		
		if (heart.m_drinksAlcohol)
			heartSeed++;
		
		if (heart.m_smokes)
			heartSeed++;
		
		if (heart.m_age > 50)
			heartSeed++;
		
		if (heart.m_heartDisease)
			heartSeed++;
		
		if (heart.m_bmi > 25) {
			heartSeed++;
		}
		
		m_chance = heartSeed/15;
		
		
		
	}
	
	public void run() {
		
	}
	
}
