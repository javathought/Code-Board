package models;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import controllers.Security;

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
	@ManyToOne(optional=true)	
	public Domain domain;
	
	
	public Project(String s) {
		this.domain = Domain.find("isDefault = ?", true).first();
	}
		
	@Override 
	public String toString() {
		return name;
	}
	

	public static JPAQuery findVisibleBy(User user, Boolean ordered) {
		JPAQuery query;
		if (user != null && ! user.domains.isEmpty() ) {
			query = Project.find("select p from Project p where p.domain in (:domains) " + 
					" or p.domain in (select distinct d from Domain d where isPublic = :true )" + 
					(ordered ? " order by created desc" : "") ).bind("domains", user.domains).bind("true", true);
		} else {
			query = Project.find("domain in (select distinct d from Domain d where isPublic = ? )", true);
		}
		return query;
	}
	
	public boolean isVisible() {
		Boolean visible = false;
		User user = Security.currentUser();
		for (Iterator iterator = user.domains.iterator(); iterator.hasNext();) {
			Domain domain = (Domain) iterator.next();
			if (domain.equals(domain)) {
				visible = true;
				break;
			}
			
		}
		return (domain.isPublic || visible); 
	}
    
}
