package icdm_sim;

public class Heart{
	private int m_patientId;
	private float m_heartrate;
	
	public Heart(int patientId) {
		m_patientId = patientId;
		m_heartrate = 60;
	}
		
	public float setHeartrate(float newHeartrate) {
		m_heartrate = newHeartrate;
		return m_heartrate;
	}
	
	public float getHeartrate() {
		return m_heartrate;
	}
	
	public int getPatientId() {
		return m_patientId;
	}
}
