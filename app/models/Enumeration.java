package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Enumeration extends Model {
	
	public String name;
	public int position;
	public Boolean is_default;
	public String type;
	public Boolean active;
 
	public String toString() {
		return name;
	}
}
