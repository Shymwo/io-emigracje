package put.poznan.estudent;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(endpointInterface = "put.poznan.estudent.AuthenticationWebService")
public class AuthenticationService implements put.poznan.estudent.AuthenticationWebService {

	private String topSecret = "tajnyKluczKomunikacji";
	public AuthenticationResult authenticate(String name, String password,  String checksum) {
		if (checksum.equals(generateChecksum(name+password))) {
			List<String> grantedAuths = new ArrayList<String>();
			StringBuilder builder = new StringBuilder();
			builder.append(name);
			builder.append(password);
			if (!checksum.equals(generateChecksum(builder.toString()))) {
				return new AuthenticationResult(grantedAuths, generateChecksum(builder.toString()));
			}
			if (name.equals("student1") || name.equals("student2")) {
				grantedAuths.add("ROLE_STUDENT");
			}
			else if(name.equals("admin")) {
				grantedAuths.add("ROLE_STUDENT");
				grantedAuths.add("ROLE_ADMIN");
			}
			for (String str : grantedAuths)
				builder.append(str);
			return new AuthenticationResult(grantedAuths, generateChecksum(builder.toString()));
			//return "test";
		}
		else {
		return null;
		}
 }
	private String generateChecksum (String data) {
		String checksum = null;
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			byte[] secretByte = topSecret.getBytes("UTF-8");
			byte[] dataBytes = data.getBytes("UTF-8");
			SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
			sha256_HMAC.init(secret);
			byte[] doFinal = sha256_HMAC.doFinal(dataBytes);
			checksum = DatatypeConverter.printBase64Binary(doFinal);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checksum;
	}

}