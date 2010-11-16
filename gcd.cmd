module gcd:
  read x;
  read y;
  do x<y -> y := y-x
  [] y<x -> x := x-y
  od;
  write x
end
