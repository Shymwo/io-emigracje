package put.poznan.io.common;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -3708038522185767920L;

	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return username;
	}

	public Boolean hasRole(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority ga: auth.getAuthorities()) {
			if (ga.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}