import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class Application {

	private static ProblemBank bank;

	private JFrame frmLicenseTest;
	private JTextField textFieldPickCnt;
	private JTextField textFieldAnswerRate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmLicenseTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLicenseTest = new JFrame();
		frmLicenseTest.setTitle("Driver's License Written Test");
		frmLicenseTest.setResizable(false);
		frmLicenseTest.setBounds(100, 100, 1280, 720);
		frmLicenseTest.setLocationRelativeTo(null);
		frmLicenseTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLicenseTest.getContentPane().setLayout(null);

		JPanel panelTest = new JPanel();
		panelTest.setBounds(0, 0, 1274, 691);
		frmLicenseTest.getContentPane().add(panelTest);
		panelTest.setLayout(null);

		JScrollPane scrollPaneProblem = new JScrollPane();
		scrollPaneProblem.setBounds(48, 33, 846, 267);
		panelTest.add(scrollPaneProblem);

		JTextArea textAreaProblem = new JTextArea();
		textAreaProblem.setLineWrap(true);
		textAreaProblem.setEditable(false);
		scrollPaneProblem.setViewportView(textAreaProblem);

		JScrollPane scrollPaneAnswers = new JScrollPane();
		scrollPaneAnswers.setBounds(939, 33, 301, 536);
		panelTest.add(scrollPaneAnswers);

		JPanel panelAnswers = new JPanel();
		scrollPaneAnswers.setViewportView(panelAnswers);
		panelAnswers.setLayout(new GridLayout(40, 5, 0, 0));

		ArrayList<JRadioButton[]> rdBtnList = new ArrayList<JRadioButton[]>();

		for (int i = 0; i < 40; i++) {
			panelAnswers.add(new JButton("" + (i + 1)));
			JRadioButton[] rdBtns = new JRadioButton[4];
			rdBtns[0] = new JRadioButton("a");
			rdBtns[1] = new JRadioButton("b");
			rdBtns[2] = new JRadioButton("c");
			rdBtns[3] = new JRadioButton("d");
			rdBtnList.add(rdBtns);
			
			panelAnswers.add(rdBtnList.get(i)[0]);
			panelAnswers.add(rdBtnList.get(i)[1]);
			panelAnswers.add(rdBtnList.get(i)[2]);
			panelAnswers.add(rdBtnList.get(i)[3]);
		}

		JScrollPane scrollPaneHint = new JScrollPane();
		scrollPaneHint.setBounds(48, 533, 846, 115);
		panelTest.add(scrollPaneHint);

		JButton btnShowHint = new JButton("\uD574\uC124 \uBCF4\uAE30");
		scrollPaneHint.setRowHeaderView(btnShowHint);

		JTextArea textAreaHint = new JTextArea();
		textAreaHint.setEditable(false);
		textAreaHint.setLineWrap(true);
		scrollPaneHint.setViewportView(textAreaHint);

		JButton btnSubmit = new JButton("\uC81C\uCD9C");
		btnSubmit.setBounds(951, 587, 127, 52);
		panelTest.add(btnSubmit);

		JButton btnExit = new JButton("\uCC98\uC74C\uC73C\uB85C");
		btnExit.setBounds(1100, 587, 127, 52);
		panelTest.add(btnExit);

		textFieldPickCnt = new JTextField();
		textFieldPickCnt.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPickCnt.setBackground(SystemColor.info);
		textFieldPickCnt.setToolTipText("\uBB38\uC81C \uC740\uD589\uC5D0\uC11C 40\uBB38\uC81C\uB85C \uBF51\uD78C \uD69F\uC218");
		textFieldPickCnt.setText("\uBF51\uD78C \uD69F\uC218 : ");
		textFieldPickCnt.setEditable(false);
		textFieldPickCnt.setBounds(58, 317, 116, 34);
		panelTest.add(textFieldPickCnt);
		textFieldPickCnt.setColumns(10);

		textFieldAnswerRate = new JTextField();
		textFieldAnswerRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnswerRate.setBackground(new Color(152, 251, 152));
		textFieldAnswerRate.setToolTipText("\uC81C\uCD9C\uD588\uC744 \uB54C \uC815\uB2F5\uB960 (\uC815\uB2F5 / \uC81C\uCD9C)");
		textFieldAnswerRate.setText("\uC815\uB2F5\uB960 : ");
		textFieldAnswerRate.setEditable(false);
		textFieldAnswerRate.setBounds(186, 317, 116, 34);
		panelTest.add(textFieldAnswerRate);
		textFieldAnswerRate.setColumns(10);

		JPanel panelRadioBtn = new JPanel();
		panelRadioBtn.setBounds(58, 367, 822, 144);
		panelTest.add(panelRadioBtn);
		panelRadioBtn.setLayout(new GridLayout(4, 0, 0, 0));

		JRadioButton rdBtnA = new JRadioButton("New radio button");
		panelRadioBtn.add(rdBtnA);

		JRadioButton rdBtnB = new JRadioButton("New radio button");
		panelRadioBtn.add(rdBtnB);

		JRadioButton rdBtnC = new JRadioButton("New radio button");
		panelRadioBtn.add(rdBtnC);

		JRadioButton rdBtnD = new JRadioButton("New radio button");
		panelRadioBtn.add(rdBtnD);

		JButton btnPrev = new JButton("\uC774\uC804 \uBB38\uC81C");
		btnPrev.setBounds(659, 318, 100, 30);
		panelTest.add(btnPrev);

		JButton btnNext = new JButton("\uB2E4\uC74C \uBB38\uC81C");
		btnNext.setBounds(780, 318, 100, 30);
		panelTest.add(btnNext);
	}
}
