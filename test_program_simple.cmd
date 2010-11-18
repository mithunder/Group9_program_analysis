module test_program_simple :
	read x;
	y := 12;
	if x > y -> a := 5*2+1909*4/2-x
	[] x <= y -> a := 10
	fi;
	write a
end
