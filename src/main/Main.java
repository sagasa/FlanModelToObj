package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JFileChooser;


public class Main {
	public static void main(String[] args) {
		//ログ
		try {
			PrintStream log = new PrintStream("./log.txt");
		//	System.setOut(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ディレクトリ選択
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File("."));
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int selected = filechooser.showOpenDialog(null);
		// パックを読む
		if (selected == 0) {
			System.out.println("start read "+filechooser.getSelectedFile().getAbsolutePath());
			new FlanModelLoader(filechooser.getSelectedFile());
		}
	}
}
