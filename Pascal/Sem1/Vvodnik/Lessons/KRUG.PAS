uses crt,graph;
var gd,gm,i,n:integer;
begin
     writeln('Vvedite kol-vo krugov');
     readln(n);
     gd:=detect;
     initgraph(gd,gm,'');
     for i:=1 to n do
     begin
         setcolor(i);
         setfillstyle(1,i);
         circle(320,240,i*10);
     end;
     readkey;
end.