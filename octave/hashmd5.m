## Integer hash based on MD5 (Octave-only). Accepts anything that
## num2str() accepts and outputs a double between 0 and 1.
function h = hashmd5(p)
  persistent max32 = double(intmax('uint32'));
  h = sscanf(md5sum(num2str(p), 1)(1:8), "%x") / max32;
end
