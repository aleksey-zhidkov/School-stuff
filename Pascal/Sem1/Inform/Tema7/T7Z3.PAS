var s,p,max,i,j,n:integer;
begin
writeln('Vvedite n');
readln(n);
s:=0;
max:=0;
p:=0;
for i:=n-1 downto 1 do
 begin
  s:=1;
  s:=s+(i div 2)+i;
  for j:=2 to i div 2 do
  begin
   if i mod j=0 then s:=s+j;
  end;
  if s>max then begin max:=s; p:=i;end;
 end;
writeln(p);
readln;
end.