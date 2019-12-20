package JavaUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Analysis extends JPanel{
	Analysis(Main main){
		super(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		
		JLabel amountOfOrderByGender = new JLabel("<html>성별간 주문횟수 비교" + main.restMain.genderStatistic() + "<br/></html>");
		JLabel amountOfOrderByAge = new JLabel("<html>나이별 주문횟수 비교 :<br/>10대 : " + main.restMain.ageStatistic(0)
												+ "<br/>20대 : " + main.restMain.ageStatistic(1)
												+ "<br/>30대 : " + main.restMain.ageStatistic(2)
												+ "<br/>40대 : " + main.restMain.ageStatistic(3)
												+ "<br/>50대 이상 : " + main.restMain.ageStatistic(4) + "</html>");
		JLabel totalOrderedCost = new JLabel("<html></br>모든 유저의 총 주문 금액 : " + main.restMain.totalPrice() + "</html>");
		//topPanel에 세가지의 JLabel을 borderLayout형태로 넣는다
		topPanel.add(amountOfOrderByGender, BorderLayout.NORTH);
		topPanel.add(amountOfOrderByAge, BorderLayout.CENTER);		
		topPanel.add(totalOrderedCost, BorderLayout.SOUTH);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		
		JButton exit = new JButton("나가기");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		bottomPanel.add(exit, BorderLayout.CENTER);
		//클래스 패넣에 topPanel과 bottomPanel을 넣는다.
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
