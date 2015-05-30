package com.gman.yahtzee;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("all")

public class YahtzeeGUI {
    
	int[] total_points;
	int themPlayers = 1;
	int allThem;
	int[][] scoreData;
	int thePlayer;
    Color original = new Color(248, 248, 248);
    Color onMouse = new Color(229, 228, 226);
    Color onClick = new Color(0, 76, 153);
	private JPanel scoreDisplay;
	private JLabel[] scoreSheet = new JLabel[15];
	private int clicks = 0;
	public static JButton roll = new JButton("Roll");
    public int roll_count;
	boolean startGame = false;
	boolean GameState = true;
	JPanel startup;
	JComboBox playerChoice;
	private int players = 0;
	boolean input = true;
	int[] theDice = new int[5];
	private JLabel[] diceImage = new JLabel[5];
	private JPanel[] diceData = new JPanel[5];
	private JCheckBox[] checkBox = new JCheckBox[5];
	public JFrame frame;
	private int player;
	private JPanel diceContainer = new JPanel();
	private boolean hasScored = false;
	private JLabel fillStorage;
	private int[] upperSection;
	
	public YahtzeeGUI() {
		
		setLookAndFeel();
		frame = new JFrame("Yahtzee!");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 570);
		setBlank();
		startup = new JPanel();
		startup.setLayout(new BoxLayout(startup, BoxLayout.Y_AXIS));
		JButton button = new JButton("Ok");
		String[] choices = { "Please select one", "1", "2", "3", "4", "5", "More..." };
		JLabel instruct = new JLabel("Please enter how many players are playing");
		startup.add(instruct);
		playerChoice = new JComboBox(choices);
		startup.add(playerChoice);
		startup.setLayout(new FlowLayout());
		startup.add(button);
		playerChoice.setSelectedIndex(0);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String choice = (String) playerChoice.getSelectedItem();
				if (choice.equals("More...")) {
					String playerInput = null;
					String[] options = { "Ok" };
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					JLabel label = new JLabel("How many players?");
					JTextField t = new JTextField(20);
					panel.add(label);
					panel.add(t);
					do {
						int selectedOption = JOptionPane.showOptionDialog(frame, panel, "Players", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if (selectedOption == 0) {
							playerInput = t.getText();
						}
						try {
							players = Integer.parseInt(playerInput);
						} catch (Exception exc) {
							//Ignore
						}
						if (players == 0 || !isDigit(playerInput)) {
							JOptionPane.showMessageDialog(frame, "Please enter a number that is greater than 0 and NOT A DECIMAL");
							input = false;
						} else {
							input = true;
						}
						if (input) {
						    startGame = true;
							frame.remove(startup);
							doGame();
							frame.repaint();
							frame.revalidate();
						}
					} while(input == false);
				} else if (!choice.equals("Please select one")){
				    startGame = true;
					players = Integer.parseInt(choice);
					frame.remove(startup);
					doGame();
					frame.repaint();
					frame.revalidate();
				}
			}
			
		});
		frame.add(startup);
		frame.setVisible(true);
	}
	public void setImage(int indicator, int index) throws IndexOutOfBoundsException {
		try {
			Image img = ImageIO.read(getClass().getResource("dice" + indicator + ".jpeg"));
			Image newimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			diceImage[index].setText("");
			diceImage[index].setIcon(new ImageIcon(newimg));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	public boolean isDigit(String digit) {
		try {
			players = Integer.parseInt(digit);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	private void doGame() {
	        upperSection = new int[players];
			allThem = themPlayers;
			scoreDisplay = new JPanel();
			scoreDisplay.setLayout(new BoxLayout(scoreDisplay, BoxLayout.Y_AXIS));
			diceContainer = new JPanel();
			diceContainer.setLayout(new FlowLayout());
			if (startGame) {
				diceContainer.add(roll);
				frame.add(diceContainer);
				frame.repaint();
				frame.revalidate();
				try {
					Thread.sleep(500);
				}catch (Exception exc) {
					
				}
				JOptionPane.showMessageDialog(frame, "Player " + themPlayers + ", please roll dice");
				roll.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						switch (clicks) {
							case 0:
								diceContainer.remove(roll);
								for (int i = 0; i < diceData.length; i++) {
									diceContainer.add(diceData[i]);
								}
								diceContainer.add(roll);
								frame.repaint();
								frame.revalidate();
								Random rnd = new Random();
								for (int i = 0; i < 5; i++) {
									theDice[i] = rnd.nextInt(6) + 1;
									setImage(theDice[i] - 1, i);
								}
								try {
									Image img2 = ImageIO.read(getClass().getResource("fill.png"));
									Image newimg2 = img2.getScaledInstance(800, 40, Image.SCALE_SMOOTH);
									fillStorage = new JLabel();
									fillStorage.setSize(80, 40);
									fillStorage.setIcon(new ImageIcon(newimg2));
									frame.add(fillStorage);
								}catch (Exception e4) {
									e4.printStackTrace();
								}
								addScore();
								for (int j = 0; j < 13; j++) {
									while(scoreSheet[j].WIDTH != setToGreatest()) {
										scoreSheet[j].setText(scoreSheet[j].getText() + " ");
									}
								}
								frame.add(scoreDisplay);
								clicks++;
								roll.setText("Reroll");
								frame.repaint();
								frame.revalidate();
								break;
							case 1:
								boolean[] selected = new boolean[5];
								for (int i = 0; i < 5; i++) {
									selected[i] = checkBox[i].isSelected();
								}
								for (int i = 0; i < selected.length; i++) {
									Random rn = new Random();
									if (selected[i]) {
										theDice[i] = rn.nextInt(6) + 1;
										setImage(theDice[i] - 1, i);
									}
								}
								for (int j = 0; j < 13; j++) {
									scoreDisplay.remove(scoreSheet[j]);
								}
								addScore();
								frame.repaint();
								frame.revalidate();
								clicks++;
								break;
							case 2:
								boolean[] selected2 = new boolean[5];
								for (int i = 0; i < 5; i++) {
									selected2[i] = checkBox[i].isSelected();
								}
								for (int i = 0; i < selected2.length; i++) {
									Random rn = new Random();
									if (selected2[i]) {
										theDice[i] = rn.nextInt(6) + 1;
										setImage(theDice[i] - 1, i);
									}
								}
								for (int j = 0; j < 13; j++) {
									scoreDisplay.remove(scoreSheet[j]);
								}
								addScore();
								roll.setEnabled(false);
								frame.repaint();
								frame.revalidate();
								clicks++;
								break;
						}
					}
				});
			}
	}
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
					);
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	private void doNext() {
		JOptionPane.showMessageDialog(frame, "Player " + themPlayers + ": " + Yahtzee.total_points[themPlayers - 1]);
		if(Yahtzee.sum(Yahtzee.scoring_bool[themPlayers - 1], -1) == 12 && Yahtzee.yahtzee_count[themPlayers - 1] > 0) {
			Yahtzee.players_done[themPlayers - 1] = true;
		}
		int sum = 0;
		for (int i = 0; i < Yahtzee.players_done.length; i++) {
			if(Yahtzee.players_done[i]) {
				sum++;
			}
		}
		if (sum == players) {
			doNextEnd();
			return;
		}
		playerChange();
		while(Yahtzee.players_done[themPlayers - 1] == true) {
			playerChange();
		}
		clicks = 0;
		frame.remove(scoreDisplay);
		roll.setEnabled(true);
		roll.setText("Roll");
		diceContainer.remove(roll);
		for (int i = 0; i < checkBox.length; i++) {
			checkBox[i].setSelected(false);
			checkBox[i].setEnabled(true);
		}
		for(int i = 0; i < diceData.length; i++) {
			frame.remove(diceData[i]);
		}
		diceContainer.removeAll();
		frame.remove(diceContainer);
		frame.remove(fillStorage);
		frame.repaint();
		frame.revalidate();
		clicks = 0;
		allThem = themPlayers;
		scoreDisplay = new JPanel();
		scoreDisplay.setLayout(new BoxLayout(scoreDisplay, BoxLayout.Y_AXIS));
		diceContainer = new JPanel();
		diceContainer.setLayout(new FlowLayout());
		if (startGame) {
			diceContainer.add(roll);
			frame.add(diceContainer);
			frame.repaint();
			frame.revalidate();
			try {
				Thread.sleep(500);
			}catch (Exception exc) {
				
			}
			JOptionPane.showMessageDialog(frame, "Player " + themPlayers + ", please roll dice");
		}
	}
	private void doNextEnd() {
		for (int i = 0; i < checkBox.length; i++) {
			checkBox[i].setSelected(false);
			checkBox[i].setEnabled(true);
		}
		for(int i = 0; i < diceData.length; i++) {
			frame.remove(diceData[i]);
		}
		diceContainer.removeAll();
		frame.remove(diceContainer);
		frame.remove(scoreDisplay);
		for (int i = 0; i < players; i++) {
			if(upperSection[i] >= 63) {
				Yahtzee.total_points[i] += 35;
				JOptionPane.showMessageDialog(frame, "Player " + (i+1) + " got bonus by score 63 or over in the upper section.");
			}
		}
		JPanel end = new JPanel();
		end.setLayout(new BoxLayout(end, BoxLayout.Y_AXIS));
		int max = Yahtzee.total_points[0];
		String winnerNum;
		for(int i = 0; i < players; i++) {
			end.add(new JLabel("Player " + (i+1) + ": " + Yahtzee.total_points[i] + " points"));
		}
		frame.add(end);
		JButton playAgain = new JButton("Play Again");
		playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Yahtzee.playAgain();
			}
			
		});
		end.add(playAgain);
		frame.repaint();
		frame.revalidate();
	}
	private void addScore() {
		for (int i = 0; i < 13; i++) {
			scoreData = new int[Yahtzee.scoreboard(theDice, themPlayers, players).length][Yahtzee.scoreboard(theDice, themPlayers, players)[0].length];
			scoreData = Yahtzee.scoreboard(theDice, themPlayers, players);
			scoreSheet[i] = new JLabel();
			scoreSheet[i].setBorder(BorderFactory.createEtchedBorder());
			switch (i) {
				case 0:
					scoreSheet[i].setText("Aces : " + scoreData[themPlayers - 1][0]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 1:
					scoreSheet[i].setText("Twos: " + scoreData[themPlayers - 1][1]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 2:
					scoreSheet[i].setText("Threes: " + scoreData[themPlayers - 1][2]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 3:
					scoreSheet[i].setText("Fours: " + scoreData[themPlayers - 1][3]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 4:
					scoreSheet[i].setText("Fives: " + scoreData[themPlayers - 1][4]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 5:
					scoreSheet[i].setText("Sixes: " + scoreData[themPlayers - 1][5]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 6:
					scoreSheet[i].setText("3 of a kind: " + scoreData[themPlayers - 1][6]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 7:
					scoreSheet[i].setText("4 of a kind: " + scoreData[themPlayers - 1][7]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 8:
					scoreSheet[i].setText("Full House: " + scoreData[themPlayers - 1][8]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 9:
					scoreSheet[i].setText("Small Straight: " + scoreData[themPlayers - 1][9]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 10:
					scoreSheet[i].setText("Large Straight: " + scoreData[themPlayers - 1][10]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 11:
					if(Yahtzee.yahtzee_count[themPlayers - 1] > 0 && scoreData[themPlayers - 1][11] == 0) {
						scoreSheet[i].setText("YAHTZEE! Bonus: 0");
						scoreSheet[i].setEnabled(false);
					}else if(scoreData[themPlayers - 1][11] == 100) {
						scoreSheet[i].setText("YAHTZEE! Bonus: " + scoreData[themPlayers - 1][11]);
					} else {
						scoreSheet[i].setText("YAHTZEE!: " + scoreData[themPlayers - 1][11]);
					}
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
				case 12:
					scoreSheet[i].setText("Chance: " + scoreData[themPlayers - 1][12]);
					addMouse(i);
					scoreDisplay.add(scoreSheet[i]);
					break;
			}
			if(Yahtzee.yahtzee_count[themPlayers - 1] >= 4) {
				scoreSheet[11].setEnabled(false);
			}
			if(Yahtzee.scoring_bool[themPlayers - 1][i] < 0) {
				scoreSheet[i].setEnabled(false);
			}
		}
	}
	private void setBlank() {
		for (int i = 0; i < diceImage.length; i++) {
			try {
				Image img = ImageIO.read(getClass().getResource("blank.png"));
				Image newimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				diceData[i] = new JPanel();
				diceData[i].setLayout(new BoxLayout(diceData[i], BoxLayout.Y_AXIS));
				diceImage[i] = new JLabel();
				diceImage[i].setSize(40, 40);
				diceImage[i].setIcon(new ImageIcon(newimg));
				diceData[i].add(diceImage[i]);
				checkBox[i] = new JCheckBox();
				diceData[i].add(checkBox[i]);
			}catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	private int setToGreatest() {
		int maximum = scoreSheet[0].WIDTH;
		for (int i = 1; i < 13; i++) {
			if(maximum < scoreSheet[i].WIDTH) {
				maximum = scoreSheet[i].WIDTH;
			}
		}
		return maximum;
	}
	private void playerChange() {
		if(themPlayers == players) {
			themPlayers = 1;
		} else {
			themPlayers++;
		}
	}
	private int sum(int[] numbers) {
		int sum = 0;
		for (int num : numbers) {
			sum += num;
		}
		return sum;
	}
	private void addMouse(final int i) {
	
	final int themPlayers = allThem;
	scoreSheet[i].setOpaque(true);
	scoreSheet[i].setBackground(original);
	scoreSheet[i].addMouseListener(new MouseListener() {
	
		@Override
		public void mousePressed(MouseEvent e) { 
			JLabel data = (JLabel) e.getSource();
			if (data.isEnabled()) {
				scoreSheet[i].setBackground(onClick);
			}
			frame.repaint();
			frame.revalidate();
		}
		
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel data = (JLabel) e.getSource();
				if (data.isEnabled()) {
					for (int i = 0; i < 13; i++) {
						scoreSheet[i].setEnabled(false);
					}
					for (int i = 0; i < scoreSheet.length; i++) {
						if (data.equals(scoreSheet[i])) {
						    if (i == 11) {
						    	Yahtzee.yahtzee_count[themPlayers - 1]++;
						    }
							Yahtzee.total_points[themPlayers - 1] = Yahtzee.total_points[themPlayers - 1] + scoreData[themPlayers - 1][i];
							clicks = 0;
							if(i >= 0 && i <= 5) {
								upperSection[themPlayers - 1] += scoreData[themPlayers - 1][i];
							}
							if (i != 11) {
								Yahtzee.scoring_bool[themPlayers - 1][i] = -1;
							}
						}
					}
					for (int i = 0; i < checkBox.length; i++) {
						checkBox[i].setSelected(false);
						checkBox[i].setEnabled(false);
					}
					roll.setEnabled(false);
					scoreSheet[i].setBackground(original);
					frame.repaint();
					frame.revalidate();
					doNext();
				}
			}
											
				@Override
				public void mouseEntered(MouseEvent e) {
				JLabel data = (JLabel) e.getSource();
				if (data.isEnabled()) {
					scoreSheet[i].setBackground(onMouse);
				}
				frame.repaint();
				frame.revalidate();
			}
											
			@Override
			public void mouseExited(MouseEvent e) {
				scoreSheet[i].setBackground(original);
				frame.repaint();
				frame.revalidate();
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
	}
}