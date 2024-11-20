package gcu_student.app;

import java.util.ArrayList;

import gcu_student.app.Salables.Salable;

/**
 * Class that holds all of the items in stock, and allows for manipulating them.
 * @author Logan Swinson
 */
public class InventoryManager
{
	// ArrayList that stores all of the items available for sale
	private ArrayList<Salable> inven;
	
	/**
	 * Initializes an empty ArrayList for Salables
	 */
	public InventoryManager()
	{
		inven = new ArrayList<Salable>();	
	};
	
	/**
	 * Allows other classes to call this method and have access to the Salables in the Inventory Manager.
	 * @return An ArrayList containing all of the items available for sale.
	 */
	public ArrayList<Salable> getInven()
	{
		return this.inven;
	}

	/**
	 * Sets the inven to match the arguement ArrayList
	 * @param newInven The replacement inven
	 */
	public void setInven(ArrayList<Salable> newInven)
	{
		this.inven = newInven;
	}
}
