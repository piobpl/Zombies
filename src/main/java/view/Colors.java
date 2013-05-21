package view;

import java.awt.Color;

public enum Colors {
	jasnaOliwka {
		Color getColor(){
			return new Color(190,225,116);
		}
	},
	
	humansCard {
		Color getColor(){
			return new Color(138,39,39); 
		}
	},
	
	zombieCard {
		Color getColor(){
			return new Color(52,68,50);
		}
	},
	
	boardsCard {
		Color getColor(){
			return new Color(243,107,32);
		}
	};
	
	abstract Color getColor();
}
