package game.view;

import game.view.GUI.Button;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class HistoryPanel {

	private JButton saveButton;
	private JSlider historySlider;
	private boolean upToDate = true;
	private EnumSet<Button> enabledInfoButtons = EnumSet.noneOf(Button.class);

	public HistoryPanel(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(390, 120));

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(120, 30));
		panel.add(saveButton);

		historySlider = new JSlider(JSlider.HORIZONTAL);
		historySlider.setPreferredSize(new Dimension(300, 30));
		historySlider.setBackground(Colors.boardsCard.getColor());
		historySlider.setForeground(Colors.napisy.getColor());
		historySlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		panel.add(historySlider);
	}

	public void setSliderRange(int turn) {
		DefaultBoundedRangeModel model = new DefaultBoundedRangeModel();
		model.setMaximum(turn);
		model.setValue(turn);
		historySlider.setModel(model);
	}

	public void addSliderChangeListener(final ChangeListener cl) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				historySlider.addChangeListener(cl);
			}
		});
	}

	public void addButtonListener(final Button button,
			final ActionListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case Save:
					saveButton.addActionListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}

	public void setButtonEnabled(final Button button, final boolean active) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case Save:
					saveButton.setEnabled(active);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
	
	public boolean isButtonEnabled(Button button) {
		switch(button) {
		case Save:
			return saveButton.isEnabled();
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	public boolean isInfoButtonEnabled(Button button) {
		return enabledInfoButtons.contains(button);
	}
	
	public void setInfoButtonEnabled(Button button, boolean active) {
		if(active)
			enabledInfoButtons.add(button);
		else
			enabledInfoButtons.remove(button);
	}
	
	public boolean isUpToDate() {
		return upToDate;
	}
	
	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}
}
