package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Project extends Model {
	
	@Required
	public String name;
	@ManyToOne(optional=true)
	public Project parent;
	public String description;
	public String identifier;
	public String homepage;
	public Boolean isPublic;
	@Temporal(value=TemporalType.TIMESTAMP)
	public Date created;
	public String textformat;
	
	
	
	public String toString() {
		return name;
	}
    
}
