const n=10;
var a:array[1..n] of integer;
    x1,x2,i:integer;
begin
 randomize;
 for i:=1 to n do
  begin
  a[i]:=random(90)+10;
  write(a[i],' ');
  end;
 writeln;
 for i:=1 to n do
  begin
  x1:=a[i] div 10;
  x2:=a[i] mod 10;
  if ((x1+x2)+sqr(x1+x2))=a[i] then writeln(a[i],' - chislo udvoletvoriaushee usloviam');
  end;
 readln;
end.