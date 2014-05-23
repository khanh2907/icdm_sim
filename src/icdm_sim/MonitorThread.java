package icdm_sim;

public class MonitorThread extends Thread {
	
	ICD m_icd;
	
	public MonitorThread(ICD icd) {
		m_icd = icd;
	}
	
	public void run() {
		while (true) {
			float heartrate = m_icd.updateCurrentHeartrate(m_icd.getHeart().getHeartrate());
			
			System.out.println("MonitorThread: " + heartrate);
//			
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
