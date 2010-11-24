module abort_cp_test :
x := 0;
read y;
if x > y -> if false -> skip [] false -> skip fi;
            read x;
            read y;
            write x
[] y = x -> abort
[] y > x -> y:= x
fi;

write y;
read y;
do y > x ->  x:= 1; abort
[] y < x ->  x:= 2; abort
[] x != y -> x:= 3; read y; abort
[] x = 3 -> skip
od;

write x
end
