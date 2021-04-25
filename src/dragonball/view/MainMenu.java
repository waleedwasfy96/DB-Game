package dragonball.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dragonball.controller.Controller;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;

public class MainMenu extends JFrame {
	private JButton newGame, loadGame;
	private Controller listener;
	public static final String[] races = { "namekian", "earthling", "majin",
		"frieza", "saiyan" };


	public MainMenu() throws HeadlessException {
		super();
		this.setTitle("DragonBall El Fashee5");
		setBounds(100, 100, 1024, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 1));
		newGame = new JButton("NEW GAME");
		loadGame = new JButton("LOAD GAME");
		newGame.addActionListener(listener);
		loadGame.addActionListener(listener);
		newGame.setBackground(Color.RED);
		loadGame.setBackground(Color.YELLOW);
		add(newGame);
		add(loadGame);
		setVisible(true);
		validate();
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText().equals("NEW GAME")) {
			try {
				if (listener != null) {
					listener.setGame(new Game());
					listener.getGame().setListener(listener);
					//JTextField name = new JTextField("ENTER PLAYER NAME");
					String name1 = JOptionPane.showInputDialog("ENTER PLAYER NAME");
					listener.getGame().getPlayer().setName(name1);
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
						Earthling f = new Earthling(name);
						listener.getGame().getPlayer().getFighters().add(f);
						listener.getGame().getPlayer().setActiveFighter(f);
						break;
					case "frieza":
						Frieza j = new Frieza(name);
						listener.getGame().getPlayer().getFighters().add(j);
						listener.getGame().getPlayer().setActiveFighter(j);
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
					listener.setWorld(new WorldMap(listener));
					listener.getWorld().setListener(listener);
					setVisible(false);
				}
			} catch (MissingFieldException | UnknownAttackTypeException e1) {

			}

		} else {
			try {
				listener.setGame(new Game());
				try {
					listener.getGame().load("save.ser");
					listener.getGame().setListener(listener);
					listener.setWorld(new WorldMap(listener));
					listener.getWorld().setListener(listener);
					this.setVisible(false);
				} catch (ClassNotFoundException | IOException e1) {

				}
			} catch (MissingFieldException | UnknownAttackTypeException e1) {

			}
		}
	}

	public Controller getListener() {
		return listener;
	}

	public void setListener(Controller listener) {
		this.listener = listener;
	}

	public JButton getNewGame() {
		return newGame;
	}

	public JButton getLoadGame() {
		return loadGame;
	}

	public static void main(String[] args) {
		MainMenu m = new MainMenu();
	}

}
