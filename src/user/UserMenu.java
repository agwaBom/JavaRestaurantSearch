package user;

import java.util.Scanner;

import manager.Manageable;

public class UserMenu implements Manageable{
	public String foodName;
	public int quantity;
	public int price;
	
	public void read(Scanner scan, String temp) {
		foodName = temp;
		quantity = scan.nextInt();
	}

	public void read(Scanner scan) {
	}

	public boolean search(String kwd) {
		return false;
	}
}
