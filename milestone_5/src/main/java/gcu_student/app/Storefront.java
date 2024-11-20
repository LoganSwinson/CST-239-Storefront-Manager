/*
 * Logan Swinson
 * Professor Sluiter
 * CST-239
 * 11/20/2024
 * Milestone 5
 */

package gcu_student.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gcu_student.app.ExceptionHandlers.CustomException;
import gcu_student.app.Salables.Armor;
import gcu_student.app.Salables.Health;
import gcu_student.app.Salables.Salable;
import gcu_student.app.Salables.Weapon;

/**
 * This is the Storefront, the place where users will browse and purchased
 * items.
 * 
 * @author Logan Swinson
 */
public class Storefront
{
	private static Scanner scanner;
	private InventoryManager inven;
	private ShoppingCart cart;

	// To make printing more fun, and readable ig
	static final public String reset = "\u001B[0m";
	static final public String green = "\u001B[32m";
	static final public String bold = "\u001B[1m";
	static final public String unBold = "\u001B[22m";

	/**
	 * Driver Code for the program. Initializes the items, and then begins the loop
	 * that the program takes place in
	 * 
	 * @param args Idk why this is here, like I get its for the console or
	 *             something, but how would I even call the main with args?
	 */
	public static void main(String[] args) {
		// Initializes the items in the shop either using the Json or stocking the
		// inventory if the Json is not found
		Storefront sf = new Storefront();
		// One of the only methods that exits the program when an exception occurs (IO,
		// Databind and StreamWrite)
		System.out.println();
		sf.init();

		// Starts out with user not picking an option
		int userInt;
		System.out.println("Welcome to the Battlefront Bargain, where we sell anything from spears to ambrosia.");

		// Start of the while loop that runs the shop until the user types "0"
		while (true) {
			// Only the Main Menu Loop will have Green Text, user input and other methods
			// will not
			System.out.println(reset + green
					+ "-------------------------------------------------------------------------------------");

			// All headers and numbers are bolded and the subsequent text is unbolded
			System.out.println(bold + "General Commands:" + unBold);
			System.out.println(bold + "\t0)" + unBold + " to Exit");
			System.out.println(bold + "\t1)" + unBold + " to Show Inventory");
			System.out.println(bold + "\t2)" + unBold + " to Sort");
			System.out.println(bold + "Admin Commands:" + unBold);
			System.out.println(bold + "\t3)" + unBold + " to Add Item to Inventory");
			System.out.println(bold + "\t4)" + unBold + " to Edit Item in Inventory");
			System.out.println(bold + "\t5)" + unBold + " to Delete Item in Inventory");
			System.out.println(bold + "Customer Commands:" + unBold);
			System.out.println(bold + "\t6)" + unBold + " to Show Cart");
			System.out.println(bold + "\t7)" + unBold + " to Add to Cart");
			System.out.println(bold + "\t8)" + unBold + " to Remove from Cart");
			System.out.println(bold + "\t9)" + unBold + " to Checkout");

			// Try catch loop to prevent errors when reading user input
			try {
				// Makes the user input white
				System.out.print(reset);
				userInt = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error in MAIN while loop");
				System.out.println(e.getStackTrace());
				userInt = -1;
			} finally {
				// If an exception occured, or if it did not, it will print a green bar and then
				// change the text color to white
				System.out.println(
						green + "-------------------------------------------------------------------------------------"
								+ reset);
			}

			// Selects the method corresponding to user Input, 0 does nothing and ends the
			// program
			switch (userInt) {
				case 0:
					// Closes the scanner object and the program
					scanner.close();
					System.out.println("Closing program.");
					System.out.print("\u001B[31m");
					System.out.println("https://www.youtube.com/watch?v=dQw4w9WgXcQ\n");
					System.out.print("\u001B[0m");
					System.exit(0);
					break;
				case 1:
					sf.printList(sf.inven.getInven());
					break;
				case 2:
					sf.sort();
					break;
				case 3:
					sf.addProduct();
					break;
				case 4:
					sf.editProduct();
					break;
				case 5:
					sf.deleteProduct();
					break;
				case 6:
					sf.printList(sf.cart.getCart());
					break;
				case 7:
					sf.addToCart();
					break;
				case 8:
					sf.removeFromCart();
					break;
				case 9:
					sf.checkout();
					break;
				default:
					System.out.println("Please type a valid option.");
			}

			// Updates the Json everytime the main menu resets, and will exit the program
			// upon an exception (IO, Databind and StreamWrite)
			// This is at the end of the loop to ensure that the Json will match the
			// Inventory and Cart at the beginning
			sf.updateJson();
		}
	}

	/**
	 * Default constructor initializes the InventoryManager and Cart for the
	 * ObjectMapper to work
	 */
	public Storefront() {
		this.inven = new InventoryManager();
		this.cart = new ShoppingCart();
	}

	/**
	 * Initializes the scanner, inventory, and cart and will exit the program if an
	 * exception occurs
	 */
	public void init() {
		// Used for both new and old files
		File file = new File("milestone_5\\src\\main\\java\\gcu_student\\app\\All Salables");
		ObjectMapper objMap = new ObjectMapper();
		scanner = new Scanner(System.in);

		// If file does not exist, populate the inventory and save it to the Json
		if (file.exists() == false)
		{
			// Creates the items to add to the inventory
			Weapon scythe = new Weapon("Scythe", "A weapon used to reap fields and souls.", 100.00, 4, 36);
			Weapon spear = new Weapon("Spear", "Created for hunting and adapted for war.", 30.00, 10, 65);
			Armor chainmail = new Armor("Chainmail", "Interconnected chains designed to protect from slashes.", 45.00, 5, 20);
			Armor fullBodyPlate = new Armor("Full Body Plate", "Offers great protection, but at the cost of mobility.", 149.99, 2, 75);
			Health medkit = new Health("Medkit", "Turns out that torniques, bandages, gauzes aren't really useful when the opponent is staring you down ", 70.00, 3, 2);
			Health ambrosia = new Health("Ambrosia", "The nectar of the gods, said to reverse ageing, cure all wounds and even bring the dead back to life.", 999.99, 1, 1000);
			
			// For loops adds the correct quantity to the items
			for (int i = 0; i < scythe.getQuantity(); i++)
				addProduct(scythe, getInven());
			for (int i = 0; i < spear.getQuantity(); i++)
				addProduct(spear, getInven());
			for (int i = 0; i < chainmail.getQuantity(); i++)
				addProduct(chainmail, getInven());
			for (int i = 0; i < fullBodyPlate.getQuantity(); i++)
				addProduct(fullBodyPlate, getInven());
			for (int i = 0; i < medkit.getQuantity(); i++)
				addProduct(medkit, getInven());
			for (int i = 0; i < ambrosia.getQuantity(); i++)
				addProduct(ambrosia, getInven());

			// Performs the creation of the file
			this.updateJson();

			System.out.println("File Created");
		} else
		{
			// The File exists, so it will copy Salables from Json file to inven and cart
			try
			{
				// Cannot directly reassign "this" object, so a temporary copy of the correct
				// data is made and then transferred
				Storefront readFromFile = new Storefront();
				readFromFile = objMap.readValue(file, Storefront.class);
				this.setInven(readFromFile.getInven());
				this.setCart(readFromFile.getCart());
				
				// Iterate through the saved items and set the maxId accordingly
				int maxId = 0;
				ArrayList<Salable> list = this.getCart();
				list.addAll(this.getInven());
				for (int i = 0; i < list.size(); i++)
					maxId = (list.get(i).getId() > maxId) ? list.get(i).getId() : maxId; 
				Salable.setMaxId(maxId);
			} catch (DatabindException e) {
				CustomException ce = new CustomException(e, "Databind Error in STOREFRONT INIT");
				ce.getMessage();
				ce.printStackTrace();
				System.exit(-1);
			} catch (StreamWriteException e) {
				CustomException ce = new CustomException(e, "Stream Write Error in STOREFRONT INIT\"");
				ce.getMessage();
				ce.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				CustomException ce = new CustomException(e, "IO Error in STOREFRONT INIT");
				ce.getMessage();
				ce.printStackTrace();
				System.exit(-1);
			}

			System.out.println("File Was Read");
		}
	}

	/**
	 * Saves the inven and cart to the Json, overwriting what was there, exits
	 * program upon catching an exception.
	 */
	public void updateJson() {
		ObjectMapper objMap = new ObjectMapper();
		try
		{
			/* objMap.writeValue(new File("src\\All_Salables.json"), this); */
			objMap.writeValue(new File("milestone_5\\src\\main\\java\\gcu_student\\app\\All Salables"), this);
		}
		catch (DatabindException e) {

			CustomException ce = new CustomException(e, "Databind Error in STOREFRONT - UPDATE JSON");
			ce.getMessage();
			ce.printStackTrace();
			System.exit(-1);
		} catch (StreamWriteException e) {
			CustomException ce = new CustomException(e, "Stream Write Error in STOREFRONT - UPDATE JSON");
			ce.getMessage();
			ce.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			CustomException ce = new CustomException(e, "IO Error in STOREFRONT - UPDATE JSON");
			ce.getMessage();
			ce.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Prints out all of the salables from an ArrayList
	 * 
	 * @param list This will be either the Inventory or Shopping Cart's ArrayList of
	 *             Salables
	 */
	public void printList(ArrayList<Salable> list) {
		// Leaves the method early if there are no items
		if (list.size() == 0)
			System.out.println("No items found");
		else
		{
			for (int i = 0; i < list.size(); i++)
			{
				// The Number of the item in the list will be white and bold
				System.out.print(bold);
				System.out.printf("\n%d) ", i);
				System.out.println(list.get(i));
			}
			System.out.println();
		}
	}

	/**
	 * Adds a Salable to a specified ArrayList, or increases its quantity by 1
	 * 
	 * @param salable Item being added
	 * @param list    Inventory or Shopping Cart's ArrayList
	 */
	public void addProduct(Salable salable, ArrayList<Salable> list) {
		// If the salable is already in the list, increase its quantity
		// If not, add a copy to the list instead
		if (list.contains(salable)) {
			Salable sInList = list.get(list.indexOf(salable));
			sInList.setQuantity(sInList.getQuantity() + 1);
		} else {
			// Copies the item using a copy constructor
			Salable copySalable = new Salable(salable);
			copySalable.setQuantity(1);
			list.add(copySalable);
		}
	}

	/**
	 * Removes a salable from a list, or decreases quantity by 1
	 * 
	 * @param salable Item being removed
	 * @param list    Inventory or Shopping Cart's ArrayList
	 */
	public void deleteProduct(Salable salable, ArrayList<Salable> list) {
		if (list.contains(salable)) {
			Salable sInList = list.get(list.indexOf(salable));
			sInList.setQuantity(sInList.getQuantity() - 1);

			if (sInList.getQuantity() <= 0)
				list.remove(sInList);
		} else
			System.out.println("Salable not found in list, could not remove");
	}

	/**
	 * Admin chooses the properties (besides ID) to create a new item
	 */
	public void addProduct() {
		// Test input by copying and pasting: Weapon|Test Name|Item
		// Description|99.99|2|65
		System.out.println("With a \"|\" delimiter, type the: Salable Type, Name, Description, Price, Quantity, and Primary Stat");
		System.out.println("For example: Weapon|New Item Name|Useless description|420.69|8|1000");

		try {
			// Takes user input and splits it into a String Array
			String type, name, description;
			double price;
			int quantity, mysteryStat;
			String longString = scanner.nextLine();
			// This avoids using arrays and other non type safe data types
			ArrayList<String> fields= new ArrayList<String>(Arrays.asList(longString.split("\\|")));

			// Converts the inputs to the respective data types
			type = fields.get(0).toLowerCase();
			name = fields.get(1);
			description = fields.get(2);
			price = Double.parseDouble(fields.get(3));
			quantity = Integer.parseInt(fields.get(4));
			mysteryStat = Integer.parseInt(fields.get(5));

			// Create the Salable as the specific type to enable toString() to work properly
			switch (type) {
				case "weapon":
					Weapon newItem = new Weapon(name, description, price, quantity, mysteryStat);
					addProduct(newItem, inven.getInven());
					System.out.println("Item was created");
					break;
				case "armor":
					Armor newItem1 = new Armor(name, description, price, quantity, mysteryStat);
					addProduct(newItem1, inven.getInven());
					System.out.println("Item was created");
					break;
				case "health":
					Health newItem2 = new Health(name, description, price, quantity, mysteryStat);
					addProduct(newItem2, inven.getInven());
					System.out.println("Item was created");
					break;
				default:
					System.out.println("Invalid salable type. Item was not created");
			}
		} catch (Exception e) {
			System.out.println("Error in ADD PRODUCT");
			System.out.println(e.getStackTrace());
			return;
		}
	}

	/**
	 * Admin chooses an item and edits one of their properties
	 */
	public void editProduct() {
		if (inven.getInven().size() == 0)
			System.out.println("There are no items in the inventory to edit");

		else {
			System.out.println("\nSelect item to edit:");
			printList(inven.getInven());

			int userChoice = -1;

			try {
				userChoice = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error in EDIT PRODUCT outer try-catch");
				System.out.println(e.getStackTrace());
				return;
			}

			if (userChoice == 0)
				System.out.println("Canceling Edit");
			if (userChoice > 0 && userChoice <= inven.getInven().size()) {
				// Creates essentially a pointer to the Salable being edited
				Salable s = ((inven.getInven().get(userChoice - 1)));

				System.out.println();
				System.out.print(s);
				System.out.printf("\n\tID: %d", s.getId());
				System.out.println();
				System.out.println("\nType the change in this format: [Number 0-5]|[New value]");
				System.out.println("\t0: ID");
				System.out.println("\t1: Name");
				System.out.println("\t2: Description");
				System.out.println("\t3: Price");
				System.out.println("\t4: Quantity");
				System.out.println("\t5: Main Stat");
				System.out.println();

				String newValue = "";
				try {
					scanner.reset();
					ArrayList<String> fullInput = new ArrayList<String>(Arrays.asList(scanner.nextLine().split("\\|",2)));
					userChoice = Integer.parseInt(fullInput.get(0));
					newValue = fullInput.get(1);
				} catch (Exception e) {
					System.out.println("Error in EDIT PRODUCT inner try-catch");
					System.out.println(e.getStackTrace());
					return;
				}

				// Chooses which attribute is changed, uses a try-catch block for parsing to
				// other data types
				switch (userChoice)
				{
					case 0:
						try {
							s.setId(Integer.parseInt(newValue));
						} catch (NumberFormatException e) {
							System.out.println("The ID entered was not an Integer");
						}
						break;
					case 1:
						s.setName(newValue);
						break;
					case 2:
						s.setDescription(newValue);
						break;
					case 3:
						try {
							s.setPrice(Double.parseDouble(newValue));
						} catch (NumberFormatException e) {
							System.out.println("The Price entered was not a Double");
						}
						break;
					case 4:
						try {
							s.setQuantity(Integer.parseInt(newValue));
						} catch (NumberFormatException e) {
							System.out.println("The Quantity entered was not an Integer");
						}
						break;

					// Each type of salable has a corresponding unique stat to adjust
					
					case 5:
						try {
							if (s instanceof Weapon)
								((Weapon) s).setAttack(Integer.parseInt(newValue));
							else if (s instanceof Armor)
								((Armor) s).setDefense(Integer.parseInt(newValue));
							else
								((Health) s).setHpHeal(Integer.parseInt(newValue));
						} catch (NumberFormatException e) {
							System.out.println("The Stat entered was not an Integer");
						}
						break;
					default:
						System.out.println("Invalid Choice");
				}
			} else
				System.out.println("Improper input, canceling edit");
		}
	}

	/**
	 * Admin chooses an item to lower its quantity, if that quantity becomes 0, the
	 * item is removed from the Inventory
	 */
	public void deleteProduct() {
		if (inven.getInven().size() == 0)
			System.out.println("There are no items in the inventory to delete");

		else {
			System.out.println("\nSelect item to Delete:");
			printList(inven.getInven());

			int userChoice = -1;

			try {
				userChoice = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error in DELETE PRODUCT outer try-catch");
				System.out.println(e.getStackTrace());
				return;
			}

			if (userChoice == 0)
				System.out.println("Canceling Edit");
			if (userChoice > 0 && userChoice <= inven.getInven().size()) {
				// Decrements choice due to indexing
				userChoice--;
				// Creates essentially a pointer to the Salable being edited
				Salable s = ((inven.getInven().get(userChoice)));
				System.out.println("Choose how many to delete, the maximum is: " + s.getQuantity());

				try {
					userChoice = Integer.parseInt(scanner.nextLine());
				} catch (Exception e) {
					System.out.println("Error in DELETE PRODUCT inner try-catch");
					System.out.println(e.getStackTrace());
					return;
				}

				if (userChoice == 0)
					System.out.println(
							"*clap* *clap* *clap* Congratulations. You deleted nothing. Everyone thinks you're soooo smart.");

				else if (userChoice > 0 && userChoice <= s.getQuantity()) {
					for (int i = 0; i < userChoice; i++)
						deleteProduct(s, inven.getInven());

					System.out.println("Removed item(s) from Inventory");
				} else
					System.out.println("Invalid input, canceling deletion");
			} else
				System.out.println("Improper input, canceling deletion");
		}

	}

	/**
	 * Customer picks which Salable to add to Cart, and how many to add
	 */
	public void addToCart() {
		if (inven.getInven().size() == 0) {
			System.out.println("There are no items in the inventory add to cart");
		} else {
			// Variable to store input
			int userInput = 0;

			// Prints directions and then the inventory
			System.out.println("Please select the number of the item you want to buy, or \"0\" to exit.");
			printList(inven.getInven());

			// Takes user input and checks if there is a number in there
			try {
				userInput = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error in ADD TO CART salable choice");
				System.out.println(e.getStackTrace());
				return;
			}

			// If input is 0, skip the rest
			if (userInput == 0) {
			}

			// Runs if (0 < userInput <= Number of Items in Inventory)
			else if (userInput > 0 && userInput <= inven.getInven().size()) {
				// Creates pointer to original item (decreased choice by 1 b/c indexing)
				Salable chosenSalable = ((inven.getInven().get(userInput - 1)));
				int maxQuantity = chosenSalable.getQuantity();

				System.out.println("Type how many to buy. The maximum is: " + maxQuantity);
				try {
					userInput = Integer.parseInt(scanner.nextLine());
				} catch (Exception e) {
					System.out.println("Error in ADD TO CART quantity choice");
					System.out.println(e.getStackTrace());
					return;
				}

				if (userInput == 0)
					System.out.println("You can't buy 0 items");

				else if (userInput <= maxQuantity && userInput > 0) {
					for (int i = 0; i < userInput; i++) {
						addProduct(chosenSalable, cart.getCart());
						deleteProduct(chosenSalable, inven.getInven());
					}

					System.out.println("Added to Cart");
				}
			} else
				System.out.println("The input was invalid. Not adding to cart");
		}
	}

	/**
	 * Customer removes a specified number of a Salable from the Cart
	 */
	public void removeFromCart() {
		if (cart.getCart().size() == 0) {
			System.out.println("There are no items in the cart to remove");
		} else {
			// Variable to store input
			int userInput = 0;

			// Prints directions and then the inventory
			System.out.println(
					" Please select the number of the item you want to remove from the cart, or \"0\" to exit.");
			printList(cart.getCart());

			// Takes user input and checks if there is a number in there
			try {
				userInput = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error in REMOVE FROM CART salable choice");
				System.out.println(e.getStackTrace());
				return;
			}

			// If input is 0, skip the rest
			if (userInput == 0) {
			}

			// Runs if (0 < userInput <= Number of Items in Inventory)
			else if (userInput > 0 && userInput <= cart.getCart().size()) {
				// Creates pointer to original item (decreased choice by 1 b/c indexing)
				Salable chosenSalable = ((Salable)(cart.getCart().get(userInput - 1)));
				int maxQuantity = chosenSalable.getQuantity();

				System.out.println("Type how many to cancel. The maximum is: " + maxQuantity);
				try {
					userInput = Integer.parseInt(scanner.nextLine());
				} catch (Exception e) {
					System.out.println("Error in REMOVE FROM CART quantity choice");
					System.out.println(e.getStackTrace());
					return;
				}

				if (userInput == 0)
					System.out.println("You can't refund 0 items");
				else if (userInput <= maxQuantity && userInput > 0) {
					// Adds the item to the inventory before removing it, in case the item is
					// deleted by hitting a quantity of 0
					for (int i = 0; i < userInput; i++) {
						addProduct(chosenSalable, inven.getInven());
						deleteProduct(chosenSalable, cart.getCart());
					}

					System.out.println("Item(s) removed from Cart");
				}
			} else
				System.out.println("The input was invalid. No changes were made to the Cart");
		}
	}

	/**
	 * Customer can choose to purchase all items in the cart, or not
	 */
	public void checkout() {
		if (cart.getCart().size() == 0) {
			System.out.println("The cart is empty, cannot perform checkout");
		} else {
			System.out.println("\nThese are the items in the cart:");

			double totalPrice = 0.0;
			/* for (T s : cart.getCart()) {
				System.out.printf("\t%d %s(s)\n", s.getQuantity(), s.getName());
				totalPrice += s.getPrice() * s.getQuantity();
			} */

			for (int i = 0; i < getCart().size(); i++)
			{
				System.out.printf("\t%d %s(s)\n", getCart().get(i).getQuantity(), getCart().get(i).getName());
				totalPrice += getCart().get(i).getPrice() * getCart().get(i).getQuantity();
			}

			System.out.println();
			System.out.println("The total price is: $" + totalPrice);

			System.out.println("\nType \"1\" to purchase all items in the cart, and anything else to cancel.");

			int userPick = 0;
			try {
				userPick = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Exception in CHECKOUT");
				e.printStackTrace();
				return;
			}

			if (userPick == 1)
				cart.emptyCart();
			else
				System.out.println("Cancelling purchase of items in cart.");
		}
	}

	/**
	 * To allow the Jackson Library to get this attribute
	 * 
	 * @return The InventoryManager's ArrayList of Salables
	 */
	public ArrayList<Salable> getInven() {
		return this.inven.getInven();
	}

	/**
	 * To allow the Jackson Library to get this attribute
	 * 
	 * @return The ShopingCart's ArrayList of Salables
	 */
	public ArrayList<Salable> getCart() {
		return this.cart.getCart();
	}

	/**
	 * Replaces inven with the items from the given list
	 * 
	 * @param list ArrayList of Salables
	 */
	public void setInven(ArrayList<Salable> list) {
		this.inven.setInven(list);
	}

	/**
	 * Replaces cart with the items from the given list
	 * 
	 * @param list ArrayList of Salables
	 */
	public void setCart(ArrayList<Salable> list) {
		this.cart.setCart(list);
	}

	/**
	 * Main sorting decision making method
	 */
	public void sort()
	{
		System.out.println("Type 0 to sort the Inventory, and 1 for the Cart:");
		int userInput;
		// Takes user input and checks if there is a number in there
		try
		{
			userInput = Integer.parseInt(scanner.nextLine());
		} catch (Exception e)
		{
			System.out.println("Error in SORT pt 1");
			System.out.println(e.getStackTrace());
			return;
		}
		
		// This will be a shallow pointer to the corresponding list
		ArrayList<Salable> list = null;

		switch (userInput)
		{
			case 0:
				list = this.getInven();
				break;
			case 1:
				list = this.getCart();
				break;
			default:
				System.out.println("Invalid Choice. Canceling Sort");
				return;
		}

		if (list.size() == 0)
		{
			System.out.println("There were no salables to sort. So, Congratulations! It's already sorted");
			return;
		}

		System.out.println("Type 0 to sort by the Name & Price, or 1 for the ID:");

		try
		{
			userInput = Integer.parseInt(scanner.nextLine());
		}
		catch (Exception e)
		{
			System.out.println("Error in SORT pt 2");
			System.out.println(e.getStackTrace());
			return;
		}

		boolean isSortByID;

		switch (userInput) {
			case 0:
				isSortByID = false;
				break;
			case 1:
				isSortByID = true;
				break;
			default:
			System.out.println("Invalid Choice. Canceling Sort");
				return;
		}

		System.out.println("Type 0 to sort in ascending order, and 1 for descending");

		try
		{
			userInput = Integer.parseInt(scanner.nextLine());
		}
		catch (Exception e)
		{
			System.out.println("Error in SORT pt 3");
			System.out.println(e.getStackTrace());
			return;
		}

		boolean isDescending;

		switch (userInput)
		{
			case 0:
				isDescending = false;
				break;
			case 1:
				isDescending = true;
				break;
			default:
			System.out.println("Invalid Choice. Canceling Sort");
				return;
		}

		if (isSortByID)
			sortByID(list, isDescending);
		else
			sortByNameAndPrice(list, isDescending);

		System.out.println("The Salables were sorted.");
	}

	/**
	 * Sorts by name first and primarily, and then by price
	 * @param list
	 * @param isDescending true = descending, false = ascending
	 */
	public void sortByNameAndPrice(ArrayList<Salable> list, final boolean isDescending)
	{
		Comparator<Salable> salableComparator = new Comparator<Salable>()
		{
			@Override
			public int compare(Salable s1, Salable s2)
			{
				int position = s1.getName().compareTo(s2.getName());
				int sign = 1;

				if (isDescending)
					sign = -1;

				if (position == 0)
				{
					if (s1.getPrice() - s2.getPrice() > 0)
						return sign*1;
					else if (s1.getPrice() - s2.getPrice() < 0)
						return sign*-1;
					else
						return 0;
				}
				else
					return sign*position;
			}
		};

		Collections.sort(list, salableComparator);
	}

	/**
	 * Sorts by Id
	 * @param list
	 * @param isDescending true = descending, false = ascending
	 */
	public void sortByID(ArrayList<Salable> list, final boolean isDescending)
	{
		Comparator<Salable> salableComparator = new Comparator<Salable>()
		{
			@Override
			public int compare(Salable s1, Salable s2)
			{
				if (!isDescending)
					return s1.getId() - s2.getId();
				else
					return s2.getId() - s1.getId();
			}
		};

		Collections.sort(list, salableComparator);
	}
	
}
