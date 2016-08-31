var tick = 0;



var mapWidth = 25;
var mapHeight = getMapHeight();

function run() {
    tick ++;
	if(tick==20) {
		var w = setWarningArea(0,0,1,mapHeight);
		sleep(700);
		setBlock(2,5,3,0.5);
		setBlock(8,7,3,0.5);
		setBlock(14,9,3,0.5);
		setBlock(20,11,3,0.5);
		sleep(800);
		removeBlock(w);
		var unit = new MyUnit();
		unit.start();
		
		while(true) {
			if(unit.turn<3) {
				unit.run();
			}else {
				if(getAttackerX(unit.ba)<0) {
					sleep(1000);
					clean();
					sleep(1000);
					clear();
				}
			}
		}
	}
}

function MyUnit() {
	this.turn = 0;
	this.speed = 0.1;
	this.y = [9,5,11,7];
	this.ba = addAttacker(0,0,1,this.y[0]);
	this.ua = addAttacker(0,this.y[0]+2,1,mapHeight);
	
	this.start = function() {
		setAttackerVelX(this.ba,this.speed);
		setAttackerVelX(this.ua,this.speed);
		setAttackerX(this.ua,0);
		setAttackerX(this.ba,0);
	};
	
	this.run = function() {
		var x = getAttackerX(this.ba);
		
		if(x<0) {
			this.turn ++;
			setAttackerVelX(this.ba,this.speed);
			setAttackerVelX(this.ua,this.speed);
			setAttackerX(this.ua,0);
			setAttackerX(this.ba,0);
			this.setY();
		}else if(x>24) {
			this.turn ++;
			setAttackerVelX(this.ba,-this.speed);
			setAttackerVelX(this.ua,-this.speed);
			setAttackerX(this.ua,24);
			setAttackerX(this.ba,24);
			this.setY();
		}
	};
	
	this.setY = function () {
		setAttackerHeight(this.ba,this.y[this.turn]);
		setAttackerY(this.ua,this.y[this.turn]+2);
	};
}

function setWarningArea(x,y,w,h){
    return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}