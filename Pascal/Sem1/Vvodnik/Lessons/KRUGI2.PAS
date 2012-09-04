uses crt,graph;
var x,y,c,r,gd,gm:integer;
procedure krug(x,y,c,r:integer);
begin
setcolor(c);
circle(x,y,r);
setfillstyle(1,c);
floodfill(x,y,c);
setcolor(c);
settextstyle(0,0,3);
outtextxy(100,200,'Jins-Tonik');
end;
begin
   gd:=detect;
   initgraph(gd,gm,'');
   randomize;
   repeat
   x:=20+random(600);
   y:=20+random(440);
   c:=random(15)+1;
   r:=random(10);
   if x+r>getmaxx then x:=1;
   if y+r>getmaxy then y:=1;
   krug(x,y,c,r);
   delay(1000);
   until keypressed
end.