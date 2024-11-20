package gcu_student.app.Salables;

/**
 * This class is used for creating any weapon (that can only harm virtual
 * people).
 */
public class Weapon extends Salable {
	private int attack;

	/**
	 * Non-Default constructor that calls the parent class to assign all of its
	 * attributes
	 * 
	 * @param name        Name of the object available for purchase.
	 * @param description Brief synopsis or background information on the item.
	 * @param price       Cost of item USD.
	 * @param quantity    Number of that particular Salable in stock.
	 * @param attack      Damage number that represents the average amount of damage
	 *                    a weapon will do.
	 */

	/**
	 * The default contructor is needed to reconstruct from JSON
	 */
	public Weapon() {
		super();
		this.attack = 0;
	}

	public Weapon(String name, String description, double price, int quantity, int attack) {
		super(name, description, price, quantity);
		this.attack = attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getAttack() {
		return this.attack;
	}

	/**
	 * Copy Constructor that copies Weapons back and forth between the Shopping Cart
	 * and Inventory Manager
	 * 
	 * @param original
	 */
	public Weapon(Weapon original) {
		super(original);
		this.attack = original.attack;
	}

	/**
	 * Overrides the Salable class's implementation to append the attack attribute
	 * to the end of any given toString.
	 */
	@Override
	public String toString() {
		return super.toString() + String.format("\n\tAttack: %d", attack);
	}
}
