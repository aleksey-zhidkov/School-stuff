var st:string;
    ch:real;
    code:integer;
procedure expF;
begin
 val(st,ch,code);{val - operator perevoda cifri v chislo, tip zavisit ot tipa peremennoi poluchatelz (ch)}
 if code<> 0 then writeln('Oshibka v ',code,' pozicii'){esli kod<>0, znachit gdeto proizoshla oshibka}
 else writeln('Znachenie v exp forme= ',ch);
end;
begin
writeln('Vvedite chislo s desatichnoi tochkoi');
readln(st);
expF;
readln;
end.