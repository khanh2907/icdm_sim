package icdm_sim;

import java.util.Random;

public class HeartbeatThread extends Thread {

	private Heart m_heart;
	private float m_chance;
	private Random random;
	public HeartbeatThread(Heart heart) {
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
		
		random = new Random();
		
		
	}
	
	public void run() {
		System.out.println(m_chance);
		m_chance = 0.5f;
		while(true){
			
			//delay to set the heartrate 
			try {
				Thread.sleep((long)((1/(m_heart.getHeartrate()/60)*1000))-(200));
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			//P wave
//			System.out.println("start P");
			
			m_heart.setPWave(true);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			m_heart.setPWave(false);
//			System.out.println("end P");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			
			//QRS complex
//			System.out.println("QRS start");
			
			//Chance that QRS won't happen
			if(random.nextFloat()>m_chance)
			m_heart.setQRSWave(true);
			
			try{
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			m_heart.setQRSWave(false);
//			System.out.println("QRS finish");
		}
		
		
	}
	
}
