## Wraps perlin() so that the point can be specified as a number of 2D
## arguments (like perlin2d()). Handy for use with meshgrid().
function v = wperlin(varargin)
  v = arrayfun(@helper, varargin{:});
end

## Helps call perlin() correctly.
function v = helper(varargin)
  v = perlin(cell2mat(varargin));
end
