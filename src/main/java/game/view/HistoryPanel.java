package game.view;

import game.view.GUI.Button;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HistoryPanel {

	private JButton saveButton;
	private JSlider historySlider;

	public HistoryPanel(JPanel panel) {
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(390, 120));

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(120, 30));
		panel.add(saveButton);

		historySlider = new JSlider(JSlider.HORIZONTAL);
		historySlider.setPreferredSize(new Dimension(300, 30));
		historySlider.setBackground(Colors.boardsCard.getColor());
		historySlider.setPaintLabels(true);
		historySlider.setForeground(Colors.napisy.getColor());
		historySlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		historySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int choice = (int) source.getValue();
					System.err.println("Selected: " + choice);
				}
			}

		});

		panel.add(historySlider);
	}

	public void setSliderRange(int turn) {
		DefaultBoundedRangeModel model = new DefaultBoundedRangeModel();
		model.setMaximum(turn);
		model.setValue(turn);
		historySlider.setModel(model);
		historySlider.setLabelTable(historySlider.createStandardLabels(1, 0));
	}

	public void addButtonMouseListener(final Button button,
			final MouseListener a) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				switch (button) {
				case Save:
					saveButton.addMouseListener(a);
					break;
				default:
					throw new UnsupportedOperationException();
				}
			}
		});
	}
}
