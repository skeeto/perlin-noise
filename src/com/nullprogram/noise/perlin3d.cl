/* Returns a pointer to the gradient vector for the given grid point. */
global const float *get_gradient(global const float *gradients,
                                 const float w, const float h, const float *c)
{
    int base = (c[0] + c[1] * w + c[2] * w * h) * 3;
    return gradients + base;
}

float calc_magnitude(global const float *g, const float *c, const float *p)
{
    return g[0] * (p[0] - c[0]) + g[1] * (p[1] - c[1]) + g[2] * (p[2] - c[2]);
}

float weight(const float *c, const float *p)
{
    float t0 = 1 - fabs(c[0] - p[0]);
    float t1 = 1 - fabs(c[1] - p[1]);
    float t2 = 1 - fabs(c[2] - p[2]);
    return (3 * pown(t0, 2) - 2 * pown(t0, 3))
         * (3 * pown(t1, 2) - 2 * pown(t1, 3))
         * (3 * pown(t2, 2) - 2 * pown(t2, 3));
}

kernel void
perlin3d(global const float *gradients,
         global const float *params,
         global float *value)
{
    /* Fetch and calculate parameters. */
    unsigned int id = get_global_id(0);
    float w = params[2]; // area width  (x)
    float h = params[3]; // area height (y)
    float d = params[4]; // area depth  (z)

    float s = params[1]; // step size

    float x = fmod(id * s, w);       // x-position to sample
    float y = floor(id * s / w) * s; // y-position to sample
    float z = params[0];             // z-position to sample
    const float p[] = {x, y, z};

    /* Calculate grid corners. */
    const float c000[] = {floor(x), floor(y), floor(z)};
    const float c001[] = {c000[0] + 0, c000[1] + 0, c000[2] + 1};
    const float c010[] = {c000[0] + 0, c000[1] + 1, c000[2] + 0};
    const float c011[] = {c000[0] + 0, c000[1] + 1, c000[2] + 1};
    const float c100[] = {c000[0] + 1, c000[1] + 0, c000[2] + 0};
    const float c101[] = {c000[0] + 1, c000[1] + 0, c000[2] + 1};
    const float c110[] = {c000[0] + 1, c000[1] + 1, c000[2] + 0};
    const float c111[] = {c000[0] + 1, c000[1] + 1, c000[2] + 1};

    /* Find each of the grid gradients. */
    global const float *g000 = get_gradient(gradients, w, h, c000);
    global const float *g001 = get_gradient(gradients, w, h, c001);
    global const float *g010 = get_gradient(gradients, w, h, c010);
    global const float *g011 = get_gradient(gradients, w, h, c011);
    global const float *g100 = get_gradient(gradients, w, h, c100);
    global const float *g101 = get_gradient(gradients, w, h, c101);
    global const float *g110 = get_gradient(gradients, w, h, c110);
    global const float *g111 = get_gradient(gradients, w, h, c111);

    /* Dot products. */
    float m000 = calc_magnitude(g000, c000, p);
    float m001 = calc_magnitude(g001, c001, p);
    float m010 = calc_magnitude(g010, c010, p);
    float m011 = calc_magnitude(g011, c011, p);
    float m100 = calc_magnitude(g100, c100, p);
    float m101 = calc_magnitude(g101, c101, p);
    float m110 = calc_magnitude(g110, c110, p);
    float m111 = calc_magnitude(g111, c111, p);

    /* Weights. */
    float w000 = weight(c000, p);
    float w001 = weight(c001, p);
    float w010 = weight(c010, p);
    float w011 = weight(c011, p);
    float w100 = weight(c100, p);
    float w101 = weight(c101, p);
    float w110 = weight(c110, p);
    float w111 = weight(c111, p);

    value[id] =
          w000 * m000
        + w001 * m001
        + w010 * m010
        + w011 * m011
        + w100 * m100
        + w101 * m101
        + w110 * m110
        + w111 * m111;
}
