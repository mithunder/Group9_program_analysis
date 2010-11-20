module chain_test_3:
  read x;
  read y;
  y := y + x;
  x := x + y;
  if x < y -> x := x + 1
  [] y < x -> y := y + 1
  fi;
  write y;
  write x
end
