package com.example.technoxtream17.user.controller;

import static com.example.technoxtream17.user.constant.Paths.API;
import static com.example.technoxtream17.user.constant.Paths.AUTH;
import static com.example.technoxtream17.user.constant.Paths.EMAILADDRESSEXIST;
import static com.example.technoxtream17.user.constant.Paths.FORWARDSLASH;
import static com.example.technoxtream17.user.constant.Paths.LOGIN;
import static com.example.technoxtream17.user.constant.Paths.SIGNUP;
import static com.example.technoxtream17.user.constant.Paths.USERSUCCESSFULLYREGISTERED;
import static com.example.technoxtream17.user.constant.Paths.VALIDATE;
import static com.example.technoxtream17.user.constant.Paths.VERSION;
import static com.example.technoxtream17.user.constant.Paths.TOKENVALIDATESUCCESS;
import static com.example.technoxtream17.user.constant.Paths.TOKENVALIDATEFAILURE;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.technoxtream17.user.error.BadRequestException;
import com.example.technoxtream17.user.model.AuthProvider;
import com.example.technoxtream17.user.model.User;
import com.example.technoxtream17.user.payload.ApiResponse;
import com.example.technoxtream17.user.payload.AuthResponse;
import com.example.technoxtream17.user.payload.LoginRequest;
import com.example.technoxtream17.user.payload.SignUpRequest;
import com.example.technoxtream17.user.payload.ValidateRequest;
import com.example.technoxtream17.user.payload.ValidateResponse;
import com.example.technoxtream17.user.service.TokenProviderService;
import com.example.technoxtream17.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(FORWARDSLASH + API + FORWARDSLASH + VERSION)
public class UserAuthController {

	private static final Logger LOGGER = getLogger(UserAuthController.class);
	
	@Autowired(required=true)
	MessageSource messageSource;

	@Autowired(required=true)
	private AuthenticationManager authenticationManager;

	@Autowired(required=true)
	private UserService userService;

	@Autowired(required=true)
	private PasswordEncoder passwordEncoder;

	@Autowired(required=true)
	private TokenProviderService tokenProviderService;

	@ApiOperation("Authenticate User with login details")
	@PostMapping(FORWARDSLASH+AUTH+FORWARDSLASH+ LOGIN)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		LOGGER.info("UserAuthController.authenticateUser( {})",loginRequest );
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProviderService.createToken(authentication);

		return ResponseEntity.ok(new AuthResponse(token));

	}

	@ApiOperation(" Sign up new user")
	@PostMapping(FORWARDSLASH+AUTH+FORWARDSLASH+SIGNUP)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		LOGGER.info("UserAuthController.registerUser( {} )",signUpRequest );
		if (userService.isEmailExist(signUpRequest.getEmail())) {
			throw new BadRequestException(messageSource.getMessage(EMAILADDRESSEXIST, null, Locale.ENGLISH));
		}

		// Create User account
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setProvider(AuthProvider.local);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		// save the user
		User result = userService.createUser(user);

		// create new uri
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(
				new ApiResponse(messageSource.getMessage(USERSUCCESSFULLYREGISTERED, null, Locale.ENGLISH), true));

	}
	
	
	@ApiOperation("Validate the User token details")
	@PostMapping(FORWARDSLASH+AUTH+FORWARDSLASH+ VALIDATE)
	public ResponseEntity<?> validateToken(@Valid @RequestBody ValidateRequest validateRequest) {
		LOGGER.info("UserAuthController.validateToken( {})",validateRequest );
		
		String message =null;
		
		boolean success = tokenProviderService.validateToken(validateRequest.getToken());
		
		if(success) {
			message= messageSource.getMessage(TOKENVALIDATESUCCESS, null, Locale.ENGLISH);
		}else {
			message =messageSource.getMessage(TOKENVALIDATEFAILURE, null, Locale.ENGLISH);
		}

		return ResponseEntity.ok(new ValidateResponse(success,message, validateRequest.getToken()));

	}

}
