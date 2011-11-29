package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class State extends Model {
	public String name;
	public Boolean closed;
	public Boolean isDefault;
	public long position;
	public int defaultDoneRatio;
	
	public String toString() {
		return name;
	}
    
}
