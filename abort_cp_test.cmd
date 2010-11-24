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

write y
end
