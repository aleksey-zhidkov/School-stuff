uses crt,graph;
var gd,gm,x,y,r,c,shx,shy,us:integer;
procedure shar(x,y,c:integer);
begin
 setcolor(c);
 setfillstyle(1,c);
 circle(x,y,25);
 floodfill(x,y,c);
end;
begin
 randomize;
 gd:=detect;
 initgraph(gd,gm,'');
 x:=random(500)+100;
 y:=random(300)+50;
 us:=0;
 shx:=1;
 shy:=1;
 repeat
  delay(10);
  shar(x,y,0);
  x:=x+shx;
  y:=y+shy;
  {smeshenie------------}
  if x=25 then shx:=1;
  if x=615 then shx:=-1;
  if y=25 then shy:=1;
  if y=455 then shy:=-1;
  {---------------------}
  {Luzi--------------------------------------------}
  if (x<30) and (y<30) then us:=us+1;
  if (x<330) and (x>310) and (y<30) then us:=us+1;
  if (x>615) and (y<30) then us:=us+1;
  if (x<30) and (y>450) then us:=2;
  if (x<330) and (x>310) and (y>450) then us:=us+1;
  if (x>615) and (y>450) then us:=us+1;
  {------------------------------------------------}
  shar(x,y,1);
  shar(25,25,2);
  shar(320,25,2);
  shar(615,25,2);
  shar(25,455,2);
  shar(320,455,2);
  shar(615,455,2);
 until keypressed;
 closegraph;
  readln;
 write('shar popal v luzu ',us,' raz');
 readln;
end.