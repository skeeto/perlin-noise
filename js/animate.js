var perlin = null;
var z = 0;
var scale = 32;
var ctx = null;

function step() {
    console.log('step ' + z);
    var start = Date.now();
    perlin.render(ctx, z, scale);
    console.log('Took ' + ((Date.now() - start) / 1000) + ' seconds.');
    z += 1;
    window.requestAnimationFrame(step);
}

window.onload = function() {
    ctx = document.getElementById('screen').getContext('2d');
    ctx.fillStyle = 'red';
    ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    perlin = new Perlin(ctx.canvas.width / scale, ctx.canvas.width / scale);
    step();
};
