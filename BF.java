import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BF {
	public static final int NUM_CELLS = 30000;

	public static int index = 0;
	public static int occupiedCells = 1;
	public static int [] cells = new int[NUM_CELLS];

	public static void parse(String program, boolean verbose) {
		int pc = 0;
		while (pc < program.length()) {
			if (verbose) {
				/*	print (minput[:pc] + "*" + minput[pc] + "*" + minput[pc + 1:])
					for i in range(len(cells)):
						if (i == index): print("[%d]" % cells[i]),
						else: print(" %d " % cells[i]),
					print */
				System.out.println(program.substring(0,pc) + "*" + program.charAt(pc) + "*" + program.substring(pc + 1));
				for (int i = 0; i < occupiedCells; i++) {
					if (i == index) System.out.print("[" + cells[i] + "]");
					else System.out.print(" " + cells[i] + " ");
				}
				System.out.println();
			}

			if (program.charAt(pc) == '>') {
				index++;
				if (index == occupiedCells) occupiedCells++;
			} else if (program.charAt(pc) == '<') index--;
			else if (program.charAt(pc) == '.') System.out.print((char)cells[index]);
			else if (program.charAt(pc) == ',') {
				
			} else if (program.charAt(pc) == '[') {
					pc++;
					int lcount = 1;
					int lend = pc;

					while (true) {
						if (program.charAt(lend) == ']') lcount--;
						if (program.charAt(lend) == '[') lcount++;
						lend++;
						if (lcount == 0) break;
					}

					if (cells[index] != 0) parse(program.substring(pc, lend - 1), verbose);
					else pc = lend + 1;

					pc -= 2;
			} else if (program.charAt(pc) == '+') cells[index]++;
			else if (program.charAt(pc) == '-') cells[index]--;

			pc++;
		}
	}

	public static void main(String [] args) throws FileNotFoundException,IOException {
		boolean verbose = false;
		String filePath = "";
		for (String arg : args) {
			if (arg.equals("-v") || arg.equals("--verbose")) verbose = true;
			if (arg.contains("file")) filePath = arg.substring(arg.indexOf("=") + 1, arg.length());
		}

		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) sb.append(line);
		parse(sb.toString(), verbose);
	}
} 