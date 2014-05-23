package icdm_sim;

public class ICD {
	
	private float m_currentHeartrate;
	private boolean m_dead, m_fast, m_slow;
	private Heart m_heart;
	private MonitorThread m_monitorThread;
	
	public ICD(Heart heart) {
		m_dead = false;
		m_fast = false;
		m_slow = false;
		m_heart = heart;
		m_monitorThread = new MonitorThread(this);
		
		m_monitorThread.start();
	}
	
	public Heart getHeart() {
		return m_heart;
	}
	
	public float updateCurrentHeartrate(float newHeartrate) {
		m_currentHeartrate = newHeartrate;
		return m_currentHeartrate;
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
}
