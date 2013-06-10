precision mediump float;

uniform float scale;
uniform float depth;
uniform float seed;

float rand(vec2 co){
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453) - .5;
}

vec3 grad(vec3 p) {
    return normalize(vec3(rand(vec2(p.x * seed, p.y)),
                          rand(seed * vec2(p.x, p.y) * p.z),
                          rand(vec2(p.x, p.y + seed) + p.z)));
}

vec3 pow3(vec3 v, float p) {
    return vec3(pow(v.x, p), pow(v.y, p), pow(v.z, p));
}

void main() {
    vec3 point = vec3(gl_FragCoord.xy / scale, depth);
    vec3 fpoint = floor(point);

    vec3 c000 = fpoint + vec3(0.0, 0.0, 0.0);
    vec3 c001 = fpoint + vec3(0.0, 0.0, 1.0);
    vec3 c010 = fpoint + vec3(0.0, 1.0, 0.0);
    vec3 c011 = fpoint + vec3(0.0, 1.0, 1.0);
    vec3 c100 = fpoint + vec3(1.0, 0.0, 0.0);
    vec3 c101 = fpoint + vec3(1.0, 0.0, 1.0);
    vec3 c110 = fpoint + vec3(1.0, 1.0, 0.0);
    vec3 c111 = fpoint + vec3(1.0, 1.0, 1.0);

    vec3 g000 = grad(c000);
    vec3 g001 = grad(c001);
    vec3 g010 = grad(c010);
    vec3 g011 = grad(c011);
    vec3 g100 = grad(c100);
    vec3 g101 = grad(c101);
    vec3 g110 = grad(c110);
    vec3 g111 = grad(c111);

    float m000 = dot(g000, point - c000);
    float m001 = dot(g001, point - c001);
    float m010 = dot(g010, point - c010);
    float m011 = dot(g011, point - c011);
    float m100 = dot(g100, point - c100);
    float m101 = dot(g101, point - c101);
    float m110 = dot(g110, point - c110);
    float m111 = dot(g111, point - c111);

    vec3 t000 = abs(point - c000) * -1. + 1.;
    vec3 t001 = abs(point - c001) * -1. + 1.;
    vec3 t010 = abs(point - c010) * -1. + 1.;
    vec3 t011 = abs(point - c011) * -1. + 1.;
    vec3 t100 = abs(point - c100) * -1. + 1.;
    vec3 t101 = abs(point - c101) * -1. + 1.;
    vec3 t110 = abs(point - c110) * -1. + 1.;
    vec3 t111 = abs(point - c111) * -1. + 1.;

    vec3 w000 = pow3(t000, 2.) * 3. - pow3(t000, 3.) * 2.;
    vec3 w001 = pow3(t001, 2.) * 3. - pow3(t001, 3.) * 2.;
    vec3 w010 = pow3(t010, 2.) * 3. - pow3(t010, 3.) * 2.;
    vec3 w011 = pow3(t011, 2.) * 3. - pow3(t011, 3.) * 2.;
    vec3 w100 = pow3(t100, 2.) * 3. - pow3(t100, 3.) * 2.;
    vec3 w101 = pow3(t101, 2.) * 3. - pow3(t101, 3.) * 2.;
    vec3 w110 = pow3(t110, 2.) * 3. - pow3(t110, 3.) * 2.;
    vec3 w111 = pow3(t111, 2.) * 3. - pow3(t111, 3.) * 2.;

    float sum =
        w000.x * w000.y * w000.z * m000 +
        w001.x * w001.y * w001.z * m001 +
        w010.x * w010.y * w010.z * m010 +
        w011.x * w011.y * w011.z * m011 +
        w100.x * w100.y * w100.z * m100 +
        w101.x * w101.y * w101.z * m101 +
        w110.x * w110.y * w110.z * m110 +
        w111.x * w111.y * w111.z * m111;

    float color = ((sum + 2. / 3.) * 2. / 3.);
    gl_FragColor = vec4(color, color, color, 1.);
}
