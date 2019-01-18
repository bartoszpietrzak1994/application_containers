package main.java.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class TicketAppAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException  {
        if (response.isCommitted())
        {
            return;
        }

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication)
    {
        boolean isUser = false;
        boolean isAdmin = false;

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities)
        {
            if (grantedAuthority.getAuthority().equals("ROLE_USER"))
            {
                isUser = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
            {
                isAdmin = true;
                break;
            }
        }

        if (isUser)
        {
            return "/shop/welcome";
        }
        else if (isAdmin)
        {
            return "/admin/welcome";
        }
        else
        {
            throw new IllegalStateException();
        }
    }
}
