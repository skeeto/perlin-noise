function Display(gl) {
    this.gl = gl;
    this.program = new Program(gl, 'src/identity.vert', 'src/perlin.frag');
    this.background = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, this.background);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array([
         1.0,  1.0,
        -1.0,  1.0,
         1.0, -1.0,
        -1.0, -1.0
    ]), gl.STATIC_DRAW);
    this.program.attrib('position', this.background, 2);
};

Display.prototype.depth = 100;
Display.prototype.scale = 30;
Display.prototype.seed = 82.2305374146;

Display.prototype.render = function() {
    this.gl.clearColor(0.83, 0.83, 0.83, 1.0);
    this.gl.clear(this.gl.COLOR_BUFFER_BIT);
    this.program.use()
        .uniform('scale', this.scale)
        .uniform('depth', this.depth)
        .uniform('seed', this.seed)
        .draw(this.gl.TRIANGLE_STRIP, 4);
};
