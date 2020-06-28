import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ResultModal extends JDialog {
	public ResultModal(Window parent, ProblemBank bank) {
		super(parent, "필기 시험 결과창", ModalityType.APPLICATION_MODAL);
		setSize(640, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		double score = bank.checkAnswer();

		JScrollPane scrollPaneAnswers = new JScrollPane();
		scrollPaneAnswers.setBounds(50, 100, 530, 310);
		add(scrollPaneAnswers);

		JPanel panelAnswers = new JPanel();
		scrollPaneAnswers.setViewportView(panelAnswers);
		panelAnswers.setLayout(new GridLayout(41, 4, 0, 0));

		JTextField textFieldAnswers = new JTextField(String.format("점수 %.2f", score));
		textFieldAnswers.setFont(new Font("굴림체", Font.BOLD, 20));
		textFieldAnswers.setBounds(55, 20, 120, 50);
		textFieldAnswers.setEditable(false);
		textFieldAnswers.setHorizontalAlignment(SwingConstants.CENTER);
		add(textFieldAnswers);

		JTextField textFieldTotalResult = new JTextField();
		if (score >= 60.0)
			textFieldTotalResult.setText("합격");
		else
			textFieldTotalResult.setText("불합격");
		textFieldTotalResult.setFont(new Font("굴림체", Font.BOLD, 30));
		textFieldTotalResult.setBounds(240, 20, 130, 50);
		textFieldTotalResult.setEditable(false);
		textFieldTotalResult.setHorizontalAlignment(SwingConstants.CENTER);
		add(textFieldTotalResult);
		
		JButton btnOK = new JButton("확인");
		btnOK.setFont(new Font("굴림체", Font.BOLD, 20));
		btnOK.setBounds(445, 20, 100, 50);
		btnOK.setHorizontalAlignment(SwingConstants.CENTER);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					bank.writeUserData();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		add(btnOK);
		
		Font font = new Font("굴림체", Font.BOLD, 20);

		JTextField[] textFieldHeaders = new JTextField[4];
		textFieldHeaders[0] = new JTextField("번호");
		textFieldHeaders[1] = new JTextField("");
		textFieldHeaders[2] = new JTextField("선택");
		textFieldHeaders[3] = new JTextField("정답");

		for (int i = 0; i < 4; i++) {
			textFieldHeaders[i].setFont(font);
			textFieldHeaders[i].setHorizontalAlignment(SwingConstants.CENTER);
			textFieldHeaders[i].setEditable(false);
			panelAnswers.add(textFieldHeaders[i]);
		}

		for (int i = 0; i < 40; i++) {
			Problem p = bank.getProblem(i);
			JTextField textFieldProblemNum = new JTextField(" " + (i + 1));
			textFieldProblemNum.setFont(font);
			textFieldProblemNum.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldProblemNum.setEditable(false);
			panelAnswers.add(textFieldProblemNum);

			JTextField textFieldResult = null;
			if (p.isCollect())
				textFieldResult = new JTextField("O");
			else
				textFieldResult = new JTextField("X");
			textFieldResult.setFont(font);
			textFieldResult.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldResult.setEditable(false);
			panelAnswers.add(textFieldResult);

			String userSelect = "";
			for (String string : p.userSelect) {
				userSelect += string + " ";
			}
			userSelect += "";
			JTextField textFieldUserSelect = new JTextField(userSelect);
			textFieldUserSelect.setFont(font);
			textFieldUserSelect.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldUserSelect.setEditable(false);
			panelAnswers.add(textFieldUserSelect);

			String answer = "";
			for (String string : p.answer) {
				answer += string + " ";
			}
			answer += "";
			JTextField textFieldAnswer = new JTextField(answer);
			textFieldAnswer.setFont(font);
			textFieldAnswer.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldAnswer.setEditable(false);
			panelAnswers.add(textFieldAnswer);
		}

	}
}
