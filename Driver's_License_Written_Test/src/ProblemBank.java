import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Kay
 * @version 1.0
 * @apiNote ���� ����: ���� Ŭ������ ������ ������ ������ ������ �о�� ������ ���� �� �ִ�. ������ �����ϰ� ���� üũ�� �� ��
 *          ������ �� ������ ������� ���α׷� ����� ������ �� �ֵ��� �Ѵ�. ���� : �ش� ������ ����, ����, ��Ʈ, ������
 *          ������ ������ ����ڰ� ������ ���⿡ ���� ������ ������ �ִ�.
 * 
 */

class Problem {
	public String problem;
	public String[] selections;
	public String hint;
	public ArrayList<String> answer;
	public ArrayList<String> userSelect;

	public int pickCnt; // �������࿡�� 40������ ���õ� ����
	public int checkCnt; // ���� üũ�� �غ� ����
	public int answerCnt; // ����ڰ� �������� ���� ����

	Problem() {
		answer = new ArrayList<String>();
		userSelect = new ArrayList<String>();
	}

	public boolean isCollect() {
		for (int i = 0; i < answer.size(); i++) {
			// �������� ���������� ���� ��
			if (answer.size() > userSelect.size())
				return false;
			// �������� �������� �ٸ� �� (��� ���Ľ�Ŵ)
			for (int j = 0; j < answer.size(); j++) {
				if (!answer.get(j).equals(userSelect.get(j)))
					return false;
			}
		}

		return true;
	}
}

public class ProblemBank {

	static ArrayList<Problem> allProblems; // �о�� ��� ����
	static ArrayList<Problem> selectedProblems; // ��� ���� �� ���õ� 40����
	static String problemsPath = "problems.txt"; // �о�� ���� �ּ�
	static String ansPath = "ans.txt"; // �о�� ���� �ּ�
	static String userDataPath = "user.txt"; // ���õǾ��� �������� �����

	ProblemBank() throws IOException {
		load();
		pickProblems();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		writeUserData();
	}

	private static void load() throws IOException {
		allProblems = new ArrayList<Problem>();

		// ���� �о����
		// ���� ����
		/*
		 * 5 line ���� ����1 ����2 ����3 ����4 �ؼ�
		 */

		Problem p = null;

		BufferedReader br = new BufferedReader(new FileReader(problemsPath));
		int i = 0;
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;

			if (i == 0) {
				p = new Problem();
				p.selections = new String[4];
			}

			switch (i) {
			case 0:
				p.problem = line; // ����
				break;
			case 1:
			case 2:
			case 3:
			case 4:
				p.selections[i - 1] = line; // ����
				break;
			case 5:
				p.hint = line;
				break;
			}

			if (i == 5)
				allProblems.add(p); // ���� �߰�

			i = (i + 1) % 6; // 0 1 2 3 4 5�ݺ�
		}
		br.close();

		// ���� �о����
		br = new BufferedReader(new FileReader(ansPath));
		i = 0;
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;

			String[] lines = line.split(" ");
			Arrays.sort(lines);

			for (String string : lines) {
				allProblems.get(i).answer.add(string);
			}
			i++;
		}
		br.close();

		// userData �о����
		br = new BufferedReader(new FileReader(userDataPath));
		i = 0;
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;

			String[] lines = line.split(" ");

			allProblems.get(i).pickCnt = Integer.parseInt(lines[0]);
			allProblems.get(i).checkCnt = Integer.parseInt(lines[1]);
			allProblems.get(i).answerCnt = Integer.parseInt(lines[2]);
			i++;
		}
		br.close();
	}

	private void pickProblems() {
		// �о�� ��� ���� �� 40���� �����ϱ� - �����ϰ� �ϸ� ���� �� �켱�� �տ��� 40����
		selectedProblems = new ArrayList<Problem>();

		int cnt = 40; // 40����
		for (int i = 0; i < cnt; i++) {
			selectedProblems.add(allProblems.get(i));
			selectedProblems.get(i).pickCnt++;
		}
	}

	public Problem getProblem(int num) {
		return selectedProblems.get(num);
	}

	public void updateProblem(int num, ArrayList<String> userSelect) {
		Problem p = selectedProblems.get(num);
		
		Collections.sort(userSelect);
		
		p.userSelect.clear();
		for (String string : userSelect) {
			p.userSelect.add(string);
		}
	}
	
	public void checkAnswer() {
		// ���� Ȯ��
		for (Problem problem : selectedProblems) {
			problem.checkCnt++;
			if (problem.isCollect())
				problem.answerCnt++;
		}
	}

	private void writeUserData() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(userDataPath);
		for (Problem problem : allProblems) {
			String data = problem.pickCnt + " " + problem.checkCnt + " " + problem.answerCnt;
			pw.println(data);
		}
		pw.close();
	}

	private void initUserData() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(userDataPath);
		for (Problem problem : allProblems) {
			String data = 0 + " " + 0 + " " + 0;
			pw.println(data);
		}
		pw.close();
	}

	public static void main(String[] args) throws Throwable {
		// Test
		ProblemBank bank = new ProblemBank();

		for (int i = 0; i < 40; i++) {
			Problem p = bank.getProblem(i);
			System.out.println(p.problem);

			for (int j = 0; j < p.selections.length; j++) {
				System.out.println(j + 1 + " : " + p.selections[j]);
			}

			System.out.println("hint: " + p.hint);

			System.out.print("answer: ");
			for (String string : p.answer) {
				System.out.print(string + " ");
			}
			System.out.println();
		}

		//bank.initUserData();
		bank.finalize();
	}
}
