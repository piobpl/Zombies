package game.view;

import game.model.Card;
import game.model.Modifier;
import game.model.Trap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Cell {

	private JPanel panel;
	private JLabel name;
	private JLabel strength;
	private JLabel trapDesc;
	private JLabel modifier;
	private Color colorKarty;
	private Color colorTla;
	private boolean isHighlighted;
	private JLabel onGlass;

	public Cell(JPanel panel, Color colorKarty, Color colorTla) {
		this.panel = panel;
		this.colorKarty = colorKarty;
		this.colorTla = colorTla;
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(name = new JLabel("Karta"));
		name.setForeground(Colors.napisy.getColor());
		
		panel.add(strength = new JLabel("Sila: 0"));
		strength.setForeground(Colors.boardsCard.getColor());
		panel.add(trapDesc = new JLabel(""));
		panel.add(modifier = new JLabel(""));
		panel.setBorder(BorderFactory.createLineBorder(Colors.margines.getColor(),
				1));
		panel.setPreferredSize(new Dimension(120, 80));
		panel.setBackground(colorTla);
		name.setVisible(false);
		strength.setVisible(false);
		isHighlighted = false;
		onGlass = null;
	}

	public void drawCard(final Card card) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (card == null) {
					panel.setBackground(colorTla);
					name.setVisible(false);
					strength.setVisible(false);
					modifier.setText("");
				} else {
					name.setVisible(true);
					name.setText(card.getName());
					String s = "";
					boolean first = true;
					for (Modifier m : card.getModifiers()) {
						if (!first)
							s += ", ";
						s += m.getName();
						first = false;
					}
					switch (card.getType()) {
					case ZOMBIE:
						panel.setBackground(Colors.zombieCard.getColor());
						break;
					case DOGS:
						panel.setBackground(Color.blue);
						break;
					case BARREL:
						panel.setBackground(Color.yellow);
						break;
					default:
						panel.setBackground(colorKarty);
					}
					modifier.setText(s);
					modifier.setForeground(Color.cyan);
					if (card.getStrength() == null) {
						strength.setVisible(false);
					} else {
						strength.setVisible(true);
						strength.setText("Sila: " + card.getStrength());
					}
				}
			}
		});
	}

	public void drawTraps(final Iterable<Trap> traps) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String trap = "";
				boolean first = true;
				for (Trap t : traps) {
					if (!first)
						trap += ", ";
					trap += t.getName();
					first = false;
				}
				trapDesc.setText(trap);
				trapDesc.setForeground(Colors.tlo.getColor());
			}
		});
	}

	public void addMouseListener(final MouseListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.addMouseListener(a);
			}
		});
	}

	public void setHighlight(final boolean light) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				isHighlighted = light;
				if (light) {
					panel.setBorder(BorderFactory.createLineBorder(Color.green,
							2));
				} else {
					panel.setBorder(BorderFactory.createLineBorder(
							Colors.margines.getColor(), 1));
				}
			}
		});
	}

	public void toggleHighlight() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (isHighlighted) {
					panel.setBorder(BorderFactory.createLineBorder(Color.green,
							1));
				} else {
					panel.setBorder(BorderFactory.createLineBorder(
							Colors.margines.getColor(), 1));
				}
				isHighlighted = !isHighlighted;
			}
		});
	}

	public void registerToGlass(JPanel glass) {
		if (onGlass != null)
			throw new UnsupportedOperationException(
					"You can only register to glass once.");
		glass.add(onGlass = new JLabel(""));
		onGlass.setHorizontalAlignment(SwingConstants.CENTER);
		onGlass.setVerticalAlignment(SwingConstants.CENTER);
		Point p = SwingUtilities.convertPoint(panel.getParent(),
				panel.getLocation(), glass);
		onGlass.setBounds(p.x, p.y, 120, 80);
		onGlass.setForeground(Color.GREEN);
		onGlass.setFont(new Font("Arial", Font.PLAIN, 30));
	}

	public void setGlassText(String text) {
		if (onGlass == null)
			throw new UnsupportedOperationException(
					"Setting text on glass before being registered.");
		onGlass.setText(text);
	}
}
