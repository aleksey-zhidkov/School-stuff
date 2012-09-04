const n=4;
var ch,x,i:integer;
    a:array[1..n] of real;
begin
 writeln('Vvedite CH<9999');readln(ch);
 x:=ch;
 for i:=1 to 4 do
  begin
   a[i]:=x mod 10;
   x:=x div 10;
  end;
 writeln('Detal 1: ',a[4]:2:0);
 writeln('Detal 2: ',a[3]:2:0);
 writeln('Detal 3: ',a[2]:2:0);
 writeln('Detal 4: ',a[1]:2:0);
 readln;
end.