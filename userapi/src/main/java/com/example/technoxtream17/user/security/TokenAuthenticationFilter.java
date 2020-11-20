package com.example.technoxtream17.user.security;

import static com.example.technoxtream17.user.constant.Paths.API;
import static com.example.technoxtream17.user.constant.Paths.AUTH;
import static com.example.technoxtream17.user.constant.Paths.AUTHORIZATION;
import static com.example.technoxtream17.user.constant.Paths.BEARER;
import static com.example.technoxtream17.user.constant.Paths.FORWARDSLASH;
import static com.example.technoxtream17.user.constant.Paths.LOGIN;
import static com.example.technoxtream17.user.constant.Paths.VERSION;
import static com.example.technoxtream17.user.constant.Paths.SIGNUP;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.technoxtream17.user.service.CustomUserDetailsService;
import com.example.technoxtream17.user.service.TokenProviderService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	@Autowired
	private TokenProviderService tokenProviderService;

	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOGGER.info("TokenAuthenticationFilter.doFilterInternal({} , {} , {}  )", request, response, filterChain);

		try {
			LOGGER.info("TokenAuthenticationFilter.doFilterInternal URL  =" + request.getRequestURI());
			LOGGER.info("url = "+FORWARDSLASH + API + FORWARDSLASH + VERSION + FORWARDSLASH + AUTH+ FORWARDSLASH+ SIGNUP );
			LOGGER.info(" condition = "+request.getRequestURI().equals(FORWARDSLASH + API + FORWARDSLASH + VERSION + FORWARDSLASH + AUTH+ FORWARDSLASH+ SIGNUP ));
			//if(!request.getRequestURI().equals(FORWARDSLASH + API + FORWARDSLASH + VERSION + FORWARDSLASH + AUTH+ FORWARDSLASH+ SIGNUP )) {
				
				LOGGER.info("inside method");
				String jwt = getJwtFromRequest(request);
				LOGGER.info(" jwt= "+jwt);
				if (StringUtils.hasText(jwt) && tokenProviderService.validateToken(jwt)) {
					Long userId = tokenProviderService.getUserIdFromToken(jwt);
	
					UserDetails userDetails = customUserDetailsService.loadUserbyId(userId);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
			/*
			 * }else { SecurityContextHolder.getContext().setAuthentication(null); }
			 */	

		} catch (Exception e) {
			LOGGER.error("Could not set user authentication in the security context");
		}
		LOGGER.info("after passing from method");
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
