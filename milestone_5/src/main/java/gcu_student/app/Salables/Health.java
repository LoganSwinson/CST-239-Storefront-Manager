package gcu_student.app.Salables;

/**
 * The Health class is about consumable items that heal the user.
 */
public class Health extends Salable
{
	private int hpHeal;
	/**
	 * Non-Default constructor that calls the parent class to assign all of its attributes
	 * @param name Name of the object available for purchase.
	 * @param description Brief synopsis or background information on the item.
	 * @param price Cost of item USD.
	 * @param quantity Number of that particular Salable in stock.
	 * @param hpHeal Number of hit points that the user will heal.
	*/

	/**
	 * The default contructor is needed to reconstruct from JSON
	 */
	public Health()
	{
		super();
		this.hpHeal = 0;
	}

	public Health(String name, String description, double price, int quantity, int hpHeal)
	{
		super(name, description, price, quantity);
		this.hpHeal = hpHeal;
	}
	
	public void setHpHeal(int hpHeal)
	{
		this.hpHeal = hpHeal;
	}
	
	public int getHpHeal()
	{
		return this.hpHeal;
	}
	
	/**
	 * Copy Constructor that copies Health items back and forth between the Shopping Cart and Inventory Manager
	 * @param original
	 */
	public Health(Health original)
	{
		super(original);
		this.hpHeal = original.hpHeal;
	}
	
	/**
	 * Overrides the Salable class's implementation to append the healPoints attribute to the end of any given toString.
	 */
	@Override
	public String toString()
	{
		 return super.toString() + String.format("\n\tHP: %d", hpHeal);
	}
}
