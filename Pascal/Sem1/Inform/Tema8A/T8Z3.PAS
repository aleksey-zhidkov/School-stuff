const n=5;
var a:array[1..n,1..n] of byte;
    i,j,min,sum:byte;
begin
 randomize;
 min:=41;
 i:=0;
 j:=0;
 for i:=1 to n do
 begin
  for j:=1 to n do
  begin
   a[i,j]:=random(21)+10;
   write(a[i,j],' ');
  end;
 writeln;
 end;
 for i:=1 to n do
  for j:=1 to n do
   if (min>a[i,j]) then min:=a[i,j];
 writeln('Min= ',min);
 i:=1;
 j:=1;
 repeat
  j:=j+1;
  if (j=6) then begin j:=1; i:=i+1;end;
 until a[i,j]=min;
 writeln('Nomer stolbca s min eltom: ',j);
 writeln('Nomer stroki s min eltom: ',i);
 j:=0;
 for j:=1 to n do
  sum:=sum+a[i,j];
 writeln('Summa= ',sum);
 readln;
end.