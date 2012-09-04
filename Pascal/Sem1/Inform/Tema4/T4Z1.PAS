var y,x,i,a,b,sch,n:integer;
begin
 writeln('Vvedite a<>0');readln(a);
 writeln('Vvedite b<>0');readln(b);
 writeln('Vvedite N');readln(n);
 randomize;
 for i:=1 to n do
  begin
   x:=random(15)-5;
   y:=random(15)-5;
   if (x>0) and (y>a*x+b) then sch:=sch+1;
  end;
 writeln('V ukazannoy oblasti lezhit ',sch,' tochek');
 readln;
end.