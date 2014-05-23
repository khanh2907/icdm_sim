package icdm_sim;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class SendThread extends Thread {
	
	private ICD m_icd;
	private HttpClient m_httpclient; 
	private HttpPost m_httppost;
	private String m_host;
	
	public SendThread(ICD icd, String host) {
		m_icd = icd;
		m_host = host;
	}
	
	
	public void run() {
		while(true) {
			m_httpclient = HttpClients.createDefault();
			m_httppost = new HttpPost(m_host);
			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("patient_id", Integer.toString(m_icd.getPatientId())));
			params.add(new BasicNameValuePair("heartrate", Float.toString(m_icd.getCurrentHeartrate())));

			try {
				m_httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//Execute and get the response.
			try {
				System.out.println("SendThread: Sending heartrate to server.");
				HttpResponse response = m_httpclient.execute(m_httppost);
				HttpEntity entity = response.getEntity();
				System.out.println("SendThread: Sending complete.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				System.out.println("SendThread: Sleeping for 1 second.");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
