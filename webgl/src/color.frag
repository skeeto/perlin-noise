precision mediump float;

varying mediump vec4 coord;
uniform vec4 color;

void main() {
    //gl_FragColor = vec4((coord.xy + 1.0) / 2.0, 0.0, 1.0);
    gl_FragColor = color;
    //gl_FragColor = vec4(1, 1, 0, 1);
}
