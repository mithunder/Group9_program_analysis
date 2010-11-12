module test_program :
	read x;
	y := 12;
	if x > y -> a := 5
	[] x <= y -> a := x
	fi;
	write a
end
