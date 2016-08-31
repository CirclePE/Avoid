var tick = 0;



var mapWidth = 25;
var mapHeight = getMapHeight();

		

function run() {
	tick ++;
	if (tick==40) {
		setBlock(5,5,5,0.7);
		setBlock(10,7,5,0.7);
		setBlock(15,9,5,0.7);
		for(var count = 0;count < 3;count ++) {
			var y = 0;
			var id = 0;
			var w = setBlock(0,getPlayerY()+0.25,25,0.5,[1.0,0.0,0.0,0.5],false);
			var t = addText("1000",0,getPlayerY()+0.75,0.4);
			setTextFormWidth(t,mapWidth);
			for(var i = 0;i < 1000;i ++) {
				y = getPlayerY();
				setBlockY(w,y+0.25);
				setText(t,"" + Math.floor((999-i)/50));
				setTextY(t,y+0.75);
				sleep(1);
			}
			removeText(t);
			sleep(500);
			removeBlock(w);
			addAttacker(0,y,25,1);
		}
		sleep(500);
		clean();
	}else if (tick==60) {
		clear();
	}
}

function setWarningArea(x,y,w,h) {
	return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}

function setWarningAreaC(x,y,w,h) {
	return setBlock(x-w/2,y-h/2,w,h,[1.0,0.0,0.0,0.2],false);
}
