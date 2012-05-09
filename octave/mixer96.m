## Bastardized version of Robert Jenkins' 96 bit Mix Function.
## http://www.concentric.net/~ttwang/tech/inthash.htm
##
## This isn't quite right because Matlab/Octave doesn't actually
## provide the required bit shift operators (out of the box). However,
## it seems to work well enough.
function c = mixer96(a, b, c)
  a = int64(hash32(a));
  b = int64(hash32(b));
  c = int64(hash32(c));

  a=a-b;  a=a-c;  a=bitxor(a, bitshift(c, -13));
  b=b-c;  b=b-a;  b=bitxor(b, bitshift(a, 8));
  c=c-a;  c=c-b;  c=bitxor(c, bitshift(b, -13 * sign(b)));
  a=a-b;  a=a-c;  a=bitxor(a, bitshift(c, -12));
  b=b-c;  b=b-a;  b=bitxor(b, bitshift(a, 16));
  c=c-a;  c=c-b;  c=bitxor(c, bitshift(b, -5));
  a=a-b;  a=a-c;  a=bitxor(a, bitshift(c, -3));
  b=b-c;  b=b-a;  b=bitxor(b, bitshift(a, 10));
  c=c-a;  c=c-b;  c=bitxor(c, bitshift(b, -15));

  persistent max32 = double(intmax('uint32'));
  c = uint32(mod(a, max32));
end
