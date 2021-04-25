package dragonball.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.view.BattleMode;
import dragonball.view.DragonMode;
import dragonball.view.MainMenu;
import dragonball.view.WorldMap;

public class Controller implements GameListener, ActionListener, FocusListener {
	private MainMenu mainMenu;
	private Game game;
	private String lastSavedFile;
	private WorldMap world;
	private BattleMode battle;
	private Battle currentBattle;
	private DragonMode dragonView;
	public static final String[] races = { "namekian", "earthling", "majin",
			"frieza", "saiyan" };

	public Controller() {
		mainMenu = new MainMenu();
		mainMenu.setListener(this);
		mainMenu.getNewGame().addActionListener(this);
		mainMenu.getLoadGame().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getTopLevelAncestor() instanceof MainMenu) {
			mainMenu.actionPerformed(e);
		}
		if (b.getTopLevelAncestor() instanceof WorldMap) {
			world.actionPerformed(e);
		}
		if (b.getTopLevelAncestor() instanceof BattleMode) {
			battle.actionPerformed(e);
		}
		if (b.getTopLevelAncestor() instanceof DragonMode) {
			dragonView.actionPerformed(e);
		}

	}

	public void focusLost(FocusEvent f) {
		JTextField t = (JTextField) f.getSource();
		if (t.getTopLevelAncestor() instanceof MainMenu) {
			this.getGame().getPlayer().setName(t.getText());
			String race = (String) JOptionPane.showInputDialog(mainMenu,
					"What is your fighter's race?", "Fighter race",
					JOptionPane.QUESTION_MESSAGE, null, races, races[0]);
			String name = JOptionPane.showInputDialog("ENTER FIGHTER NAME");
			switch (race) {
			case "saiyan":
				Saiyan s = new Saiyan(name);
				this.getGame().getPlayer().getFighters().add(s);
				this.getGame().getPlayer().setActiveFighter(s);
				break;
			case "earthling":
				Earthling e = new Earthling(name);
				this.getGame().getPlayer().getFighters().add(e);
				this.getGame().getPlayer().setActiveFighter(e);
				break;
			case "frieza":
				Frieza j = new Frieza(name);
				this.getGame().getPlayer().getFighters().add(j);
				this.getGame().getPlayer().setActiveFighter(j);
				break;
			case "majin":
				Majin m = new Majin(name);
				this.getGame().getPlayer().getFighters().add(m);
				this.getGame().getPlayer().setActiveFighter(m);
				break;
			case "namekian":
				Namekian n = new Namekian(name);
				this.getGame().getPlayer().getFighters().add(n);
				this.getGame().getPlayer().setActiveFighter(n);
				break;
			}
			this.setWorld(new WorldMap(this));
			this.getWorld().setListener(this);
			mainMenu.setVisible(false);
		}
	}

	public void focusGained(FocusEvent f) {
	}

	public String getLastSavedFile() {
		return lastSavedFile;
	}

	public void setLastSavedFile(String lastSavedFile) {
		this.lastSavedFile = lastSavedFile;
	}

	public static void main(String[] args) {
		Controller c = new Controller();
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public WorldMap getWorld() {
		return world;
	}

	public void setWorld(WorldMap world) {
		this.world = world;
	}

	@Override
	public void onDragonCalled(Dragon dragon) {
		world.setVisible(false);
		dragonView = new DragonMode(dragon, this);

	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			JOptionPane.showMessageDialog(world,
					"You Have Collected A SenzuBean");
			break;

		case DRAGON_BALL:
			JOptionPane.showMessageDialog(world,
					"You Have Collected A DragonBall");
			break;
		}
		world.updateStats();
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		Battle b = (Battle) e.getSource();
		BattleEventType t = e.getType();
		switch (t) {
		case STARTED:
			currentBattle = b;
			world.setVisible(false);
			battle = new BattleMode(this);
			break;

		case NEW_TURN:
			if (currentBattle.getAttacker() == currentBattle.getFoe()) {
				JOptionPane.showMessageDialog(battle, "Opponent's Turn");
				boolean happened = false;
				while (!happened) {
					try {
						currentBattle.play();
						happened = true;
					} catch (NotEnoughKiException e1) {
					}
				}
			} else {
				JOptionPane.showMessageDialog(battle, "Your Turn");
			}
			battle.updateStats();
			break;

		case ATTACK:
			if (currentBattle.getAttacker() == currentBattle.getMe()) {
				JOptionPane.showMessageDialog(battle,
						"You Have Attacked , Bravo");
			} else {
				JOptionPane.showMessageDialog(battle, "You Have Been Attacked");
			}
			battle.updateStats();
			break;

		case ENDED:
			battle.setVisible(false);
			if (e.getWinner() == b.getMe()) {
				world.getPanelHolder()[0][0].removeAll();
				world.getPanelHolder()[0][0].add(new JLabel(new ImageIcon(
						"supersaiyan.jpg")));
				world.updatePosition();
				world.updateStats();
				world.setVisible(true);
			} else if (e.getWinner() == b.getFoe()) {
				JOptionPane.showMessageDialog(battle, "GAME OVER!");
				world = new WorldMap(this);
			}
			break;

		case USE:
			JOptionPane.showMessageDialog(battle,
					"You Have Used A SenzuBean , Bravo");
			battle.updateStats();
			break;

		case BLOCK:
			if (currentBattle.getAttacker() == currentBattle.getMe()) {
				JOptionPane.showMessageDialog(battle,
						"You Have Blocked , Bravo");
			} else {
				JOptionPane.showMessageDialog(battle, "Foe Is Blocking");
			}
			break;
		}
	}

	public BattleMode getBattle() {
		return battle;
	}

	public void setBattle(BattleMode battle) {
		this.battle = battle;
	}

	public Battle getCurrentBattle() {
		return currentBattle;
	}

	public void setCurrentBattle(Battle currentBattle) {
		this.currentBattle = currentBattle;
	}

}
