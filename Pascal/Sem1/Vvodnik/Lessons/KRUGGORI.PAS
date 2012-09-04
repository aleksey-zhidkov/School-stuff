uses crt,graph;
var x,y,c,r,h,gd,gm:integer;
procedure krug(x,y,r,c:integer);
begin
setcolor(c);
circle(x,y,r);
setfillstyle(1,c);
floodfill(x,y,c);
end;
begin
   gd:=detect;
   initgraph(gd,gm,'');
   r:=10;
   h:=50;
   repeat
   delay(10000);
   krug(x,y,r,0);
   x:=x+20;
   y:=round(sin(x)*50)+h;
   krug(x,y,r,1);
   if x>640 then
   begin
   x:=20;
   h:=h+50
   end;
   until keypressed
end.