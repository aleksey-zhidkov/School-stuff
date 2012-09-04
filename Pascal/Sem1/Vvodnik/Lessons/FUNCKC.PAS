uses crt,graph;
var x,y,i,c,r,gd,gm:integer;
procedure krug(x,y,r,c:integer);
begin
putpixel(x,y,1);
end;
begin
   gd:=detect;
   initgraph(gd,gm,'');
   r:=10;
   x:=0;
   for i:=1 to 640 do
   begin
   x:=x+1;
   y:=round(ln(x)*10)+300;
   krug(x,y,r,1);
   end;
   readln;
end.