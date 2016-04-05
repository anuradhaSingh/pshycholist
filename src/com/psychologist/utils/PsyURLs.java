package com.psychologist.utils;

public interface PsyURLs {
	
	String prodDomain = "",testDomain="http://45.79.156.212:8080/mypsycho";
	String domain = testDomain ;
	
	
	interface Users{
		String registerUser = domain +"/users.json";
		String userLoginPost = domain + "/users/login.json";
		String forgotpasswordPost = domain + "/users/forgot/password.json?";
	}
	
	interface Country{
		String country = domain +"/master/search/countries.json?text=";
		String state = domain + "/master/search/states.json?text=";
		String city = domain + "/master/search/cities.json?text=";
	}
	interface Category{
		String category = domain +"/master/user/categories/1.json";
		
	}
	interface Doctor{
		String doctor = domain +"/users/search.json";
		
	}
}
