var time = 0;

var Thread = java.lang.Thread;

var mapHeight = getMapHeight();
var blocks = new Array();
var attackers = new Array();

//setEnableGround(false);

//setViewRotate(50);

function run() {
	time ++;
	
	if (time==20) {
		var b = setBlock(0,0,25,3.5,[1,0,0,0.5],false);
		var t = addText("WARNING",0,3,1);
		setTextFormWidth(t,25);
		sleep(1200);
	
		
		for(var i = 0;i < 20;i ++) {
			var block = setBlock(Math.random()*21,mapHeight,3,0.8);
			setBlockVelY(block,-0.1);
			
			sleep(500);
			if(i==2) {
				removeBlock(b);
				removeText(t);
				setEnableGround(false);
			}
			if (i>=10) {
				var tarX = getPlayerX()+0.25;
				var tarY = getPlayerY()+0.25;
				var lw = setBlock(tarX,tarY,0.5,0.5,[1,0,0,0.5],false);
				var la = addAttacker(0,Math.random()*(mapHeight-0.5),0.5,0.5);
				sleep(500);
				var ld = getDistance(tarX,tarY,la);
				removeBlock(lw);
				setAttackerVelX(la,getGuidedXVel(tarX,la)/ld*0.5);
				setAttackerVelY(la,getGuidedYVel(tarY,la)/ld*0.5);
			}
		}
		sleep(200);
		setEnableGround(true);
		sleep(1500);
	}else if (time==50) {
		var cenX = 12.5;
		var cenY = mapHeight/2;

        var w1 = setWarningAreaC(cenX,cenY,2,2);
        var w2 = setWarningAreaC(cenX,cenY,4,4);
        sleep(1500);
        removeBlock(w1);
        removeBlock(w2);
		for(var i = 0;i < 7;i ++) {
			var angle = -90+Math.random()*180;
			var tarX = cenX+Math.sin(angle*Math.PI/180);//*0.15;
			var tarY = cenY+Math.cos(angle*Math.PI/180);//*0.15;
			var gX = tarX-cenX;
			var gY = tarY-cenY;
			//var d = getDistanceP(tarX,tarY,cenX,cenY);
			var a = addAttacker(cenX-0.25,cenY-0.25,0.5,0.5);
			attackers[i] = a;
			setAttackerVelX(a,gX*0.15);
			setAttackerVelY(a,gY*0.15);
			//sleep(00);
			
		}
		for(var sec = 0;sec < 5000;sec ++) {
			for(var i = 0;i < 7;i ++) {
				var a = attackers[i];
				var ax = getAttackerX(a);
				var ay = getAttackerY(a)
				if (ax>24.5) {
					//appendLog("X> | ");
					setAttackerX(a,24.5);
					setAttackerVelX(a,-getAttackerVelX(a));
				}else if(ax<0) {
					//appendLog("X< | ");
					setAttackerX(a,0);
					setAttackerVelX(a,-getAttackerVelX(a));
				}
				
				if (ay>mapHeight-0.5) {
					//appendLog("Y> | ");
					setAttackerY(a,mapHeight-0.5);
					setAttackerVelY(a,-getAttackerVelY(a));
				}else if (ay<2) {
					//appendLog("Y< | ");
					setAttackerY(a,2);
					setAttackerVelY(a,-getAttackerVelY(a));
				}
			}
			sleep(1);
		}
		sleep(3000);
		clean();
	}else if(time==60) {
		
		
		setBlock(0,mapHeight/2-1,25,0.5);
		var left = addAttacker(0,0,0.5,mapHeight);
		var top = addAttacker(0,mapHeight-0.5,25,0.5);
		var right = addAttacker(24.5,0,0.5,mapHeight);
		var bottom = addAttacker(0,0,25,0.5);
		sleep(1000);
		setAttackerVelX(left,0.05);
		setAttackerVelX(right,-0.05);
		setAttackerVelY(bottom,0.01);
		setAttackerVelY(top,-0.05);
		sleep(500);
		setAttackerVelX(left,0.1);
		setAttackerVelX(right,-0.1);
		setAttackerVelY(bottom,0.1);
		setAttackerVelY(top,-0.1);
		
		for(var l = false,r = false,t = false,b = false;;) {
			if(getAttackerX(left)>=9.5) {
				l = true;
				setAttackerVelX(left,0);
			}
			
			if(getAttackerY(top)<=mapHeight/2+2) {
				t = true;
				setAttackerVelY(top,0);
			}
			
			if(getAttackerX(right)<=15) {
				r = true;
				setAttackerVelX(right,0);
			}
			
			if(getAttackerY(bottom)>=mapHeight/2-1.5) {
				b = true;
				setAttackerVelY(bottom,0);
			}
			
			if(l&&t&&r&&b) {
				break;
			}
		}
		
		sleep(1000);
		
		goLeft(left,right,300);
		sleep(1000);
		goRight(left,right,600);
		sleep(1000);
		goLeft(left,right,300);
		sleep(1000);
		clean();
		sleep(3000);
		clear();
	}
}


function setWarningAreaC(x,y,w,h) {
	return setBlock(x-w/2,y-h/2,w,h,[1.0,0.0,0.0,0.2],false);
}

function goLeft(left,right,dur) {
	setAttackerVelX(left,-0.22);
	sleep(300);
	setAttackerVelX(right,-0.22);
	sleep(dur);
	setAttackerVelX(left,0);
	sleep(300);
	setAttackerVelX(right,0);
}

function goRight(left,right,dur) {
	setAttackerVelX(right,0.22);
	sleep(300);
	setAttackerVelX(left,0.22);
	sleep(dur);
	setAttackerVelX(right,0);
	sleep(300);
	setAttackerVelX(left,0);
}


function getGuidedXVel(attacker) {
	return (getPlayerX()-getAttackerX(attacker));
}

function getGuidedYVel(attacker) {
	return (getPlayerY()-getAttackerY(attacker));
}

function getGuidedXVel(x,attacker) {
	return (x-getAttackerX(attacker));
}

function getGuidedYVel(y,attacker) {
	return (y-getAttackerY(attacker));
}

function getDistance(attacker) {
	return Math.sqrt(Math.pow(getPlayerX()-getAttackerX(attacker),2)+Math.pow(getPlayerY()-getAttackerY(attacker),2));
}

function getDistance(x,y,attacker) {
	return Math.sqrt(Math.pow(x-getAttackerX(attacker),2)+Math.pow(y-getAttackerY(attacker),2));
}

function getDistanceP(x,y,x2,y2) {
	return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
}
