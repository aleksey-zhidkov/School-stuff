uses crt,graph;
var x,y,wx1,wy1,wx2,wy2,bx1,by1,bx2,by2,rx1,ry1,rx2,ry2,gd,gm:integer;
begin
     gd:=detect;
     initgraph(gd,gm,'');
     x:=getmaxx;
     y:=getmaxy;
     wx1:=x div 5;
     wy1:=y div 5;
     wx2:=x-wx1;
     wy2:=wy1*2;
     setfillstyle(1,15);
     bar(wx1,wy1,wx2,wy2);
     bx1:=wx1;
     by1:=wy1*2;
     bx2:=wx2;
     by2:=wy1*3;
     setfillstyle(1,blue);
     bar(bx1,by1,bx2,by2);
     rx1:=wx1;
     ry1:=wy1*3;
     rx2:=wx2;
     ry2:=wy1*4;
     setfillstyle(1,red);
     bar(rx1,ry1,rx2,ry2);
     settextstyle(0,0,3);
     outtextxy(160,360,'Russia forever');
     readkey;
end.