kernel void
perlin3d(global const float *gradients,
         global float *value)
{
    unsigned int id = get_global_id(0);
    //value[id] = gradients[id];
    value[id] = id;
}
