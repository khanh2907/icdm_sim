package icdm_sim;

public class ICD {
	
	private float m_currentHeartrate;
	private boolean m_dead, m_fast, m_slow;
	private Heart m_heart;
	private MonitorThread m_monitorThread;
	private SendThread m_sendThread;
	private int m_patientId;
	
	public ICD(Heart heart) {
		m_dead = false;
		m_fast = false;
		m_slow = false;
		m_heart = heart;
		m_patientId = heart.getPatientId();
		
		m_monitorThread = new MonitorThread(this);
		m_sendThread = new SendThread(this, "http://130.56.248.71/elec5614/icdm/web/post/heartrate/");
		
		m_monitorThread.start();
		
		m_sendThread.start();
		
		
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
}
