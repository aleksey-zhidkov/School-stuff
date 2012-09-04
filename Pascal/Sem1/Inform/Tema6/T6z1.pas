const n=10;
var a:array[1..n] of integer;
    x,i,m:integer;
    s:longint;
begin
 randomize;
 for i:=1 to n do
 begin
  a[i]:=random(256);
  write(a[i],' ');
 end;
 x:=random(256);
 writeln('');
 writeln('x= ',x);
 i:=1;
 m:=0;
 s:=1;
 while (a[i]<>x) or (i<=10) do
 begin
  if a[i]=x then m:=i;
  i:=i+1;
 end;
 writeln('Mesto el-ta v massive:', m);
 if m<>0 then
  for i:=m to n do
   s:=s*a[i];
 if m<>0 then writeln('proizvedenie = ',s)
 else writeln('X ne vhodit v massiv');
 readln;
end.













