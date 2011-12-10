package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Issue extends Model {
	
//	@Required
	@ManyToOne
	public Project project;
//	@Required
	@ManyToOne
	public Tracker tracker;
	@Required
	public String subject;
//	@MaxSize 2000
	public String description;
	@ManyToOne
	public User assignee;
	public Date updated;
//	@Required
	@ManyToOne
	public State state;
//	@Required
	@ManyToOne
	public Enumeration priority;

    
}
