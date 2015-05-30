package com.gman.yahtzee;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;

@SuppressWarnings("all")

public class Yahtzee {
	public static int players;
	public static YahtzeeGUI gui;
	static int count;
	public static void main(String[] args) {
		gui = new YahtzeeGUI();
	}
	public static void playAgain() {
		try {
			System.out.println(Yahtzee.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		  File currentJar = null;
		try {
			currentJar = new File(Yahtzee.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  /* is it a jar file? */
		  if(!currentJar.getName().endsWith(".jar"))
		    return;

		  /* Build command: java -jar application.jar */
		  final ArrayList<String> command = new ArrayList<String>();
		  command.add(javaBin);
		  command.add("-jar");
		  command.add(currentJar.getPath());

		  final ProcessBuilder builder = new ProcessBuilder(command);
		  try {
			builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.exit(0);
	}
	static int null_sum;
	public static boolean[] players_done;
	static int yahtzee_sum;
	static int null_test;
	static String[][] scoring;
	static int[][] scoring_bool;
	static int[] yahtzee_count;
	static int choice;
	static int[] null_count;
	static int[][] points;
	static int[] yahtzee_less;
	static int[] bonus;
	public static int[] total_points;
	public static int[][] scoreboard(int dice[], int player, final int players) {
		
		if(count == 0) {
			total_points = new int[players];
			scoring = new String[players][13];
			scoring_bool = new int[players][13];
			yahtzee_count = new int[players];
			null_count = new int[players];
			yahtzee_less = new int[players];
			bonus = new int[players];
			players_done = new boolean[players];
		}
		points = new int[players][13];
		for (int i = 0; i < players; i++) {
			scoring[i][0] = "Aces";
			scoring[i][1] = "Twos";
			scoring[i][2] = "Threes";
			scoring[i][3] = "Fours";
			scoring[i][4] = "Fives";
			scoring[i][5] = "Sixes";
			scoring[i][6] = "3 of a kind";
			scoring[i][7] = "4 of a kind";
			scoring[i][8] = "Full House";
			scoring[i][9] = "Small Straight";
			scoring[i][10] = "Large Straight";
			scoring[i][11] = "YAHTZEE!";
			scoring[i][12] = "Chance";
		}
		if (count == 0) {
			for (int i = 0; i < players; i++) {
				scoring_bool[i][0] = 1;
				scoring_bool[i][1] = 1;
				scoring_bool[i][2] = 1;
				scoring_bool[i][3] = 1;
				scoring_bool[i][4] = 1;
				scoring_bool[i][5] = 1;
				scoring_bool[i][6] = 1;
				scoring_bool[i][7] = 1;
				scoring_bool[i][8] = 1;
				scoring_bool[i][9] = 1;
				scoring_bool[i][10] = 1;
				scoring_bool[i][11] = 1;
				scoring_bool[i][12] = 1;
			}
			count++;
		}
		for (int i = 1; i <= 6; i++) { //To calculate points for the upper section { points [player-1][i-1] = sum(dice, i) * i; //Score it in the points array } for (int i = 1; i <= 6; i++) { if (sum(dice, i) >= 3) //If the amount of the varible 'i' is more than three or equal to it { points[player - 1][6] = add_array(dice); //All the elements of the array are added } else if (points[player - 1][6] != add_array(dice)){ points[player - 1][6] = 0; } } //next four of a kind 		for (int i = 1; i <= 6; i++) { if (sum(dice, i) >= 4) //If the amount of the varible 'i' is more than three or equal to it { points[player - 1][7] = add_array(dice);//All the elements of the array are added } else if (points[player - 1][7] != add_array(dice)){ points[player - 1][7] = 0; } }
			points[player-1][i-1] = sum(dice, i) * i;
		}
		//Now we find lower section points
		//We start with three of a kind
		for (int i = 1; i <= 6; i++) {
			if (sum(dice, i) >= 3) {
				points[player-1][6] = add_array(dice);
			}
			else if(points[player-1][6] != add_array(dice)) {
				points[player-1][6] = 0;
			}
		}
		//Now for four of a kind
		for (int i = 1; i <= 6; i++) {
			if(sum(dice, i) >= 4) {
				points[player-1][7] = add_array(dice);
			}
			else if(points[player-1][7] != add_array(dice)) {
				points[player-1][7] = 0;
			}
		}
		int[] test = new int[14];
		for (int i = 1; i <= 6; i++) {
			test[i-1] = sum(dice, i);
		}
		//Now for full house
		int first_number;
		for (int i = 0; i <= 5; i++) {
			if(test[i] == 3) {
				for (int x = 0; x <= 5; x++) {
					if(test[x] == 2) {
						points[player-1][8] = 25;
					}else if(points[player-1][8] != 25) {
						points[player-1][8] = 0;
					}
					first_number = i + 1;
				}
			}
				else if (points[player-1][8] != 25) {
					points[player-1][8] = 0;
				}
			}
			//Now straights
			/**
			 * First a small straight for out program. There is an inventory of the dice
			 * and if four in a row are >= 1, we have a small straight
			 */
			for (int i = 1; i <= 3; i++) {
				if(test[i-1] >= 1 && test[i] >= 1 && test[i+1] >= 1 && test[i+2] >= 1) {
					points[player-1][9] = 30;
				} else if(points[player-1][9] != 30) {
					points[player-1][9] = 0;
				}
			}
			/**
			 * Now for a large straight. Basically the same as a small straight exception five
			 * have to be in a row
			 */
			for (int i = 1; i <= 2; i++) {
				if(test[i-1] >= 1 && test[i] >= 1 && test[i+1] >= 1 && test[i+2] >= 1 && test[i+3] >= 1) {
					points[player-1][10] = 40;
				} else if(points[player-1][10] != 40) {
					points[player-1][10] = 0;
				}
			}
			/**
			 * Now for Yahtzee! This does support Yahtzee bonus
			 */
			for (int i = 0; i < 6; i++) {
				if(test[i] == 5) {
					if(yahtzee_count[player-1] > 0) { 		//We've got a Yahtzee bonus!
						points[player-1][11] = 100;
						i = 7; //end loop
					} else if (points[player-1][11] != 100) {
						points[player-1][11] = 50;
					}
				} else if (points[player-1][11] != 50 && points[player-1][11] != 100) {
					points[player-1][11] = 0;
				}
				if(yahtzee_count[player-1] > 5) {
					System.out.println("Here");
					points[player-1][11] = 0;
					scoring_bool[player-1][11] = -1;
				}
			}
			points[player-1][12] = add_array(dice);
			return points;
		}
	static int scoring_count;
	static int sum(int dice[], int num) { //To see how many times a number appears in an array
		scoring_count = 0;
		for (int i = 0; i < dice.length; i++)
		{
			if (dice[i] == num)
			scoring_count++;
		}
		return scoring_count;
	}
	static int sum(boolean[] data, boolean test) { //To see how many times a number appears in an array
		int test2 = 0;
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == test)
			test2++;
		}
		return test2;
	}
	static int total;
	static int add_array(int dice[]) { // sum up all elements of array
		total = 0;
		for (int i = 0; i < 5; i++)
		{
			total += dice[i];
		}
		return total;
	}
	static boolean compare_greater(int z, int a) {
		int b = z;
		if (b > a)
			return true;
		else
			return false;
	}
}