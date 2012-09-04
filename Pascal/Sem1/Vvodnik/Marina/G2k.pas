Uses Crt,Graph;
const dlar=3; yd=300;
Var Gd, Gm : Integer;
    Radius : Integer;
    d:array[1..dlar] of integer;
    s:array[1..dlar] of integer;
    sz:longint;
    usHole:word;
    p,q,p1,p2,p3,pb1,pb2,pb3:pointer;
    k,y1,x1,t,x3,y3,s1:integer;
    y2: real;
    XText,YText,delta:integer;
    Mx,My,x,y,st:integer;
    st1,chs:string;
    a,key,a1:char;
    stf,usPrijok:boolean;
    ch,i,xd,scorech:integer;
    gp1,gp2,usScore,usPM:byte;
{----------------------------loader-------------------------}
function loader (filename:string):pointer;
   var
   f1:file;
   g:pointer;
 begin
   assign(f1,filename);
   reset(f1,1);
   sz:=filesize(f1);
   getmem(g,sz);
   blockread(f1,g^,sz);
   close(f1);
   loader:=g;
 end;
{----------------------------loader---------------------------}

{----------------------------calcypos-------------------------}
function  CalcYPos(x:integer): integer;
 begin
   CalcYPos:=round(abs(15*sin(x/30)+300-(x/2)));
 end;
{---------------------------calaypos-------------------------}

{----------------------------test-----------------------------}
{function test:integer;
var
   f : text;
   s : char;
   rez,e : string;
   i,j,t,r,cod,a: Integer;
begin
 p1:=loader('kolob');
 putimage(x3,y3,p1^,1);
 SetTextStyle(0,0,8);
 Setcolor(14);
  Outtextxy (200,140,'Тест');
    Readln;
     SetTextStyle(0,0,1);
assign(f,'t1.txt');
r:=0;
reset(f);
for i:=1 to 20 do
begin
cleardevice;
readln(f,rez);
outtextxy(10,90,rez);
for j:=1 to 5 do
        begin
         readln (f,rez);
         str(j,e);
         outtextxy(10,((30*j)+90),e);
         outtextxy(30,((30*j)+90),rez);
         end;
         readln (f,rez);
         val(rez,t,cod);
s:=readkey;
val(s,a,cod);
if a=t then r:=r+1;
end;
close(f);
str(r,e);
if r>=10 then
   test:=1
   else
   test:=0;
end;                   }
{----------------------------test---------------------------}

{----------------------drawcurvetext----------------------}
Procedure DrawCurveText(x,y:integer;text:string);
  var
    i:integer;
    xpos:integer;
  begin
    xpos:=x;
    for i:=1 to Length(text) do
      begin
        xpos:=xpos+70;
        OutTextXY(xpos,y+CalcYPos(xpos),text[i]);
      end;
  end;
{-------------------------drawcuretext------------------------}

{---------------------------zast-------------------------------}
procedure zast;
begin
  setcolor(15);
  Circle(30, 400,20 );
  Circle(13,421,7);
  Circle(47,421,7);
  Circle(6,400,3);
  Circle(54 ,400,3);
  Circle(23,393,2);
  Circle(37,393,2);
  Line(20,375,20,385);
  Line(23,375,23,382);
  Line(26,375,26,382);
  Line(29,375,29,382);
  Line(32,375,32,382);
  Line(35,375,35,382);
  Line(38,375,38,382);
  Line(41,375,41,385);
  setfillstyle(1,14);
  floodfill(30,400,white);
  setcolor(4);
  Line(25,405,35,405);
  setcolor(15);
  floodfill(6,400,white);
  floodfill(54,400,white);
  floodfill(13,421,white);
  floodfill(47,421,white);
  setfillstyle(1,2);
  floodfill(23,393,white);
  floodfill(37,393,white); {}
  getmem(p,imagesize(0,370,70,430));
  getimage(1,370,70,430,p^);
  cleardevice;
  x1:=0;         {word}
  Mx:=GetMaxx;
  My:=GetMaxy;
  st1:='JUMPER';
  XText:=90;
  YText:=My div 6;
  setcolor(8);          {stena}
  x:=0;
  y:=0;
  while (y<480) do
  begin
  rectangle(x,y,x+20,y+10);
  setfillstyle(1,6);
  floodfill(x+1,y+1,8);
    x:=x+20;
    if x>640 then
       begin
       y:=y+10;
       st:=st+1;
       if st mod 2 =0 then x:=0 else
       begin
       x:=0;
       rectangle(x,y,x+10,y+10);
                setfillstyle(1,6);
           floodfill(x+1,y+1,8);
               x:=10;
          end;
     end;
  end;
  for delta:=1 to 6 do                 {word}
  begin
    SetTextStyle(DefaultFont, 0, 9);
    setcolor(9);
    DrawCurveText(XText+delta, YText+delta,st1);
    delay(1000);
  end;
  SetTextStyle(DefaultFont, 0, 9);
  setcolor(1);
  DrawCurveText(XText, YText,st1);
  getmem(q,imagesize(0,370,70,430));
  repeat
   y2:=CalcYPos(x1);              { dvigenie prigyna}
   y1:=round(y2);
   getimage(x1,y1,x1+70,y1+80,q^);
   putimage(x1,y1,p^,2);
   delay(80);
   putimage(x1,y1,q^,0);
   x1:=x1+20;
  until (x1>(getmaxx-100));
  putimage(x1,y1,p^,2);
end;
{--------------------------zast-------------------------------}

{---------------------------fall-------------------------------}
function fall(y3:integer):boolean;
begin
 gp1:=getpixel(x3+1,310);
 gp2:=getpixel(x3+49,310);
 if (gp1=0) and (gp2=0) and (y3=241) then
 fall:=true
 else fall:=false;
end;
{---------------------------fall-------------------------------}

{-------------------------bonys-------------------------------}
procedure bonus;
begin
 if (s[1]<640) and (s[1]>=0) then putimage(s[1],221,pb1^,1);
 if (s[2]<640) and (s[2]>=0) then putimage(s[2],221,pb2^,1);
 if (s[3]<640) and (s[3]>=0) then putimage(s[3],221,pb3^,1);
 usScore:=random(4);
 if (usScore=1) and (s[1]<=0) and (s[2]<=450) and (s[3]<=450) then s[1]:=640 else
 if (usScore=2) and (s[2]<=0) and (s[1]<=450) and (s[3]<=450) then s[2]:=640 else
 if (usScore=3) and (s[3]<=0) and (s[1]<=450) and (s[2]<=450) then s[3]:=640;
 if (s[1]>=0) then s[1]:=s[1]-10;
 if (s[2]>=0) then s[2]:=s[2]-10;
 if (s[3]>=0) then s[3]:=s[3]-10;
 if (s[1]=-20) then
 begin
  usPM:=random(9);
  if usPM=0 then begin pb1:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusm');end
  else begin pb1:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusp');end;
 end;
 if (s[2]=-20) then
 begin
  usPM:=random(9);
  if usPM=0 then begin pb2:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusm');end
  else begin pb2:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusp');end;
 end;
 if (s[3]=-20) then
 begin
  usPM:=random(9);
  if usPM=0 then begin pb3:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusm');end
  else begin pb3:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/bonusp');end;
 end;
 if (s[1]<640) then putimage(s[1],221,pb1^,1);
 if (s[2]<640) then putimage(s[2],221,pb2^,1);
 if (s[3]<640) then putimage(s[3],221,pb3^,1);
end;
{--------------------------bonys-------------------------------}

{---------------------------hole-------------------------------}
procedure hole;
begin
 usHole:=random(4);
 if (usHole=0) and (d[1]<=0) and (d[2]<520) and (d[3]<520) then d[1]:=640 else
 if (usHole=1) and (d[2]<=0) and (d[1]<520) and (d[3]<520) then d[2]:=640 else
 if (usHole=2) and (d[3]<=0) and (d[2]<520) and (d[1]<520) then d[3]:=640;
 if (d[1]>=0) then d[1]:=d[1]-10;
 if (d[2]>=0) then d[2]:=d[2]-10;
 if (d[3]>=0) then d[3]:=d[3]-10;
 setfillstyle(1,0);
 bar(d[1],yd,d[1]+70,yd+48);
 setfillstyle(1,0);
 bar(d[2],yd,d[2]+70,yd+48);
 setfillstyle(1,0);
 bar(d[3],yd,d[3]+70,yd+48);
end;
{---------------------------hole-------------------------------}

{---------------------------score------------------------------}
procedure score;
var gp:byte;
begin
 for i:=1 to 3 do
 begin
  gp:=getpixel(s[i]+10,221);
  if (s[i]>=x3-10) and (s[i]<=x3+50) and (gp=10) and (y3=201) then
  begin
   scorech:=scorech+1;
   if i=1 then putimage(s[1],221,pb1^,1);
   if i=2 then putimage(s[2],221,pb2^,1);
   if i=3 then putimage(s[3],221,pb3^,1);
   s[i]:=-20;
   end;
  if (s[i]>=x3-10) and (s[i]<=x3+50) and (gp=12) and (y3=201) then
  begin
   scorech:=scorech-1;
   if i=1 then putimage(s[1],221,pb1^,1);
   if i=2 then putimage(s[2],221,pb2^,1);
   if i=3 then putimage(s[3],221,pb3^,1);
   s[i]:=-20;
  end;
 end;
 setfillstyle(1,0);
 bar(280,0,380,40);
 str(scorech,chs);
 setcolor(15);
 settextstyle(0,0,5);
 outtextxy(303,3,chs);
end;
{---------------------------score------------------------------}

{--------------------------stena-------------------------------}
procedure stena;
const ys=300;
var xs:integer;
begin
 xs:=0;
 if stf then begin p2:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/stena1');end
 else begin p2:=loader('c:/bp/bin/work/kurs1/vvodnik/marina/stena2');end;
 putimage(xs,ys,p2^,0);
 for i:=1 to 16 do
  begin
   xs:=xs+40;
   putimage(xs,ys,p2^,0);
  end;
  hole;
  bonus;
  score;
  delay(10000);
  stf:=not(stf);
end;
{--------------------------stena-------------------------------}

{--------------------------kolobok-----------------------------}
procedure kolobok;
begin
 if not(usPrijok) then
 begin
  putimage(x3,y3,p^,1);
  if (x3=10) and (y3=0) then putimage(x3,y3,p^,1);
  key:=readkey;
  case key of
             #72:begin y3:=y3-40;usPrijok:=true;end;
             #75:begin x3:=x3-40;end;
             #77:begin x3:=x3+40;end;
  end;
  if (y3=201) and not(usPrijok) then y3:=241;
  putimage(x3,y3,p^,1);
 end;
end;
{--------------------------kolobok-----------------------------}

{---------------------------game-------------------------------}
procedure game;
begin
 scorech:=0;
 stf:=false;
 s[1]:=-20;
 s[2]:=-20;
 s[3]:=-20;
 d[1]:=-70;
 d[2]:=-70;
 d[3]:=-70;
 usPrijok:=false;
 cleardevice;
 ch:=0;
 x3:=10;
 y3:=241;
 putimage(x3,y3,p^,1);
 repeat
  stena;
  if usPrijok then
   begin
    ch:=ch+1;
    if ch=6 then
    begin
     putimage(x3,y3,p^,1);
     y3:=241;
     putimage(x3,y3,p^,1);
     usPrijok:=false;
     ch:=0;
    end;
   end;
  if keypressed then kolobok;
  if keypressed then kolobok;
  if fall(y3) then
  begin
   cleardevice;
   key:=#27;
   setcolor(4);
   settextstyle(0,0,5);
   outtextxy(130,170,'You lost!!');
   settextstyle(0,0,3);
   outtextxy(130,210,'Your score is ');
   outtextxy(490,210,chs);
  end;
  if (chs='20') then
  begin
   cleardevice;
   key:=#27;
   setcolor(4);
   settextstyle(0,0,5);
   outtextxy(130,170,'You WIN!!!');
   settextstyle(0,0,3);
   outtextxy(130,210,'Your score is ');
   outtextxy(490,210,chs);
  end;
 until key=#27;
end;
{--------------------------game-------------------------------}

{---------------------------help-------------------------------}
procedure help;
var f:text;
    tx,ty:integer;
    sth:string;
begin
 assign(f,'Help.txt');
 reset(f);
 tx:=10;
 ty:=25;
 while not(eof(f)) do
 begin
  readln(f,sth);
  setcolor(14);
  settextstyle(0,0,3);
  outtextxy(tx,ty,sth);
  ty:=ty+50;
 end;
end;
{---------------------------help-------------------------------}

{---------------------------menu-------------------------------}
procedure menu;
var menpos:word;
begin
 menpos:=1;
 setfillstyle(1,9);
 bar(240,60,355,200);
 setfillstyle(1,1);
 bar(245,65,350,195);
 setcolor(14);
 settextstyle(0,0,3);
 outtextxy(250,70,'Game');
 setcolor(15);
 settextstyle(0,0,3);
 outtextxy(250,120,'Help');
 setcolor(15);
 settextstyle(0,0,3);
 outtextxy(250,170,'Exit');
 while key <> #13 do
 begin
  key:=readkey;
  if key=#0 then key:=readkey;
  case key of
             #72:begin {++Opredeliaem smeshenie ukozatelia, ego novuu poziciu i  koordinati++}
                  menpos:=menpos-1;
                  if menpos=0 then menpos:=3
                 end;
             #80:begin
                  menpos:=menpos+1;
                  if menpos=4 then menpos:=1
                 end;
  end;
  case menpos of {++Vidiliaem knopky menu v zavisimosti ot pozicii ukozatelia:1-game;2-help;3-record;4-exit;++}
              1:begin
                 setcolor(14);
                 settextstyle(0,0,3);
                 outtextxy(250,70,'Game');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,120,'Help');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,170,'Exit');
                end;
              2:begin
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,70,'Game');
                 setcolor(14);
                 settextstyle(0,0,3);
                 outtextxy(250,120,'Help');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,170,'Exit');
                end;
              3:begin
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,70,'Game');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,120,'Help');
                 setcolor(14);
                 settextstyle(0,0,3);
                 outtextxy(250,170,'Exit');
                end;
  end;
  key:=readkey;
  if key=#13 then
  case menpos of {++Vizivaem proceduru v zavisimosti ot pozicii ukozatelia:1-game;2-help;3-record;4-exit;++}
              1:begin
                 cleardevice;
                 game;
                 key:=readkey;
                 cleardevice;
                 zast;
                 menu;
                end;
              2:begin
                 cleardevice;
                 help;
                 key:=readkey;
                 cleardevice;
                 zast;
                 menu;
                end;
              3:begin
                 halt
                end;
  end;
 end;
 key:=#0;
end;
{---------------------------menu-------------------------------}
{----------------------------ingr------------------------------}
procedure ingr;
var gd,gm:integer;
begin
 gd:=detect;
 initgraph(gd,gm,'');
end;
{----------------------------ingr------------------------------}
Begin
 ingr;
 zast;
 menu;
 readln;
 CloseGraph;
end.








