package user;

import java.util.ArrayList;
import java.util.Scanner;

import manager.Manageable;

public class ShoppingCart implements Manageable{
	public ArrayList <UserMenu> userMenuList = new ArrayList<> ();
	public String restaurantName;
	public int totalPrice = 0;
	
	public void read(Scanner scan) {
		restaurantName = scan.next();
	}	
	
	public String[] getTexts(int menu) {
		return new String[] {restaurantName, userMenuList.get(menu).foodName, ""+userMenuList.get(menu).quantity};
	}

	public boolean search(String kwd) {
		return false;
	}
}