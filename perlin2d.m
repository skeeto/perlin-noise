## Returns the Perlin noise value for the given 2D point. Accepts 2D
## arrays for x and y, i.e. from meshgrid(). For example,
##
##   [x y] = meshgrid(0:0.1:10, 0:0.1:10);
##   d = perlin2d(x, y);
##
## The special thing here is it's entirely vectorized: no loops!
function v = perlin2d(x, y)
  ## Find the nearest grid points (2D)
  x0 = floor(x);
  x1 = floor(x) + 1.0;
  y0 = floor(y);
  y1 = floor(y) + 1.0;

  ## Gradient vectors for each grid point (3D)
  v00 = gradp(x0, y0);
  v01 = gradp(x0, y1);
  v10 = gradp(x1, y0);
  v11 = gradp(x1, y1);

  ## Distances (2D)
  tx0 = x - x0;
  tx1 = x - x1;
  ty0 = y - y0;
  ty1 = y - y1;

  ## Value for each grid point (2D)
  s00 = sum(v00 .* cat(3, tx0, ty0), 3);
  s01 = sum(v01 .* cat(3, tx0, ty1), 3);
  s10 = sum(v10 .* cat(3, tx1, ty0), 3);
  s11 = sum(v11 .* cat(3, tx1, ty1), 3);

  ## Weights (2D)
  w00 = (3*tx1.^2 + 2*tx1.^3) .* (3*ty1.^2 + 2*ty1.^3);
  w01 = (3*tx1.^2 + 2*tx1.^3) .* (3*ty0.^2 - 2*ty0.^3);
  w10 = (3*tx0.^2 - 2*tx0.^3) .* (3*ty1.^2 + 2*ty1.^3);
  w11 = (3*tx0.^2 - 2*tx0.^3) .* (3*ty0.^2 - 2*ty0.^3);

  ## Weighted sum of the values
  v = w00 .* s00 + w01 .* s01 + w10 .* s10 + w11 .* s11;
end

## Return the gradient at the given grid point.
function v = gradp(x, y)
  persistent max32 = double(intmax('uint32'));
  v = mod(cat(3, hash(1, x, y), hash(2, x, y)), max32);
  v = double(v) / max32 - 0.5;
end

## Return a hash of three integers. Got this algo from here:
## http://www.concentric.net/~ttwang/tech/inthash.htm
function a = hash(a, b, c)
  a = uint64(bitxor(a,  b) + c);  # pre-mix

  a = bitxor(bitxor(a, 61), bitshift(a, -16));
  a = a + bitshift(a, 3);
  a = bitxor(a, bitshift(a, -4));
  a = a * uint64(0x27d4eb2d);
  a = bitxor(a, bitshift(a, -15));
end
