

/*

A multiline comment in the start.

*/

#@ b2 = "SomeVar  "

//Line comment.
			//Another line comment.

#@ annotation_variable = "value1"
module test_program :
	y := 3;
	x := b;
	x := (((((a)))));
	z := a*a;
	z := !a*a;
	z := !!a;
	z := -a;
	abc := a*a*3*3*a;
	write 3*3*3;
	write (3*3)*3;
	write 3*(!!4*!!5);
	read a;
        {skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip};
	x := !!!!-!!-3;
	x := a*--!12312093 + 132 + ----!-23432;
	x := 3;
        {skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip};
#@ point = "first_loop"
	do
		(a > 3 > x*30 + asdf / 30) -> x := 1
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	od
	;
#@ point = "first_if"
	if 
		(a > x) -> x := 1
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> {
                        {skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip};
			do
				(a > x) -> x := 1
			[]	a <= x -> x := 0
			[]	a <= x -> x := 0
			[]	a <= x -> x := 0; {skip; {skip; a := 3*3*3}}; skip; skip; skip
			[]	a <= x -> x := 0; abort
			od;
                        {skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip;skip};
                        skip
		}
	fi
	;
	{{{{skip}}}; skip; skip; {skip; skip; {skip; skip; {skip; skip; {skip}}}}}
	;
	{skip};
	{{{{skip}}}; skip; skip; {skip; skip; {skip; skip; {skip; b := 7/3; skip; {a := 3}}}}}
	
end			//LineComment.


#@ a1 = "something"

/*

A multiline comment in the end.

*/
//Line comment at the end of the file.
