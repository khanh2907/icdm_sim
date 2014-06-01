package icdm_sim;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Heart{

	Lock lockHeartRate;
	Lock lockPWave;
	Lock lockQRSWave;
	Lock lockVentricFib;
	
	public int m_patientId;

	public int m_age;
	public float m_bmi;
	public int m_dietRating;
	public boolean m_smokes;
	public boolean m_drinksAlcohol;
	public boolean m_heartDisease;
	public boolean m_pWave;
	public boolean m_qrsWave;
	public boolean m_isVentricularFibrillation;

	private float m_heartrate;

	private HeartbeatThread m_heartbeatThread;


	public Heart(int patientId, int age, float bmi, int dietRating, boolean smokes, boolean drinksAlcohol, boolean heartDisease) {
		
		
		lockHeartRate = new ReentrantLock();
		lockQRSWave = new ReentrantLock();
		lockVentricFib = new ReentrantLock();
		
		m_pWave = false;
		m_qrsWave = false;

		m_patientId = patientId;
		m_age = age;
		m_bmi = bmi;
		m_dietRating = dietRating;
		m_smokes = smokes;
		m_drinksAlcohol = drinksAlcohol;
		m_heartDisease = heartDisease;
		m_isVentricularFibrillation = false;

		m_heartrate = 80;

		m_heartbeatThread = new HeartbeatThread(this);
		m_heartbeatThread.start();


	}

	public void setFib(boolean state){
		m_isVentricularFibrillation = state;
	}

	public float setHeartrate(float newHeartrate) {
		m_heartrate = newHeartrate;
		return m_heartrate;
	}

	public void setPWave(boolean state){
		m_pWave = state;
	}

	public void setQRSWave(boolean state){
		m_qrsWave = state;
	}

	public float getHeartrate() {
		return m_heartrate;
	}

	public int getPatientId() {
		return m_patientId;
	}

	public boolean getPWave(){
		return m_pWave;
	}

	public boolean getQRSWave(){
		return m_qrsWave;
	}

	public boolean getFib(){
		return m_isVentricularFibrillation;
	}

	public Lock getLockHeartRate(){
		return lockHeartRate;
	}

	public Lock getLockQRSWave(){
		return lockQRSWave;
	}

	
	public Lock getLockVentricFib(){
		return lockVentricFib;
	}
}
