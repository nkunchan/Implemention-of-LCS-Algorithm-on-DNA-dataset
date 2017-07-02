// Nikita Kunchanwar
// Shashank Gupta
// Varun Krishnan

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LCSMenu {
	static ArrayList<String> output = new ArrayList<>();

	public static void main(String[] args) {
		FileInputStream fileinput; // declares variable to read the data from
									// input file
		BufferedInputStream bufferinput = null;
		String s = new String();
		String[] list1;
		ArrayList<String> list = new ArrayList<>(); // Arraylist for storing strands
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nPlease provide a command \n");
			String selection = scanner.nextLine(); // reads the command provided
													// in console

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
						System.out.println("File provided is empty"); // checks
																		// if
																		// file
																		// is
																		// empty
					} else {
						list1 = new String[s.split("\n").length];
						list1 = (s.split("\n")); // splits the file entries
													// using new lines.
						if (list1.length != 0) {
							for (String temp : list1) {
								// if block checks if file contains any blank
								// rows will discard if any.
								if (temp.trim().equals("")) {
								} else {
									list.add(temp.trim()); // add each entry
															// read from file to
															// array list
								}

							}
							if ((list.size() % 2) != 0) {
								list.remove(list.size() - 1); // if file
																// contains odd
																// no of DNA
																// strands, will
																// delete the
																// last entry to
																// make even no
																// of pairs
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
				Random r = new Random(); //

				String alphabet = "ACGT";

				int count = 0;
				for (int i = 0; count < 2; i++) {
					if (count < 2) {
						int k = r.nextInt(21);
						if (k > 6) { // Restricts length from 7 to 20
							count++;
							StringBuffer sb = new StringBuffer();
							// generate random characters
							for (int j = 0; j < k; j++) {

								sb.append(alphabet.charAt(r.nextInt(alphabet.length()))); // append
																							// randomly
																							// generated
																							// characters
							}
							list.add(sb.toString()); // add the newly created
														// DNA strands to input
														// array list
						}
					}

				}
				System.out.println("strands generated successfully:");
				System.out.println(list.get(list.size() - 2));
				System.out.println(list.get(list.size() - 1));

			}

			else if (selection.equalsIgnoreCase("LCS")) {
				int counter = output.size();
				// This block picks two strings from input array list to compute
				// LCS
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
							System.out.println("LCS is " + output.get(l / 2)); // prints
																				// output
																				// corresponding
																				// to
																				// 2
																				// input
																				// DNA
																				// strands
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
		int c[][] = new int[m + 1][n + 1]; // matrix of size m+1 and n+1 created
		String direction[][] = new String[m + 1][n + 1]; // matrix of storing
															// direction
		for (int i = 1; i <= m; i++) // Initializing first column as zero
			c[i][0] = 0;
		for (int j = 0; j <= n; j++) // Initializing first row as zero
			c[0][j] = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++)
				if (X[i - 1] == Y[j - 1]) { // If the characters match
					c[i][j] = c[i - 1][j - 1] + 1;
					direction[i][j] = "cross";
				} else if (c[i - 1][j] >= c[i][j - 1]) {
					// If the characters don't match and top value of matrix c
					// is greater than or equal to the left value
					c[i][j] = c[i - 1][j];
					direction[i][j] = "top";
				} else {
					// Left value of matrix c is greater than the top value
					c[i][j] = c[i][j - 1];
					direction[i][j] = "left";
				}
		}
		StringBuilder str = new StringBuilder();
		int i = m;
		int j = n;
		while (i != 0 && j != 0) {
			// While first row or first column is not reached
			if (direction[i][j] == "cross") {
				str = str.append(X[i - 1]); // Appending matching character
				// Moving cross
				i--;
				j--;
			} else if (direction[i][j] == "top") {
				i--; // Moving up
			} else if (direction[i][j] == "left") {
				j--; // Moving left
			}

		}
		output.add(str.reverse().toString());
		// Reversing and adding

	}

}

/*Programming Project 3– LCS
//Shashank Gupta, Nikita Kunchanwar, Varun Krishnan

README:

->Coded LCS in Java.
->The input DNA strands can be given as a text file using "LCS filename.txt" or can be generated using "new" command.
->The command "new" randomly generates DNA strands greater than 6 and smaller than or equal to 20.
->The command "LCS" finds the LCS from all the DNA strands stored in the program.
->The command "print" prints all DNA strands and their LCS.
->The command "quit" causes the program to exit.

->For finding the LCS
--Created a matrix c (storing the computed values) and matrix direction
--Initializing first row and column values as zero.
--Iterating through the matrix
	-If matching characters are found then 
		i) assigning the value of c[i][j] as (diagonal element + 1)
		ii) assigning direction as "cross"
	-Else if the characters dont match
		i) choose the maximum between top and left element and assign it to c[i][j]
		ii) assigning the corresponding direction as "top" or "left"
--For printing the subsequence
	-Start from the last element (last row, last column)
	-If the direction is "cross", then append the character to the string and move diagonally
	-else if direction is "top", then move towards top
	-else if direction is "left", then move towards left
--After the end of while loop, reverse the subsequence string and add it to the output Arraylist
*/

