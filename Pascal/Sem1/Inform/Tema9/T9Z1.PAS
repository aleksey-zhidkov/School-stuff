var st:string;
    ch:char;
    k,kod,i,dl:byte;
{------------\/UNSHIFR\/------------------}
procedure unshifr(dl:byte);
begin
 for i:=1 to dl do
 begin
  if (st[i]<>' ') then {esli simvol - probel to nichego ne delaem}
  begin
   kod:=ord(st[i]);{opredeliaem kod simvola}
   kod:=kod-k;
   writeln(kod);
   if (kod<97) then kod:=123-k;{esli kod menshe 'a' to kod:='z'}
   ch:=chr(kod);{vozvrashaem simvol}
   st[i]:=ch;
  end;
 end;
end;
{-------------/\UNSHIFR/\-----------------}
begin
 writeln('Vvedite stroku');readln(st);
 writeln('Vvedite k');readln(k);
 dl:=length(st);{opredelaem dlinu stroki}
 unshifr(dl);
 writeln(st);
 readln;
end.