package Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Practice {

	public static void main(String[] args) throws IOException {

		// �ѱ� �� Test
//		System.out.println("�ѱ�".equals("�ѱ�"));
//		System.out.println("����".equals("�ѱ�"));
//		System.out.println("�ѱۿ���".contains("�ѱ�"));
		
		// �ѱ� ���� �Է� Test
		BufferedReader br = new BufferedReader(new FileReader("problems.txt"));
		while (true) {
			String line = br.readLine();
			if (line == null) break;
			System.out.println(line);
		}
		br.close();
	}

}
