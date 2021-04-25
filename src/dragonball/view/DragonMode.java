package dragonball.view;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.controller.Controller;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.player.Player;

public class DragonMode extends JFrame {

	private JButton senzuBeans,abilityPoints,superAttacks,ultimateAttacks;
	private Dragon dragon;
	private Controller listener;
	private JPanel AboIzzy;
	private DragonWish[] wishes;

	public DragonMode(Dragon dragon,Controller listener) {
		super();
		this.dragon=dragon;
		this.listener=listener;
		wishes=dragon.getWishes();
		setVisible(true);
		setBounds(100, 100, 1024, 768);
		senzuBeans=new JButton("Senzu Beans: "+wishes[0].getSenzuBeans());
		abilityPoints=new JButton("Ability Points: "+wishes[1].getAbilityPoints());
		superAttacks=new JButton("Super Attack: "+wishes[2].getSuperAttack().getName());
		ultimateAttacks=new JButton("Ultimate Attack: "+wishes[3].getUltimateAttack().getName());
		senzuBeans.addActionListener(listener);
		abilityPoints.addActionListener(listener);
		superAttacks.addActionListener(listener);
		ultimateAttacks.addActionListener(listener);
		try {
			AboIzzy=new JPanelWithBackground("dragon.jpg");
		} catch (IOException e) {
		}
		AboIzzy.add(senzuBeans);
		AboIzzy.add(abilityPoints);
		AboIzzy.add(superAttacks);
		AboIzzy.add(ultimateAttacks);
		add(AboIzzy);
		
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		JButton b = (JButton) e.getSource();
		Player x = listener.getGame().getPlayer();
		if (b.getText().contains("Senzu Beans: ")) {
			x.chooseWish(wishes[0]);
		}
		if (b.getText().contains("Ability Points: ")) {
			x.chooseWish(wishes[1]);
		}
		if (b.getText().contains("Super Attack: ")) {
			x.chooseWish(wishes[2]);
		}
		if (b.getText().contains("Ultimate Attack: ")) {
			x.chooseWish(wishes[3]);
		}
		setVisible(false);
		listener.getWorld().updateStats();
		listener.getWorld().setVisible(true);
	}
}
