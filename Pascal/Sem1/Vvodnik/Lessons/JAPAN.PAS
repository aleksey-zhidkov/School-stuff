uses crt,graph;
var x1,y1,x2,y2,s,c,gd,gm:integer;
    xm,ym,fx1,fy1,fx2,fy2,dx,dy:integer;
    kx,ky,kr:integer;
begin
     {writeln('Введите координаты');
     readln(x1,y1,x2,y2);
     writeln('Введите цвет');
     readln(C);
     writeln('Введите стиль закраски');
     readln(s); }
     gd:=detect;
     initgraph(gd,gm,'');
     xm:=getmaxx;
     ym:=getmaxy;
     setfillstyle(1,11);
     bar(0,0,xm,ym);
     fx1:=xm div 4;
     fy1:=ym div 4;
     fx2:=fx1*3;
     fy2:=fy1*3;
     setfillstyle(1,15);
     bar(fx1,fy1,fx2,fy2);
     kx:=xm div 2;
     ky:=ym div 2;
     kr:=ym div 10;
     setcolor(4);
     setfillstyle(1,4);
     circle(kx,ky,kr);
     floodfill(kx,ky,4);
     settextstyle(0,0,3);
     outtextxy(165,360,'Japan flag');
     readkey;
end.