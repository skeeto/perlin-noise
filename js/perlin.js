function Perlin(width, height) {
    this.width = width;
    this.height = height;
    this.cache = [];
}

Perlin.random = function() {
    return new Vec3(Math.random() * 2 - 1,
                    Math.random() * 2 - 1,
                    Math.random() * 2 - 1).normalize();
};

Perlin.prototype.gradient = function(vec) {
    var p = (vec.z * this.width * this.height) +
            (vec.y * this.width) + vec.x;
    while (this.cache.length < p + 1) {
        this.cache.push(Perlin.random());
    }
    return this.cache[p];
};

Perlin.prototype.sample = function(point) {
    var floor = point.floor();
    var sum = 0;
    for (var x = 0; x <= 1; x++) {
        for (var y = 0; y <= 1; y++) {
            for (var z = 0; z <= 1; z++) {
                var q = floor.add(new Vec3(x, y, z));
                var g = this.gradient(q);
                var m = g.dot(point.subtract(q));
                var t = point.subtract(q).abs().negate().fadd(1);
                var w = t.pow2().fmultiply(3).subtract(t.pow3().fmultiply(2));
                sum += w.product() * m;
            }
        }
    }
    return sum;
};

Perlin.prototype.render = function(ctx, z, scale) {
    var w = ctx.canvas.width, h = ctx.canvas.height;
    var image = ctx.getImageData(0, 0, w, h);
    for (var y = 0; y < h; y++) {
        for (var x = 0; x < w; x++) {
            var point = new Vec3(x / scale, y / scale, z / scale);
            var sample = this.sample(point);
            sample = Math.floor((sample + 1) * 128);
            image.data[(y * w + x) * 4 + 0] = sample;
            image.data[(y * w + x) * 4 + 1] = sample;
            image.data[(y * w + x) * 4 + 2] = sample;
            image.data[(y * w + x) * 4 + 3] = 255;
        }
    }
    ctx.putImageData(image, 0, 0);
};
