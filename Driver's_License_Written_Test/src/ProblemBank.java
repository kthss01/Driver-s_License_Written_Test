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
 * @apiNote 문제 은행: 문제 클래스를 가지고 있으며 문제와 정답을 읽어와 문제를 만들 수 있다. 문제를 조작하고 정답 체크를 할 수
 *          있으며 각 문제의 정답률을 프로그램 종료시 저장할 수 있도록 한다. 문제 : 해당 문제의 문제, 보기, 힌트, 정답을
 *          가지고 있으며 사용자가 선택한 보기에 대한 정보도 가지고 있다.
 * 
 */

class Problem {
	public String problem;
	public String[] selections;
	public String hint;
	public ArrayList<String> answer;
	public ArrayList<String> userSelect;

	public int pickCnt; // 문제은행에서 40문제로 선택된 갯수
	public int checkCnt; // 정답 체크를 해본 갯수
	public int answerCnt; // 사용자가 정답으로 맞힌 갯수

	Problem() {
		answer = new ArrayList<String>();
		userSelect = new ArrayList<String>();
	}

	public boolean isCollect() {
		for (int i = 0; i < answer.size(); i++) {
			// 선택지가 정답지보다 적을 때
			if (answer.size() > userSelect.size())
				return false;
			// 선택지와 정답지가 다를 때 (모두 정렬시킴)
			for (int j = 0; j < answer.size(); j++) {
				if (!answer.get(j).equals(userSelect.get(j)))
					return false;
			}
		}

		return true;
	}
}

public class ProblemBank {

	static ArrayList<Problem> allProblems; // 읽어온 모든 문제
	static ArrayList<Problem> selectedProblems; // 모든 문제 중 선택된 40문제
	static String problemsPath = "problems.txt"; // 읽어올 문제 주소
	static String ansPath = "ans.txt"; // 읽어올 정답 주소
	static String userDataPath = "user.txt"; // 선택되었던 문제들의 정답률

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

		// 문제 읽어오기
		// 문제 구성
		/*
		 * 5 line 문제 보기1 보기2 보기3 보기4 해설
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
				p.problem = line; // 문제
				break;
			case 1:
			case 2:
			case 3:
			case 4:
				p.selections[i - 1] = line; // 보기
				break;
			case 5:
				p.hint = line;
				break;
			}

			if (i == 5)
				allProblems.add(p); // 문제 추가

			i = (i + 1) % 6; // 0 1 2 3 4 5반복
		}
		br.close();

		// 정답 읽어오기
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

		// userData 읽어오기
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
		// 읽어온 모든 문제 중 40문제 선택하기 - 랜덤하게 하면 좋을 듯 우선은 앞에서 40개만
		selectedProblems = new ArrayList<Problem>();

		int cnt = 40; // 40개만
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
		// 정답 확인
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
