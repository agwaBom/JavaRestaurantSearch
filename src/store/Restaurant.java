package store;

import java.util.ArrayList;
import java.util.Scanner;

import manager.Manageable;

public class Restaurant implements Manageable{
	public ArrayList<Menu> menuList = new ArrayList<>();
	public String storeName;
	String category;
	String openHour;
	String closeHour;
	int leastOrderPrice;
	
	public void read(Scanner scan) {
		storeName = scan.next();
		category = scan.next();
		openHour = scan.next();
		closeHour = scan.next();
		leastOrderPrice = scan.nextInt();
	}
	
	public boolean search(String kwd) {
//		if(storeName.equals(kwd) || category.equals(kwd))
//			return true;
		//StoreName의 경우 부분만 일치해도 true를 리턴함.
		if(storeName.indexOf(kwd) == 0 || category.equals(kwd))
			return true;
		if(kwd.matches("[0-9]+"))
			if((Integer.parseInt(kwd) >= leastOrderPrice - 5000) && (Integer.parseInt(kwd) <= leastOrderPrice + 5000)) 
				//검색어가 숫자일때 : 최소주문금액-5천원 <= 키워드 <= 최소주문금액 +5천원일때 true 값 리턴
				return true;
		return false;
	}
	//레스토랑의 테이블을 출력시키기 위한 메서드.
	public String[] printOnlyRestaurant() {
		System.out.printf("업체명: %s 카테고리: %s 오픈시간: %s ~ %s 최소주문금액: %d \n",
				storeName, category, openHour, closeHour, leastOrderPrice);
		return new String[] {storeName, category, openHour+"-"+closeHour, ""+leastOrderPrice};
	}
	//랜덤한 메뉴를 출력시킴. 메뉴 추천기능.
	public String randomMenu() {
		double random = Math.random();
		int ran = (int)(random * menuList.size());
		Menu randomMenu = menuList.get(ran);
		String output = storeName + " 가게의 " + randomMenu.foodName;
		return output;
	}
}
