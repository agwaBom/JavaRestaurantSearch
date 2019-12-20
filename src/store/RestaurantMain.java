package store;

import java.util.ArrayList;
import java.util.Scanner;

import manager.Manager;
import user.ShoppingCart;
import user.User;
import user.UserMenu;

public class RestaurantMain extends Manager {
	public ArrayList<Restaurant> restList = new ArrayList<>();
	public ArrayList<User> userList = new ArrayList<>();
	public ArrayList<ShoppingCart> cart = new ArrayList<>();
	//음식점의 txt파일을 읽어들여서 리스트에 넣음.
	public void readStore() {
		Scanner scanf = openFile("store.txt");
		int temp;
		int count = 0;
		while (scanf.hasNext()) {
			temp = scanf.nextInt();
			if (temp == 0) {
				Restaurant rest = new Restaurant();
				rest.read(scanf);
				restList.add(rest);
				count++;
			}
			if (temp == 1) {
				Menu menu = new Menu();
				menu.read(scanf);
				restList.get(count - 1).menuList.add(menu);
			}
		}
		count = 0;
	}
	//유저의 txt파일을 읽어들여서 리스트에 넣음
	public void readUser() {
		ShoppingCart cart;
		UserMenu um;
		String tempUserMenu;
		int temp;
		int count = 0;
		Scanner scanf = openFile("user.txt");
		while (scanf.hasNext()) {
			temp = scanf.nextInt();
			if (temp == 0) {
				User user = new User();
				user.read(scanf);
				userList.add(user);
				count++;
			}
			if (temp == 1) {
				cart = new ShoppingCart();
				cart.read(scanf);
				while (true) {
					um = new UserMenu();
					tempUserMenu = scanf.next();
					if (tempUserMenu.equals("end"))
						break;
					um.read(scanf, tempUserMenu);
					cart.userMenuList.add(um);
					menuPrice(cart, cart.restaurantName);
					cart.totalPrice += (um.price * um.quantity);
				}
				userList.get(count - 1).shoppingList.add(cart);
			}
		}
		count = 0;
	}

	/* 회원가입 메소드
	 * void addUser(Scanner scan) { User user = new User();
	 * System.out.print(">> 유저를 등록합니다. 유저 정보를 입력하세요. (아이디 이름 나이 성별(M/F) 주소)\n");
	 * user.read(scan); userList.add(user); }
	 */
	//레스토랑에 있는 메뉴 찾기.
	public Menu findMenu(String input, Restaurant rest) {
		for (Menu menu : rest.menuList) {
			if (menu.foodName.equals(input))
				return menu;
		}
		return null;
	}
	//유저의 쇼핑카트에서의 메뉴 찾기.
	public UserMenu findUserMenu(ShoppingCart cart, String userMenuInput) {
		for (UserMenu um : cart.userMenuList) {
			if (userMenuInput.equals(um.foodName))
				return um;
		}
		return null;
	}
	//중복된 메뉴가 있나 찾아봄
	public boolean checkDuplicateUserMenu(ShoppingCart cart, String userMenuInput) {
		for (UserMenu um : cart.userMenuList) {
			if (userMenuInput.equals(um.foodName))
				return true;
		}
		return false;
	}
	//카트에 같은 레스토랑 명이 있으면 해당 카트를 반환.
	public ShoppingCart findCart(User user, String restaurantNameInput) {
		for (ShoppingCart cart : user.shoppingList) {
			if (restaurantNameInput.equals(cart.restaurantName))
				return cart;
		}
		return null;
	}
	//카트에 같은 레스토랑명이 이미 있는지 검색
	public boolean checkDuplicateCart(User user, String restaurantNameInput) {
		for (ShoppingCart cart : user.shoppingList) {
			if (restaurantNameInput.equals(cart.restaurantName))
				return true;
		}
		return false;
	}

	//쇼핑카트에 레스토랑의 가격정보를 가져오는 메서드.
	public void menuPrice(ShoppingCart cart, String restName) {
		ArrayList<Menu> tempMenuList = new ArrayList<>();
		//레스토랑 리스트를 돌려봄.
		for (Restaurant r : restList) {
			//만약 레스토랑의 이름이 입력된 레스토랑 이름과 같다면
			if (r.storeName.equals(restName))
				// tempMenuList에서 레스토랑의 메뉴를 추가함. 
				for (Menu m : r.menuList)
					tempMenuList.add(m);
		}
		//유저의 카트에 있는 메뉴를 돌림.
		for (UserMenu um : cart.userMenuList) {
			//메뉴가 레스토랑의 메뉴와 같은 것이 있다면.
			for (Menu m : tempMenuList) {
				//일치하는 음식명을 찾는다면
				if (um.foodName.equals(m.foodName))
					//유저 메뉴의 가격에 레스토랑의 음식 가격을 입력함.
					um.price = m.price;
			}
		}
	}
	//메뉴추천
	public String recommandMenu() {
		double random = Math.random();
		int ran = (int) (random * restList.size()) - 1;
		Restaurant rRest = restList.get(ran);
		rRest.printOnlyRestaurant();
		return rRest.randomMenu();
	}
	//유저의 주문 총액을 계산.
	public String totalPrice() {
		int total = 0;
		for (User u : userList) {
			for (ShoppingCart sc : u.shoppingList) {
				total += sc.totalPrice;
			}
		}
		return total + "원";
	}
	//전체 유저의 주문량 총계를 나이별로 출력.
	public String ageStatistic(int age) {
		int[] ageCount = new int[5];
		for (int i = 0; i < 5; i++) {
			ageCount[i] = 0;
		}
		for (User u : userList) {
			switch (u.age / 10) {
			case 1:
				ageCount[0]++;
				break;
			case 2:
				ageCount[1]++;
				break;
			case 3:
				ageCount[2]++;
				break;
			case 4:
				ageCount[3]++;
				break;
			case 5:
			case 6:
			case 7:
			case 8:
				ageCount[4]++;
				break;
			}
		}
		return (age + 1) * 10 + "대 고객 주문량 : " + ageCount[age] + "회\n";
	}
	//성별당 주문량을 비교
	public String genderStatistic() {
		int Mcount = 0, Fcount = 0;
		for (User u : userList) {
			if (u.gender.equals("M"))
				Mcount++;
			else if (u.gender.contentEquals("F"))
				Fcount++;
		}
		return "현재 남성 고객은 " + Mcount + "번 주문, 여성 고객은 " + Fcount + "번 주문하였습니다.";
	}

}