import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LCSMenu {
	static ArrayList<String> output = new ArrayList();

	public static void main(String[] args) {
		FileInputStream fileinput; // declares variable to read the data from
									// input file
		BufferedInputStream bufferinput = null;
		String s = new String();
		String[] list1;
		ArrayList<String> list = new ArrayList();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nPlease provide a command \n");
			String selection = scanner.nextLine();  // reads the command provided in console

			if (selection.startsWith("LCS ")) {
				StringBuilder s1 = new StringBuilder();
				try {
					fileinput = new FileInputStream(selection.substring(4, selection.length())); // Creates
																									// a
																									// FileInputStream
																									// object
																									// on
																									// the
																									// file
																									// name
																									// passed
																									// in
																									// the
																									// arguments.
					bufferinput = new BufferedInputStream(fileinput); // Creates
																		// a
																		// BufferInputStream
																		// from
																		// the
																		// FileInputStream
					/*
					 * iterates through bufferinput and appends to string
					 * builder
					 */
					while (bufferinput.available() > 0) {
						char ch = (char) bufferinput.read();
						s1.append(ch);
					}
					/* convert to string */
					s = s1.toString();
					if (s.isEmpty()) {
						System.out.println("File provided is empty");   //checks if file is empty
					} else {
						list1 = new String[s.split("\n").length];
						list1 = (s.split("\n"));  //splits the file entries using new lines.
						if (list1.length != 0) {
							for (String temp : list1) {
                             // if block checks if file contains any blank rows will discard if any.
								if (temp.trim().equals("")) {
								} else {
									list.add(temp.trim()); //add each entry read from file to array list
								}

							}
							if ((list.size() % 2) != 0) {
								list.remove(list.size() - 1); //if file contains odd no of DNA strands, will delete the last entry to make even no of pairs
							}
							System.out.println("strands stored successfully.");
						} else {
							System.out.println("File provided is empty");

						}

					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("File not found");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (selection.equalsIgnoreCase("new")) {
				Random r = new Random();    // 

				String alphabet = "ACGT";

				int count = 0;
				for (int i = 0; count < 2; i++) {
					if (count < 2) {
						int k = r.nextInt(21);    //Restricts length from 6 to 20
						if (k > 6) {
							count++;
							StringBuffer sb = new StringBuffer();
							// generate random characters
							for (int j = 0; j < k; j++) {

								sb.append(alphabet.charAt(r.nextInt(alphabet.length()))); //append randomly generated characters to string buffer
							}
							list.add(sb.toString());   //add the newly created DNA strands to input array list to compute LCS on LCS command
						}
					}

				}
				System.out.println("strands generated successfully:");
				System.out.println(list.get(list.size() - 2));
				System.out.println(list.get(list.size() - 1));

			}

			else if (selection.equalsIgnoreCase("LCS")) {
				int counter = output.size();
				// This block picks two strings from input array list to compute LCS
				if (list.size() != 0 && (counter * 2) != list.size()) {
					for (int k = counter * 2; k < list.size(); k = k + 2) {
						char X[] = list.get(k).toCharArray();
						char Y[] = list.get(k + 1).toCharArray();
						LCS(X, Y); // call LCS function to compute LCS 
					}
					System.out.println(
							"longest common subsequences (LCS) of all stored pair strands computed successfully. ");
				} else {
					System.out.println("No DNA strands available to compute LCS");
				}

			} else if (selection.equalsIgnoreCase("print")) {

				if (list.size() != 0 && output.size() != 0) {
					System.out.println("-----------------------------------------------------");
					for (int l = 0; l < list.size(); l = l + 2) {
						if (output.size() > l / 2) {
							System.out.println("The DNA strands: ");
							System.out.println(list.get(l));
							System.out.println(list.get(l + 1));
							System.out.println("LCS is " + output.get(l / 2));  //prints output corresponding to 2 input DNA strands from the list array list. 
							System.out.println("LCS length is " + output.get(l / 2).length());

							System.out.println("-----------------------------------------------------");
						}

					}
				} else {
					System.out.println("No computed LCS available to print");
				}

			} else if (selection.equals("quit")) {
             // finish the program
				break;
			} else {
				System.out.println("Please give a valid command");
			}
		}

	}

	static void LCS(char X[], char Y[]) {
		int m = X.length;
		int n = Y.length;
		int c[][] = new int[m + 1][n + 1];
		String direction[][] = new String[m + 1][n + 1];
		for (int i = 1; i <= m; i++)
			c[i][0] = 0;
		for (int j = 0; j <= n; j++)
			c[0][j] = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++)
				if (X[i - 1] == Y[j - 1]) {
					c[i][j] = c[i - 1][j - 1] + 1;
					direction[i][j] = "cross";
				} else if (c[i - 1][j] >= c[i][j - 1]) {
					c[i][j] = c[i - 1][j];
					direction[i][j] = "top";
				} else {
					c[i][j] = c[i][j - 1];
					direction[i][j] = "left";
				}
		}
		// String x = solution[A.length][B.length];
		StringBuilder str = new StringBuilder();
		// int a = A.length;
		// int b = B.length;
		int i = m;
		int j = n;
		while (i != 0 && j != 0) {
			// System.out.println("i="+i+"j="+j);
			if (direction[i][j] == "cross") {
				str = str.append(X[i - 1]);
				// System.out.println("i="+i+"j="+j);
				i--;
				j--;
			} else if (direction[i][j] == "top") {
				i--;
			} else if (direction[i][j] == "left") {
				j--;
			}

		}
		output.add(str.reverse().toString());

	}

}
