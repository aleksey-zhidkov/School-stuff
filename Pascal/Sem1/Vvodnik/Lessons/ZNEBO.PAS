uses crt,graph;
var x,y,c,s,gd,gm:integer;
begin
   gd:=detect;
   initgraph(gd,gm,'');
   randomize;
   repeat
    x:=random(getmaxx);
    y:=random(getmaxy);
    c:=random(15+1);
    s:=random(500)+300;
    putpixel(x,y,c);
    delay(1000);
    setcolor(c);
    settextstyle(0,0,3);
    outtextxy(250,250,'starsi');
    sound(s);
    delay(1000);
    nosound;
   until keypressed;
   readln;
end.