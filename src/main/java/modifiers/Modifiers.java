package modifiers;
/**
 * 
 * Treat it as a flag: all humans have the same properties,
 * so use Modifiers.Human instead of new HumanModifier()
 * @author jerzozwierz
 * 
 */
public abstract class Modifiers {
	
	public static final HumanModifier Human = new HumanModifier();
}
