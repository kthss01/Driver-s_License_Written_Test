package Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Practice {

	public static void main(String[] args) throws IOException {

		// 한글 비교 Test
//		System.out.println("한글".equals("한글"));
//		System.out.println("영어".equals("한글"));
//		System.out.println("한글영어".contains("한글"));
		
		// 한글 문서 입력 Test
		BufferedReader br = new BufferedReader(new FileReader("problems.txt"));
		while (true) {
			String line = br.readLine();
			if (line == null) break;
			System.out.println(line);
		}
		br.close();
	}

}
