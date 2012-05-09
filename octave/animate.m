step = 0.1;
maxv = 20;
r = step:step:maxv;
[xx yy] = meshgrid(r, r);

grad = (yy / maxv) .^ 3;
prefix = "fire";

count = 0;
for i = r
  layer = perlin3d(xx, yy + i, ones(size(xx)) * i);
  layer = (layer + 0.33) / 0.66;
  layer = layer .* grad;
  name = sprintf("%s-%06d.png", prefix, count++);
  imwrite(uint8(layer * 255) + 1, hot(255), name);
  disp(["Wrote " name]);
end
