module test_program_simple2 :
	read x;
	y := 12;
	if x > y -> a := 5
	[] x <= y -> a := x
	fi;
	write a
end
