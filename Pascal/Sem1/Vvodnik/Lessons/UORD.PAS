uses crt,graph;
const left=#75;
      right=#77;
      up=#72;
      down=#80;
      esc=#27;
var gd,gm,x,y,c,sh,r:integer;
    ch:char;
procedure krug(x,y,c:integer);
 begin
  setcolor(c);
  circle(x,y,15);
  setfillstyle(1,c);
  floodfill(x,y,c);
 end;
procedure upr(var x,y,c:integer);
 begin
  ch:=readkey;
  krug(x,y,0);
  case ch of
   left:x:=x-sh;
   right:x:=x+sh;
   up:y:=y-sh;
  down:y:=y+sh;
 end;
 if x<15 then x:=15;
 if x>625 then x:=625;
 if y<15 then y:=15;
 if y>465 then y:=465;
 krug(x,y,c);
end;
begin
 gd:=detect;
 initgraph(gd,gm,'');
 x:=getmaxx div 2;
 y:=getmaxy div 2;
 sh:=5;
 c:=15;
 krug(x,y,c);
 repeat
 upr(x,y,c);
 until ch=esc;
end.