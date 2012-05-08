## Wrapper for perlin() for using meshgrid() for 3D noise.
function v = perlin3d(x, y, z)
  v = arrayfun((@(a, b, c) perlin([a b c])), x, y, z);
end
