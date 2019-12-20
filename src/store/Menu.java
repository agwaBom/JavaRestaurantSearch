package store;

import java.util.Scanner;

import manager.Manageable;

public class Menu implements Manageable{
	public String foodName;
	int price;
	
	public void read(Scanner scan) {
		foodName = scan.next();
		price = scan.nextInt();
	}
	
	public boolean search(String kwd) {
		int tempPrice;
		if(foodName.contains(kwd))      //if(kwd.equals(foodName)) : 키워드가 음식이름과 같을때 X -> 음식이름이 키워드를 포함할때로 변경 ex)"치"검색 : 치킨, 김치만두 등 다 검색됨 
			return true;
		if(kwd.contains("원")) {
			tempPrice = Integer.parseInt(kwd.substring(0, kwd.length() - 1));
			if(price >= tempPrice - 5000 && price <= tempPrice + 5000)
				return true;
		}
		return false;
	}
	//이걸로 테이블의 행에 들어갈 값들을 가져옴.
	public String[] getTexts() {
		return new String[] {foodName, ""+price};
	}
}