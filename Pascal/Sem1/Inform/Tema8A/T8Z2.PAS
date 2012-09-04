const n=5; m=7;
var a:array[1..n,1..m] of integer;
    i,j,max,min,sar:integer;
begin
 randomize;
 max:=0;
 min:=0;
 i:=0;
 j:=0;
 for i:=1 to n do
 begin
  for j:=1 to m do
  begin
   a[i,j]:=random(31)-10;
   write(a[i,j],' ');
  end;
 writeln;
 end;
 for i:=1 to n do
  for j:=1 to m do
   if (max<a[i,j]) then max:=a[i,j];
 writeln('Max= ',max);
 for i:=1 to n do
  for j:=1 to m do
   if (min>a[i,j]) then min:=a[i,j];
 writeln('Min= ',min);
 sar:=round((max+min)/2);
 writeln('Srednee arifmeticheskoe= ',sar);
 readln
end.