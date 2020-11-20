package com.example.technoxtream17.user.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.technoxtream17.user.config.AppProperties;
import com.example.technoxtream17.user.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenProviderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenProviderService.class);
	
	@Autowired
	private AppProperties appProperties;
	
	public TokenProviderService(AppProperties appProperties) {
		this.appProperties=appProperties;
	}
	
	public String createToken(Authentication authentication) {
		LOGGER.info("TokenProviderService.createToken({})", authentication);
		
		UserPrincipal userPrincipal=  (UserPrincipal)authentication.getPrincipal();
		
		Date now= new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		
		return Jwts.builder()
				.setSubject(Long.toString(userPrincipal.getId()))
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
				.compact();
				
	}
	
	public Long getUserIdFromToken(String token){
		LOGGER.info("TokenProviderService.getUserIdFromToken({} )", token);
		Claims  claims  = Jwts.parser()
							.setSigningKey(appProperties.getAuth().getTokenSecret())
							.parseClaimsJws(token)
							.getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	public boolean validateToken(String token) {
		
		LOGGER.info("TokenProviderService.validateToken( {})", token);
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			LOGGER.error(" Invalid JWT Signature - {} ",e.getMessage());
		}catch (MalformedJwtException e) {
			LOGGER.error(" Invalid JWT Token - {} ", e.getMessage());
		}catch (ExpiredJwtException e) {
			LOGGER.error(" Expired JWT Token - {} ", e.getMessage());
		}catch (UnsupportedJwtException e) {
			LOGGER.error(" Unsupported JWT Signature - {} ",e.getMessage());
		}catch (IllegalArgumentException e) {
			LOGGER.error(" JWT claims string is empty - {}", e.getMessage());
		}
		return false;
		
	}

}
