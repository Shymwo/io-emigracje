package put.poznan.io.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.BindingProvider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import put.poznan.estudent.AuthenticationResult;
import put.poznan.estudent.AuthenticationServiceService;
import put.poznan.estudent.AuthenticationWebService;

@Component
public class EmigrantAuthenticationProvider implements AuthenticationProvider {

	private final static String END_POINT_URL = "http://localhost:8081/eStudent/authenticationService";
	private String topSecret = "tajnyKluczKomunikacji";
    @Override
    public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (name == null)
        	name = "";
        if (password == null)
        	password = "";
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        /*
        if (name.equals("student1") || name.equals("student2"))
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        else if (name.equals("admin")) {
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else
        	throw new UsernameNotFoundException("User was not found.");
		*/
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(password);
        AuthenticationResult authenticationResult = authenticate(name, password, generateChecksum(builder.toString()));
        for (String str : authenticationResult.getGrantedAuths()) {
        	builder.append(str);
        	grantedAuths.add(new SimpleGrantedAuthority(str));
        }
        if(authenticationResult.getChecksum().equals(generateChecksum(builder.toString())) && !grantedAuths.isEmpty())
        	return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        else 
        	throw new UsernameNotFoundException("User was not found.");
        /*
        // use the credentials to try to authenticate against the third party system
        if (authenticatedAgainstThirdPartySystem()) {
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        } else {
            throw new AuthenticationException("Unable to auth against third party systems");
        }
        */
        
    }
    private String generateChecksum(String data) {
    	try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			byte[] secretByte = topSecret.getBytes("UTF-8");
			byte[] dataBytes = data.getBytes("UTF-8");
			SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
			sha256_HMAC.init(secret);
			byte[] doFinal = sha256_HMAC.doFinal(dataBytes);
			return DatatypeConverter.printBase64Binary(doFinal);
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
		return null;
    }
    public AuthenticationResult authenticate(String name, String password, String checksum) {


    	AuthenticationServiceService authenticationServiceService = new AuthenticationServiceService();
    	AuthenticationWebService authenticationWebService = authenticationServiceService.getAuthenticationServicePort();
        ((BindingProvider) authenticationWebService).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, END_POINT_URL);

        return authenticationWebService.authenticate(name, password, checksum);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
