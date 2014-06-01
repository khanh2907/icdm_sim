package icdm_sim;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class StimulateThread extends Thread {

	private ICD m_icd;
	private Heart m_heart;
	private Random chance;
	
	private HttpClient m_httpclient; 
	private HttpPost m_httppost;

	public StimulateThread (ICD icd) {
		m_icd = icd;
		m_heart = m_icd.getHeart();
		chance = new Random();
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

			//send pacing signal
			if(!m_icd.getQRSFlag()){
				int defJobId = createNewJob("VENT", "Ventricle pacing signal sent.");
				System.out.println("Ventricle pacing signal sent");

				try{
					m_heart.getLockQRSWave().lock();
					m_icd.setQRSFlag(true);
					updateJob(defJobId, "COMPLETED", "Ventricle was successful.", "true");
				} finally {
					m_heart.getLockQRSWave().unlock();
				}
			}

			//defibrillate heart

			if(m_icd.getHeart().getFib() && !m_icd.getHeart().isDead()){
				int defJobId = createNewJob("DEF", "Defibrillation.");
				System.out.println("Preparing to defibrillate...");
				System.out.println("Charging....");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Discharge");

				if(chance.nextFloat() < 0.3){		
					try{
						m_heart.getLockVentricFib().lock();				
						m_icd.getHeart().setFib(false);
					} finally {
						m_heart.getLockVentricFib().unlock();
					}

					try{
						m_heart.getLockHeartRate().lock();
						m_heart.setHeartrate(80);
					} finally {
						m_heart.getLockHeartRate().unlock();
					}
					updateJob(defJobId, "COMPLETED", "Defibrillation was successful.", "true");
				}
				else 
					System.out.println("Defibrillation failed");
			
			}

			if(m_icd.getSlow() && !m_icd.getHeart().isDead()){
				int defJobId = createNewJob("BRAD", "Bradycardia detected, heart pacing signal sent.");
				System.out.println("Bradycardia detected, heart pacing signal sent");
				m_icd.setSlowFlag(false);
				try{
					m_heart.getLockHeartRate().lock();
					m_heart.setHeartrate(80);
				} finally {
					m_heart.getLockHeartRate().unlock();
				}
				updateJob(defJobId, "COMPLETED", "Bradycardia was successful.", "true");

			}

			if(m_icd.getFast() && !m_icd.getHeart().isDead()){
				int defJobId = createNewJob("TACH", "Tachycardia detected, heart pacing signal sent");
				System.out.println("Tachycardia detected, heart pacing signal sent");

				m_icd.setFastFlag(false);
				try{
					m_heart.getLockHeartRate().lock();
					m_heart.setHeartrate(80);
				} finally {
					m_heart.getLockHeartRate().unlock();
				}
				updateJob(defJobId, "COMPLETED", "Tachycardia was successful.", "true");
			}
		}
	}
	
	public int createNewJob(String type, String description) {
		int job_id = -1;
		m_httpclient = HttpClients.createDefault();
		m_httppost = new HttpPost("http://130.56.248.71/elec5614/post/job/");
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("method", "new"));
		params.add(new BasicNameValuePair("patient_id", Integer.toString(m_icd.getPatientId())));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("description", description));

		try {
			m_httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Execute and get the response.
		try {
			System.out.println("StimulateThread: Creating Job.");
			HttpResponse response = m_httpclient.execute(m_httppost);
			
			InputStream ips  = response.getEntity().getContent();
	        BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));

	        StringBuilder sb = new StringBuilder();
	        String s;
	        while(true )
	        {
	            s = buf.readLine();
	            if(s==null || s.length()==0)
	                break;
	            sb.append(s);

	        }
	        buf.close();
	        ips.close();
	        job_id = Integer.parseInt(sb.toString());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return job_id;
		
	}
	
	public void updateJob(int job_id, String status, String description, String completed) {
		m_httpclient = HttpClients.createDefault();
		m_httppost = new HttpPost("http://130.56.248.71/elec5614/post/job/");
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("method", "update"));
		params.add(new BasicNameValuePair("job_id", Integer.toString(job_id)));
		params.add(new BasicNameValuePair("status", status));
		params.add(new BasicNameValuePair("description", description));
		params.add(new BasicNameValuePair("completed", completed));

		try {
			m_httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Execute and get the response.
		try {
			System.out.println("StimulateThread: Updating Job " + job_id);
			HttpResponse response = m_httpclient.execute(m_httppost);
			
			InputStream ips  = response.getEntity().getContent();
	        BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));

	        StringBuilder sb = new StringBuilder();
	        String s;
	        while(true )
	        {
	            s = buf.readLine();
	            if(s==null || s.length()==0)
	                break;
	            sb.append(s);

	        }
	        buf.close();
	        ips.close();
	        System.out.println(sb.toString());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
