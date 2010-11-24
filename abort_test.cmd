module abort_test :
x := 0;
y := 0;
if true -> 
        if false -> skip 
        fi;
        read x;
        read y;
        do x > y -> x := x - y
        [] y > x -> y := y - x
        od;
        write x
fi;
write x;
write y;

if false -> skip
fi;

write x;
write y

end
