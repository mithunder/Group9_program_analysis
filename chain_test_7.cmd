module chain_test_7:
  read x;
  read y;
  y := y + x;
  x := x + y;
  do x < y -> x := x + 1
  [] y < x -> {do y > x -> x := x + 1 [] y <= x -> y := y + 1 od}
  od;
  write y;
  write x
end
