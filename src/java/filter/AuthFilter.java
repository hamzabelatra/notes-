
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter (  urlPatterns = { "/*"} )
public class AuthFilter  implements  Filter {
      public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            String reqURI = req.getRequestURI();
         
            if (  (ses != null && ses.getAttribute("email") != null)  
                      ||  reqURI.contains("/all/")
                      ||  reqURI.contains(".js")
                      ||  reqURI.contains(".css")
                      ||  reqURI.contains("/rest/"))
            {
                   chain.doFilter(request, response);
            }
            else   // user didn't log in but asking for a page that is not allowed, so take user to login page
            {
                  res.sendRedirect(req.getContextPath() + "/faces/all/login.xhtml");  // Anonymous user. Redirect to login page
                   
            }
      }
     catch(Throwable t) {
         System.out.println( t.getMessage());
     }
    } //doFilter

    @Override
    public void destroy() {
        
    }
    
}
