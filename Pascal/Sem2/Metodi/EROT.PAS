 Uses crt;
 var
 chisla:set of byte;
 del,i,n:integer;
 begin
  clrscr;
  repeat
   writeln('Vvedite n');
   readln(n);
  until (n<256);
  for i:=1 to n do
   include(chisla,i);
  del:=2;
  repeat
   for i:=2 to n do
    begin
     if (i mod del=0) and (del<>i) then exclude(chisla,i);
    end;
    del:=del+1;
  until (del=n);
  for i:=1 to n do
   if (i in chisla) then
   write(i:4);
  readln;
  end.