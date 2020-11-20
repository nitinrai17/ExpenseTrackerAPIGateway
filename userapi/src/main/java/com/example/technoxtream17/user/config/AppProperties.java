package com.example.technoxtream17.user.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;

@ConfigurationProperties(prefix="app")
@Getter
public class AppProperties {
	
	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();
	
	@Data
	public static class Auth{
		private String tokenSecret;
		private long tokenExpirationMsec;
		
	}
	
	@Data
	public static class OAuth2{
		private List<String> authorizedRedirectUris = new ArrayList<>();
	}

}
