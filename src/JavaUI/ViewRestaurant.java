package JavaUI;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import store.Menu;
import store.Restaurant;
import user.ShoppingCart;
import user.UserMenu;


public class ViewRestaurant extends JPanel implements ActionListener {
	final String[] columnNames = {"음식이름", "가격", "주문"};
	DefaultTableModel tableModel;
	JTable searchTable;
	Restaurant updatedRestaurant;
	int tableRow;
	
	void updateSelection() {
		searchTable = new JTable(tableModel);		
	}
	//table 업데이트용 함수.
	void update() {
		tableRow = tableModel.getRowCount();
		for(int i = 0; i < tableRow; i++) {
			tableModel.removeRow(0);
		}
		for(Menu m : updatedRestaurant.menuList) {
			tableModel.addRow(m.getTexts());
		}
		updateSelection();
	}
	
	//쇼핑카트에 있는 체크된 메뉴를 가져옴.
	void getMenu(Main main, ArrayList<String> menuList) {
		boolean checked;
		for(int i = 0; i < searchTable.getRowCount(); i++) {
			if(Boolean.valueOf(searchTable.getValueAt(i, 2) == null) || Boolean.valueOf(searchTable.getValueAt(i, 2).toString()) == false) {		
				checked = false;
			} else {
				checked = Boolean.valueOf(searchTable.getValueAt(i, 2).toString());
				String dataColumn1 = searchTable.getValueAt(i, 0).toString();
				menuList.add(dataColumn1);
				JOptionPane.showMessageDialog(null, dataColumn1);
			}
		}
	}
	
	void addShoppingCartUI(Main main) {
		//테이블에서 받아온 값을 String 형식으로 저장함. 
		ArrayList <String> menuList = new ArrayList<>();
		Menu menuSelected;
		ShoppingCart cart;
		UserMenu um;
		//만약 레스토랑 명이 같은 것이 없다면.
		if(!main.restMain.checkDuplicateCart(main.loggedInUser, main.restaurantSelected.storeName)) {
			//테이블에 있는 체크된 메뉴를 가져온다.
			getMenu(main, menuList);
			//레스토랑을 입력할 카트를 생성.
			cart = new ShoppingCart();
			//카트의 레스토랑 이름을 메인에서 받아놓은 레스토랑 객체의 레스토랑 명을 가져옴.
			cart.restaurantName = main.restaurantSelected.storeName;
			//table에 있는 메뉴 값들을 for문으로 모든 table의 메뉴를 string으로 돌림. 
			for(String menuName : menuList) {
				//새로운 유저메뉴를 생성. 
				um = new UserMenu();
				//restaurantMain에서 해당 메뉴명과 같은 메뉴를 찾아서 menuSelected에 입력.
				menuSelected = main.restMain.findMenu(menuName, main.restaurantSelected);
				//만들어진 유저메뉴의 foodName을 선택된 메뉴의 foodName을 입력.
				um.foodName = menuSelected.foodName;
				//체크된 메뉴이므로 메뉴의 주문량을 ++함.
				um.quantity++;
				//카트에 메뉴를 추가
				cart.userMenuList.add(um);
				//레스토랑 명과 일치하는 가격정보를 가져옴.
				main.restMain.menuPrice(cart, cart.restaurantName);
				//가격정보와 그 수량을 곱하여 해당 메뉴의 총 가격을 산출.
				cart.totalPrice += um.price * um.quantity;
			}
			//유저의 쇼핑리스트에 쇼핑카트를 추가함.
			main.loggedInUser.shoppingList.add(cart);
			//메뉴리스트 초기화
			menuList.clear();
		} else {
			int tempQuantity = 0;
			getMenu(main, menuList);
			//중복된 레스토랑 이름이 있는 경우.
			for(String menuName : menuList) {
				//카트의 레스토랑 명과 같은 카트를 검색하여 해당 카트를 가져옴.
				cart = main.restMain.findCart(main.loggedInUser, main.restaurantSelected.storeName);
				//만약 유저가 레스토랑에 있는 메뉴를 이미 가지고 있다면.
				if(main.restMain.checkDuplicateUserMenu(cart, main.restMain.findMenu(menuName, main.restaurantSelected).foodName)) {
					//해당되는 유저메뉴를 찾아서
					um = main.restMain.findUserMenu(cart, main.restMain.findMenu(menuName, main.restaurantSelected).foodName);
					//수량을 늘림.
					um.quantity++;
					//카트의 총액을 위하기 위한 수량.
					tempQuantity++;
				} else {
					//레스토랑은 있지만 중복된 메뉴가 없는 경우 유저메뉴를 새로 생성 
					um = new UserMenu();
					//유저메뉴에 foodName을 찾아서 입력.
					um.foodName = main.restMain.findMenu(menuName, main.restaurantSelected).foodName;
					//해당되는 메뉴의 quantity를 1올림.
					um.quantity++;
					//유저 메뉴 리스트에서 카트를 추가.
					cart.userMenuList.add(um);
				}
				//메뉴의 가격 정보를 가져옴.
				main.restMain.menuPrice(cart, cart.restaurantName);
				//카트의 총액을 계산.
				cart.totalPrice += um.price * tempQuantity;
			}
			//초기화.
			tempQuantity = 0;
			menuList.clear();
		}
		//쇼핑카트 리스트를 업데이트 시킴.
		main.shoppingCart.update(main);
	}
	
	public ViewRestaurant(Main main) {
		super(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		JLabel restaurantName;
		restaurantName = new JLabel("please select menu", JLabel.CENTER);			
		topPanel.add(restaurantName, BorderLayout.NORTH);
		
		tableModel = new DefaultTableModel(columnNames, 0) {
			public Class<?> getColumnClass(int column){
				switch(column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		
		JScrollPane scroll = new JScrollPane(searchTable);
		
		updateSelection();
		searchTable.setFillsViewportHeight(true);
		searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		searchTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		
		scroll.setViewportView(searchTable);
		
		topPanel.add(scroll, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JButton addShoppingCart = new JButton();
		addShoppingCart.setText("장바구니에 추가");
		addShoppingCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addShoppingCartUI(main);
			}
		});
		JButton exit = new JButton();
		exit.setText("나가기");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		bottomPanel.add(addShoppingCart);
		bottomPanel.add(exit);
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
