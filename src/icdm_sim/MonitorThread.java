package icdm_sim;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorThread extends Thread {
	
	ICD m_icd;
	private Timer t = new Timer(true);
	
	public MonitorThread(ICD icd) {
		m_icd = icd;
	}
	
	public void run() {
		while (true) {
			float heartrate = m_icd.updateCurrentHeartrate(m_icd.getHeart().getHeartrate());
			//seems to poll better with blank statements added in
			System.out.print("");
			
			if(m_icd.getHeart().getPWave()){
			
				System.out.println("P wave detected");
				
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					
//					e.printStackTrace();
//				}
				long startTime = System.currentTimeMillis();
				while((System.currentTimeMillis()-startTime)< 160){
//					System.out.println(m_icd.getHeart().getQRSWave());
					if(m_icd.getHeart().getQRSWave()){
						m_icd.setQRSFlag(true);		
						System.out.println("QRS wave detected");
						break;
					}
				
				}
					
					if(!m_icd.getHeart().getQRSWave()){
					m_icd.setQRSFlag(false);
					System.out.println("QRS wave not detected");
					}
				
				//reset flags
			
		
				System.out.print("");
			}
		
			
		
			
//			if (heartrate < 60) {
//				System.out.println("Setting slow flag.");
//				m_icd.setSlowFlag(true);
//			}
//			else if (heartrate > 120) {
//				System.out.println("Setting fast flag.");
//				m_icd.setFastFlag(true);
//			}
//			else if (heartrate == 0) {
//				System.out.println("Setting dead flag.");
//				m_icd.setDeadFlag(true);
//			}
			
		}
	}
}

