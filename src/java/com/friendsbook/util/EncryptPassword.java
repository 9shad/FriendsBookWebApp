package com.friendsbook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {

	private static MessageDigest md;
	
	   public static String cryptWithMD5(String pass){
		    try {
		    	//get and instance of the MD5 MessageDigest
		        md = MessageDigest.getInstance("MD5");
		        //get a byte array of the String to be hashed
		        byte[] passBytes = pass.getBytes();
		        //Make sure the Digest is clear
		        md.reset();
		        //Populate the Digest with the byte array and create MD5 hash
		        byte[] digested = md.digest(passBytes);
		        //sb is used to store the hash
		        StringBuffer sb = new StringBuffer();
		        
		        //Now we need to pull out each char of the byte array digested into the StringBuffer
		        for(int i=0;i<digested.length;i++){
		            sb.append(Integer.toHexString(0xff & digested[i]));
		        }
		        return sb.toString();
		    } catch (NoSuchAlgorithmException ex) {
		        //Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
		    	ex.printStackTrace();
		    }
		    return null;
	   }
}
