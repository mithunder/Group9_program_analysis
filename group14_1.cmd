module test01 :
 a := 1 ;
 b := 2 ;
 f := 3 ;
 c := 10 ;
 skip ;
 write a + b ;
 if true -> b := 4 ; b := 5
 fi;
 do false -> c := 89
  [] a < b -> b := 0 ; f := 2 
  [] a > b -> skip ; abort 
 od ;
 if false -> c := f ; g := h + b + a fi ;
 skip ;
 do true -> a := 2 ; write 2 + 4 od ;
 a := b
end
