import java.security.*;

public class MD5 {
	public String MD5(String txt) throws Exception {
		StringBuffer sbuf = new StringBuffer();
		
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		mDigest.update(txt.getBytes());
		
		byte[] msgStr = mDigest.digest();
		for(int i=0; i < msgStr.length; i++){
			String tmpEncTxt = Integer.toHexString((int)msgStr[i] & 0x00ff);
			sbuf.append(tmpEncTxt);
		}
		return sbuf.toString();
	}
}