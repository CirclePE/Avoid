var tick = 0;

var Thread = java.lang.Thread;

var mapWidth = 25;
var mapHeight = getMapHeight();

function run() {
	tick ++;
	if (tick==40) {
		var b1 = setBlock(0,0,10.5,3,[1.0,0,0,0.5],false);
		var t1 = addText("WARNING",0,2,1);
		setTextFormWidth(t1,10);
		var b2 = setBlock(14.5,0,11,3,[1.0,0,0,0.5],false);
		var t2 = addText("WARNING",14,2,1);
		setTextFormWidth(t2,11);
		sleep(1000);
		var block = setBlock(10.5,1.5,4,1);
		sleep(1000);
		removeBlock(b1);
		removeBlock(b2);
		removeText(t1);
		removeText(t2);
		setEnableGround(false);
		setBlockVelX(block,0.1);
		
		var xaim;
		var yaim;
		
		for(var sec = 0;sec < 10000;sec ++) {
			var bx = getBlockX(block);
			if (bx<=0) {
				setBlockX(block,0);
				setBlockVelX(block,-getBlockVelX(block));
			}else if (bx>=21) {
				setBlockX(block,21);
				setBlockVelX(block,-getBlockVelX(block));
			}
			
			if (getPlayerY()==2.5) {
				setPlayerVelX(getBlockVelX(block));
			}else {
				setPlayerVelX(0);
			}
			
			if (sec==3000) {
				xaim = setWarningAreaC(getPlayerX()+0.5,mapHeight/2,0.2,mapHeight);
				yaim = setWarningAreaC(mapWidth/2,getPlayerY()+0.5,mapWidth,0.2);
			}else if(sec>3000) {
				setBlockXC(xaim,getPlayerX()+0.5);
				setBlockYC(yaim,getPlayerY()+0.5);
				
				if (sec%700==0) {
					var xa = addAttackerC(getPlayerX()+0.5,mapHeight,0.2,0.2);
					setAttackerVelY(xa,-0.2);
					var ya = addAttackerC(0,getPlayerY()+0.5,0.2,0.2);
					setAttackerVelX(ya,0.2);
				}
			}
			
			if(sec==9000) {
				setEnableGround(true);
			}
			
			sleep(1);
		}
		
		sleep(500);
		setPlayerVelX(0);
		clean();
	}else if (tick==60) {
		var warr = [];
		warr[0] = setWarningAreaC(4.8,mapHeight/2,0.2,mapHeight);
		warr[1] = setWarningAreaC(20.2,mapHeight/2,0.2,mapHeight);
		sleep(800);
		removeAttacker(warr[0]);
		removeAttacker(warr[1]);
		addAttackerC(4.8,mapHeight/2,0.2,mapHeight);
		addAttackerC(20.2,mapHeight/2,0.2,mapHeight);
		sleep(1000);
		warr = setWarningAreaC(mapWidth/2,9.8,1,1);
		sleep(800);
		removeAttacker(warr);
		var xv = 0.2;
		var yv = -0.2;
		var arr = new Array();
		for (var i = 0;i < 14;i ++) {
			arr.push(addAttackerC(mapWidth/2,9.8,1,1));
		}
		
		sleep(300);
		for (var i = 1;i < 14;i ++) {
			var vel = xv;
			if(i<=7) {
				vel = -xv;
			}
			
			setAttackerAllVel(arr[i],vel,yv);
		}
		sleep(200);
		attakersStop(arr);
		
		sleep(300);
		for (var i = 2;i < 14;i ++) {
			if(i==8) {
				continue;
			}
			var vel = xv;
			if(i%2==0) {
				vel = -xv;
			}
			
			setAttackerAllVel(arr[i],vel,yv);
		}
		sleep(200);
		attakersStop(arr);
		
		sleep(300);
		for (var i = 4;i < 14;i ++) {
			if(i==8||i==9) {
				continue;
			}
			var vel = xv;
			if(i%2==0) {
				vel = -xv;
			}
			
			setAttackerAllVel(arr[i],vel,yv);
		}
		sleep(200);
		attakersStop(arr);
		removeAttacker(arr[5]);
		removeAttacker(arr[13]);
		removeAttacker(arr[6]);
		removeAttacker(arr[12]);
		sleep(1000);
		clean();
	}else if (tick==80) {
		var pos = [[5,3],
				   [5,mapHeight-5],
				   [mapWidth/2,mapHeight-5],
				   [mapWidth-5,3],
				   [mapWidth-5,mapHeight-5]];
				   
		for(var i = 0;i < 5;i ++) {
			var maxVtx = 10;
			var x = pos[i][0];
			var y = pos[i][1];
			
			for(var vertex = 0;vertex < maxVtx;vertex ++) {
				a = addAttacker(x-0.2,y-0.2,0.4,0.4);
				setAttackerVelX(a,getGuidedXVel(x,x+Math.sin(vertex*Math.PI/(maxVtx/2))*0.1));
				setAttackerVelY(a,getGuidedYVel(y,y+Math.cos(vertex*Math.PI/(maxVtx/2))*0.1));
			}
			sleep(200);
		}
		
		sleep(5000);
		clear();
	}
}

function setWarningArea(x,y,w,h) {
	return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}

function setWarningAreaC(x,y,w,h) {
	return setBlock(x-w/2,y-h/2,w,h,[1.0,0.0,0.0,0.2],false);
}

function setBlockXC(id,x) {
	setBlockX(id,x-getBlockWidth(id)/2);
}
function setBlockYC(id,y) {
	setBlockY(id,y-getBlockHeight(id)/2);
}

function addAttackerC(x,y,w,h) {
	return addAttacker(x-w/2,y-h/2,w,h);
}

function getAttackerXC(a) {
	return getAttackerX(a)+getAttackerWidth(a)/2;
}

function getAttackerYC(a) {
	return getAttackerY(a)+getAttackerHeight(a)/2;
}

function setAttackerAllVel(id,xv,yv,dur) {
	setAttackerVelX(id,xv);
	setAttackerVelY(id,yv);
}

function attakersStop(arr) {
	for(var i = 0;i < arr.length;i ++) {
		setAttackerVelX(arr[i],0);
		setAttackerVelY(arr[i],0);
	}
}

function getGuidedXVel(x1,x2) {
	return (x1-x2);
}

function getGuidedYVel(y1,y2) {
	return (y1-y2);
}

function getDistance(x1,y1,x2,y2) {
	return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
}
