package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Tracker extends Model {
	public String name;
	public Boolean inRoadmap;
	public long position;
	
	public String toString() {
		return name;
	}
    
}
