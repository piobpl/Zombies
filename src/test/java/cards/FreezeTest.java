package cards;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import model.Modifier;

import org.junit.Test;

import cards.humans.Freeze;
import controller.Controller;

public class FreezeTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Freeze freeze = new Freeze();
		freeze.makeEffect(null, controller.gameState);
		boolean a = false;
		Iterator<Modifier> it = controller.gameState.globalModifiers.iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("BeenFrozen")) {
				a = true;
			}
		}
		assertEquals(true, a);
	}
}