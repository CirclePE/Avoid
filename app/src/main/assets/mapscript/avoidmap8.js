var tick = 0;

var mapWidth = 25;
var mapHeight = getMapHeight();

function run() {
	tick ++;
	if (tick==40) {
		var la;
		var ra;
		for(var sec = 0;sec < 6000;sec ++) {
			if (sec%300==0) {
				var a = setDrawC(-10,Math.random()*mapHeight,Math.random()*3+2,0.05,[0.5,0.5,1.0,0.8]);
				setBlockVelX(a,Math.random()*0.15+0.1);
			}
			if (sec==350) {
				setPlayerVelX(0.15);
				setWarningAreaC(24.5,mapHeight/2,1,mapHeight);
				setWarningAreaC(0.5,mapHeight/2,1,mapHeight);
			}else if (sec==1000) {
				la = addAttackerC(getPlayerX()-5,mapHeight/2,0.2,mapHeight);
				ra = addAttackerC(getPlayerX()+5,mapHeight/2,0.2,mapHeight);
				addAttackerC(24.5,mapHeight/2,1,mapHeight);
				addAttackerC(0.5,mapHeight/2,1,mapHeight);
			}else if (sec==1200) {
				setAttackerVelX(la,0.2);
				setAttackerVelX(ra,-0.2);
			}else if (sec==1340) {
				setAttackerVelX(la,0);
				setAttackerVelX(ra,0);
			}
			sleep(1);
		}
		sleep(3500);
		setPlayerVelX(0);
		clean();
	}else if (tick==60) {
		setPlayerVelX(-1);
		var t = addText("DON'T TOUCH",10,10,1);
		sleep(1000);
		setText(t,"→GO");
		addAttacker(3,0,1,10);
		addAttacker(6.2,0,1,10);
		addAttacker(9.4,3.5,1,mapHeight);
		addAttacker(12.6,0,1,6);
		addAttacker(12.6,9,1,mapHeight);
		addAttacker(15.8,0,3,6);
		addAttacker(15.8,9,1,mapHeight);
		
		setDraw(23,0,2,mapHeight,[0.3,0.3,1.0,0.5]);
		var t2 = addText("HERE",23,mapHeight/2,0.4);
		setTextFormWidth(t2,2);
		setPlayerVelX(0);
		
		while(true) {
			if(getPlayerX()>4) {
				setAttackerVelX(addAttacker(0,0,0.02,mapHeight),0.03);
				removeText(t);
				break;
			}
			sleep(300);
		}
		
		while(true) {
			if(getPlayerX()>23) {
				clean();
				break;
			}
			sleep(300);
		}
	}else if (tick==80) {
		var w = setWarningArea(0,7.2,25,mapHeight);
		var t = addText("←Go and DON'T JUMP",0,mapHeight-3,1);
		setTextFormWidth(t,mapWidth);
		sleep(1800);
		removeBlock(w);
		removeText(t);
		addAttacker(0,7.2,25,mapHeight);
		
		var b = setBlock(0,0,25,1,[0.8,0.8,0.8,0.5]);
		setBlockVelY(b,0.1);
		while(true) {
			if(getBlockY(b)>5) {
				setBlockVelY(b,0);
				setBlockY(b,5);
				setEnableGround(false);
				break;
			}
			sleep(1);
		}
		sleep(1000);
		setBlockVelX(b,-0.1);
		while(true) {
			if(getBlockX(b)<-22) {
				setBlockVelX(b,0);
				setBlockX(b,-22);
				break;
			}
			sleep(1);
		}
		
		setBlock(23,0,2,1);
		setDraw(23,1,2,2,[0.3,0.3,1.0,0.5]);
		
		var bcks = new Array();
		var j = 0;
		for(var i = 0;;i ++) {
			if(i%4==0) j ++;
			
			if(getPlayerX()>23&&getPlayerY()<3) {
				setEnableGround(true);
				clean();
				break;
			}
			
			removeBlocks(bcks);
			bcks = new Array();
			for(var x = 1;x <= 8;x ++) {
				bcks.push(setBlock(j%3+x*3,5.8,1,0.2));
			}
			sleep(300);
		}
	}else if(tick==100) {
		var w = setWarningArea(24,0,1,mapHeight);
		sleep(1000);
		removeBlock(w);
		var a = addAttacker(25,2,0.5,0.5);
		setAttackerVelX(a,-0.05);
		setAttackerVelY(a,0.8);
		while(true) {
			if(getAttackerY(a)>mapHeight) {
				setAttackerVelY(a,-(Math.random()*0.5+0.4));
				setAttackerY(a,mapHeight);
			}else if(getAttackerY(a)<2) {
				setAttackerVelY(a,Math.random()*0.5+0.4);
				setAttackerY(a,2);
			}
			
			if(getAttackerX(a)<getPlayerX()) {
				break;
			}
			sleep(1);
		}
		
		sleep(1000);
		clear();
	}
}

function setWarningArea(x,y,w,h) {
	return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}

function setWarningAreaC(x,y,w,h) {
	return setBlock(x-w/2,y-h/2,w,h,[1.0,0.0,0.0,0.2],false);
}

function setDraw(x,y,w,h,c) {
	return setBlock(x,y,w,h,c,false);
}

function setDrawC(x,y,w,h,c) {
	return setBlock(x-w/2,y-h/2,w,h,c,false);
}

function setBlcokXC(id,x) {
	setBlockX(id,x-getBlockWidth(id)/2);
}
function setBlcokYC(id,y) {
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

function removeBlocks(arr) {
	for(var i = 0;i < arr.length;i ++) {
		removeBlock(arr[i]);
	}
}
