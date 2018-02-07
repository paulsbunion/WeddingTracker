/**
 * 
 */

window.onload = function() {
	// get the canvas and context and store in vars
	var canvas = document.getElementById("sky");
	var ctx = canvas.getContext("2d");
	
	// set canvas dims to window height and width
	var width = window.innerWidth;
	var height = window.innerHeight - 20;
	canvas.width = width*2;
	canvas.height = height;
	
	// generate snowflakes and apply attributes
	var maxFlakes = 1000;
	var minFlakeSize = 2;
	var maxFlakeSize = 7;
	var flakes = [];
	var angle = 0;
	
	// loop through flakes and apply attributes
	for (var i = 0; i < maxFlakes; i++) {
		flakes.push({ 
			x : (Math.random() * 3 - 1 )* width,
			y : Math.random() * height,
			radius : Math.random() * (maxFlakeSize - minFlakeSize) + minFlakeSize,
			density : Math.random() + 1
		})
	}
	
	// draw flakes
	function drawFlakes() {
		ctx.clearRect(0, 0 , width, height);
		ctx.fillStyle = "white";
		
		ctx.beginPath();
		var tempFlake;
		
		for (var i = 0; i < maxFlakes; i++) {
			tempFlake = flakes[i];
			ctx.moveTo(tempFlake.x, tempFlake.y);
//			ctx.moveTo(100, 100);
			ctx.arc(tempFlake.x, tempFlake.y, tempFlake.radius, 0, Math.PI * 2, true);
//			ctx.arc(100, 100, 50, 0, Math.PI * 2, true);
		}
		ctx.fill();
		update();
	}
	
	// update flake position
	function update() {
		angle += 0.01;
		for (var i = 0; i < maxFlakes; i++) {
			tempFlake = flakes[i];
			
			// update x, y
			tempFlake.y += Math.pow(tempFlake.density, 2) + 1;
			tempFlake.x += Math.sin(angle) * 2;
			
			// reset at bottom
			if (tempFlake.y >= height + tempFlake.radius) {
				flakes[i] = { 
						x : (Math.random() * 3 - 1 )* width, /*Math.random() * width,*/
						y : -10,
						radius : Math.random() * (maxFlakeSize - minFlakeSize) + minFlakeSize,
						density : Math.random() + 1
					}
//				tempFlake.y = height - tempFlake.radius;
			}
		}
	}
	
	setInterval(drawFlakes, 40);
}
