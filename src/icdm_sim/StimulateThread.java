package icdm_sim;
import java.util.Random;

public class StimulateThread extends Thread {

	private ICD m_icd;
	private String DEAD = "dead";
	private String FAST = "fast";
	private String SLOW = "slow";
	private Heart m_heart;
	private Random chance;

	public StimulateThread (ICD icd) {
		m_icd = icd;
		m_heart = m_icd.getHeart();
		chance = new Random();
	}

	public void run() {
		while(true) {
			//			if (m_icd.getFlag(FAST) || m_icd.getFlag(SLOW) || m_icd.getFlag(DEAD)) {
			//				System.out.println("StimulateThread: Emergency - stimulating heart.");
			//				m_icd.getHeart().setHeartrate(60);
			//				m_icd.resetFlags();
			//			}
			//			doesn't poll properly without print
			try{
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//send pacing signal
			if(!m_icd.getQRSFlag()){
				System.out.println("Ventricle pacing signal sent");

				try{
					m_heart.getLockQRSWave().lock();
					m_icd.setQRSFlag(true);
				} finally {
					m_heart.getLockQRSWave().unlock();
				}
			}

			//defibrillate heart

			if(m_icd.getHeart().getFib() && !m_icd.getHeart().isDead()){
				System.out.println("Preparing to defibrillate...");
				System.out.println("Charging....");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Discharge");

				if(chance.nextFloat() > 0.3){		
					try{
						m_heart.getLockVentricFib().lock();				
						m_icd.getHeart().setFib(false);
					} finally {
						m_heart.getLockVentricFib().unlock();
					}

					try{
						m_heart.getLockHeartRate().lock();
						m_heart.setHeartrate(80);
					} finally {
						m_heart.getLockHeartRate().unlock();
					}
				}
				else 
					System.out.println("Defibrillation failed");
			}

			if(m_icd.getSlow()){
				System.out.println("Bradycardia detected, heart pacing signal sent");
				m_icd.setSlowFlag(false);
				try{
					m_heart.getLockHeartRate().lock();
					m_heart.setHeartrate(80);
				} finally {
					m_heart.getLockHeartRate().unlock();
				}

			}

			if(m_icd.getFast()){
				System.out.println("Tachycardia detected, heart pacing signal sent");

				m_icd.setFastFlag(false);
				try{
					m_heart.getLockHeartRate().lock();
					m_heart.setHeartrate(80);
				} finally {
					m_heart.getLockHeartRate().unlock();
				}
			}
		}
	}

}
