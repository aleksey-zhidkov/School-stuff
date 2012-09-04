var a,x,ch,b,y,n,i,x0: integer;
begin
 writeln('Vvedite A');readln(a);
 writeln('Vvedite B');readln(b);
 writeln('Vvedite N');readln(n);
 x0:=round(-1*(b/a));
 ch:=0;
 randomize;
 for i:=1 to n do
  begin
   x:=random(30)-15;
   y:=random(30)-15;
   if (a>0) and (x>x0) and (y<a*x+b) then
   ch:=ch+1;
   if (a<0) and (x>x0) and (y>a*x+b) then
   ch:=ch+1;
  end;
 writeln(ch,' tochek udvoletvoriaut usloviy');
 readln;
end.