package game.view;

import java.awt.Color;

public enum Colors {
	otoczka {
		public Color getColor(){
			return new Color(3,2,29);
		}
	},
	
	green {
		public Color getColor(){
			return new Color(0,164,255);
		}
	},
	
	jasnaOliwka {
		public Color getColor(){
			return new Color(180,202,157);
		}
	},
	
	napisy {
		public Color getColor(){
			return new Color(255,234,0);
		}
	},
	
	margines {
		public Color getColor(){
			return new Color(172,129,83);
		}
	},
	
	tlo {
		public Color getColor(){
			return new Color(10,9,30);
		}
	},
	
	humansCard {
		public Color getColor(){
			return new Color(10,55,41); 
		}
	},
	
	zombieCard {
		public Color getColor(){
			return new Color(255,90,30);
		}
	},
	
	boardsCard {
		public Color getColor(){
			return new Color(97,10,14);
		}
	};
	
	abstract public Color getColor();
}
