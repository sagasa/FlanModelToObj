package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import main.FlanModelLoader.ModelType;

public class Main {
	public static void main(String[] args) {
		// ログ
		try {
			PrintStream err = new PrintStream("./log.txt");
			System.setErr(err);
			PrintStream log = new PrintStream("./log.txt");
			System.setOut(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// FlanModelLoader loader = new FlanModelLoader(new File("./Flan"));
		// loader.read(ModelType.VEHICLE, "/vehicles/GER_HT1_Pz.VI_Ausf_E_Tiger.txt");
		// loader.read(ModelType.VEHICLE, "/vehicles/JPN_MT1_Type3_Chi-Nu.txt");
		// loader.readAll();

		// ディレクトリ選択
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File("."));
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int selected = filechooser.showOpenDialog(null);
		// パックを読む
		if (selected == 0) {
			System.out.println("start read " + filechooser.getSelectedFile().getAbsolutePath());
			new FlanModelLoader(filechooser.getSelectedFile()).readAll();
			System.out.println("end read");
		}
	}
}
