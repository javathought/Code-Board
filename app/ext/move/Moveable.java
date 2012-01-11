/**
 * 
 */
package ext.move;

import java.util.List;

/**
 * @author javathought
 *
 */
public interface Moveable {
	
	public List<Moveable> findBefore();
	public Moveable findPrevious();
	public Moveable findNext();
	public List<Moveable> findAfter();
	
	public void first();
	public void up();
	public void down();
	public void last();
	public void persist();
	
}
