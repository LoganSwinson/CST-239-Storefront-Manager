package gcu_student.app.Salables;

/**
 * The Armor class allows for equipable items that provide defense.
 */
public class Armor extends Salable
{
	private int defense;
	
	/**
	 * Non-Default constructor that calls the parent class to assign all of its attributes
	 * @param name Name of the object available for purchase.
	 * @param description Brief synopsis or background information on the item.
	 * @param price Cost of item USD.
	 * @param quantity Number of that particular Salable in stock.
	 * @param defense Amount of damage it will mitigate.
	*/

	/**
	 * The default contructor is needed to reconstruct from JSON
	 */
	public Armor()
	{
		super();
		this.defense = 0;
	}

	public Armor(String name, String description, double price, int quantity, int defense)
	{
		super(name, description, price, quantity);
		this.defense = defense;
	}
	
	public void setDefense(int defense)
	{
		this.defense = defense;
	}
	
	public int getDefense()
	{
		return this.defense;
	}
	
	/**
	 * Copy Constructor that copies Armors back and forth between the Shopping Cart and Inventory Manager
	 * @param original
	 */
	public Armor(Armor original)
	{
		super(original);
		this.defense = original.defense;
	}
	
	/**
	 * Overrides the Salable class's implementation to append the defense attribute to the end of any given toString.
	 */
	@Override
	public String toString()
	{
		 return super.toString() + String.format("\n\tDefense: %d", defense);
	}
}
