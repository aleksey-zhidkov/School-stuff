const n=10;
var sort:array[1..n] of byte;
    i,j,min,imin:byte;
begin
 randomize;
 for i:=1 to n do
 begin
  sort[i]:=random(256);
  write(sort[i],' ');
 end;
 writeln;
 for i:=1 to n-1 do
  begin
  min:=sort[i];
  imin:=i;
  for j:=i+1 to n do;
   if min>sort[j] then
   begin
    min:=sort[j];
    imin:=j;
   end;
  sort[imin]:=sort[i];
  sort[i]:=min;
  end;
 for i:=1 to n do
  write(sort[i],' ');
 writeln;
 readln;
end.