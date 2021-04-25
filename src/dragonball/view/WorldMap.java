package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dragonball.controller.Controller;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.player.Player;

public class WorldMap extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718549437717345940L;
	private Controller listener;
	private JPanel[][] panelHolder;
	JPanel stats;
	JPanel options;
	JPanel grid;
	public static final String[] races = { "namekian", "earthling", "majin",
			"frieza", "saiyan" };
	public static final String[] upgrades = { "Ki", "Stamina", "Health",
			"Blast Damage", "Physical Damage" };
	private JButton createFighter, upgradeFighter, setActiveFighter,
			addSuperAttack, addUltimateAttack,save;

	public WorldMap(Controller listener) {
		super();
		this.listener = listener;
		panelHolder = new JPanel[10][10];
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		
		grid = new JPanel();
		grid.setLayout(new GridLayout(10, 10));
		stats = new JPanel();
		options = new JPanel();
		options.setLayout(new GridLayout(0, 1));
		grid.setBackground(Color.GREEN);
		options.setBackground(Color.BLUE);
		stats.setLayout(new FlowLayout());
		stats.setBackground(Color.GRAY);
		add(grid, BorderLayout.CENTER);
		add(options, BorderLayout.EAST);
		add(stats, BorderLayout.SOUTH);
		this.setTitle("WORLD");
		ImageIcon p = new ImageIcon("grass.jpg");
		ImageIcon q = new ImageIcon("frieza.jpg");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				panelHolder[i][j] = new JPanel();
				panelHolder[i][j].add(new JLabel(p));
				grid.add(panelHolder[i][j]);
			}
		}
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].removeAll();
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].add(new JLabel(q));
		panelHolder[0][0].removeAll();
		panelHolder[0][0].add(new JLabel(new ImageIcon("supersaiyan.jpg")));
		JTextArea s = new JTextArea();
		s.setPreferredSize(new Dimension(1010, 35));
		Player you = listener.getGame().getPlayer();
		s.setText("PLAYER NAME: " + you.getName() + " 	FIGHTER NAME: "
				+ you.getActiveFighter().getName() + " 	DRAGONBALLS: "
				+ you.getDragonBalls() + " 	SENZU BEANS: "
				+ you.getSenzuBeans() + "	Level:"
				+ you.getActiveFighter().getLevel());
		s.setEditable(false);
		s.setBackground(Color.GRAY);
		stats.add(s);
		createFighter = new JButton("CREATE A FIGHTER");
		createFighter.addActionListener(listener);
		createFighter.setFocusable(false);
		createFighter.setBackground(Color.GRAY);
		options.add(createFighter);
		upgradeFighter = new JButton("UPGRADE YOUR ACTIVE FIGHTER");
		upgradeFighter.addActionListener(listener);
		upgradeFighter.setBackground(Color.GRAY);
		options.add(upgradeFighter);
		setActiveFighter = new JButton("SET YOUR ACTIVE FIGHTER");
		setActiveFighter.addActionListener(listener);
		setActiveFighter.setBackground(Color.GRAY);
		options.add(setActiveFighter);
		addSuperAttack = new JButton("ADD A SUPER ATTACK");
		addSuperAttack.addActionListener(listener);
		addSuperAttack.setBackground(Color.GRAY);
		options.add(addSuperAttack);
		addUltimateAttack = new JButton("ADD AN ULTIMATE ATTACK");
		addUltimateAttack.addActionListener(listener);
		addUltimateAttack.setBackground(Color.GRAY);
		options.add(addUltimateAttack);
		save=new JButton("SAVE");
		save.addActionListener(listener);
		save.setBackground(Color.GRAY);
		options.add(save);
		grid.setFocusable(true);
		grid.addKeyListener(this);
		setActiveFighter.setFocusable(false);
		upgradeFighter.setFocusable(false);
		addSuperAttack.setFocusable(false);
		addUltimateAttack.setFocusable(false);
		save.setFocusable(false);
		setVisible(true);
		validate();
	}

	public Controller getListener() {
		return listener;
	}

	public void setListener(Controller listener) {
		this.listener = listener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText().equals("UPGRADE YOUR ACTIVE FIGHTER")) {
			upgrade();
			return;
		}
		if (b.getText().equals("CREATE A FIGHTER")) {
			createFighter();
		}
		if (b.getText().equals("SET YOUR ACTIVE FIGHTER")) {
			setFighter();
		}
		if (b.getText().equals("ADD A SUPER ATTACK")) {
			addASuperAttack();
		}
		if (b.getText().equals("ADD AN ULTIMATE ATTACK")) {
			addAnUltimateAttack();
		}
		if(b.getText().equals("SAVE")){
			try {
				listener.getGame().save("save.ser");
			} catch (IOException e1) {
				
			}
		}
	}

	public void setFighter() {
		Player you = listener.getGame().getPlayer();
		int i = 0;
		ArrayList<String> names = new ArrayList<>();
		for (i = 0; i < you.getFighters().size(); i++) {
			names.add(you.getFighters().get(i).getName());
		}
		String fighterName = (String) JOptionPane.showInputDialog(this,
				"Choose Your Active Fighter", "Active Fighter",
				JOptionPane.QUESTION_MESSAGE, null, names.toArray(),
				names.get(0));
		PlayableFighter active = you.getFighters().get(
				names.indexOf(fighterName));
		you.setActiveFighter(active);
		updateStats();

	}

	public void createFighter() {
		String race = (String) JOptionPane.showInputDialog(this,
				"What is your fighter's race?", "Fighter race",
				JOptionPane.QUESTION_MESSAGE, null, races, races[0]);
		String name = JOptionPane.showInputDialog("ENTER FIGHTER NAME");
		switch (race) {
		case "saiyan":
			Saiyan s = new Saiyan(name);
			listener.getGame().getPlayer().getFighters().add(s);
			listener.getGame().getPlayer().setActiveFighter(s);
			break;
		case "earthling":
			Earthling e = new Earthling(name);
			listener.getGame().getPlayer().getFighters().add(e);
			listener.getGame().getPlayer().setActiveFighter(e);
			break;
		case "frieza":
			Frieza f = new Frieza(name);
			listener.getGame().getPlayer().getFighters().add(f);
			listener.getGame().getPlayer().setActiveFighter(f);
			break;
		case "majin":
			Majin m = new Majin(name);
			listener.getGame().getPlayer().getFighters().add(m);
			listener.getGame().getPlayer().setActiveFighter(m);
			break;
		case "namekian":
			Namekian n = new Namekian(name);
			listener.getGame().getPlayer().getFighters().add(n);
			listener.getGame().getPlayer().setActiveFighter(n);
			break;
		}
		updateStats();
	}

	public void upgrade() {
		String upgrade = (String) JOptionPane.showInputDialog(this,
				"Available ability points: "
						+ listener.getGame().getPlayer().getActiveFighter()
								.getAbilityPoints(), "upgrade",
				JOptionPane.QUESTION_MESSAGE, null, upgrades, upgrades[0]);
		Player you = listener.getGame().getPlayer();
		switch (upgrade) {
		case "Ki":
			try {
				you.upgradeFighter(you.getActiveFighter(), 'K');
			} catch (NotEnoughAbilityPointsException e) {
				JOptionPane
						.showMessageDialog(this, "Not Enough Ability Points");
			}
			;
			break;
		case "Stamina":
			try {
				you.upgradeFighter(you.getActiveFighter(), 'S');
			} catch (NotEnoughAbilityPointsException e) {
				JOptionPane
						.showMessageDialog(this, "Not Enough Ability Points");
			}
			;
			break;
		case "Health":
			try {
				you.upgradeFighter(you.getActiveFighter(), 'H');
			} catch (NotEnoughAbilityPointsException e) {
				JOptionPane
						.showMessageDialog(this, "Not Enough Ability Points");
			}
			;
			break;
		case "Physical Damage":
			try {
				you.upgradeFighter(you.getActiveFighter(), 'P');
			} catch (NotEnoughAbilityPointsException e) {
				JOptionPane
						.showMessageDialog(this, "Not Enough Ability Points");
			}
			;
			break;
		case "Blast Damage":
			try {
				you.upgradeFighter(you.getActiveFighter(), 'B');
			} catch (NotEnoughAbilityPointsException e) {
				JOptionPane
						.showMessageDialog(this, "Not Enough Ability Points");
			}
			;
			break;
		}
		updateStats();
	}

	public void updateStats() {
		stats.removeAll();
		JTextArea s = new JTextArea();
		s.setPreferredSize(new Dimension(800, 40));
		Player you = listener.getGame().getPlayer();
		s.setText("PLAYER NAME: " + you.getName() + " 	FIGHTER NAME: "
				+ you.getActiveFighter().getName() + " 	DRAGONBALLS: "
				+ you.getDragonBalls() + " 	SENZU BEANS: "
				+ you.getSenzuBeans() + "\n	Level:"
				+ you.getActiveFighter().getLevel());
		s.setEditable(false);
		s.setBackground(Color.GRAY);
		stats.add(s);
		validate();

	}

	public void addAnUltimateAttack() {

		Player you = listener.getGame().getPlayer();
		ArrayList<String> namesNew = new ArrayList<>();
		for (int i = 0; i < you.getUltimateAttacks().size(); i++) {
			namesNew.add(you.getUltimateAttacks().get(i).getName());
		}
		namesNew.add("empty");
		ArrayList<String> namesOld = new ArrayList<>();
		for (int i = 0; i < you.getActiveFighter().getUltimateAttacks().size(); i++) {
			namesOld.add(you.getActiveFighter().getUltimateAttacks().get(i)
					.getName());
		}
		namesOld.add("none");
		String NEW = (String) JOptionPane.showInputDialog(this,
				"What is the attack you want to add?", "ADD ULTIMATE ATTACK",
				JOptionPane.QUESTION_MESSAGE, null, namesNew.toArray(), namesNew.get(0));
		
		if (!(NEW.equals("empty"))) {
			String old = (String) JOptionPane.showInputDialog(this,
					"What is the attack you want to replace?",
					"ADD ULTIMATE ATTACK", JOptionPane.QUESTION_MESSAGE, null,
					namesOld.toArray(), namesOld.get(0));
			try {

				if (old.equals("none"))
					listener.getGame()
							.getPlayer()
							.assignAttack(
									listener.getGame().getPlayer()
											.getActiveFighter(),
									you.getUltimateAttacks().get(
											namesNew.indexOf(NEW)), null);
				else
					listener.getGame()
							.getPlayer()
							.assignAttack(
									listener.getGame().getPlayer()
											.getActiveFighter(),
									you.getUltimateAttacks().get(
											namesNew.indexOf(NEW)),
									you.getActiveFighter().getUltimateAttacks()
											.get(namesOld.indexOf(old)));

			} catch (MaximumAttacksLearnedException e) {
				JOptionPane.showMessageDialog(this, "Maximum Attacks Learned");
			} catch (DuplicateAttackException e) {
				JOptionPane.showMessageDialog(this,
						"You Already Have This Attack");
			} catch (NotASaiyanException e) {
				JOptionPane.showMessageDialog(this, "You are not a Saiyan");
			}
		}
	}

	public void addASuperAttack() {

		Player you = listener.getGame().getPlayer();
		ArrayList<String> namesNew = new ArrayList<>();
		for (int i = 0; i < you.getSuperAttacks().size(); i++) {
			namesNew.add(you.getSuperAttacks().get(i).getName());
		}
		namesNew.add("empty");
		ArrayList<String> namesOld = new ArrayList<>();
		for (int i = 0; i < you.getActiveFighter().getSuperAttacks().size(); i++) {
			namesOld.add(you.getActiveFighter().getSuperAttacks().get(i)
					.getName());
		}
		namesOld.add("none");
		String NEW = (String) JOptionPane.showInputDialog(this,
				"What is the attack you want to add?", "ADD Super ATTACK",
				JOptionPane.QUESTION_MESSAGE, null, namesNew.toArray(), namesNew.get(0));
		if (!(NEW.equals("empty"))) {
			String old = (String) JOptionPane.showInputDialog(this,
					"What is the attack you want to replace?",
					"ADD SUPER ATTACK", JOptionPane.QUESTION_MESSAGE, null,
					namesOld.toArray(), namesOld.get(0));
			try {

				if (old.equals("none"))
					listener.getGame()
							.getPlayer()
							.assignAttack(
									listener.getGame().getPlayer()
											.getActiveFighter(),
									you.getSuperAttacks().get(
											namesNew.indexOf(NEW)), null);
				else
					listener.getGame()
							.getPlayer()
							.assignAttack(
									listener.getGame().getPlayer()
											.getActiveFighter(),
									you.getSuperAttacks().get(
											namesNew.indexOf(NEW)),
									you.getActiveFighter().getSuperAttacks()
											.get(namesOld.indexOf(old)));
			} catch (MaximumAttacksLearnedException e) {
				JOptionPane.showMessageDialog(this, "Maximum Attacks Learned");
			} catch (DuplicateAttackException e) {
				JOptionPane.showMessageDialog(this,
						"You Already Have This Attack");
			} catch (NotASaiyanException e) {
				JOptionPane.showMessageDialog(this, "You are not a Saiyan");
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].removeAll();
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].add(new JLabel(
				new ImageIcon("grass.jpg")));
		try {
			switch (keyCode) {
			case KeyEvent.VK_UP:
				listener.getGame().getWorld().moveUp();
				updatePosition();
				break;
			case KeyEvent.VK_DOWN:
				listener.getGame().getWorld().moveDown();
				updatePosition();
				break;
			case KeyEvent.VK_LEFT:
				listener.getGame().getWorld().moveLeft();
				updatePosition();
				break;
			case KeyEvent.VK_RIGHT:
				listener.getGame().getWorld().moveRight();
				updatePosition();
				break;
			}
		} catch (MapIndexOutOfBoundsException k) {
			updatePosition();
			JOptionPane.showMessageDialog(this, "Invalid Move !");

		}
	}

	public void updatePosition() {
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].removeAll();
		panelHolder[listener.getGame().getWorld().getPlayerRow()][listener
				.getGame().getWorld().getPlayerColumn()].add(new JLabel(
				new ImageIcon("frieza.jpg")));
		validate();
	}

	public static void main(String[] args) {
		WorldMap x = new WorldMap(null);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public JPanel[][] getPanelHolder() {
		return panelHolder;
	}

	public void setPanelHolder(JPanel[][] panelHolder) {
		this.panelHolder = panelHolder;
	}

	public JPanel getStats() {
		return stats;
	}

	public void setStats(JPanel stats) {
		this.stats = stats;
	}

	public JPanel getOptions() {
		return options;
	}

	public void setOptions(JPanel options) {
		this.options = options;
	}

	public JPanel getGrid() {
		return grid;
	}

	public void setGrid(JPanel grid) {
		this.grid = grid;
	}

	public JButton getCreateFighter() {
		return createFighter;
	}

	public void setCreateFighter(JButton createFighter) {
		this.createFighter = createFighter;
	}

	public JButton getUpgradeFighter() {
		return upgradeFighter;
	}

	public void setUpgradeFighter(JButton upgradeFighter) {
		this.upgradeFighter = upgradeFighter;
	}

	public JButton getSetActiveFighter() {
		return setActiveFighter;
	}

	public void setSetActiveFighter(JButton setActiveFighter) {
		this.setActiveFighter = setActiveFighter;
	}

	public JButton getAddSuperAttack() {
		return addSuperAttack;
	}

	public void setAddSuperAttack(JButton addSuperAttack) {
		this.addSuperAttack = addSuperAttack;
	}

	public JButton getAddUltimateAttack() {
		return addUltimateAttack;
	}

	public void setAddUltimateAttack(JButton addUltimateAttack) {
		this.addUltimateAttack = addUltimateAttack;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String[] getRaces() {
		return races;
	}

	public static String[] getUpgrades() {
		return upgrades;
	}
	

}
