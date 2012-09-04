uses graph;
var i,y,gd,gm,errcode:integer;
    s:string;
begin
 gD:=Detect;
 InitGraph(gD, gM, '');
 y:=20;
 for i:=0 to 15 do
 begin
  setcolor(i);
  setfillstyle(1,i);
  bar(20,y,120,y+10);
  floodfill(60,y+5,i);
  y:=y+20;
  settextstyle(0,0,1);
  str(i,s);
  outtextxy(130,y-17,s);
 end;
 readln;
end.