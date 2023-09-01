package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;

public class GUI extends JFrame implements ActionListener {

	private Player p1 = new Player("Saeed");
	private Player p2 = new Player("Ezzat");
	private ArrayList<JButton> BCell = new ArrayList<>();
	private Game G = new Game(p1, p2);
	private JTextField Player2 = new JTextField(10);
	private JTextField Player1 = new JTextField(10);
	private JPanel board = new JPanel();
	private JComboBox move = new JComboBox();
	JPanel message = new JPanel();
	JPanel moveMessage = new JPanel();
	// JButton clickToMove = new JButton("move");
	private JComboBox ActionType = new JComboBox();
	private JTextArea playerOnePayLoad = new JTextArea();
	private JTextArea playerTwoPayLoad = new JTextArea();
	private JTextArea Response = new JTextArea();
	boolean selectingTarget = false;
	Direction powerdirection;
	Point hero;
	boolean teleporting = false;
	Point teleporttarget;
	private JTextArea deadForPlayer1 = new JTextArea();
	private JTextArea deadForPlayer2 = new JTextArea();
	private JComboBox deadCharachtersP1 = new JComboBox();
	private JComboBox deadCharachtersP2 = new JComboBox();

	public GUI() {
		setTitle("IChess");
		setLayout(null);
		// change the default close operation of the JFrame to exit the
		// application instead of hiding the window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// set the location and size of the JFrame
		setBounds(0, 0, 1000, 1000);
		getPlayers();
		initializeBoard();

		// setInfo();

		setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

	}

	/*
	 * Method : getPlayers() Description: Getting the Names of the two players*
	 * 
	 */

	public void putDeadCharacheters() {
		deadForPlayer1.setText("player one dead Characheters ");
		deadForPlayer2.setText("player two dead Characheters ");
		deadForPlayer1.setBackground(this.getBackground());
		deadForPlayer2.setBackground(this.getBackground());
		deadForPlayer1.setBounds(15, 50, 250, 30);
		deadForPlayer2.setBounds(750, 50, 250, 30);
		deadForPlayer1.setEditable(false);
		deadForPlayer2.setEditable(false);
		deadCharachtersP1.setBounds(15, 80, 250, 40);
		deadCharachtersP2.setBounds(750, 80, 250, 40);
		deadCharachtersP1.setBackground(this.getBackground());
		deadCharachtersP2.setBackground(this.getBackground());
		deadCharachtersP1.removeAllItems();
		deadCharachtersP2.removeAllItems();

		for (int i = 0; i < G.getPlayer1().getDeadCharacters().size(); i++) {
			deadCharachtersP1.addItem(G.getPlayer1().getDeadCharacters().get(i).toString());
		}
		for (int j = 0; j < G.getPlayer2().getDeadCharacters().size(); j++) {
			deadCharachtersP2.addItem(G.getPlayer2().getDeadCharacters().get(j).toString());
		}

		this.getContentPane().add(deadForPlayer1);
		this.getContentPane().add(deadForPlayer2);
		this.getContentPane().add(deadCharachtersP1);
		this.getContentPane().add(deadCharachtersP2);

	}

	private void getPlayers() {

		Object[] options1 = { "Submit", "Exit" };
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter player one name "));
		// JTextField textField = new JTextField(10);
		panel.add(Player1);
		panel.add(new JLabel("Enter player two name "));
		JComboBox pieces = new JComboBox();
		pieces.addItem("Each PLayer has the following Pieces");
		pieces.addItem("Medic Hero");
		pieces.addItem("Super Hero");
		pieces.addItem("Tech Hero");
		pieces.addItem("Armored Hero");
		pieces.addItem("Speedster");
		pieces.addItem("Ranged Hero");
		pieces.addItem("And 6 Side Kick Pieces for each");
		pieces.setSelectedIndex(0);
		pieces.setBounds(50, 150, 50, 50);
		panel.add(Player2);
		panel.add(pieces);

		int result2 = JOptionPane.showOptionDialog(null, panel, "SUPER HEROS CHESS", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options1, null);
		if (result2 == JOptionPane.YES_OPTION) {

			if (!(Player2.getText().equals("")) && !(Player1.getText().equals(""))) {
				JOptionPane.showMessageDialog(null,
						" Player one name is " + Player1.getText() + "  player two name is " + Player2.getText());
			} else {
				JOptionPane.showMessageDialog(null, "Enter the Names of The Players");
				getPlayers();
			}
		} else {
			System.exit(0);
		}

		p1.setName(Player1.getText());
		p2.setName(Player2.getText());
	}

	/*
	 * 
	 * Initializing the Board
	 * 
	 */
	private void initializeBoard() {
		board.setBounds(120, 130, 750, 550);
		board.setLayout(new GridLayout(7, 6));
		for (int x = 0; x < 42; x++) {
			JButton c = new JButton("Hello");
			c.addActionListener(this);
			BCell.add(c);
			c.setText(x + "");
			board.add(c);
			// setInfo()
		}
		System.out.println(BCell.get(0).toString());
		setInfo();

		playerOnePayLoad.setBounds(15, 20, 200, 30);
		Response.setBounds(350, 50, 200, 30);
		playerTwoPayLoad.setBounds(750, 20, 200, 30);
		this.getContentPane().add(playerOnePayLoad);
		this.getContentPane().add(Response);
		this.getContentPane().add(playerTwoPayLoad);
		this.getContentPane().add(board);
		message.add(ActionType);
		initActions();

	}

	/*
	 * 
	 * Changes the text and tool tip for every button
	 */
	private void setInfo() {
		for (int x = 0; x < 42; x++) {
			Point current = getEq(x);
			JButton B = BCell.get(x);
			Player owner = null;
			Cell c = G.getCellAt(current.x, current.y);
			if (c.getPiece() != null)
				owner = c.getPiece().getOwner();

			/*
			 * System.out.println("x Value is " + x + " K value is :" + current.x +
			 * " J Value is : " + current.y + " The part is " + G.getCellAt(current.x,
			 * current.y).toString() + "The Piece is : " + G.getCellAt(current.x,
			 * current.y).toString());
			 */

			if (c.getPiece() != null) {
				if (G.getCellAt(current.x, current.y).getPiece() instanceof Armored
						&& ((Armored) G.getCellAt(current.x, current.y).getPiece()).isArmorUp()) {
					BCell.get(x).setToolTipText(c.getPiece().getClass().toString() + " armor is up "
							+ "and it belongs to  " + c.getPiece().getOwner().getName());
				} else {
					BCell.get(x).setToolTipText(c.getPiece().getClass().toString() + "and it belongs to  "
							+ c.getPiece().getOwner().getName());

				}
				if (owner == p1) {
					B.setBackground(Color.YELLOW);
				} else if (owner == p2) {
					B.setBackground(Color.CYAN);
				} else {
				}

			} else {
				B.setText("N");
				B.setBackground(Color.GRAY);
			}
			if (G.getCellAt(current.x, current.y).getPiece() != null)
				B.setText("" + G.getCellAt(current.x, current.y).getPiece().getName());
			else
				B.setText("");

		}
		Player PTurn = G.getCurrentPlayer();
		if (PTurn == p1)
			Response.setBackground(Color.YELLOW);
		else
			Response.setBackground(Color.CYAN);

		switch (getState()) {
		case 0:
			Response.setText("This is " + PTurn.getName() + " Turn!");
			break;
		case 1:
			Response.setText("Player " + PTurn.getName() + " is selecting target!");
			break;
		case 2:
			Response.setText("Player " + PTurn.getName() + " Is selecting a target cell!");

		}
		updatePayload();
		putDeadCharacheters();
	}

	public int getState() {
		if (selectingTarget && teleporting) {
			return 2;
		} else if (selectingTarget && !teleporting) {
			return 1;
		} else if (!selectingTarget && !teleporting)
			return 0;
		return 0;
	}

	private void setInfo(String text) {
		for (int x = 0; x < 42; x++) {
			Point current = getEq(x);
			JButton B = BCell.get(x);
			Player owner = null;
			Cell c = G.getCellAt(current.x, current.y);
			if (c.getPiece() != null)
				owner = c.getPiece().getOwner();

			/*
			 * System.out.println("x Value is " + x + " K value is :" + current.x +
			 * " J Value is : " + current.y + " The part is " + G.getCellAt(current.x,
			 * current.y).toString() + "The Piece is : " + G.getCellAt(current.x,
			 * current.y).toString());
			 */
			if (c.getPiece() != null) {
				B.setToolTipText("" + c.getPiece().getOwner().getName());
				if (owner == p1) {
					B.setBackground(Color.YELLOW);
				} else if (owner == p2) {
					B.setBackground(Color.CYAN);
				} else {

				}

			} else {
				B.setText("N");
				B.setBackground(Color.GRAY);
			}
			B.setText("" + G.getCellAt(current.x, current.y).toString());

		}
		Response.setText("");
	}

	/*
	 * 
	 * Getting the equivelent point
	 */
	private Point getEq(int x) {
		Point r = new Point();
		r.y = x % 6;
		r.x = x / 6;
		return r;
	}

	public static void main(String[] args) {
		GUI letsplay = new GUI();
	}

	/*
	 * 
	 * Getting the type of action that happend Either Power or normal movment and
	 * passing it to Get action
	 */

	public void actionPerformed(ActionEvent e) {
		JButton btnPressed = (JButton) e.getSource();
		int btnIndex = BCell.indexOf(btnPressed);
		Point ignition = getEq(btnIndex);
		Piece p = G.getCellAt(ignition.x, ignition.y).getPiece();
		if (!selectingTarget) {
			if (p != null) {
				if (p.getOwner() != G.getCurrentPlayer())
					Response.setText("You cant use enemy peices!");
				else if (p.getOwner() == G.getCurrentPlayer())
					getAction(ignition);
			} else {
				Response.setText("This cell is empty!");
			}

		} else if (selectingTarget && teleporting == false) {
			performPower(ignition);
		} else if (selectingTarget && teleporting == true) {
			teleport(ignition);
		}

	}

	private boolean getActionType() {
		if (JOptionPane.showOptionDialog(null, message, "SUPER HEROS CHESS", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
			return true;

		} else {
			return false;

		}

	}

	/*
	 * 
	 * Shows a dialog box to get the move direction
	 */

	private Direction getDirection() {
		if (JOptionPane.showOptionDialog(null, moveMessage, "SUPER HEROS CHESS", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.NO_OPTION) {
			return null;
		}
		switch (move.getSelectedItem().toString()) {
		case "Down":
			return Direction.DOWN;
		case "up":
			return Direction.UP;
		case "left":
			return Direction.LEFT;
		case "right":
			return Direction.RIGHT;
		case "DownLeft":
			return Direction.DOWNLEFT;
		case "DownRight":
			return Direction.DOWNRIGHT;
		case "UpLeft":
			return Direction.UPLEFT;
		case "UpRight":
			return Direction.UPRIGHT;
		default:
			return null;
		}

	}

	/*
	 * 
	 * Performs a move action in the selected direction
	 */
	private void performMove(Point cellPoint, Direction d) {
		if (d == null)
			return;
		try {
			G.getCellAt(cellPoint.x, cellPoint.y).getPiece().move(d);
			setInfo();
			revalidate();
			repaint();
		} catch (UnallowedMovementException e) {
			Response.setText("UnAllowed Movement!");
		} catch (OccupiedCellException e) {
			// TODO Auto-generated catch block
			Response.setText("Ocuppied Cell");

		} catch (WrongTurnException e) {
			// TODO Auto-generated catch block
			Response.setText("Wrong Turn! No Cheating!");

		} catch (NullPointerException e) {
			Response.setText("There is no piece over here!");
		}
	}

	/*
	 * 
	 * Initializes the ComboBoxes of power move and action type
	 */
	private void initActions() {
		move.addItem("please select Direction");
		move.addItem("Down");
		move.addItem("up");
		move.addItem("left");
		move.addItem("right");
		move.addItem("DownLeft");
		move.addItem("DownRight");
		move.addItem("UpLeft");
		move.addItem("UpRight");
		move.setSelectedIndex(0);
		ActionType.addItem("Move");
		ActionType.addItem("Power");
		moveMessage.add(move);
	}

	/*
	 * gets the actionand performes it with the corresponding type either calls
	 * perform move or perform power
	 * 
	 */
	private Piece getPiece(Point at) {
		return G.getCellAt(at.x, at.y).getPiece();
	}

	private void getAction(Point x) {
		Point doer = x;
		if (!getActionType())
			return;
		if (ActionType.getSelectedItem().equals("Move")) {
			performMove(doer, getDirection());

		} else {
			try {
				Piece theHero = getPiece(doer);
				if (theHero instanceof ActivatablePowerHero) {
					if (theHero instanceof Medic || theHero instanceof Ranged || theHero instanceof Super) {
						Direction d = getDirection();
						if (d != null)
							usePower(doer, d);
					} else
						usePower(doer, null);
				} else {
					Response.setText("This piece has no powers!");
				}

			} catch (InvalidPowerUseException e) {
				// TODO Auto-generated catch block
				Response.setText("Invalid Power Use!");
			} catch (WrongTurnException e) {
				// TODO Auto-generated catch block
				Response.setText("Wrong turn! no cheating!");
			}
		}

	}

	private void usePower(Point x, Direction d) throws InvalidPowerUseException, WrongTurnException {
		Piece performer = G.getCellAt(x.x, x.y).getPiece();

		if (performer instanceof ActivatablePowerHero) {
			if (performer instanceof Tech) {
				getTarget();
				powerdirection = d;
				hero = x;
			} else if (performer instanceof Super || performer instanceof Ranged) {
				((ActivatablePowerHero) performer).usePower(d, null, null);
				Response.setText("Performed Super doper ability");

			} else if (performer instanceof Medic) {
				if (performer.getOwner() == p1) {
					if (!p1.getDeadCharacters().isEmpty())
						((ActivatablePowerHero) performer).usePower(d,
								G.getCurrentPlayer().getDeadCharacters().get(deadCharachtersP1.getSelectedIndex()),
								null);

					Response.setText("Performed Super doper ability");
				} else {
					if (!p2.getDeadCharacters().isEmpty())
						((ActivatablePowerHero) performer).usePower(d,
								G.getCurrentPlayer().getDeadCharacters().get(deadCharachtersP2.getSelectedIndex()),
								null);

					Response.setText("Performed Super doper ability");

				}

			}

		} else {
			Response.setText("This piece has no powers! Piece:" + performer.toString());
		}
		setInfo();
	}

	void getTarget() {
		selectingTarget = true;
		Response.setText("Select target for the  power!");

	}

	/*
	 * updates the payload for both players
	 */
	private void performPower(Point target) { // tech specially until now
		selectingTarget = false;
		Piece performer = G.getCellAt(hero.x, hero.y).getPiece();
		Piece targetPiece = G.getCellAt(target.x, target.y).getPiece();
		if (targetPiece.getOwner() != performer.getOwner()) {
			if (performer instanceof ActivatablePowerHero) {
				try {

					if (targetPiece instanceof ActivatablePowerHero) {
						System.out.println("Disabled the enemy piece");
						Response.setText("Disabled the enemy piece");
						((ActivatablePowerHero) performer).usePower(null, targetPiece, null);
					}
				} catch (InvalidPowerUseException e) {
					Response.setText("Invalid Power use!");
				} catch (WrongTurnException e) {
					Response.setText("Wrong turn!");
				}
			}
		} else if (targetPiece.getOwner() == performer.getOwner()) {
			if (performer instanceof ActivatablePowerHero) {
				if ((targetPiece instanceof Armored && ((Armored) targetPiece).isArmorUp() == false)) {
					getTarget();
					getArmorBack(target);
				} else {
					selectingTarget = true;
					teleporting = true;
					teleporttarget = target;
					Response.setText("Select target empty cell to be teleported to");

				}
			}
		}
		revalidate();
		repaint();
	}

	private void teleport(Point targetCell) {
		Piece performer = G.getCellAt(hero.x, hero.y).getPiece();
		Piece ally = G.getCellAt(teleporttarget.x, teleporttarget.y).getPiece();
		Piece atargetcell = G.getCellAt(targetCell.x, targetCell.y).getPiece();
		if (performer instanceof ActivatablePowerHero) {
			try {
				if (atargetcell == null || ally != atargetcell)
					((ActivatablePowerHero) performer).usePower(null, ally, targetCell);
				else if (atargetcell == ally) {
					((ActivatablePowerHero) performer).usePower(null, ally, null);
					Response.setText("Restored the ability of the unit!");
				}

			} catch (InvalidPowerUseException e) {
				Response.setText("Invalid power target");
			} catch (WrongTurnException e) {
				Response.setText("Wrong Turn!!!!");
			}

		}
		teleporting = false;
		selectingTarget = false;
		setInfo();
	}

	public void getArmorBack(Point target) {
		Piece targetPiece = G.getCellAt(target.x, target.y).getPiece();
		Piece performer = G.getCellAt(hero.x, hero.y).getPiece();
		if (performer instanceof ActivatablePowerHero) {
			if (performer.getOwner() == targetPiece.getOwner()) {
				if (performer instanceof Tech) {
					if (targetPiece instanceof Armored) {
						try {
							((ActivatablePowerHero) performer).usePower(null, targetPiece, null);
							Response.setText("Armor is up again");
							setInfo();
						} catch (InvalidPowerUseException e) {
							Response.setText("Invalid Power Use");
						} catch (WrongTurnException e) {
							Response.setText("Wrong Turn ");
						}

					}
				}
			}
		}
	}

	private void updatePayload() {

		playerOnePayLoad.setEditable(false);
		playerTwoPayLoad.setEditable(false);
		playerOnePayLoad.setBackground(this.getBackground());
		playerTwoPayLoad.setBackground(this.getBackground());
		playerOnePayLoad.setText(p1.getName() + " Payload is " + p1.getPayloadPos());
		playerTwoPayLoad.setText(p2.getName() + " Payload is " + p2.getPayloadPos());
	}

}
