uses crt,graph;
var gd,gm,c,i,n:integer;
begin
     writeln('Vvedite kol-vo krugov');
     readln(n);
     gd:=detect;
     initgraph(gd,gm,'');
     randomize;
     for i:=1 to n do
     begin
         c:=random(15)+1;
         setcolor(c);
         setfillstyle(1,c);
         circle(320,240,i*10);
     end;
     readkey;
end.