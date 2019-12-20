package JavaUI;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import store.RestaurantMain;
import user.User;

public class Login extends JPanel implements ActionListener {
	
	public Login(Main main) {
		super(new BorderLayout());
		
		JPanel topPanel = new JPanel(new BorderLayout());
		
		JLabel loginText = new JLabel("Welcome to RestaurantSearch!", JLabel.CENTER);
		JLabel idTextBoxIdentifier = new JLabel("ID: ");
		//아이디의 값을 입력하는 필드
		JTextField idTextBox = new JTextField();
		
		topPanel.add(loginText, BorderLayout.PAGE_START);
		topPanel.add(idTextBoxIdentifier, BorderLayout.WEST);
		topPanel.add(idTextBox, BorderLayout.CENTER);
		
		//gridLayout 형태로 bottomPanel을 정렬
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		
		JButton loginButton = new JButton();
		loginButton.setText("로그인");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//로그인 버튼을 누르면 idTextBox에 있는 텍스트를 가져온다
				String input = idTextBox.getText();
				//모든 유저들의 리스트를 돌려서 id가 맞다면, popup 윈도우로 환영 창을 띄우고, 로그인 유저로 등록한다.
				for(User u : main.restMain.userList) {
					if(input.equals(u.id)) {
						JOptionPane.showMessageDialog(null, "" + u.id + "님 어서오세요");
						main.loggedInUser = u;
						//로그인 된 유저의 쇼핑카트를 업데이트함.
						main.shoppingCart.update(main);
					}				
				}
			}
		});
		
		JButton exitButton = new JButton();
		exitButton.setText("나가기");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		bottomPanel.add(loginButton);
		bottomPanel.add(exitButton);
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

