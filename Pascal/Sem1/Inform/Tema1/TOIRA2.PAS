var m,n,sh,sl,st:integer;
begin
 writeln('Vvedite kol-vo sharov');readln(m);
 n:=0;
 sl:=0;
 while m>0 do
 begin
  n:=n+1;
  st:=sqr(n);
  sl:=sl+1;
  m:=m-st;
 end;
 if m<0 then sl:=sl-1;
 writeln('mozhno postroit piramidy iz ', sl,' sloev');
 readln;
end.
