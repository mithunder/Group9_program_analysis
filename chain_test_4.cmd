module chain_test_4:
  read x;
  read y;
  y := y + x;
  x := x + y;
  if x < y -> x := x + 1
  [] y < x -> {do y < x -> y := y + 1 [] y > x -> x := x + 1 od}
  fi;
  write y;
  write x
end
