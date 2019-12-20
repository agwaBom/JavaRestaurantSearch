package JavaUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import user.ShoppingCart;
import user.User;
import user.UserMenu;


public class ShoppingCartUI extends JPanel implements ActionListener {
	final String[] columnNames = {"가게이름", "음식이름", "현재수량", "주문"};
	DefaultTableModel tableModel;
	JTable searchTable;
	int tableRow;
	
	void update(Main main) {
		tableRow = tableModel.getRowCount();
		//로그인 유저가 존재할 경우에만 실행.
		if(main.loggedInUser != null) {
			tableRow = tableModel.getRowCount();
			for(int i = 0; i < tableRow; i++) {
				tableModel.removeRow(0);
			}
			//유저의 쇼핑카트 리스트를 찾아내어 해당 리스트에 부합하는 메뉴들을 출력.
			for(ShoppingCart sc : main.loggedInUser.shoppingList) {
				for(int i = 0; i < sc.userMenuList.size(); i++) {
					tableModel.addRow(sc.getTexts(i));				
				}
			}			
		}
		searchTable = new JTable(tableModel);
	}
	
	void getShoppingCart(Main main) {
		Boolean checked;
		//i번째 행을 계속 행의 끝까지 받아들임.
		for(int i = 0; i < searchTable.getRowCount(); i++) {
			//3번째 값이 주문 열임. 아무것도 선택이 안됐거나 선택이 됐던 적이 있지만, false로 바뀐경우 checked를 false로 설정.
			if(Boolean.valueOf(searchTable.getValueAt(i, 3) == null 
					|| Boolean.valueOf(searchTable.getValueAt(i, 3).toString()) == false)) {
				checked = false;
			} else {
				//선택이 된 경우 true로 받게됨. 
				checked = Boolean.valueOf(searchTable.getValueAt(i, 3).toString());
				//음식이름은 1번째 값이므로 받아냄
				String foodName = searchTable.getValueAt(i, 1).toString();
				//레스토랑이름은 0번째 값이므로 받아냄.
				String restaurantName = searchTable.getValueAt(i, 0).toString();
				//로그인된 유저의 쇼핑카드를 뒤져봄.
				for(ShoppingCart cart : main.loggedInUser.shoppingList) {
					if(cart.restaurantName.equals(restaurantName)) {
						//음식리스트를 받아서 음식이름이 일치하면 수량을 -1하고 수량이 0인 경우 리스트에서 지워버림.
						for(UserMenu menu : cart.userMenuList) {						
							if(menu.foodName.equals(foodName)) {
								menu.quantity--;
								cart.totalPrice -= menu.price;
								if(menu.quantity == 0)
									cart.userMenuList.remove(menu);
								break;
							}
						}
					}
				}
				//수정된 값의 음식을 출력.
				JOptionPane.showMessageDialog(null, foodName);
			}
		}
		update(main);
	}
	
	
	public ShoppingCartUI(Main main) {
		super(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		
		JLabel restaurantName = new JLabel("쇼핑카트", JLabel.CENTER);
		
		tableModel = new DefaultTableModel(columnNames, 0) {
			public Class<?> getColumnClass(int column){
				switch(column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		//로그인된 유저의 쇼핑카트리스트를 띄움.
		if(main.loggedInUser != null) {
			for(ShoppingCart sc : main.loggedInUser.shoppingList) {
				for(int i = 0; i < sc.userMenuList.size(); i++) {
					tableModel.addRow(sc.getTexts(i));				
				}
			}			
		}
		JScrollPane scroll = new JScrollPane(searchTable);
		
		searchTable = new JTable(tableModel);
		searchTable.setFillsViewportHeight(true);
		searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		searchTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		
		ListSelectionModel rowSM = searchTable.getSelectionModel();
		//rowSM.addListSelectionListener(this);
		
		scroll.setViewportView(searchTable);
		
		topPanel.add(restaurantName, BorderLayout.NORTH);
		topPanel.add(scroll, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
		JButton deleteShoppingCart = new JButton();
		deleteShoppingCart.setText("장바구니에서 삭제");
		deleteShoppingCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getShoppingCart(main);
			}
		});
		JButton printTotalPrice = new JButton();
		printTotalPrice.setText("주문!");
		printTotalPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//유저의 전체 주문된 음식의 가격을 출력함.
				JOptionPane.showMessageDialog(null, main.loggedInUser.userTotal() + "원");
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
		
		bottomPanel.add(deleteShoppingCart);
		bottomPanel.add(printTotalPrice);
		bottomPanel.add(exit);
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}