package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import dragonball.controller.Controller;
import dragonball.model.attack.Attack;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;

public class BattleMode extends JFrame{
	private JTextArea playerStats,foeStats;
	private JProgressBar playerHealthBar,foeHealthBar,playerKiBar,foeKiBar,playerStaminaBar,foeStaminaBar;
	private Controller listener;
	private JPanel[][] playerPanelHolder,foePanelHolder;
	private JPanel view,bars,playerBars,foeBars;
	private JButton use,attack,block;
	private JLabel background;
	private JTextArea senzu;
	public BattleMode(Controller listener){
		this.listener=listener;
		PlayableFighter me=(PlayableFighter)listener.getCurrentBattle().getMe();
		NonPlayableFighter foe=(NonPlayableFighter)listener.getCurrentBattle().getFoe();
		this.setBounds(50,50,1024,768);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		view=new JPanel();
		bars=new JPanel();
		background=new JLabel(new ImageIcon("battle.jpg"));
		background.setLayout(new FlowLayout());
		setContentPane(background);
		use = new JButton("USE");
		use.addActionListener(listener);
		attack = new JButton("ATTACK");
		attack.addActionListener(listener);
		block = new JButton("BLOCK");
		block.addActionListener(listener);
		add(bars,BorderLayout.NORTH );
		playerBars=new JPanel(new GridLayout(4,2));
		foeBars=new JPanel(new GridLayout(4,2));
		bars.setLayout(new GridLayout(1, 2));
		playerPanelHolder=new JPanel[4][2];
		foePanelHolder=new JPanel[4][2];
		playerPanelHolder[0][0]=new JPanel();
		playerPanelHolder[0][0].add(new JTextArea("SENZU BEANS:"+listener.getGame().getPlayer().getSenzuBeans()));
		//foePanelHolder[0][0].add(new JTextArea("FOE LEVEL:"+foe.getLevel()));
		for (int i = 1; i < playerPanelHolder.length; i++) {
			for (int j = 0; j < playerPanelHolder[i].length; j++) {
				playerPanelHolder[i][j]=new JPanel();
				foePanelHolder[i][j]=new JPanel();
				foeBars.add(foePanelHolder[i][j]);
				playerBars.add(playerPanelHolder[i][j]);
			}
		}
		view.setLayout(new BorderLayout());
		background.add(attack);
		background.add(use);
		background.add(block);
		senzu=new JTextArea("SENZU BEANS:"+listener.getGame().getPlayer().getSenzuBeans());
		senzu.setEditable(false);
		background.add(senzu);
		playerHealthBar=new JProgressBar(0,me.getMaxHealthPoints());
		foeHealthBar=new JProgressBar(0,foe.getMaxHealthPoints());
		playerKiBar=new JProgressBar(0,me.getMaxKi());
		foeKiBar=new JProgressBar(0,foe.getMaxKi());
		playerStaminaBar=new JProgressBar(0,me.getMaxStamina());
		foeStaminaBar=new JProgressBar(0,foe.getMaxStamina());
		playerHealthBar.setValue(me.getMaxHealthPoints());
		playerStaminaBar.setValue(me.getMaxStamina());
		foeHealthBar.setValue(foe.getMaxHealthPoints());
		foeStaminaBar.setValue(foe.getMaxStamina());
		foeStaminaBar.setStringPainted(true);
		foeKiBar.setBorderPainted(true);
		foeHealthBar.setBorderPainted(true);
		foeStaminaBar.setBorderPainted(true);
		playerHealthBar.setStringPainted(true);
		playerKiBar.setStringPainted(true);
		playerStaminaBar.setStringPainted(true);
		foeKiBar.setStringPainted(true);
		foeHealthBar.setStringPainted(true);
		playerHealthBar.setBorderPainted(true);
		playerStaminaBar.setBorderPainted(true);
		playerKiBar.setBorderPainted(true);
		playerHealthBar.setForeground(Color.RED);
		playerKiBar.setForeground(Color.BLUE);
		playerStaminaBar.setForeground(Color.GREEN);
		foeHealthBar.setForeground(Color.RED);
		foeKiBar.setForeground(Color.BLUE);
		foeStaminaBar.setForeground(Color.GREEN);
		JTextArea a=new JTextArea("PLAYER HEALTH:");
		JTextArea b=new JTextArea("PLAYER STAMINA:");
		JTextArea c=new JTextArea("PLAYER KI:");
		JTextArea d=new JTextArea("FOE HEALTH:");
		JTextArea e=new JTextArea("FOE STAMINA:");
		JTextArea f=new JTextArea("FOE KI:");
		a.setEditable(false);
		b.setEditable(false);
		c.setEditable(false);
		d.setEditable(false);
		e.setEditable(false);
		f.setEditable(false);
		playerPanelHolder[1][0].add(a);
		playerPanelHolder[2][0].add(b);
		playerPanelHolder[3][0].add(c);
		foePanelHolder[1][0].add(d);
		foePanelHolder[2][0].add(e);
		foePanelHolder[3][0].add(f);
		playerPanelHolder[1][1].add(playerHealthBar);
		playerPanelHolder[2][1].add(playerStaminaBar);
		playerPanelHolder[3][1].add(playerKiBar);
		foePanelHolder[1][1].add(foeHealthBar);
		foePanelHolder[2][1].add(foeStaminaBar);
		foePanelHolder[3][1].add(foeKiBar);
		bars.add(playerBars);
		bars.add(foeBars);
		bars.setSize(getMinimumSize());
		bars.setMinimumSize(bars.getMinimumSize());
		playerHealthBar.setVisible(true);
		playerKiBar.setVisible(true);
		playerStaminaBar.setVisible(true);
		foeHealthBar.setVisible(true);
		foeKiBar.setVisible(true);
		foeStaminaBar.setVisible(true);
		validate();
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b.getText().equals("USE")){
			try{
				listener.getCurrentBattle().use(listener.getGame().getPlayer(), Collectible.SENZU_BEAN);
			}
			catch(NotEnoughSenzuBeansException x){
				JOptionPane.showMessageDialog(this, "You Don't Have Enough Senzu Beans!!");
			}
		}
		
		if(b.getText().equals("BLOCK")){
			listener.getCurrentBattle().block();
		}
		
		if(b.getText().equals("ATTACK")){
			
			ArrayList<Attack> myAttacks = listener.getCurrentBattle().getAssignedAttacks();
			ArrayList<String> availableAttacks = new ArrayList<>();
			for (int i = 0; i < myAttacks.size(); i++) {
				availableAttacks.add(myAttacks.get(i).getName());
			}
			String chosenAttack = (String) JOptionPane.showInputDialog(this,
					"What is the attack you want to use?", "ATTACK",
					JOptionPane.QUESTION_MESSAGE, null, availableAttacks.toArray(),
					availableAttacks.get(0));
			try {
				listener.getCurrentBattle().attack(myAttacks.get(availableAttacks.indexOf(chosenAttack)));
			} catch (NotEnoughKiException e1) {
				JOptionPane.showMessageDialog(this, "You Don't Have Enough Ki!!");
			}
			
		}
}

	public void updateStats() {
		Fighter me = (Fighter)listener.getCurrentBattle().getMe();
		Fighter foe = (Fighter) listener.getCurrentBattle().getFoe();
		playerHealthBar.setValue(me.getHealthPoints());
		playerKiBar.setValue(me.getKi());
		playerStaminaBar.setValue(me.getStamina());
		foeHealthBar.setValue(foe.getHealthPoints());
		foeKiBar.setValue(foe.getKi());
		foeStaminaBar.setValue(foe.getStamina());
		senzu.removeAll();
		senzu.setText("SENZU BEANS:"+listener.getGame().getPlayer().getSenzuBeans());
		
	}

}
