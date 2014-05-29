package icdm_sim;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorThread extends Thread {

	ICD m_icd;
	Heart heart;
	private Timer t = new Timer(true);

	public MonitorThread(ICD icd) {
		m_icd = icd;
		heart = m_icd.getHeart();
	}

	public void run() {
		lookForP:
			while (true) {
				long startTime = System.currentTimeMillis();
				float heartrate = m_icd.updateCurrentHeartrate(heart.getHeartrate());
				//seems to poll better with blank statements added in

				try{
					Thread.sleep(1);
				} catch (InterruptedException e){
					e.printStackTrace();
				}

				while(System.currentTimeMillis() - startTime < (1/(heart.getHeartrate()/60)*1000)){
					System.out.print("");
					if(heart.getPWave()){
						m_icd.setPFlag(true);
						System.out.println("P wave detected");

						//				try {
						//					Thread.sleep(100);
						//				} catch (InterruptedException e) {
						//					
						//					e.printStackTrace();
						//				}
						long startTime1 = System.currentTimeMillis();

						while((System.currentTimeMillis()-startTime1)< 160){
							//					System.out.println(heart.getQRSWave());
							if(heart.getQRSWave()){
								m_icd.setQRSFlag(true);		
								System.out.println("QRS wave detected");
								continue lookForP;
							}

						}

						if(!heart.getQRSWave()){
							m_icd.setQRSFlag(false);
							System.out.println("QRS wave not detected");
							continue lookForP;
						}

					}

				}


			}
	}

}


