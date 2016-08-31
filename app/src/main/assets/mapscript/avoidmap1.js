var tick = 0;



var mapWidth = 25;
var mapHeight = getMapHeight();

function run() {
    tick ++;

    if(tick==20) {
        var x = getPlayerX();
        var w = setWarningArea(x,0,1,mapHeight);
		var t = addText("W\nA\nR\nN\nI\nN\nG",x,12,0.7,getColor(255,255,255,255));
		setTextFormWidth(t,1);
        sleep(500);
        removeBlock(w);
		removeText(t);
        setAttackerVelY(addAttacker(x,mapHeight,1,1),-0.08);
    }else if(tick==50) {
        var x = getPlayerX();
        var w = setWarningArea(x,0,1,mapHeight);
		var t = addText("W\nA\nR\nN\nI\nN\nG",x,12,0.7,getColor(255,255,255,255));
		setTextFormWidth(t,1);
        sleep(500);
        removeBlock(w);
		removeText(t);
        setAttackerVelY(addAttacker(x,mapHeight,1,1),-0.11);
    }else if(tick==80) {
        var x = getPlayerX();
        var w = setWarningArea(x,0,1,mapHeight);
		var t = addText("W\nA\nR\nN\nI\nN\nG",x,12,0.7,getColor(255,255,255,255));
		setTextFormWidth(t,1);
        sleep(500);
        removeBlock(w);
		removeText(t);
        setAttackerVelY(addAttacker(x,mapHeight,1,1),-0.14);
    }else if(tick==110) {
        var x = getPlayerX();
        var w = setWarningArea(x,0,1,mapHeight);
		var t = addText("W\nA\nR\nN\nI\nN\nG",x,12,0.7,getColor(255,255,255,255));
		setTextFormWidth(t,1);
        sleep(500);
        removeBlock(w);
		removeText(t);
        setAttackerVelY(addAttacker(x,mapHeight,1,1),-0.3);
    }else if(tick==150) {
        clear();
    }
}


function setWarningArea(x,y,w,h) {
	return setBlock(x,y,w,h,[1.0,0.0,0.0,0.5],false);
}