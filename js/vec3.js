function Vec3(x, y, z) {
    this.x = x;
    this.y = y;
    this.z = z;
}

Vec3.prototype.add = function(vec) {
    return new Vec3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
};

Vec3.prototype.subtract = function(vec) {
    return new Vec3(this.x - vec.x, this.y - vec.y, this.z - vec.z);
};

Vec3.prototype.multiply = function(vec) {
    return new Vec3(this.x * vec.x, this.y * vec.y, this.z * vec.z);
};

Vec3.prototype.abs = function() {
    return new Vec3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
};

Vec3.prototype.floor = function() {
    return new Vec3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
};

Vec3.prototype.negate = function() {
    return new Vec3(-this.x, -this.y, -this.z);
};

Vec3.prototype.fadd = function(scalar) {
    return new Vec3(this.x + scalar, this.y + scalar, this.z + scalar);
};

Vec3.prototype.fmultiply = function(scalar) {
    return new Vec3(this.x * scalar, this.y * scalar, this.z * scalar);
};

Vec3.prototype.fdivide = function(scalar) {
    return new Vec3(this.x / scalar, this.y / scalar, this.z / scalar);
};

Vec3.prototype.sum = function() {
    return this.x + this.y + this.z;
};

Vec3.prototype.product = function() {
    return this.x * this.y * this.z;
};

Vec3.prototype.magnitude = function() {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
};

Vec3.prototype.dot = function(vec) {
    return this.multiply(vec).sum();
};

Vec3.prototype.pow2 = function() {
    return new Vec3(this.x * this.x, this.y * this.y, this.z * this.z);
};

Vec3.prototype.pow3 = function() {
    return new Vec3(this.x * this.x * this.x,
                    this.y * this.y * this.y,
                    this.z * this.z * this.z);
};

Vec3.prototype.toString = function() {
    return ['[Vec3 ', '(', this.x, ', ', this.y, ', ', this.z, ')]'].join('');
};

Vec3.prototype.normalize = function() {
    return this.fdivide(this.magnitude());
};
