

/*

A multiline comment in the start.

*/

#@ b2 = "SomeVar  "

//Line comment.
			//Another line comment.

module skulls :
	x := b;
	x := a;
	write 3*3*3;
	read a;
	x := !!!!-!!-3;
	x := a*--!12312093 + 132 + ----!-23432;
	x := 3;
	do
		(a > x) -> x := 1
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	od
	;
	if 
		(a > x) -> x := 1
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> x := 0
	[]	a <= x -> {
			do
				(a > x) -> x := 1
			[]	a <= x -> x := 0
			[]	a <= x -> x := 0
			[]	a <= x -> x := 0; {skip; {skip; a := 3*3*3}}; skip; skip; skip
			[]	a <= x -> x := 0; abort
			od
		}
	fi
	
end			//LineComment.


#@ a1 = "something"

/*

A multiline comment in the end.

*/
//Line comment at the end of the file.
