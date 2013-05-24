package game.view;

import java.awt.Color;

public enum Colors {
	otoczka {
		Color getColor(){
			return new Color(3,2,29);
		}
	},
	
	green {
		Color getColor(){
			return new Color(0,164,255);
		}
	},
	
	jasnaOliwka {
		Color getColor(){
			return new Color(180,202,157);
		}
	},
	
	napisy {
		Color getColor(){
			return new Color(255,234,0);
		}
	},
	
	margines {
		Color getColor(){
			return new Color(172,129,83);
		}
	},
	
	tlo {
		Color getColor(){
			return new Color(10,9,30);
		}
	},
	
	humansCard {
		Color getColor(){
			return new Color(10,55,41); 
		}
	},
	
	zombieCard {
		Color getColor(){
			return new Color(255,90,30);
		}
	},
	
	boardsCard {
		Color getColor(){
			return new Color(97,10,14);
		}
	};
	
	abstract Color getColor();
}
