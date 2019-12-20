package JavaUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import store.Restaurant;
import store.RestaurantMain;

public class Search extends JPanel implements ActionListener, ListSelectionListener {
	final String[] columnNames = {"업체명", "카테고리", "오픈시간", "최소주문금액"};
	DefaultTableModel tableModel;
	ViewRestaurant view;
	JTable searchTable;
	int tableRow;
	
	void update(Main main, String searchInput) {
		//현재 테이블의 열의 개수를 가져온다.
		tableRow = tableModel.getRowCount();
		//테이블의 모든 열을 제거함.
		for(int i = 0; i < tableRow; i++) {
			tableModel.removeRow(0);
		}
		//만약 검색창에 아무 값도 없다면. 모든 레스토랑의 정보를 출력한다.
		if(searchInput.equals("")) {			
			for(Restaurant rest : main.restMain.restList) {
				tableModel.addRow(rest.printOnlyRestaurant());
				//model.addListSelectionListener(searchTable);
			}
		} else {
			//검색창에 들어온 input값에 따른 레스토랑 검색을 실시한다.
			for(Restaurant rest: main.restMain.restList) {
				if(rest.search(searchInput)) {
					//검색결과와 맞는 레스토랑만 출력.
					tableModel.addRow(rest.printOnlyRestaurant());
				}
			}
		}
		searchTable = new JTable(tableModel);
	}
	
	public Search(Main main) {
		super(new BorderLayout());
		view = new ViewRestaurant(main);
		
		JPanel topPanel = new JPanel(new BorderLayout());
		//restaurantMain클래스에 있는 recommandMenu()기능을 통해서 랜덤값을 가져옴.
		JLabel recommendation = new JLabel("오늘의 추천메뉴는..." + main.restMain.recommandMenu(), JLabel.CENTER);
		JTextField searchBox = new JTextField();	//검색창
		JButton searchButton = new JButton("검색");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//검색을 누를 시 검색창의 텍스트를 가져와서 업데이트를 실행. 새로운 테이블을 출력.
				String input = searchBox.getText();
				update(main, input);
			}
		});
		//테이블 모델 설정.
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
					return String.class;
				default:
					return String.class;
				}
			}
		};
		//초기엔 ""가 들어가므로 모든 레스토랑이 출력됨
		update(main, searchBox.getText());
		//스크롤 기능
		JScrollPane scroll = new JScrollPane(searchTable);
		
		searchTable.setFillsViewportHeight(true);
		searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		searchTable.setPreferredScrollableViewportSize(new Dimension(500, 200));				
		//리스트 셀렉션 모델을 통해 클릭으로 레스토랑의 정보를 가져온다. 
		ListSelectionModel model = searchTable.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!model.isSelectionEmpty()) {
//					int selectedRow = model.getMinSelectionIndex();
					int selectedRow = model.getMinSelectionIndex();
					//클릭된 레스토랑은 열의 값이랑 동일한 순서이므로 restaurantMain에서 해당 값에 속하는 레스토랑 객체를 가져온다. 
					Restaurant selected = main.restMain.restList.get(selectedRow);
					//UImain에 있는 restaurantSelected를 결정함.
					main.restaurantSelected = selected;
					//ViewRestaurant에 있는 레스토랑 메뉴를 업데이트한다.
					view.updatedRestaurant = selected;
					view.update();
				}
			}
		});
		
		scroll.setViewportView(searchTable);
			
		topPanel.add(recommendation, BorderLayout.NORTH);
		topPanel.add(searchBox, BorderLayout.CENTER);
		topPanel.add(searchButton, BorderLayout.EAST);	
		topPanel.add(scroll, BorderLayout.SOUTH);

		JPanel middlePanel = new JPanel(new GridLayout(1, 4));
		
		JButton korean = new JButton("한식");
		korean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//버튼에 따른 검색을 자동으로 실행하도록 함.
				update(main, "한식");
			}
		});
		
		JButton chinese = new JButton("중식");
		chinese.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				update(main, "중식");
			}
		});
		
		JButton japanese = new JButton("일식");
		japanese.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				update(main, "일식");
			}
		});

		JButton western = new JButton("양식");
		western.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				update(main, "양식");
			}
		});

		middlePanel.add(korean);
		middlePanel.add(chinese);
		middlePanel.add(japanese);
		middlePanel.add(western);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
		
		JButton exit = new JButton("나가기");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		bottomPanel.add(exit);
				
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
