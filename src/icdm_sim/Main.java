package icdm_sim;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	
	public static void main(String[] args) {
		
		
		Heart heart = new Heart(2, 60, 29, 9, true, true, true);
		
		ICD icd = new ICD(heart);
		
		

	}

}
