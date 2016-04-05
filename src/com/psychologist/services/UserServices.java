package com.psychologist.services;


public interface UserServices {

	void userLoggingIn(boolean userResponse);
	
	void registerUser(String afterRegisteration);
	void forgotUser(String response);
	
	
}
