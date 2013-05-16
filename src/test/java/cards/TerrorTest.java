package cards;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import model.Modifier;

import org.junit.Test;

import cards.zombies.Terror;
import controller.Controller;

public class TerrorTest {

	@Test
	public void test() {
		Controller controller = new Controller();
		Terror terror = new Terror();
		terror.makeEffect(null, controller.gameState);
		boolean a = false;
		Iterator<Modifier> it = controller.gameState.globalModifiers.iterator();
		while (it.hasNext()) {
			if(it.next().getDescription().equals("Terror")){
				a=true;
			}
		}
		assertEquals(true, a);
	}
}
