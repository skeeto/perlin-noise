## Return an integer hash from an integer. Accepts and returns 32-bit
## ints.
##
## Got this algo from here:
## http://www.concentric.net/~ttwang/tech/inthash.htm
function a = hash32(a)
  a = uint64(a);
  a = bitxor(bitxor(a, 61), bitshift(a, -16));
  a = a + bitshift(a, 3);
  a = bitxor(a, bitshift(a, -4));
  a = a * uint64(0x27d4eb2d);
  a = bitxor(a, bitshift(a, -15));

  persistent max32 = double(intmax('uint32'));
  a = uint32(mod(a, max32));
end
