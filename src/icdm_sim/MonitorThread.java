package icdm_sim;

public class MonitorThread extends Thread {

	ICD m_icd;
	Heart heart;

	public MonitorThread(ICD icd) {
		m_icd = icd;
		heart = m_icd.getHeart();
	}

	public void run() {
		lookForP:
			while (true) {
				long startTime = System.currentTimeMillis();
				float heartrate = m_icd.updateCurrentHeartrate(heart.getHeartrate());
				
				if (heart.isDead()){
					m_icd.updateCurrentHeartrate(0);
				}

				try{
					Thread.sleep(1);
				} catch (InterruptedException e){
					e.printStackTrace();
				}

				if(heart.isDead()){

					System.out.println("Patient is dead");
					m_icd.setDead(true);
					continue lookForP;

				}


				System.out.println("Heart rate is: " + heartrate);
				if(heartrate<60){
					System.out.println("Bradycardia");
					m_icd.setSlowFlag(true);
				}

				if(heartrate>100){
					System.out.println("Tachycardia");
					m_icd.setFastFlag(true);
				}
				while(System.currentTimeMillis() - startTime < (1/(heart.getHeartrate()/60)*1000)){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
								System.out.println("QRS wave detected");
								try{
									heart.getLockQRSWave().lock();
									m_icd.setQRSFlag(true);
								} finally {
									heart.getLockQRSWave().unlock();
								}

								continue lookForP;
							}

						}

						if(!heart.getQRSWave()){
							System.out.println("QRS wave not detected");
							try{
								heart.getLockQRSWave().lock();
								m_icd.setQRSFlag(false);
							} finally {
								heart.getLockQRSWave().unlock();
							}

							continue lookForP;
						}

					}

				}
			}
	}

}


