/*
 * EE422C Final Project submission by
 * Mehmet Uzgoren
 * msu259
 * 17615
 * Used 1 Slip Day
 * Fall 2022
 */
package application;

public class PasswordEncrypter {
	public static String password;
	public static String EncryptPassword(String username) {
		if(username.length() >= 3) {
			String first3 = username.substring(0,3);
			String primenumberencryption = "1753";
			String first3flipped = "";
			first3flipped += first3.substring(2,3);
			first3flipped += first3.substring(1,2);
			first3flipped += first3.substring(0,1);
			String encryptedpassword = first3flipped + primenumberencryption;
			return encryptedpassword;
		}		
		else {			
			return ("1753");
		}
	}
	
}
