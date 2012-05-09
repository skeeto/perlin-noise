## Returns the Perlin noise value for a point of arbitrary dimension.
## Only one point at a time, so the wperlin() wrapper may be helpful.
##
##    [xx yy] = meshgrid(0:0.1:20, 0:0.1:20);
##    d = arrayfun(@wperlin, xx, yy));
##
function v = perlin(p)
  v = 0;
  ## Iterate over each corner
  for dirs = [dec2bin(0:(2 ^ length(p) - 1)) - 48]'
    q = floor(p) + dirs';  # This iteration's corner
    g = qgradient(q);      # This corner's gradient
    m = dot(g, p - q);
    t = 1.0 - abs(p - q);
    v += m * prod(3 * t .^ 2 - 2 * t .^ 3);
  end
end

## Return the gradient at the given grid point.
function v = qgradient(q)
  v = zeros(size(q));
  for i = 1:length(q);
      v(i) = hashmd5([i q]) * 2.0 - 1.0;
  end
end
