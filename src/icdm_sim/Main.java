package icdm_sim;

import java.util.Random;


public class Main {
	
	public static void main(String[] args) {
		
		Heart heart = new Heart(2, 60, 29, 9, true, true, true);
		
		ICD icd = new ICD(heart);
		
		

	}

}
