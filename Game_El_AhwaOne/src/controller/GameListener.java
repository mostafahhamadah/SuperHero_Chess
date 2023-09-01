package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.game.Game;
import model.game.Player;

public class GameListener implements ActionListener{

  Player p1 = new Player(""); 
  Player p2 = new Player(""); 
Game g = new Game(p1,p2);
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
