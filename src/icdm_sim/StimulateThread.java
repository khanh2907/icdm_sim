package icdm_sim;

public class StimulateThread extends Thread {
	
	private ICD m_icd;
	private String DEAD = "dead";
	private String FAST = "fast";
	private String SLOW = "slow";
	
	public StimulateThread (ICD icd) {
		m_icd = icd;
	}
	
	public void run() {
		while(true) {
			if (m_icd.getFlag(FAST) || m_icd.getFlag(SLOW) || m_icd.getFlag(DEAD)) {
				System.out.println("StimulateThread: Emergency - stimulating heart.");
				m_icd.getHeart().setHeartrate(60);
				m_icd.resetFlags();
			}
		}
	}
	
}
