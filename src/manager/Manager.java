package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

	protected ArrayList<Manageable> mList = new ArrayList<>();

	protected Scanner openFile(String filename) {
		Scanner s = null;
		try {
			s = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return s;
	}
	
}
