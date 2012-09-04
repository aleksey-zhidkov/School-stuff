uses crt,graph;
var p,i,gd,gm,x,xc,yc,xch,ych:integer;
begin
     gd:=detect;
     initgraph(gd,gm,'c:bp/bgi');
     xc:=getmaxx div 2;
     yc:=getmaxy div 2;
     xch:=xc;
     ych:=yc-150;
     setcolor(1);
     setfillstyle(1,1);
     circle(xc,yc, 155);
     floodfill(320,240,1);
     for i:=1 to 16 do
     begin
     setcolor(4);
     setlinestyle(0,0,1);
     line(xc,yc,xch,ych);
     xch:=round(10);
     ych:=ych+10;
     end;
     readkey;
end.