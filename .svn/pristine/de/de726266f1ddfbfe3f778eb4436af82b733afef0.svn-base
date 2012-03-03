package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="cb_user")
public class User extends Model {
    
	@Required
	public String login;
	@Required
	public String firstname;
	@Required
	public String lastname;
	@Email
	@Required
	public String mail;
	public Boolean admin;
	public Date created_on;
	public Date last_login_on;
	public String hashed_password;
	@ManyToMany
	public List<Domain> domains;

	public User() {
		domains = new ArrayList<Domain>();
	}
	
	
	public String toString() {
		return firstname + " " + lastname;
	}

}
