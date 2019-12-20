package user;

import java.util.ArrayList;
import java.util.Scanner;

import manager.Manageable;

public class User implements Manageable{
	public ArrayList <ShoppingCart> shoppingList = new ArrayList<>();
	public String id;
	public String name;
	public int age;
	public String gender;
	String address;
	
	public void read(Scanner scan) {
		id = scan.next();
		name = scan.next();
		age = scan.nextInt();
		gender = scan.next();
		address = scan.next();
	}
	
	public boolean search(String kwd) {
		if(kwd.equals(name))
			return true;
		return false;
	}
	
	public String userTotal() {
		int total = 0;
		for(ShoppingCart cart : shoppingList)
			total += cart.totalPrice;
		return name + "님의 총 주문금액은 : " + total;
	}
}