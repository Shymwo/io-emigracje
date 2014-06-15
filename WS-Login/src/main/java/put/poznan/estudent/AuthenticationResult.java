package put.poznan.estudent;

import java.util.List;

public class AuthenticationResult {
	private String checksum;
	private List<String> grantedAuths;
	
	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public List<String> getGrantedAuths() {
		return grantedAuths;
	}

	public void setGrantedAuths(List<String> grantedAuths) {
		this.grantedAuths = grantedAuths;
	}

	public AuthenticationResult(List<String> grantedAuths, String checksum) {
		this.grantedAuths = grantedAuths;
		this.checksum = checksum;
	}

	public String toString() { 
		StringBuilder builder = new StringBuilder();
		for (String str : grantedAuths)
			builder.append(str);
		return builder.toString();
	}

}
