if (window.requestAnimationFrame == null) {
    window.requestAnimationFrame =
        window.mozRequestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        function(f) {
            setTimeout(f, 16);
        };
}

var display;  // for skewer

function draw() {
    display.depth += 0.03;
    display.render();
    window.requestAnimationFrame(draw);
}

window.addEventListener('load', function() {
    var canvas = document.getElementById('screen');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    try {
        var gl = canvas.getContext('webgl') ||
                 canvas.getContext('experimental-webgl');
        if (gl == null) throw new Error('Could not get WebGL context.');
        display = new Display(gl);
    } catch (e) {
        alert('Unable to initialize WebGL: ' + e);
        throw e;
    }
    window.requestAnimationFrame(draw);
});

window.addEventListener('resize', function() {
    var canvas = document.getElementById('screen');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    display.gl.viewport(0, 0, canvas.width, canvas.height);
});
