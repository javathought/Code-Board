package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {
    
	@Required
	public String login;
	public String firstname;
	public String lastname;
	@Email
	@Required
	public String mail;
	public Boolean admin;
	public Date created_on;
	public Date last_login_on;
	public String hashed_password;
	
	public String toString() {
		return firstname + " " + lastname;
	}

}
