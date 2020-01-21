package ru.kpfu.itis.Security.Filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Models.Token;
import ru.kpfu.itis.Repositories.TokensRepository;
import ru.kpfu.itis.Security.Token.TokenAuthentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class TokenAuthFilter implements Filter {

    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = request.getParameter("token");

        TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
        if(token == null){
            tokenAuthentication.setAuthenticated(false);
        }else{
//            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);

            Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenAuthentication.getToken());

            if(tokenCandidate.isPresent()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(tokenCandidate.get().getUser().getLogin());
                tokenAuthentication.setUserDetails(userDetails);
                tokenAuthentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
