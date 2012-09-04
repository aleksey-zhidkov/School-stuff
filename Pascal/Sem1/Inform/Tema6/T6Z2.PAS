const n=10;
      zn=20;
var x:array[1..n] of integer;
    y:array[1..n] of integer;
    z:array[1..zn] of integer;
    i,j,s:integer;
begin
 randomize;
 for i:=1 to n do
 begin
  x[i]:=random(50)+1;
  write(x[i],' ');
 end;
 writeln('');
 writeln('');
 y[1]:=x[1];
 s:=10;
 for i:=2 to 9 do
  begin
   if x[i]<=y[1] then
   begin
    s:=s+1;
    j:=i;
    for j:=i downto 1 do
     begin
      y[j+1]:=y[j]
     end;
    y[1]:=x[i];
   end;
  end;
  write('Uporiadochennii massiv: ');
 for i:=1 to n do
 write(y[i],' ');
 readln;
end.














