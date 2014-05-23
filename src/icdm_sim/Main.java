package icdm_sim;

import java.util.Random;


public class Main {
	
	public static void main(String[] args) {
		
		Heart heart = new Heart(2);
		
		ICD icd = new ICD(heart);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random rng = new Random();
		
		while (true) {
			System.out.println("Main Thread: check");
			
			heart.setHeartrate(rng.nextFloat()*70);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
