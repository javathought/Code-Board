package controllers;

import models.User;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
		return Users.connect(username, password) != null;
        
    }
    
    static boolean check(String profile) {
        if("admin".equals(profile)) {
        	Boolean isAdmin = User.find("byLogin", connected()).<User>first().admin;
            return (isAdmin != null );
        }
        return false;
    }
    
    static void onDisconnected() {
        Application.index();
    }

}
