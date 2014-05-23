package icdm_sim;

public class Heart{
	public int m_patientId;
	
	public int m_age;
	public float m_bmi;
	public int m_dietRating;
	public boolean m_smokes;
	public boolean m_drinksAlcohol;
	public boolean m_heartDisease;
	
	private float m_heartrate;
	
	private HeartPThread m_heartPThread;
	 
	public Heart(int patientId, int age, float bmi, int dietRating, boolean smokes, boolean drinksAlcohol, boolean heartDisease) {
		m_patientId = patientId;
		m_age = age;
		m_bmi = bmi;
		m_dietRating = dietRating;
		m_smokes = smokes;
		m_drinksAlcohol = drinksAlcohol;
		m_heartDisease = heartDisease;
		
		m_heartrate = 60;
		
		m_heartPThread = new HeartPThread(this);
		m_heartPThread.start();
		
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
