var s,n,b,i,r:integer;
begin
 writeln('Vvedite nakoplenia');readln(n);
 writeln('Vvedite stipendiy');readln(s);
 writeln('Vvedite rashodi>stipendii');readln(r);
 b:=n;
 i:=-1;
 while b>0 do
  begin
   b:=(b+s)-r;
   r:=r+round(r*00.3);
   i:=i+1;
  end;
 writeln('Matematicheski student prozhivet ',i,' mesiacev');
 writeln('No na samom dele on vizhivet ',i*2,' mesiacev');
 readln;
end.
