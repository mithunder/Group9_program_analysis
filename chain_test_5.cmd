module chain_test_5:
  read x;
  read y;
  y := y + x;
  x := x + y;
  do x < y -> x := x + 1
  [] y < x -> {if y > x -> x := x + 1 [] y <= x -> y := y + 1 fi}
  od;
  write y;
  write x
end
