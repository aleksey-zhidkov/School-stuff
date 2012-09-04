uses crt;
const n=20;
var i,j,k,c,f,f1,f2,f3,chose:longint;
    value:array [1..n] of longint;
procedure First;
begin
 value[n-1]:=1;
 for j:=1 to n do
  if (value[j]<>0) then write(value[j]:5);
 writeln;
 for j:=1 to i do
 begin
  for k:=1 to n-1 do
  begin
   value[k]:=value[k]+value[k+1];
   if (value[k]<>0) then write(value[k]:5);
  end;
  writeln;
 end;
end;
function fact(c:longint):longint;
begin
 if (c=0) then fact:=1
 else fact:=fact(c-1)*c;
end;
begin
 repeat
  for i:=1 to n do value[i]:=0;
  writeln('Vvedite deistvie (1- 1 algoritm ;2- 2 algoritm; 3- vihod)');
  readln(chose);
  if (chose<3) then
  begin
   repeat
    writeln('Vvedite stepen<13');
    readln(i);
   until (i<13);
  end;
  case chose of
               1:begin clrscr;First;end;
               2:begin
                  clrscr;
                  for j:=1 to i do
                  begin
                   for k:=0 to j do
                   begin
                    f1:=fact(j);
                    f2:=fact(k);
                    f3:=fact(j-k);
                    f:=round(f1/(f2*f3));
                    write(f,' ');
                   end;
                  writeln;
                  end;
                 end;
               3:begin clrscr;halt;end;
  end;
 until (false);
end.