module chain_test_6:
  read x;
  read y;
  y := y + x;
  x := x + y;
  if x < y -> x := x + 1
  [] y < x -> {if y < x -> y := y + 1 [] y > x -> x := x + 1 fi}
  fi;
  write y;
  write x
end
