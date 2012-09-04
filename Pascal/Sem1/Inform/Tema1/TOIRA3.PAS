var y,m,md,d,x,i:integer;
begin
 writeln('Vvedite god');readln(y);
 writeln('Vvedite mesiac');readln(m);
 writeln('Vvedite den');readln(d);
 writeln('Vvedite x');readln(x);
 if (m mod 2=0) then md:=31;
 if (m mod 2<>0) then md:=30;
 if m=2 then md:=28;
 for i:=1 to x do
  begin
   d:=d+1;
   if d>md then
    begin
     m:=m+1;
     d:=1;
     if (m mod 2=0) then md:=31;
     if (m mod 2<>0) then md:=30;
     if m=2 then md:=28;
     if m>12 then
      begin
       y:=y+1;
       m:=1;
       d:=1;
       if (m mod 2=0) then md:=31;
       if (m mod 2<>0) then md:=30;
       if m=2 then md:=28;
      end;
    end;
  end;
 writeln('data:',d,'.',m,'.',y);
 readln;
end.





