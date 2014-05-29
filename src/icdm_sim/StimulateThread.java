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

			if(!m_icd.getQRSFlag()){
				System.out.println("Ventricle pacing signal sent");
				m_icd.setQRSFlag(true);
			}

			if(!m_icd.getPFlag()){
				System.out.println("Atrium pacing signal sent");
				m_icd.setPFlag(true);
			}

			if(m_icd.getHeart().getFib()){
				System.out.println("Preparing to defibrillate...");
				System.out.println("Charging....");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Discharge");
				m_icd.getHeart().setFib(false);

			}
		}
	}

}
