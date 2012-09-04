uses crt,graph;
var gd,gm,h,c,x,x1,i,n:integer;
begin
     writeln('Vvedite kol-vo krugov');
     readln(n);
     x:=20;
     h:=15;
     gd:=detect;
     initgraph(gd,gm,'');
     randomize;
     for i:=1 to n do
     begin
         c:=random(15)+1;
         setcolor(c);
         setfillstyle(1,c);
         circle(x,240,i*h);
         x:=x+h;
     end;
     readkey;
end.