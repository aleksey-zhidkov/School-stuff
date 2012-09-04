var st:string;
    ch:char;
    dl,i,sh,max,j:byte;
{----------------------\/FINDMAX\/---------------------------}
procedure findMAX(dl:byte);
begin
  for i:=1 to dl do
  begin
   if (st[i]<>' ') and (st[i]<>',') and (st[i]<>'.') then sh:=sh+1 {Esli slovo ne konchilos' uvelichivaem schetchik}
   else
   begin
    if (max<sh) then max:=sh;{Esli max < tekushego slova, to max:=dlina(schetchik) tekushego slova, a shetchik obnulit}
    sh:=0;
   end;
  end;
end;
{----------------------/\FINDMAX/\---------------------------}
{============================================================}
{----------------------\/WRITEMAX\/--------------------------}
procedure writeMAX(dl,max:byte);
begin
 for i:=1 to dl do
  begin
   if (st[i]<>' ') and (st[i]<>',') and (st[i]<>'.') then sh:=sh+1{Esli slovo ne konchilos' uvelichivaem schetchik}
   else
   begin
    if (max=sh) then{Esli max = tekushemu slovu, to vivesti slovo, a shetchik obnulit}
    begin
     for j:=i-max to i-1 do{i-max - 1 simvol slova, i - 1 razdelitel' posle slova}
      write(st[j]);
     writeln;
    end;
    sh:=0;
   end;
 end;
end;
{----------------------/\WRITEMAX/\--------------------------}
begin
 writeln('Vvedite cherez zapatuu neskolko slov, ne bolee 8 bukv v kazhdom');
 readln(st);
 max:=0;
 dl:=length(st);
 sh:=0;
 findMAX(dl);
 writeMAX(dl,max);
 readln;
end.





