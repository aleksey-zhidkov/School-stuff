uses crt;
var s,q:string;
    d:array[0..255] of integer;
    n,m,i,j,k:integer;
begin
 writeln('Vvedite stroky');
 readln(s);
 writeln('Vvedite podstroky');
 readln(q);
 n:=length(s);
 m:=length(q);
 for i:=0 to 255 do d[i]:=m;
 for i:=1 to m-1 do d[ord(q[i])]:=m-i;
 i:=m;
 repeat
  j:=m;
  k:=i;
  while ((j<>0) and (q[j]=s[k])) do
  begin
   dec(k);
   dec(j);
  end;
  i:=i+d[ord(s[i])];
 until ((j=0) or (i>n));
 if (j=0) then writeln(k+1)
 else writeln('Net');
 readln;
end.