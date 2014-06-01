package icdm_sim;


public class ICD {

	private float m_currentHeartrate;
	private boolean m_dead, m_fast, m_slow;
	private Heart m_heart;
	private MonitorThread m_monitorThread;
	private SendThread m_sendThread;
	private StimulateThread m_stimulateThread;
	private int m_patientId;
	private boolean m_detectedP, m_detectedQRS;

	public ICD(Heart heart) {
		m_dead = false;
		m_fast = false;
		m_slow = false;
		m_heart = heart;
		m_patientId = heart.getPatientId();

		m_monitorThread = new MonitorThread(this);
		m_sendThread = new SendThread(this, "http://130.56.248.71/elec5614/icdm/web/post/heartrate/");
		m_stimulateThread = new StimulateThread(this);



		m_monitorThread.start();
		m_stimulateThread.start();

		//		m_sendThread.start();


	}

	public Heart getHeart() {
		return m_heart;
	}

	public int getPatientId() {
		return m_patientId;
	}

	public float updateCurrentHeartrate(float newHeartrate) {
		m_currentHeartrate = newHeartrate;
		return m_currentHeartrate;
	}

	public float getCurrentHeartrate() {
		return m_currentHeartrate;
	}

	public boolean getFlag(String type) {
		if (type == "dead") {
			return m_dead;
		}
		else if (type == "fast") {
			return m_fast;
		}
		else if (type == "slow") {
			return m_slow;
		}
		else {
			return false;
		}
	}

	public void setPFlag(boolean state){
		m_detectedP = state;
	}

	public void setQRSFlag(boolean state){
		m_detectedQRS = state;
	}

	public boolean getPFlag(){
		return m_detectedP;
	}

	public boolean getQRSFlag(){
		return m_detectedQRS;
	}

	public void setDeadFlag(boolean state) {
		m_dead = state;
	}

	public void setFastFlag(boolean state) {
		m_fast = state;
	}

	public void setSlowFlag(boolean state) {
		m_slow = state;
	}

	public void resetFlags() {
		m_dead = false;
		m_fast = false;
		m_slow = false;
	}

	public boolean getSlow(){
		return m_slow;
	}

	public boolean getFast(){
		return m_fast;
	}
	
	public void setDead(boolean state){
		m_dead = state;
	}
	
	public boolean isDead(){
		return m_dead;
	}

}
