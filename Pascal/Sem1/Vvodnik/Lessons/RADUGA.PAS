uses crt,graph;
var r,gd,gm,c,x,y,i,n:integer;
begin
     gd:=detect;
     initgraph(gd,gm,'');
     x:=getmaxx;
     y:=getmaxy;
     for i:=7 downto 1 do
     begin
     r:=i*10;
         case i of
          1:c:=5;
          2:c:=1;
          3:c:=3;
          4:c:=2;
          5:c:=14;
          6:c:=12;
          7:c:=4;
         end;
         setcolor(c);
         setfillstyle(1,c);
         circle(x div 2,y,r*2);
         floodfill(x div 2,y,c);
     end;
     readkey;
end.