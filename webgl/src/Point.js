/**
 * @param {number} x
 * @param {number} y
 * @param {number} z
 * @constructor
 */
function Point(x, y, z) {
    this.x = x;
    this.y = y;
    this.z = z;
}

/**
 * Shorthand Point construction.
 * @param {number} x
 * @param {number} y
 * @param {number} z
 * @returns {Point}
 */
function P(x, y, z) {
    return new Point(x, y, z);
}

/**
 * @returns {string}
 * @method
 */
Point.prototype.toString = function() {
    return "(" + this.x + ", " + this.y + ", " + this.z + ")";
};

/**
 * @returns {string} a CSS rgba() string.
 * @method
 */
Point.prototype.toColor = function() {
    return 'rgba(' + ~~(255 * this.x) + ',' + ~~(255 * this.y) + ',' +
        ~~(255 * this.z) + ',1)';
};

/**
 * @returns {number} the distance from the origin.
 * @method
 */
Point.prototype.abs = function() {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
};

/**
 * @param {number} s Divisor.
 * @returns {Point} a new point.
 * @method
 */
Point.prototype.div = function(s) {
    return new Point(this.x / s, this.y / s, this.z / s);
};

/**
 * @param {number} s Multiplier.
 * @returns {Point} a new point.
 * @method
 */
Point.prototype.mult = function(s) {
    return new Point(this.x * s, this.y * s, this.z * s);
};

/**
 * @param {Point} point
 * @returns {Point} a new point.
 * @method
 */
Point.prototype.plus = function(point) {
    return new Point(this.x + point.x, this.y + point.y, this.z + point.z);
};

/**
 * Project this point onto a 2D surface, with the camera pointed at
 * the origin along the z-axis. The origin is on the surface.
 * @param {number} fl Focal length (distance between camera and surface).
 * @returns {Point}
 * @method
 */
Point.prototype.project = function(fl) {
    return new Point((fl * this.x) / (fl + this.z),
                     (fl * this.y) / (fl + this.z));
};

/**
 * @param {number} a A rotation angle (radians).
 * @returns {Point} this point rotated about the X axis.
 * @method
 */
Point.prototype.rotateX = function(a) {
    return new Point(this.x,
                     this.y * Math.cos(a) - this.z * Math.sin(a),
                     this.y * Math.sin(a) + this.z * Math.cos(a));
};

/**
 * @param {number} a A rotation angle (radians).
 * @returns {Point} this point rotated about the Y axis.
 * @method
 */
Point.prototype.rotateY = function(a) {
    return new Point(this.z * Math.sin(a) + this.x * Math.cos(a),
                     this.y,
                     this.z * Math.cos(a) - this.x * Math.sin(a));
};

/**
 * @returns {Point} this point normalized.
 * @method
 */
Point.prototype.normalize = function() {
    return this.div(this.abs());
};

/**
 * Select a point inside a unit sphere and project it onto the sphere.
 * @returns {Point} A uniformly random point on the outside of a unit sphere.
 */
Point.random = function() {
    function r() { return Math.random() * 2 - 1; }
    do {
        var p = new Point(r(), r(), r());
    } while (p.abs() > 1);
    return p.normalize();
};

/**
 * Select two spherical coordinate angles and set radius to 1.
 * @returns {Point} A biased random point on the outside of a unit sphere.
 */
Point.randomSpherical = function() {
    var q = Math.random() * Math.PI * 2,
        f = Math.random() * Math.PI * 2;
    return new Point(Math.sin(q) * Math.cos(f),
                     Math.sin(q) * Math.sin(f),
                     Math.cos(q));
};

/**
 * Select a point in the unit cube and project it into the sphere.
 * @returns {Point} A biased random point on the outside of a unit sphere.
 */
Point.randomCube = function() {
    function unit() { return Math.random() * 2 - 1; }
    return P(unit(), unit(), unit()).normalize();
};
