package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import ext.move.Moveable;

import java.util.*;

@Entity
public class State extends Model implements Moveable {
	public String name;
	public Boolean closed;
	public Boolean isDefault;
	public long position;
	public int defaultDoneRatio;
	
	public String toString() {
		return name;
	}

	@Override
	public List<Moveable> findBefore() {
		return State.find("position < ?", position).fetch();
	}
	
	@Override
	public Moveable findPrevious() {
		return State.find("position = ?", position - 1).first();
	}

	@Override
	public Moveable findNext() {
		return State.find("position = ?", position + 1).first();
	}

	@Override
	public List<Moveable> findAfter() {
		return State.find("position > ?", position).fetch();
	}

	@Override
	public void first() {
		position = 1;
	}

	@Override
	public void up() {
		position = position - 1;
	}

	@Override
	public void down() {
		position = position + 1;
	}

	@Override
	public void last() {
		position = State.count();	
	}

	@Override
	public void persist() {
		save();
	}
    
}
