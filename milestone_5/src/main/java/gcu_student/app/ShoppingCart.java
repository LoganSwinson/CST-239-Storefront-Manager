package gcu_student.app;

import java.util.ArrayList;

import gcu_student.app.Salables.Salable;

/**
 * The Shopping Cart of a Customer, contains logic for adding items to cart, removing them, and checking out
 * @author Logan Swinson
 */
public class ShoppingCart
{
	private ArrayList<Salable> cart;
	
	/**
	 * Initializes an empty ArrayList for Salables
	 */
	public ShoppingCart()
	{
		this.cart = new ArrayList<Salable>();
	};
	
	
	/**
	 * This is the most used method from this class, allowing the Storefront to have access to the customer's cart
	 * @return The ArrayList with all of the Salables in the Cart
	 */
	public ArrayList<Salable> getCart()
	{
		return this.cart;
	}
	
	/**
	 * Empties all items from the cart, is used when purchasing all of the items in the cart
	 */
	public void emptyCart()
	{	
		// Removes the first element "n" times until the ArrayList contains no more products
		int cartSize = cart.size();
		for (int i = 0; i < cartSize; i++)
			cart.remove(0);
		
		System.out.println(" Congratulations on your purchase!");
	}

	/**
	 * Sets the cart to match the arguement ArrayList
	 * @param newCart The replacement cart
	 */
	public void setCart(ArrayList<Salable> newCart)
	{
		this.cart = newCart;
	}
}
