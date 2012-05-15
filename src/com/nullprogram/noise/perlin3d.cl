kernel void
perlin3d(global const float *gradients,
         global const float *params,
         global float *value)
{
    unsigned int id = get_global_id(0);
    float z = params[0];
    float w = params[1];
    float h = params[2];
    float s = params[3];
    value[id] = id + z;
}
