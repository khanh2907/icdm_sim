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
		Heartbeat:
		while(true){
			
			//delay to set the heartrate 
			try {
				Thread.sleep((long)((1/(m_heart.getHeartrate()/60)*1000))-(200));
			} catch (InterruptedException e) {

				e.printStackTrace();
			}


			//randomize the heartrate
			if(random.nextFloat()<m_chance){
				if(random.nextFloat()<0.5){
					try{
						m_heart.getLockHeartRate().lock();
						m_heart.setHeartrate(m_heart.getHeartrate() + 5);
					} finally{
						m_heart.getLockHeartRate().unlock();
					}
				}
				else
					try{
						m_heart.getLockHeartRate().lock();
						m_heart.setHeartrate(m_heart.getHeartrate() - 5);
					} finally{
						m_heart.getLockHeartRate().unlock();
					}
			}
			//chance or cardiac arrest

			if(random.nextFloat() < m_chance/9){
				System.out.println("Ventricular Fibrillation ");
				try{
					m_heart.getLockVentricFib().lock();
					m_heart.setFib(true);
				} finally {
					m_heart.getLockVentricFib().unlock();
				}
				
				try{
					m_heart.getLockHeartRate().lock();
					m_heart.setHeartrate(0);
				} finally {
					m_heart.getLockHeartRate().unlock();
				}
			
				long startTime = System.currentTimeMillis();
				while(System.currentTimeMillis() - startTime < 10000){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(m_heart.getFib() == false){

						continue Heartbeat;
					}
				}	
				
				System.out.println("Dead!");
				m_heart.setDead(true);
				return;
			}

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
			if(random.nextFloat()>m_chance/4)
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
