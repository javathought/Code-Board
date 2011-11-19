package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Project extends Model {
	
	public String name;
	@ManyToOne(optional=true)
	public Project parent;
	public String description;
	public String identifier;
	public String homepage;
	public Boolean isPublic;
	@Temporal(value=TemporalType.TIMESTAMP)
	public Date created;
	
	
	
	public String toString() {
		return name;
	}
    
}
