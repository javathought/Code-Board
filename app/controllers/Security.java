package controllers;

import models.User;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
		return Users.connect(username, password) != null;
        
    }
    
    static boolean check(String profile) {
    	String login = connected();
    	if (login == null)
    		return false;
        if("admin".equals(profile)) { 
        	Boolean isAdmin = User.find("byLogin", connected()).<User>first().admin;
            return (isAdmin != null );
        }    
        return false;
    }

	public static User currentUser() {
    	String login = connected();
    	if (login == null)
    		return null;
		return User.find("byLogin", connected()).<User>first();
	}

    
    static void onDisconnected() {
        Application.index();
    }

}
