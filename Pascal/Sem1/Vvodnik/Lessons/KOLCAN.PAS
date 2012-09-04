uses crt,graph;
var r,gd,gm,h,c,x,x1,i,n:integer;

begin
     writeln('Vvedite kol-vo kolec');
     readln(n);
     x:=20;
     h:=15;
     gd:=detect;
     initgraph(gd,gm,'');
     randomize;
     for i:=n downto 1 do
     begin
         r:=i*10;
         c:=random(15)+1;
         setcolor(c);
         setfillstyle(1,c);
         circle(320,240,r);
         floodfill(320,240,c);
     end;
     readkey;
end.