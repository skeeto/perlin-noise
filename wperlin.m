## Wraps perlin() so that the point can be specified as a number of
## arguments. Handy for use with meshgrid().
##
function v = wperlin(varargin)
  v = perlin(cell2mat(varargin));
end
