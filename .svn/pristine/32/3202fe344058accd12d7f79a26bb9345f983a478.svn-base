package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@OnDelete(action=OnDeleteAction.CASCADE)
public class Project extends Model {
	
	@Required
	public String name;
	@ManyToOne(optional=true)
	public Project parent;
	public String description;
	@Required
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
