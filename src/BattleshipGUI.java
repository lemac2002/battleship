import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class BattleshipGUI extends JFrame implements ActionListener {

	private JFrame helpInfo;
	private int N = 10;
	private JPanel gameboardPanel1, gameboardPanel2, startPanel, consolePanel; // create a panel to add to JFRAME
	private JButton[][] buttons1;// = new JButton[N*N]; // Make room for 16 button objects
	private JButton[][] buttons2;// = new JButton[N*N]; // Make room for 16 button objects
	private JTextArea console;
	private JComboBox<Object> shipList, directionList, startXlist, startYlist;
	private int moves = 0;
	private static int textCount = 0;
	private static int consoleCnt = 0;

	private Font f = new Font("Arial", Font.ITALIC, 36);
	private Font f1 = new Font("Arial", Font.ITALIC, 14);
	private Font f2 = new Font("Ubuntu", Font.BOLD, 18);
	private Font f3 = new Font("Arial", Font.ITALIC, 16);
	private Font f4 = new Font("Arial", Font.BOLD, 18);
	private Font f5 = new Font("Ubuntu", Font.BOLD, 17);
	private Font f6 = new Font("Arial", Font.BOLD, 24);

	private JButton reset, exit, random, rules, play, start, ship, surrender;
	private JLabel player, BattleshipLabel, computer, movesLabel, consoleLabel, bg;

	private String[] ships = { "ship", "carrier", "destroyer", "battleship", "submarine", "crusier" };
	private Player player1, player2;

	private boolean startGame = false;

	private static String[] leaerName = new String[11]; // always keep 10 records
	private static int[] leaderGuesses = new int[11]; // always keep 10 records
	private static String leadfile = "leaddata.txt";

	private Color lightBlue = new Color(148, 211, 247);
	private Color coralRed = new Color(252, 227, 227);
	private Color lightGrey = new Color(229, 229, 229);

	public static void main(String[] args) {
		new BattleshipGUI();
	}

	public BattleshipGUI() {
		super("Battle ship Game");

		for (int i = 0; i < 11; i++) {
			leaderGuesses[i] = 101; // initialize leaderboard data
		}
		// this.pack();
		setSize(1365, 730); // 760, 350
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		player = new JLabel("Player");
		player.setFont(f4);
		player.setBounds(240, 20, 70, 70);
		player.setForeground(Color.BLACK);
		player.setVisible(false);

		computer = new JLabel("Computer");
		computer.setFont(f4);
		computer.setBounds(770, 20, 100, 70);
		computer.setForeground(Color.BLACK);
		computer.setVisible(false);

		consoleLabel = new JLabel("Console");
		consoleLabel.setFont(f3);
		consoleLabel.setBounds(40, 580, 80, 50);
		consoleLabel.setForeground(Color.BLACK);
		consoleLabel.setVisible(false);

		movesLabel = new JLabel("Moves: " + moves);
		movesLabel.setFont(f4);
		movesLabel.setBounds(780, 595, 160, 25);
		movesLabel.setForeground(Color.BLACK);
		movesLabel.setVisible(false);
		/*
		 * console = new JTextArea (16, 58 ); console.setBounds(200, 600, 340, 60);
		 * console.setEditable ( false ); // set textArea non-editable JScrollPane
		 * scroll = new JScrollPane ( console ); scroll.setVerticalScrollBarPolicy (
		 * ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS ); console.setVisible(false);
		 */
		consolePanel = new JPanel();
		consolePanel.setBounds(110, 580, 600, 120);
		consolePanel.setVisible(false);

		console = new JTextArea(4, 50);
		console.setText("Welcome to Battleship!\n");

		// console.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		console.setEditable(false); // set textArea non-editable
		JScrollPane scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setLocation(0, 0);
		console.setVisible(true);
		consolePanel.add(scroll);
		consolePanel.setBackground(lightGrey);
		console.setFont(f1);
		console.setCaretPosition(console.getDocument().getLength());
		// manual set up ships

		String[] choices = { "ship", "carrier", "destroyer", "battleship", "submarine", "crusier" };
		shipList = new JComboBox<Object>(choices);
		shipList.setBounds(980, 585, 80, 30); // 540, 30, 60, 20
		shipList.setFont(f3);
		shipList.addActionListener(this);
		((JLabel) shipList.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		shipList.setBackground(Color.WHITE);
		// shipList.setForeground(lightBlue);
		shipList.setVisible(false);

		// directionList, startXlist, startYlist;
		String[] dchoices = { "direction", "up", "down", "left", "right" };
		directionList = new JComboBox<Object>(dchoices);
		directionList.setBounds(1060, 585, 100, 30);
		directionList.setFont(f3);
		directionList.addActionListener(this);
		((JLabel) shipList.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		directionList.setBackground(Color.WHITE);
		// directionList.setForeground(lightBlue);
		directionList.setVisible(false);

		String[] xchoices = { "X", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		;
		startXlist = new JComboBox<Object>(xchoices);
		startXlist.setBounds(1160, 585, 60, 30);
		startXlist.setFont(f3);
		startXlist.addActionListener(this);
		((JLabel) startXlist.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		startXlist.setBackground(Color.WHITE);
		// startXlist.setForeground(lightBlue);
		startXlist.setVisible(false);

		String[] ychoices = { "Y", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		startYlist = new JComboBox<Object>(ychoices);
		startYlist.setBounds(1220, 585, 60, 30);
		startYlist.setFont(f3);
		startYlist.addActionListener(this);
		((JLabel) startYlist.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		startYlist.setBackground(Color.WHITE);
		// startYlist.setForeground(lightBlue);
		startYlist.setVisible(false);
		// ImageIcon imageIcon = new ImageIcon(new
		// ImageIcon("icon.png").getImage().getScaledInstance(20, 20,
		// Image.SCALE_DEFAULT));
		final ImageIcon image = requestImage("BattleShipImage.png", 800, 100);
		BattleshipLabel = new JLabel(image);
		BattleshipLabel.setBounds(0, 20, 1365, 200);
		BattleshipLabel.setVisible(true);

		final ImageIcon imagebg = requestImage("ww2-63.jpg", 1365, 700);
		bg = new JLabel(imagebg);
		bg.setBounds(0, 0, 1365, 730);
		bg.setVisible(true);

		startPanel = new JPanel();
		startPanel.setLayout(null);
		startPanel.setLocation(0, 0);
		startPanel.setSize(1365, 730);// 760, 350
		startPanel.setEnabled(false);
		startPanel.setBackground(lightGrey);
		startPanel.setVisible(true);
		startPanel.add(BattleshipLabel);
		startPanel.add(bg);
		/*
		 * leaderBoard = new JLabel(); leaderBoard.setBounds(40, 120, 720, 180);
		 * leaderBoard.setBackground(lightBlue); leaderBoard.setVisible(true);
		 * leaderBoard.setOpaque(true); startPanel.add(leaderBoard);
		 */
		play = new JButton("Play");
		play.setBackground(coralRed);
		play.setForeground(Color.BLACK);
		play.setFont(f);
		play.setBounds(560, 550, 240, 60);
		play.addActionListener(this);
		startPanel.add(play);

		gameboardPanel1 = new JPanel();
		gameboardPanel1.setLayout(null);
		gameboardPanel1.setLocation(40, 20);
		gameboardPanel1.setSize(480, 600);
		buttons1 = new JButton[N][];
		gameboardPanel1.setEnabled(false);
		gameboardPanel1.setVisible(false);
		gameboardPanel1.setBackground(lightGrey); // set background color

		for (int i = 0; i < N; i++) {
			buttons1[i] = new JButton[10];
			int x = 40;
			int y = 80 + 40 * i;
			for (int j = 0; j < N; j++) {

				buttons1[i][j] = new JButton(""); // Constructor sets text on new buttons
				buttons1[i][j].setBounds(x, y, 40, 40);
				buttons1[i][j].setBackground(lightBlue);
				buttons1[i][j].setFont(f3);
				buttons1[i][j].addActionListener(this); // Set up ActionListener on each button
				gameboardPanel1.add(buttons1[i][j]); // Add buttons to the grid layout of
				// gameboardPanel
				x += 40;

			}

		}

		gameboardPanel2 = new JPanel();
		gameboardPanel2.setLayout(null);
		gameboardPanel2.setLocation(580, 20);
		gameboardPanel2.setSize(480, 600);
		buttons2 = new JButton[N][];
		gameboardPanel2.setEnabled(false);
		gameboardPanel2.setVisible(false);
		gameboardPanel2.setBackground(lightGrey);

		for (int i = 0; i < N; i++) {
			buttons2[i] = new JButton[N];
			int x = 600;
			int y = 100 + i * 40;
			for (int j = 0; j < N; j++) {

				buttons2[i][j] = new JButton("");
				buttons2[i][j].setBounds(x, y, 40, 40);
				buttons2[i][j].setBackground(lightBlue);
				buttons2[i][j].setFont(f3);
				buttons2[i][j].addActionListener(this); // Set up ActionListener on each button
				gameboardPanel2.add(buttons2[i][j]);
				x += 40;
			}

		}

		start = new JButton("Start");
		start.setBackground(coralRed);
		start.setForeground(Color.BLACK);
		start.setFont(f2);
		start.setBounds(1120, 100, 160, 40);
		start.addActionListener(this);
		start.setEnabled(false);
		gameboardPanel2.add(start);

		rules = new JButton("Rules");
		rules.setBackground(coralRed);
		rules.setForeground(Color.BLACK);
		rules.setFont(f2);
		rules.setBounds(1120, 170, 160, 40);
		rules.addActionListener(this);
		gameboardPanel2.add(rules);

		reset = new JButton("Reset");
		reset.setBackground(coralRed);
		reset.setForeground(Color.BLACK);
		reset.setFont(f2);
		reset.setBounds(1120, 240, 160, 40);
		reset.addActionListener(this);
		gameboardPanel2.add(reset);

		/*
		 * save = new JButton("Save"); save.setBackground(coralRed);
		 * save.setForeground(Color.BLACK); save.setFont(f3); save.setBounds(570, 155,
		 * 70, 20); save.addActionListener(this); gameboardPanel2.add(save);
		 */
		random = new JButton("Randomize");
		random.setBackground(coralRed);
		random.setForeground(Color.BLACK);
		random.setFont(f2);
		random.setBounds(1120, 310, 160, 40);
		random.addActionListener(this);
		gameboardPanel2.add(random);

		ship = new JButton("Place");
		ship.setBackground(coralRed);
		ship.setForeground(Color.BLACK);
		ship.setFont(f2);
		ship.setBounds(1120, 380, 160, 40);
		ship.addActionListener(this);
		gameboardPanel2.add(ship);

		surrender = new JButton("Surrender");
		surrender.setBackground(coralRed);
		surrender.setForeground(Color.BLACK);
		surrender.setFont(f2);
		surrender.setBounds(1120, 450, 160, 40);
		surrender.addActionListener(this);
		surrender.setEnabled(false);
		gameboardPanel2.add(surrender);

		exit = new JButton("Exit");
		exit.setBackground(coralRed);
		exit.setForeground(Color.BLACK);
		exit.setFont(f2);
		exit.setBounds(1120, 520, 160, 40);
		exit.addActionListener(this);
		gameboardPanel2.add(exit);

		this.setResizable(false);
		c.add(player);
		c.add(computer);
		c.add(movesLabel);
		c.add(consoleLabel);
		c.add(consolePanel);
		c.add(shipList);
		// directionList, startXlist, startYlist;
		c.add(directionList);
		c.add(startXlist);
		c.add(startYlist);
		c.add(startPanel);
		c.add(gameboardPanel1);
		c.add(gameboardPanel2);

		setVisible(true);

		player1 = new Player();
		player2 = new Player();

		player2.setAsComputer();

		readLeaderFile(leadfile);
		sortLeaderData();

	}

	public void displayBattleShipRules() {
		Color lightBlue = new Color(148, 211, 247);
		Color coralRed = new Color(252, 227, 227);
		helpInfo = new JFrame("Mac Battle ship Game Help Info");
		helpInfo = new JFrame("Mac Battle ship Game Help Info");
		helpInfo.setSize(600, 500);
		helpInfo.setVisible(true);

		Container c = helpInfo.getContentPane();

		JLabel hInfo, titleInfo;
		JButton exitHelpFrame;

		JPanel infoPanel = new JPanel();
		infoPanel.setLocation(40, 40);
		infoPanel.setSize(520, 360);

		infoPanel.setLayout(null);

		infoPanel.setEnabled(false);

		infoPanel.setBackground(lightBlue); // Allows empty space to be black

		String battleHelp = "<html><p>1. Players take turns firing shots at opponent's ships <br/> <br/>2. On your turn click on an area in the opponent's grid to make a guess <br/><br/>3. A missed shot is marked white while a hit is marked red<br/> <br/>4. The console will display a 'hit' or a 'miss' or display the phrase 'Battleship sunk' when you or your opponent sunk a battleship</p></html>";
		hInfo = new JLabel(battleHelp);
		hInfo.setForeground(Color.BLACK);
		hInfo.setFont(f5);
		hInfo.setBounds(140, 80, 360, 300);
		hInfo.setVisible(true);
		infoPanel.add(hInfo);

		titleInfo = new JLabel("Rules");
		titleInfo.setForeground(Color.BLACK);
		titleInfo.setFont(f6);
		titleInfo.setBounds(240, 10, 140, 50);
		titleInfo.setVisible(true);
		infoPanel.add(titleInfo);

		exitHelpFrame = new JButton("Exit");
		exitHelpFrame.setBackground(coralRed);
		exitHelpFrame.setForeground(Color.BLACK);
		exitHelpFrame.setBounds(220, 400, 140, 50);
		exitHelpFrame.setFont(f2);
		exitHelpFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpInfo.dispose();
			}
		});

		exitHelpFrame.setVisible(true);
		infoPanel.add(exitHelpFrame);

		infoPanel.setOpaque(true);

		helpInfo.setResizable(false);
		c.add(infoPanel);
	}

	public void actionPerformed(ActionEvent e) {
		Color lightBlue = new Color(148, 211, 247);
		if (e.getSource() == random) {
			player1.autoSetUp();
			player2.autoSetUp();

			player1.printgrid();
			// System.out.println("******************************************************");
			player2.printgrid();

			Grid grid1 = player1.getGrid();
			int[][] cells1 = grid1.getGrid();

			Grid grid2 = player2.getGrid();
			int[][] cells2 = grid2.getGrid();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (cells1[i][j] == 1) {
						buttons1[i][j].setBackground(Color.GRAY);
					}
					/*
					 * if(cells2[i][j] == 1) { buttons2[i][j].setBackground(Color.GREEN); }
					 */
				}
			}

			random.setEnabled(false);
			start.setEnabled(true);
			displayConsole("Automatically set up ships!\n");
		} else if (e.getSource() == start) {
			surrender.setEnabled(true);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					buttons1[i][j].setEnabled(false);

				}
			}
			startGame = true;
			random.setEnabled(false);
			start.setEnabled(false);
			ship.setEnabled(false);
			displayConsole("Start game!\n");
			moves = 0;
			movesLabel.setText("Moves: " + moves);
		} else if (e.getSource() == reset) {
			console.setText("");
			surrender.setEnabled(false);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					buttons1[i][j].setEnabled(true);
					buttons1[i][j].setBackground(lightBlue);
					buttons2[i][j].setEnabled(true);
					buttons2[i][j].setBackground(lightBlue);
				}
			}
			player1.resetPlayer();
			player2.resetPlayer();
			random.setEnabled(true);
			start.setEnabled(false);
			startGame = false;
			ship.setEnabled(true);
			moves = 0;
			movesLabel.setText("Moves: " + moves);

			displayConsole("Game reset, please set up ships!\n");

		} else if (e.getSource() == exit) {
			super.dispose();
			System.exit(0);
		} else if (e.getSource() == surrender) {
			displayConsole("You lost\n");
			random.setEnabled(false);
			start.setEnabled(false);
			ship.setEnabled(false);
			Grid grid2 = player2.getGrid();
			int[][] cells2 = grid2.getGrid();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					
					 if(cells2[i][j] == 1) { buttons2[i][j].setBackground(Color.GREEN); }
					 
				}
			}
		
		
		}else if (e.getSource() == play) {
			startPanel.setVisible(false);
			gameboardPanel1.setVisible(true);
			gameboardPanel2.setVisible(true);
			player.setVisible(true);
			computer.setVisible(true);
			movesLabel.setVisible(true);
			consolePanel.setVisible(true);
			consoleLabel.setVisible(true);
			shipList.setVisible(true);
			directionList.setVisible(true);
			startXlist.setVisible(true);
			startYlist.setVisible(true);
			// directionList, startXlist, startYlist;
			startGame = false;

		} else if (e.getSource() == rules) {
			displayBattleShipRules();
			displayConsole("Game rules!!!\n");
		} else if (e.getSource() == ship) {
			setShip();
		} else {
			if (!startGame) {
				return;
			}
			for (int i = 0; i < N; i++) {
				String s = "";
				for (int j = 0; j < N; j++) {
					if (e.getSource() == buttons2[i][j]) {
						int cellType = player2.getShipCellType(i, j);
						if (cellType == 1) {
							buttons2[i][j].setBackground(Color.red);
							int id = player2.setOneCell(i, j);
							s = s + "You hit!\n";
							displayConsole(s);
							if (!player2.getOneShipSink(id)) {
								displayConsole("You sunk computer's " + ships[id] + "!\n");
							}

						} else if (cellType == 0) {
							buttons2[i][j].setBackground(Color.white);
							s = s + "You missed!\n";
							displayConsole(s);
						}
						buttons2[i][j].setEnabled(false);

						moves++;
						movesLabel.setText("Moves: " + moves);
						if (player2.isAllShipsSink()) {
							// System.out.println("You Win!");
							s = "You win!!\n";
							for (int m = 0; m < N; m++) {
								for (int n = 0; n < N; n++) {
									buttons2[m][n].setEnabled(false);
								}
							}
							start.setEnabled(false);
							ship.setEnabled(true);
							displayConsole(s);
							startGame = false;
							String curName = JOptionPane.showInputDialog("Enter the player's name:");
							if (curName.isEmpty()) {
								curName = "AAA";
							}
							insertOneLeaderData(curName, moves);
							displayLeadBoard();

						} else {
							computerPlay();
						}
					}
				}
			}

		}
	}

	public void computerPlay() {

		int x, y;
		Cell nextCell = player1.getNextStep();
		if (nextCell != null) {
			x = nextCell.getX();
			y = nextCell.getY();
		} else {
			x = (int) (Math.random() * N);
			y = (int) (Math.random() * N);
		}
		while (buttons1[x][y].getBackground() == Color.white || buttons1[x][y].getBackground() == Color.red) {
			x = (int) (Math.random() * N);
			y = (int) (Math.random() * N);

		}
		int cellType = player1.getShipCellType(x, y);
		if (cellType == 1) {
			buttons1[x][y].setBackground(Color.red);
			int id = player1.setOneCell(x, y);
			displayConsole("Computer Hit!\n");
			if (!player1.getOneShipSink(id)) {
				displayConsole("Computer sunk your " + ships[id] + "!\n");
			}

		} else if (cellType == 0) {
			buttons1[x][y].setBackground(Color.white);
			displayConsole("Computer missed!!\n");
		}

		if (player1.isAllShipsSink()) {
			// System.out.println("Computer Win!");
			displayConsole("Computer Wins!\n");
			displayConsole("You lost\n");
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					buttons2[i][j].setEnabled(false);
				}
			}
			start.setEnabled(false);
			ship.setEnabled(true);
			startGame = false;
		}
	}

	public ImageIcon requestImage(String s, int x, int y) {
		ImageIcon image = null;

		// image = new ImageIcon("BattleshipImage.png");//ImageIO.read(new
		// File("BattleshipImage.png"));
		image = new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT));
		return image;
	}

	public void displayConsole(String s) {
		if (consoleCnt > 30) {
			console.setText(s);
			consoleCnt = 1;
		} else {
			console.append(s);
			consoleCnt++;
		}
	}

	public void setShip() {
		String shipStr = (String) (shipList.getSelectedItem());
		String dirStr = (String) (directionList.getSelectedItem());
		String startXStr = (String) (startXlist.getSelectedItem());
		String startYStr = (String) (startYlist.getSelectedItem());
		if (shipStr == "ship" || dirStr == "direction" || startXStr == "X" || startYStr == "Y") {
			displayConsole("Invalid ship parameters!\n");
			return;
		}
		int row = Integer.parseInt(startXStr);
		int col = Integer.parseInt(startYStr);

		if (!player1.setOneShip(shipStr, dirStr, row, col)) {
			displayConsole("Failed to set up ship!!! please adjust ship parameters!!!\n");
			return;
		}
		Grid grid1 = player1.getGrid();
		int[][] cells1 = grid1.getGrid();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (cells1[i][j] == 1) {
					buttons1[i][j].setBackground(Color.GRAY);
				}
				if (cells1[i][j] == 0) {
					buttons1[i][j].setBackground(lightBlue);
				}
			}
		}
		if (player1.isAllShipsAlive()) {
			player2.autoSetUp();
			Grid grid2 = player2.getGrid();
			int[][] cells2 = grid2.getGrid();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					/*
					 * if(cells2[i][j] == 1) { buttons2[i][j].setBackground(Color.GREEN); }
					 */
				}
			}
			random.setEnabled(false);
			start.setEnabled(true);
			displayConsole("Placing ships done, enjoy your game!\n");
		}
		displayConsole("Set one ship!\n");

	}

	public static void readLeaderFile(String filename) {
		File file = new File(filename);
		try {
			if (file.createNewFile()) {

				return;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;
			int index = 0;
			while ((line = bufferedReader.readLine()) != null && index < 10) {
				String[] s1 = line.split(" ");
				leaerName[index] = s1[0];
				leaderGuesses[index] = Integer.parseInt(s1[1]);
				index++;

			}
			textCount = index;
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLeaderFile(String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter(new File(filename));

			for (int i = 0; i < textCount; i++) {
				fw.write(leaerName[i] + " " + leaderGuesses[i]);
				fw.write(System.lineSeparator());
			}
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	// use slection sort
	public void sortLeaderData() {
		for (int i = 0; i < textCount; i++) {
			// Find the minimum element in leaderGuesses
			int minidx = i;
			for (int j = i + 1; j < textCount; j++)
				if (leaderGuesses[j] < leaderGuesses[minidx])
					minidx = j;

			// Swap the found minimum element with the first
			// element
			int temp = leaderGuesses[minidx];
			String ts = leaerName[minidx];
			leaderGuesses[minidx] = leaderGuesses[i];
			leaerName[minidx] = leaerName[i];
			leaderGuesses[i] = temp;
			leaerName[i] = ts;
		}

	}

	// insert one play guesses to leader array
	public void insertOneLeaderData(String s, int steps) {
		String ts = s;
		int t = steps;
		if (textCount == 0) {
			leaderGuesses[0] = steps;
			leaerName[0] = s;
			textCount++;
			writeLeaderFile(leadfile);
			return;
		} else {
			leaderGuesses[textCount] = t;
			leaerName[textCount] = ts;
		}

		textCount++;
		sortLeaderData();
		if (textCount > 10) {
			textCount = 10;
		}

		writeLeaderFile(leadfile);
	}

	public void displayLeadBoard() {
		JFrame lead = new JFrame("Mac Battle ship Game Leader borad");
		lead.setSize(700, 500);
		lead.setVisible(true);
		final ImageIcon imagebg = requestImage("leadimg.jpg", 700, 500);
		JLabel bg = new JLabel(imagebg);
		bg.setBounds(0, 0, 700, 500);
		bg.setVisible(true);
		lead.add(bg);
		// JLabel leadInfo;
		JButton exitFrame;
		JTextArea leadInfo1 = new JTextArea(4, 10);
		leadInfo1.append("\n");
		leadInfo1.append("    Rank \n"); // Name Guesses \n");
		for (int i = 0; i < textCount; i++) {
			String s1 = "     " + (i + 1) + "\n";
			leadInfo1.append(s1);
		}
		leadInfo1.setForeground(Color.BLACK);
		leadInfo1.setBackground(lightBlue);
		leadInfo1.setFont(f2);
		leadInfo1.setBounds(120, 60, 140, 300);
		leadInfo1.setOpaque(true);
		leadInfo1.setVisible(true);

		JTextArea leadInfo2 = new JTextArea(4, 30);
		leadInfo2.append("\n");
		leadInfo2.append("    Name \n"); // Name Guesses \n");
		for (int i = 0; i < textCount; i++) {
			String s1 = "     " + leaerName[i] + "\n";
			leadInfo2.append(s1);
		}
		leadInfo2.setForeground(Color.BLACK);
		leadInfo2.setBackground(lightBlue);
		leadInfo2.setFont(f2);
		leadInfo2.setBounds(260, 60, 200, 300);
		leadInfo2.setOpaque(true);
		leadInfo2.setVisible(true);

		JTextArea leadInfo3 = new JTextArea(4, 20);
		leadInfo3.append("\n");
		leadInfo3.append("Guesses \n"); // Name Guesses \n");
		for (int i = 0; i < textCount; i++) {
			String s1 = " " + leaderGuesses[i] + "\n";
			leadInfo3.append(s1);
		}
		leadInfo3.setForeground(Color.BLACK);
		leadInfo3.setBackground(lightBlue);
		leadInfo3.setFont(f2);
		leadInfo3.setBounds(460, 60, 100, 300);
		leadInfo3.setOpaque(true);
		leadInfo3.setVisible(true);

		exitFrame = new JButton("You won! Click to play again!");
		exitFrame.setBackground(coralRed);
		exitFrame.setForeground(Color.BLACK);
		exitFrame.setBounds(140, 400, 360, 50);
		exitFrame.setFont(f2);
		exitFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				surrender.setEnabled(false);
				lead.dispose();
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						buttons1[i][j].setEnabled(true);
						buttons1[i][j].setBackground(lightBlue);
						buttons2[i][j].setEnabled(true);
						buttons2[i][j].setBackground(lightBlue);
					}
				}
				player1.resetPlayer();
				player2.resetPlayer();
				random.setEnabled(true);
				start.setEnabled(false);
				startGame = false;
				ship.setEnabled(true);
				moves = 0;
				movesLabel.setText("Moves: " + moves);

				displayConsole("Game reset, please set up ships!\n");

			}
		});

		exitFrame.setVisible(true);
		bg.add(exitFrame);
		bg.add(leadInfo1);
		bg.add(leadInfo2);
		bg.add(leadInfo3);
		lead.setResizable(false);

	}
} // end class
