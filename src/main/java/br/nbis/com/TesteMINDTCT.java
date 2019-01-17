package br.nbis.com;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * @link http://www.hjort.co/2015/12/evaluating-nbis-using-fvc2004-databases-part1.html
 * @author rodolfo.ruffer
 *
 */
public class TesteMINDTCT {

	//linha de comando mindtct.exe -b -m1 digital.wsq digital
	public static void main(String[] args) throws Exception {

		File file = new TesteMINDTCT().getFile("win-64/bin/mindtct.exe");

		File f = new File("digital.wsq");
		
		Runtime rt = Runtime.getRuntime();
		String[] commands = {file.getAbsolutePath(), "-b", "-m1", f.getAbsolutePath(), f.getName()};
		
		Process process = new ProcessBuilder(commands).start();
		
		//OutputStream rsyncStdIn = proc.getOutputStream();
		
		BufferedImage array = ImageIO.read(process.getInputStream());

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		// read the output from the command
		System.out.print("Here is the standard output of the command: ");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(getQualidade(s));
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}

	}

	private File getFile(String fileName) {

		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

		return file;

	}

	private static int getQualidade(String nfiq) {
		
		int num = Integer.parseInt(nfiq);
		
		switch (num) {
		case 1:
			return 100;
		case 2:
			return 80;
		case 3:
			return 60;
		case 4:
			return 40;
		case 5:
			return 20;
		default:
			return 0;
		}
	}
}
