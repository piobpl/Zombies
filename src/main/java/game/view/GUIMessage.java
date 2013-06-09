package game.view;

import game.model.Card;
import game.model.Modifier;
import game.model.Player;
import game.model.Trap;
import game.view.EventReceiver.Event;
import game.view.GUI.Button;
import server.controller.Message;

public abstract class GUIMessage extends Message {

	private static final long serialVersionUID = -684702361391462220L;

	@Override
	public MessageType getType() {
		return MessageType.GUI;
	}

	public enum GUIMessageType {
		SetHandHighlight, DrawCellCard, DrawCellTraps, SetCellHighlight, SetCellRedHighlight, ToggleCellHighlight, SetBoardHighlight, SetBoardColumnHighlight, SetBoardRowHighlight, ClearBoardGlassText, SetGUIButtonEnabled, SetGUICardsLeft, ModelGUISends, DrawGlobalModifiers, SetGUIHighlight, ExitGUI, EventGUI;
	}

	public abstract GUIMessageType getSubType();

	public static class SetHandHighlightMessage extends GUIMessage {
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

	public static class DrawCellCardMessage extends GUIMessage {
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

	public static class DrawCellTrapsMessage extends GUIMessage {
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

	public static class SetCellHighlightMessage extends GUIMessage {
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

	public static class SetCellRedHighlightMessage extends GUIMessage {
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

	public static class ToggleCellHighlightMessage extends GUIMessage {
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

	public static class SetBoardHighlightMessage extends GUIMessage {
		private static final long serialVersionUID = 299264655882224002L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetBoardHighlight;
		}

		final boolean set;

		public SetBoardHighlightMessage(boolean set) {
			this.set = set;
		}
	}

	public static class SetBoardColumnHighlightMessage extends GUIMessage {
		private static final long serialVersionUID = -1890773289050564141L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetBoardColumnHighlight;
		}

		final int j;
		final boolean set;

		public SetBoardColumnHighlightMessage(int j, boolean set) {
			this.j = j;
			this.set = set;
		}
	}

	public static class SetBoardRowHighlightMessage extends GUIMessage {
		private static final long serialVersionUID = 7636608244592240363L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetBoardRowHighlight;
		}

		final boolean set;
		final int i;

		public SetBoardRowHighlightMessage(int i, boolean set) {
			super();
			this.set = set;
			this.i = i;
		}
	}

	public static class ClearBoardGlassTextMessage extends GUIMessage {
		private static final long serialVersionUID = 4336324652511904374L;

		public GUIMessageType getSubType() {
			return GUIMessageType.ClearBoardGlassText;
		}
	}

	public static class SetGUIButtonEnabledMessage extends GUIMessage {
		private static final long serialVersionUID = 7932781654100062600L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetGUIButtonEnabled;
		}

		final Button button;
		final boolean aktywny;

		public SetGUIButtonEnabledMessage(Button button, boolean aktywny) {
			this.button = button;
			this.aktywny = aktywny;
		}
	}

	public static class SetGUICardsLeftMessage extends GUIMessage {
		private static final long serialVersionUID = 6913019356194958775L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetGUICardsLeft;
		}

		final Player player;
		final int left;

		public SetGUICardsLeftMessage(Player player, int left) {
			this.player = player;
			this.left = left;
		}
	}

	public static class ModelGUISendsMessage extends GUIMessage {
		private static final long serialVersionUID = 7715462650421718336L;

		public GUIMessageType getSubType() {
			return GUIMessageType.ModelGUISends;
		}

		final String message;

		public ModelGUISendsMessage(String message) {
			this.message = message;
		}
	}

	public static class DrawGUIGlobalModifiersMessage extends GUIMessage {
		private static final long serialVersionUID = -6673547246305372646L;

		public GUIMessageType getSubType() {
			return GUIMessageType.DrawGlobalModifiers;
		}

		final Iterable<Modifier> modifiers;

		public DrawGUIGlobalModifiersMessage(Iterable<Modifier> modifiers) {
			this.modifiers = modifiers;
		}
	}

	public static class SetGUIHighlightMessage extends GUIMessage {
		private static final long serialVersionUID = -7661673438202398018L;

		public GUIMessageType getSubType() {
			return GUIMessageType.SetGUIHighlight;
		}

		final boolean set;

		public SetGUIHighlightMessage(boolean set) {
			this.set = set;
		}
	}

	public static class ExitGUIMessage extends GUIMessage {

		private static final long serialVersionUID = 3525530383546619224L;

		public GUIMessageType getSubType() {
			return GUIMessageType.ExitGUI;
		}
	}

	public static class EventGUIMessage extends GUIMessage {
		private static final long serialVersionUID = -2595768761154346307L;
		public final Event event;

		public EventGUIMessage(Event event) {
			this.event = event;
		}

		public GUIMessageType getSubType() {
			return GUIMessageType.EventGUI;
		}
	}
}
