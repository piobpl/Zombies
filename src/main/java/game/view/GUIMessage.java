package game.view;

import game.model.Card;
import game.model.Player;
import server.controller.Message;

public abstract class GUIMessage extends Message {

	private static final long serialVersionUID = -684702361391462220L;

	@Override
	public MessageType getType() {
		return MessageType.GUI;
	}
	
	public enum GUIMessageType {
		SetHandHighlight, DrawCellCard, DrawCellTraps, SetCellHighlight, SetCellRedHighlight,
		ToggleCellHighlight, RegisterCellToGlass, SetCellGlassText;
	}
	
	public abstract GUIMessageType getSubType();

	public static class SetHandHighlightMessage extends GUIMessage{
		private static final long serialVersionUID = -8869667944502636210L;
		public GUIMessageType getSubType() {
			return GUIMessageType.SetHandHighlight;
		}
		final Player player;
		final boolean set;
		public SetHandHighlightMessage(Player player, boolean set) {
			this.player = player;
			this.set = set;
		}
	}
	public static class DrawCellCard extends GUIMessage{
		private static final long serialVersionUID = -3655210426494320068L;
		public GUIMessageType getSubType() {
			return GUIMessageType.DrawCellCard;
		}
		final Card card;
		final int board, row, column;
		public DrawCellCard(Card card, int board, int row, int column) {
			this.card = card;
			this.board = board;
			this.row = row;
			this.column = column;
		}
		
	}
	
}
