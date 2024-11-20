package gcu_student.app.Salables;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

// This tells Jackson to save a property called "Salable Type" in the Json, so that we can reconstruct the Salable into the correct type later
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "salableType")	

// All salable subtypes will be saved as the correct type
@JsonSubTypes
({
	@Type(value = Weapon.class, name = "Weapon"),
	@Type(value = Armor.class, name = "Armor"),
	@Type(value = Health.class, name = "Health")
})


/**
 * This class is the base class to all purchasable items in this program.
 */
public class Salable implements Comparable<Salable>
{
	private String name;
	private String description;
	private double price;
	private int quantity;
	// Every new salable will have a unique id
	private static int maxId = 0;
	private int id;
	
	/**
	 * The default contructor is needed to reconstruct from JSON
	 */
	public Salable()
	{
		this.name = "";
		this.description = "";
		this.price = 0.0;
		this.quantity = 0;
		this.id = -1;
	}
	
	/**
	 * A non-default constructor that sets the name, description, price, quantity, and id attributes of the Salable
	 * @param name Name of the object available for purchase.
	 * @param description Brief synopsis or background information on the item.
	 * @param price Cost of item in USD.
	 * @param quantity Number of that particular Salable in stock.
	 */
	public Salable(String name, String description, double price, int quantity)
	{
		// Define the attributes to the parameters
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.id = ++maxId;
	}
	
	/**
	 * Copy Constructor, it is the only way to create a new salable and not increment the idCount attribute
	 * @param original The salable being copied
	 */
	public Salable(Salable original)
	{
		this.name = original.name;
		this.description = original.description;
		this.price = original.price;
		this.quantity = original.quantity;
		this.id = original.id;
	}
	
	public static void setMaxId(int newMaxId)
	{
		maxId = newMaxId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public double getPrice()
	{
		return price;
	}

	public int getQuantity()
	{
		return quantity;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Overrides the toString method from the Object class to print out objects easier.
	 * return All attributes from the Salable class as a string.
	 */
	@Override
	public String toString()
	{
		// When printing, the name will be white & bold, while everything else will be white
		 return String.format("\u001B[1m%s\u001B[22m\n\tId: %d\n\tDescription: %s\n\tPrice: $%.2f\n\tQuantity: %d", this.name, this.id, this.description, this.price, this.quantity);
	}

	/**
	 * Returns whether the argument is later or earlier in the alphabet than the Salable that calls the method. Useful for sorting.
	 * @param salable Object that is passed by value for comparison
	 * @return position Alphabetical order of the Salable names. 1 = "this" is earlier, -1 = "salable" is earlier, 0 = tie/same
	 */
	@Override
	public int compareTo(Salable salable)
	{
		// Casts the Object to a Salable to call the getName() method
		int position = this.getName().compareToIgnoreCase(((Salable)salable).getName());
		
		return position;
	}
	
	/**
	 * Checks if the names of the items are the same, and is useful for the .contains() method from the ArrayList class & the "==" operator
	 * @param Object it is the other object of comparison, which will be another Salable
	 * @return True if the both objects share the same name, and false otherwise
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this.getName().compareToIgnoreCase( ((Salable)obj).getName() ) == 0)
			return true;
		else
			return false;
	}
}
