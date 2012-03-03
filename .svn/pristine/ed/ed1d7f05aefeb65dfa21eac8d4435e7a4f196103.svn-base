package ext.move;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.State;

import ext.move.Moveable;
import play.i18n.Messages;

public class Mover {


	public static void upAfter(Moveable object_to_move) {
		List<Moveable> objects = object_to_move.findAfter();
		for (Iterator iterator = objects.iterator(); iterator.hasNext();) {
			Moveable object = (Moveable) iterator.next();
			object.up();
			object.persist();
		}
	}

	public static void move(Moveable object_to_move, String to) {
		
		if (to.equals("highest")) {
			List<Moveable> objects = object_to_move.findBefore();
			for (Iterator iterator = objects.iterator(); iterator.hasNext();) {
				Moveable object = (Moveable) iterator.next();
				object.down();
				object.persist();
			}
			object_to_move.first();	
		} else if (to.equals("higher")) {
			Moveable object_to_swap = object_to_move.findPrevious();
			object_to_swap.down();	
			object_to_move.up();
			object_to_swap.persist();
		} else if (to.equals("lower")) {
			Moveable object_to_swap = object_to_move.findNext();
			object_to_swap.up();	
			object_to_move.down();
			object_to_swap.persist();
		} else if (to.equals("lowest")) {
			upAfter(object_to_move);
			object_to_move.last();	
		}
			
	}

	
}
