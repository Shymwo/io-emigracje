package put.poznan.io.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class EmigrantAuthenticationProvider implements AuthenticationProvider {
 
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        
        if (name.equals("student1") || name.equals("student2"))
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        else if (name.equals("admin")) {
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
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
        return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
