import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
public class Hash {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		AccessFile af = new AccessFile();
		
		File file = new File("C:\\Users\\Lucas A\\Desktop\\410READ\\sample.txt");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		try {
			String CheckSum = getFileChecksum(md, file);
			System.out.println(CheckSum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}
	public static String getFileChecksum(MessageDigest digest, File file)throws IOException{
		FileInputStream fis = new FileInputStream(file);
		
		byte[] byteArray = new byte [1024];
		int bytesCount = 0;
		while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	    fis.close();
	    byte[] bytes = digest.digest();
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    
	   return sb.toString();
	    
	}

	public  String hashPassword(String pass){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
		
			byte[] messageDigest = md.digest(pass.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while(hashtext.length()<32){
				hashtext = "0"+hashtext;
			}
			return hashtext;
		
		}
		catch(NoSuchAlgorithmException e){
				throw new RuntimeException(e);
		}
	}
}
