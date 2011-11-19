package models;

import play.*;
import play.data.validation.MaxSize;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Issue extends Model {
	
	@ManyToOne
	public Project project;
	@ManyToOne
	public Tracker tracker;
	public String subject;
//	@MaxSize 2000
	public String description;
	@ManyToOne
	public User assignee;
	public Date updated;

    
}
