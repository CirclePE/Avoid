var tick = 0;

var Thread = java.lang.Thread;

var mapWidth = 25;
var mapHeight = getMapHeight();

function run() {
    tick ++;
	if(tick==20) {
		var w1 = setWarningArea(0,0,0.2,mapHeight);
		var w2 = setWarningArea(24.8,0,0.2,mapHeight);
		sleep(1000);
		removeBlock(w1);
		removeBlock(w2);
		var la = addAttacker(0,0,0.2,mapHeight);
		setAttackerVelX(la,0.06);
		var ra = addAttacker(24.8,0,0.2,mapHeight);
		setAttackerVelX(ra,-0.06);
		
		for(var l = false,r = false;!(l&&r);) {
			if(getAttackerX(la)>5) {
				setAttackerVelX(la,0);
				setAttackerX(la,5);
				l = true;
			}
			
			if(getAttackerX(ra)<19.8) {
				setAttackerVelX(ra,0);
				setAttackerX(ra,19.8);
				r = true;
			}
		}
		sleep(500);
		
		var arr = [];
		
		for(var i = 0;i < 5000;i ++) {
			if(i%150==0&&i<=4500) {
				var unit = new MyUnit(11.5,13,1,1);
				setAttackerVelX(unit.id,Math.random()*0.2-0.1);
				setAttackerVelY(unit.id,0.1);
				arr.push(unit);
			}
			
			for(var j = 0;j < arr.length;j ++) {
				//setLog("" + arr.length);
				var a = arr[j];
				a.run();
				/*if(a.getY()<5) {
					//removeAttacker(a.id);
					arr.slice(j,1);
					break;
					//appendLog("" + arr.length);
				}*/
			}
			sleep(1);
		}
		clean();
	}else if(tick==60) {
		var arr = [];
		
		for(var x = 0;x < 1000;x ++) {
			if(x%20==0&&x<=250) {
				var unit = new MyUnit(x/10,13,1,1);
				setAttackerVelX(unit.id,Math.random()*0.2-0.1);
				setAttackerVelY(unit.id,0.1);
				arr.push(unit);
			}
			
			
			for(var j = 0;j < arr.length;j ++) {
				var a = arr[j];
				a.run();
			}
			sleep(1);
		}
		
		sleep(1000);
		clean()
		sleep(100);
		clear();
	}
}

function MyUnit(x,y,w,h) {
	this.id = addAttacker(x,y,w,h);
	
	this.run = function() {
		setAttackerVelY(this.id,getAttackerVelY(this.id) - 0.001);
	};
}

function setWarningArea(x,y,w,h){
    return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}
