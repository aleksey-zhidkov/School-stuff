const n=5;
var a:array[1..n,1..n] of byte;
    i,j,max:byte;
begin
 randomize;
 max:=0;
 i:=0;
 j:=0;
 for i:=1 to n do
 begin
  for j:=1 to n do
  begin
   a[i,j]:=random(11)+10;
   write(a[i,j],' ');
  end;
 writeln;
 end;
 for i:=1 to n do
  for j:=1 to n do
   if (max<a[i,j]) then max:=a[i,j];
 writeln('Max= ',max);
 for i:=1 to n do
  for j:=1 to n do
   if (max=a[i,j]) then writeln('Elt s indeksom ',i,',',j,' raven max');
 readln
end.