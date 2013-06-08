package game.view;

import game.model.Card;
import game.model.Player;
import game.model.Trap;
import server.controller.Message;

public abstract class GUIMessage extends Message {

	private static final long serialVersionUID = -684702361391462220L;

	@Override
	public MessageType getType() {
		return MessageType.GUI;
	}
	
	public enum GUIMessageType {
		SetHandHighlight, DrawCellCard, DrawCellTraps, SetCellHighlight, SetCellRedHighlight,
		ToggleCellHighlight;
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
	public static class DrawCellCardMessage extends GUIMessage{
		private static final long serialVersionUID = -3655210426494320068L;
		public GUIMessageType getSubType() {
			return GUIMessageType.DrawCellCard;
		}
		final Card card;
		final int board, row, column;
		public DrawCellCardMessage(Card card, int board, int row, int column) {
			this.card = card;
			this.board = board;
			this.row = row;
			this.column = column;
		}	
	}
	
	public static class DrawCellTrapsMessage extends GUIMessage{
		private static final long serialVersionUID = 6422412584989159135L;
		public GUIMessageType getSubType() {
			return GUIMessageType.DrawCellTraps;
		}
		final Iterable<Trap> traps;
		final int board, row, column;
		public DrawCellTrapsMessage(Iterable<Trap> traps, int board, int row,
				int column) {
			this.traps = traps;
			this.board = board;
			this.row = row;
			this.column = column;
		}
	}
	
	public static class SetCellHighlightMessage extends GUIMessage{
		private static final long serialVersionUID = 3331517099509135476L;
		public GUIMessageType getSubType() {
			return GUIMessageType.SetCellHighlight;
		}
		final boolean light;
		final int board, row, column;
		public SetCellHighlightMessage(boolean light, int board, int row,
				int column) {
			this.light = light;
			this.board = board;
			this.row = row;
			this.column = column;
		}
	}
	
	public static class SetCellRedHighlightMessage extends GUIMessage{
		private static final long serialVersionUID = -5420260677706099363L;
		public GUIMessageType getSubType() {
			return GUIMessageType.SetCellRedHighlight;
		}
		final boolean light;
		final int board, row, column;
		public SetCellRedHighlightMessage(boolean light, int board, int row,
				int column) {
			super();
			this.light = light;
			this.board = board;
			this.row = row;
			this.column = column;
		}
	}
	
	public static class ToggleCellHighlightMessage extends GUIMessage{
		private static final long serialVersionUID = -7682005361397176355L;
		public GUIMessageType getSubType() {
			return GUIMessageType.ToggleCellHighlight;
		}
		final int board, row, column;
		public ToggleCellHighlightMessage(int board, int row, int column) {
			this.board = board;
			this.row = row;
			this.column = column;
		}
	}	
}
