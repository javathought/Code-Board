package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Enumeration extends Model {
	
	public static final String ISSUE_PRIORITY_TYPE = "IssuePriority";

	public String name;
	public long position;
	public Boolean is_default;
	public String type;
	public Boolean active;
 
	public String toString() {
		return name;
	}
}
