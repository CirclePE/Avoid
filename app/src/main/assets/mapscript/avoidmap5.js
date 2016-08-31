var time = 0;

var Thread = java.lang.Thread;

var blocks = new Array();
var attackers = new Array();

var mapHeight = getMapHeight();

function run() {
	time ++;
	
	if (time==20) {
		setBlock(5,4,5,0.7);
		setBlock(10,6,5,0.7);
		setBlock(5,8.5,5,0.7);
	}else if (time==35) {
		var b = setBlock(24,2,1,20,[1,0,0,0.5],false);
		var t = addText("W\nA\nR\nN\nI\nN\nG",24,mapHeight-3,0.7);
		setTextFormWidth(t,1);
		sleep(1000);
		removeBlock(b);
		removeText(t);
		for(var i = 0;i < 5;i ++) {
			attackers[i] = addAttacker(24.5,2+i,0.5,0.5);
			var a = attackers[i];
			var d = getDistance(a);
			setAttackerVelX(a,getGuidedXVel(a)/d*0.5);
			setAttackerVelY(a,getGuidedYVel(a)/d*0.5);
			sleep(200);
		}
		sleep(1000);
		for(var i = 0;i < 5;i ++) {
			removeAttacker(attackers[i]);
			attackers.slice(i,1);
		}
	}else if (time==36) {
		var b = setBlock(0,2,1,20,[1,0,0,0.5],false);
		var t = addText("W\nA\nR\nN\nI\nN\nG",0,mapHeight-3,0.7);
		setTextFormWidth(t,1);
		sleep(500);
		removeBlock(b);
		removeText(t);
		for(var i = 0;i < 5;i ++) {
			attackers[i] = addAttacker(0,2+i,0.5,0.5);
			var a = attackers[i];
			var d = getDistance(a);
			setAttackerVelX(a,getGuidedXVel(a)/d*0.5);
			setAttackerVelY(a,getGuidedYVel(a)/d*0.5);
			sleep(200);
		}
		sleep(1000);
		for(var i = 0;i < 5;i ++) {
			removeAttacker(attackers[i]);
			attackers.slice(i,1);
		}
	}else if (time==40) {
		var i = 0;
		for(var c = 0;c < 3;c ++) {
			for(var vertex = 0;vertex < 7;vertex ++) {
				attackers[i] = addAttacker(getPlayerX()+Math.sin(vertex*Math.PI/(3.5))*6,getPlayerY()+Math.cos(vertex*Math.PI/(3.5))*6,0.3,0.3);
				var a = attackers[i];
				var d = getDistance(a);
				setAttackerVelX(a,getGuidedXVel(a)/d*0.05);
				setAttackerVelY(a,getGuidedYVel(a)/d*0.05);
				
				i ++;
			}
			
			sleep(500);
		}
		sleep(2000);
		for(var i = 0;i < attackers.length;i ++) {
			removeAttacker(attackers[i]);
			attackers.slice(i,1);
		}
	}else if (time==55) {
		blockClean();
	}else if (time==70) {
		var b = setBlock(0,0,25,3.5,[1,0,0,0.5],false);
		var t = addText("WARNING",0,3,1);
		setTextFormWidth(t,25);
		sleep(800);
		removeBlock(b);
		removeText(t);
		for(var x = 0;x < 31;x ++) {
			
			if(x<=24) {
				attackers[x] = addAttacker(x,-2,1,3.5);
				setAttackerVelY(attackers[x],0.2);
			}
			if(x>=6) {
				setAttackerVelY(attackers[x-6],-0.2);
			}
			
			sleep(25);
		}
		attackerClean();
	}else if (time==90) {
		for(var c = 0;c < 5;c ++) {
			var targetY = getPlayerY()+0.25;
			var b = setBlock(0,targetY,25,0.5,[1,0,0,0.5],false);
			sleep(800);
			removeBlock(b);
		
			var a = addAttacker(0,targetY+0.25,0.5,0.5);
			setAttackerVelX(a,0.3);
			a = addAttacker(24.5,targetY+0.25,0.5,0.5);
			setAttackerVelX(a,-0.3);
		}
	}else if (time==150) {
		clear();
	}
}

function getGuidedXVel(attacker) {
	return (getPlayerX()-getAttackerX(attacker));
}

function getGuidedYVel(attacker) {
	return (getPlayerY()-getAttackerY(attacker));
}

function getDistance(attacker) {
	return Math.sqrt(Math.pow(getPlayerX()-getAttackerX(attacker),2)+Math.pow(getPlayerY()-getAttackerY(attacker),2));
}
