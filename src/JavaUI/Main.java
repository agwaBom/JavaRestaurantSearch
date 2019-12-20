package JavaUI;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import store.Restaurant;
import store.RestaurantMain;
import user.User;


public class Main extends JFrame{
	//실제 코드가 동작할 메인클래스의 객체를 하나 생성.
	RestaurantMain restMain = new RestaurantMain();
	User loggedInUser; //로그인된 유저가 들어갈 곳.
	Restaurant restaurantSelected;	//레스토랑 리스트에서 선택할 시, 자동으로 저장됨.
	ShoppingCartUI shoppingCart = new ShoppingCartUI(this);
	
	Main(){
		setTitle("Restaurant Search!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		JTabbedPane pane = createTabbedPane();
		c.add(pane, BorderLayout.CENTER);
		setVisible(true);
		setSize(500, 400);
	}

	private JTabbedPane createTabbedPane() {
		//메인클래스에서 음식점과 유저 정보를 읽어들인다.
		restMain.readStore();
		restMain.readUser();
		
		//panel클래스들을 추가할 pane을 생성.
		JTabbedPane pane = new JTabbedPane();
		//현재 Swing main에 접근할 수 있도록 this를 준다.
		Login login = new Login(this);
		Search search = new Search(this); //selectRestaurant
		Analysis analysis = new Analysis(this);
		
		pane.add("Login", login);
		pane.add("ViewRestaurant", search.view);
		pane.add("Search", search);
		pane.add("ShoppingCart", shoppingCart);
		pane.add("Analysis", analysis);

		return pane;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}

}
