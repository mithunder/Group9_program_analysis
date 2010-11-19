module chain_test_2:
  read x;
  read y;
  y := y + x;
  x := x + y;
  do x < y -> x := x + 1
  [] y < x -> y := y + 1
  od;
  write y;
  write x
end
