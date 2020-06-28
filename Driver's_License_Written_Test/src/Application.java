import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Application {

	private static ProblemBank bank;
	private static int currentProblemNum;
	private static boolean bShowHint;

	private JFrame frmLicenseTest;

	private JPanel panelTest;
	private JPanel panelAnswers;
	private JPanel panelRadioBtn;

	private JScrollPane scrollPaneProblem;
	private JScrollPane scrollPaneAnswers;
	private JScrollPane scrollPaneHint;

	private static JTextField textFieldPickCnt;
	private static String pickCntStr = "¼±ÅÃ È½¼ö : ";
	private static JTextField textFieldAnswerRate;
	private static String answerRateStr = "Á¤´ä·ü : ";

	private static JTextArea textAreaProblem;
	private static JTextArea textAreaHint;

	private static ArrayList<JRadioButton[]> rdBtnList;
	private JButton[] btnProblemNums;

	private JButton btnShowHint;
	private JButton btnSubmit;
	private JButton btnExit;
	private JButton btnPrev;
	private JButton btnNext;

	private static JRadioButton rdBtnA;
	private static JRadioButton rdBtnB;
	private static JRadioButton rdBtnC;
	private static JRadioButton rdBtnD;
	private JLabel labelMain;
	private JLabel lblLicense;

	public static void initProblem() {
		currentProblemNum = 0;
		bShowHint = false;

//		try {
//			bank.initUserData();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

		for (JRadioButton[] jRadioButtons : rdBtnList) {
			for (int i = 0; i < jRadioButtons.length; i++) {
				jRadioButtons[i].setSelected(false);
			}

		}

		setProblem();
	}

	public static void setProblem() {
		Problem p = bank.getProblem(currentProblemNum);

		textAreaProblem.setText(p.problem);
		textAreaHint.setText("");
		// textAreaHint.setText(p.hint);
		bShowHint = false;

		textFieldPickCnt.setText(pickCntStr + p.pickCnt);

		double rate = 0;
		if (p.checkCnt != 0)
			rate = ((double) p.answerCnt / (double) p.checkCnt) * 100;

		textFieldAnswerRate.setText(answerRateStr + rate + "%");

		JRadioButton[] rdBtns = rdBtnList.get(currentProblemNum);

		rdBtnA.setSelected(rdBtns[0].isSelected());
		rdBtnB.setSelected(rdBtns[1].isSelected());
		rdBtnC.setSelected(rdBtns[2].isSelected());
		rdBtnD.setSelected(rdBtns[3].isSelected());

		rdBtnA.setText(p.selections[0]);
		rdBtnB.setText(p.selections[1]);
		rdBtnC.setText(p.selections[2]);
		rdBtnD.setText(p.selections[3]);

	}

	public static void showHint() {
		Problem p = bank.getProblem(currentProblemNum);

		textAreaHint.setText(p.hint);
	}

	public static void hideHint() {
		textAreaHint.setText("");
	}

	public static void checkUserSelect(int problemNum, int selectNum, boolean isListSelected) {
		// ¸®½ºÆ®¿¡¼­ Å¬¸¯Çß´ÂÁö ¾Æ´Ï¸é ÇöÀç È­¸é¿¡¼­ Å¬¸¯Çß´ÂÁö ±¸ºÐ

		Problem p = bank.getProblem(problemNum);
		int maxUserSelectCnt = p.answer.size();
		int currentUserSelectCnt = 0;
		JRadioButton[] rdBtns = rdBtnList.get(problemNum);
		ArrayList<String> userSelect = new ArrayList<String>();

		// ÇöÀç È­¸é¿¡¼­ Å¬¸¯
		if (isListSelected == false) {
			if (rdBtnA.isSelected()) {
				userSelect.add("a");
				currentUserSelectCnt++;
			}
			if (rdBtnB.isSelected()) {
				userSelect.add("b");
				currentUserSelectCnt++;
			}
			if (rdBtnC.isSelected()) {
				userSelect.add("c");
				currentUserSelectCnt++;
			}
			if (rdBtnD.isSelected()) {
				userSelect.add("d");
				currentUserSelectCnt++;
			}

			if (maxUserSelectCnt == 1) {
				rdBtnA.setSelected(0 == selectNum);
				rdBtnB.setSelected(1 == selectNum);
				rdBtnC.setSelected(2 == selectNum);
				rdBtnD.setSelected(3 == selectNum);

				for (int i = 0; i < rdBtns.length; i++)
					rdBtns[i].setSelected(i == selectNum);

				userSelect.clear();

				switch (selectNum) {
				case 0:
					userSelect.add("a");
					break;
				case 1:
					userSelect.add("b");
					break;
				case 2:
					userSelect.add("c");
					break;
				case 3:
					userSelect.add("d");
					break;
				}

				bank.updateProblem(problemNum, userSelect);
			} else {
				if (maxUserSelectCnt == currentUserSelectCnt)
					bank.updateProblem(problemNum, userSelect);
				else if (maxUserSelectCnt < currentUserSelectCnt) {
					rdBtnA.setSelected(rdBtnA.isSelected() && 0 != selectNum);
					rdBtnB.setSelected(rdBtnB.isSelected() && 1 != selectNum);
					rdBtnC.setSelected(rdBtnC.isSelected() && 2 != selectNum);
					rdBtnD.setSelected(rdBtnD.isSelected() && 3 != selectNum);
				}

				rdBtns[0].setSelected(rdBtnA.isSelected());
				rdBtns[1].setSelected(rdBtnB.isSelected());
				rdBtns[2].setSelected(rdBtnC.isSelected());
				rdBtns[3].setSelected(rdBtnD.isSelected());
			}
		}
		// ¸®½ºÆ®¿¡¼­ Å¬¸¯
		else {
			if (rdBtns[0].isSelected()) {
				userSelect.add("a");
				currentUserSelectCnt++;
			}
			if (rdBtns[1].isSelected()) {
				userSelect.add("b");
				currentUserSelectCnt++;
			}
			if (rdBtns[2].isSelected()) {
				userSelect.add("c");
				currentUserSelectCnt++;
			}
			if (rdBtns[3].isSelected()) {
				userSelect.add("d");
				currentUserSelectCnt++;
			}

			if (maxUserSelectCnt == 1) {
				for (int i = 0; i < rdBtns.length; i++)
					rdBtns[i].setSelected(i == selectNum);

				if (problemNum == currentProblemNum) {
					rdBtnA.setSelected(0 == selectNum);
					rdBtnB.setSelected(1 == selectNum);
					rdBtnC.setSelected(2 == selectNum);
					rdBtnD.setSelected(3 == selectNum);
				}
				userSelect.clear();

				switch (selectNum) {
				case 0:
					userSelect.add("a");
					break;
				case 1:
					userSelect.add("b");
					break;
				case 2:
					userSelect.add("c");
					break;
				case 3:
					userSelect.add("d");
					break;
				}

				bank.updateProblem(problemNum, userSelect);
			} else {
				if (maxUserSelectCnt == currentUserSelectCnt)
					bank.updateProblem(problemNum, userSelect);
				else if (maxUserSelectCnt < currentUserSelectCnt) {
					rdBtns[0].setSelected(rdBtnA.isSelected() && 0 != selectNum);
					rdBtns[1].setSelected(rdBtnB.isSelected() && 1 != selectNum);
					rdBtns[2].setSelected(rdBtnC.isSelected() && 2 != selectNum);
					rdBtns[3].setSelected(rdBtnD.isSelected() && 3 != selectNum);
				}

				if (problemNum == currentProblemNum) {
					rdBtnA.setSelected(rdBtns[0].isSelected());
					rdBtnB.setSelected(rdBtns[1].isSelected());
					rdBtnC.setSelected(rdBtns[2].isSelected());
					rdBtnD.setSelected(rdBtns[3].isSelected());
				}
			}
		}

	}

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		bank = new ProblemBank();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmLicenseTest.setVisible(true);

					// initProblem();

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

		JPanel panelMain = new JPanel();
		panelMain.setBackground(UIManager.getColor("Button.background"));
		panelMain.setBounds(0, 0, 1274, 691);
		frmLicenseTest.getContentPane().add(panelMain);
		panelMain.setLayout(null);

		labelMain = new JLabel("");
		labelMain.setBounds(81, 34, 617, 625);
		panelMain.add(labelMain);

		BufferedImage img = null;
		try {
			img = ImageIO.read(
					new File("D:\\git\\Driver-s_License_Written_Test\\Driver's_License_Written_Test\\image\\main.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg = img.getScaledInstance(labelMain.getWidth(), labelMain.getHeight(), Image.SCALE_SMOOTH);
		labelMain.setIcon(new ImageIcon(dimg));

		lblLicense = new JLabel("");
		lblLicense.setBounds(799, 97, 405, 250);
		panelMain.add(lblLicense);

		img = null;
		try {
			img = ImageIO.read(
					new File("D:\\git\\Driver-s_License_Written_Test\\Driver's_License_Written_Test\\image\\icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		dimg = img.getScaledInstance(lblLicense.getWidth(), lblLicense.getHeight(), Image.SCALE_SMOOTH);
		lblLicense.setIcon(new ImageIcon(dimg));

		JButton btnStart = new JButton("\uBB38\uC81C \uD480\uAE30");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bank.pickProblems();
				initProblem();

				panelMain.setVisible(false);
				panelTest.setVisible(true);
			}
		});
		btnStart.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		btnStart.setBounds(958, 526, 246, 40);
		panelMain.add(btnStart);

		JButton btnUserDataInit = new JButton("\uC0AC\uC6A9\uC790 \uB370\uC774\uD130 \uCD08\uAE30\uD654");
		btnUserDataInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bank.initUserData();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUserDataInit.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		btnUserDataInit.setBounds(958, 586, 246, 40);
		panelMain.add(btnUserDataInit);

		JTextArea textArea = new JTextArea();
		textArea.setText(
				" \uC6B4\uC804\uBA74\uD5C8 \uC2DC\uD5D8 \uD544\uAE30 App\r\n\r\n \uAC1C\uBC1C \uAE30\uAC04 : 2020.06.24(\uC218) ~ 06.28(\uC77C)\r\n \uAC1C\uBC1C \uC5B8\uC5B4 : Java (Swing)\r\n github.com/kthss01/Driver-s_License_Written_Test\r\n https://youtu.be/PsoAaB3XQVM");
		textArea.setFont(new Font("±¼¸²Ã¼", Font.PLAIN, 18));
		textArea.setEditable(false);
		textArea.setBounds(735, 367, 469, 139);
		panelMain.add(textArea);

		panelTest = new JPanel();
		panelTest.setBounds(0, 0, 1274, 691);
		frmLicenseTest.getContentPane().add(panelTest);
		panelTest.setLayout(null);

		panelTest.setVisible(false);

		scrollPaneProblem = new JScrollPane();
		scrollPaneProblem.setBounds(48, 33, 846, 133);
		panelTest.add(scrollPaneProblem);

		textAreaProblem = new JTextArea();
		textAreaProblem.setFont(new Font("±¼¸²Ã¼", Font.PLAIN, 30));
		textAreaProblem.setLineWrap(true);
		textAreaProblem.setEditable(false);
		scrollPaneProblem.setViewportView(textAreaProblem);

		scrollPaneAnswers = new JScrollPane();
		scrollPaneAnswers.setBounds(939, 33, 301, 536);
		panelTest.add(scrollPaneAnswers);

		panelAnswers = new JPanel();
		panelAnswers.setBackground(Color.WHITE);
		scrollPaneAnswers.setViewportView(panelAnswers);
		panelAnswers.setLayout(new GridLayout(40, 5, 0, 0));

		scrollPaneHint = new JScrollPane();
		scrollPaneHint.setBounds(48, 501, 846, 115);
		panelTest.add(scrollPaneHint);

		btnShowHint = new JButton("\uD574\uC124 \uBCF4\uAE30");
		btnShowHint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (bShowHint = !bShowHint)
					showHint();
				else
					hideHint();
			}
		});
		btnShowHint.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 16));
		scrollPaneHint.setRowHeaderView(btnShowHint);

		textAreaHint = new JTextArea();
		textAreaHint.setFont(new Font("±¼¸²Ã¼", Font.PLAIN, 18));
		textAreaHint.setEditable(false);
		textAreaHint.setLineWrap(true);
		scrollPaneHint.setViewportView(textAreaHint);

		btnSubmit = new JButton("\uC81C\uCD9C");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultModal m = new ResultModal(frmLicenseTest, bank);
				m.setVisible(true);
			}
		});
		btnSubmit.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btnSubmit.setBounds(951, 587, 127, 52);
		panelTest.add(btnSubmit);

		btnExit = new JButton("\uCC98\uC74C\uC73C\uB85C");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTest.setVisible(false);
				panelMain.setVisible(true);
			}
		});
		btnExit.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btnExit.setBounds(1100, 587, 127, 52);
		panelTest.add(btnExit);

		textFieldPickCnt = new JTextField();
		textFieldPickCnt.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		textFieldPickCnt.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPickCnt.setBackground(SystemColor.info);
		textFieldPickCnt
				.setToolTipText("\uBB38\uC81C \uC740\uD589\uC5D0\uC11C 40\uBB38\uC81C\uB85C \uBF51\uD78C \uD69F\uC218");
		textFieldPickCnt.setEditable(false);
		textFieldPickCnt.setBounds(58, 176, 116, 34);
		panelTest.add(textFieldPickCnt);
		textFieldPickCnt.setColumns(10);

		textFieldAnswerRate = new JTextField();
		textFieldAnswerRate.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		textFieldAnswerRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnswerRate.setBackground(new Color(152, 251, 152));
		textFieldAnswerRate
				.setToolTipText("\uC81C\uCD9C\uD588\uC744 \uB54C \uC815\uB2F5\uB960 (\uC815\uB2F5 / \uC81C\uCD9C)");
		textFieldAnswerRate.setEditable(false);
		textFieldAnswerRate.setBounds(186, 176, 116, 34);
		panelTest.add(textFieldAnswerRate);
		textFieldAnswerRate.setColumns(10);

		panelRadioBtn = new JPanel();
		panelRadioBtn.setBounds(58, 224, 822, 204);
		panelTest.add(panelRadioBtn);
		panelRadioBtn.setLayout(new GridLayout(4, 0, 0, 0));

		rdBtnA = new JRadioButton("New radio button");
		rdBtnA.setBackground(UIManager.getColor("Button.background"));
		rdBtnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUserSelect(currentProblemNum, 0, false);
			}
		});
		rdBtnA.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		panelRadioBtn.add(rdBtnA);

		rdBtnB = new JRadioButton("New radio button");
		rdBtnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUserSelect(currentProblemNum, 1, false);
			}
		});
		rdBtnB.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		panelRadioBtn.add(rdBtnB);

		rdBtnC = new JRadioButton("New radio button");
		rdBtnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUserSelect(currentProblemNum, 2, false);
			}
		});
		rdBtnC.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		panelRadioBtn.add(rdBtnC);

		rdBtnD = new JRadioButton("New radio button");
		rdBtnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUserSelect(currentProblemNum, 3, false);
			}
		});
		rdBtnD.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 18));
		panelRadioBtn.add(rdBtnD);

		btnPrev = new JButton("ÀÌÀü ¹®Á¦");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentProblemNum--;
				if (currentProblemNum < 0)
					currentProblemNum = 0;
				setProblem();
			}
		});
		btnPrev.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btnPrev.setBounds(668, 451, 100, 30);
		panelTest.add(btnPrev);

		btnNext = new JButton("´ÙÀ½ ¹®Á¦");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentProblemNum++;
				if (currentProblemNum >= 40)
					currentProblemNum = 39;
				setProblem();
			}
		});
		btnNext.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btnNext.setBounds(780, 451, 100, 30);
		panelTest.add(btnNext);

		JButton btnCheat = new JButton("\uCE58\uD2B8");
		btnCheat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 40; i++) {
					Problem p = bank.getProblem(i);
					for (int j = 0; j < p.answer.size(); j++) {
						switch (p.answer.get(j)) {
						case "a":
							rdBtnList.get(i)[0].setBackground(Color.RED);
							break;
						case "b":
							rdBtnList.get(i)[1].setBackground(Color.RED);
							break;
						case "c":
							rdBtnList.get(i)[2].setBackground(Color.RED);
							break;
						case "d":
							rdBtnList.get(i)[3].setBackground(Color.RED);
							break;
						}
					}
				}
			}
		});
		btnCheat.setFont(new Font("±¼¸²", Font.BOLD, 14));
		btnCheat.setBounds(939, 10, 75, 23);
		panelTest.add(btnCheat);

		JButton btnCheatCancel = new JButton("\uD574\uC81C");
		btnCheatCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < 40; i++) {
					for (int j = 0; j < 4; j++) {
						rdBtnList.get(i)[j].setBackground(new Color(240, 240, 240));
					}
				}
			}
		});
		btnCheatCancel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		btnCheatCancel.setBounds(1018, 10, 75, 23);
		panelTest.add(btnCheatCancel);

		rdBtnList = new ArrayList<JRadioButton[]>();
		btnProblemNums = new JButton[40];

		for (int i = 0; i < 40; i++) {
			btnProblemNums[i] = new JButton("" + (i + 1));
			btnProblemNums[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton) e.getSource();
					currentProblemNum = Integer.valueOf(b.getText()) - 1;
					setProblem();
				}
			});

			panelAnswers.add(btnProblemNums[i]);
			JRadioButton[] rdBtns = new JRadioButton[4];
			rdBtns[0] = new JRadioButton("a");
			rdBtns[0].setActionCommand("" + i);
			rdBtns[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton rb = (JRadioButton) e.getSource();
					checkUserSelect(Integer.valueOf(rb.getActionCommand()), 0, true);
				}
			});

			rdBtns[1] = new JRadioButton("b");
			rdBtns[1].setActionCommand("" + i);
			rdBtns[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton rb = (JRadioButton) e.getSource();
					checkUserSelect(Integer.valueOf(rb.getActionCommand()), 1, true);
				}
			});

			rdBtns[2] = new JRadioButton("c");
			rdBtns[2].setActionCommand("" + i);
			rdBtns[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton rb = (JRadioButton) e.getSource();
					checkUserSelect(Integer.valueOf(rb.getActionCommand()), 2, true);
				}
			});

			rdBtns[3] = new JRadioButton("d");
			rdBtns[3].setActionCommand("" + i);
			rdBtns[3].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton rb = (JRadioButton) e.getSource();
					checkUserSelect(Integer.valueOf(rb.getActionCommand()), 3, true);
				}
			});
			rdBtnList.add(rdBtns);

			panelAnswers.add(rdBtnList.get(i)[0]);
			panelAnswers.add(rdBtnList.get(i)[1]);
			panelAnswers.add(rdBtnList.get(i)[2]);
			panelAnswers.add(rdBtnList.get(i)[3]);
		}
	}
}
