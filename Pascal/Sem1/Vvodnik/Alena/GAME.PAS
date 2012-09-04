program Cars;
uses Crt, Graph;
var t,c,e,g,z,pul,n1,n2,n3,n4:pointer;
    i,z1, p2, Color,GraphDriver, GraphMode,l: Integer;
    Palette: PaletteType;
    y1,x1,xb,yb:integer;
    p1,p:pointer;
    x,y,xp,yp,k,xf,yf,ns,nt,mp,usshot:integer;
    a:char;
    usMT,usMP,usBT,usBP1,usBP2,usFS:boolean;
    score,bullets,sz:longint;
    pic,pic1,pic2,pic3:word;
{-------------------------------------Test-----------------------------}
function test:integer;
var
   f : text;
   s : char;
   rez,e : string;
   i,j,t,r,cod,a: Integer;
begin
  Setcolor(11);
  Rectangle(120,120,500,360);
  SetTextStyle(0,0,8);
  Setcolor(12);
  Outtextxy (200,140,'TEST');
  SetTextStyle(0,0,2);
  Outtextxy (190,240,'For HAPPY ENGLISH');
  Outtextxy (240,280,'TO ENTER');
   Readln;
     SetTextStyle(0,0,1);
assign(f,'c:/bp/bin/work/kurs1/vvodnik/alena/files/v2.txt');
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
end;
{-------------------------------------Test-----------------------------}
{----------------------------------Loader------------------------------}
function loader(filename:string):pointer;
 var
 f1:file;
  g:pointer;
  begin
  assign(f1,filename);
  reset(f1,1);
  sz:=filesize(f1);
  Getmem(g,sz);
  blockread(f1,g^,sz);
  close(f1);
  loader:=g;
 end;
{----------------------------------Loader------------------------------}
{----------------------------------Help--------------------------------}
procedure help;
var s,x,y,r,al,i,l:integer;
    k,e,ex,sh:pointer;
t:text;  a:char;
f,u,v,h:string;
begin
   setcolor(2);
  line (26,0,613,0);    {ramka}
  line (639,26,639,453);
  line (26,479,613,479);
  line (0,26,0,453);
  ellipse(613,453,270,0,25,25);
  ellipse(613,26,0,90,25,25);
  ellipse(26,26,90,180,25,25);
  ellipse(26,453,180,270,25,25); {ramka}
  setbkcolor(0);
   k:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Pk');
   e:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Enter');
   ex:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Ex');
   sh:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Shoot');
   putimage(280,100,k^,0);
   putimage(280,200,e^,0);
   putimage(280,250,sh^,0);
   putimage(280,300,ex^,0);
 settextstyle(0,0,0);
 OutTextXY(100,120,'MAIN BOTTONS TO MOVE >');
 OutTextXY(100,220,'TAKE A THING >');
 OutTextXY(100,270,'TO SHOOT >');
 OutTextXY(100,320,'EXIT >');
 readln;
 end;
{----------------------------------Help--------------------------------}
{--------------------------------pscore--------------------------------}
procedure pscore;
var scorest,bulletst:string;
begin
 setfillstyle(1,0);
 bar(575,300,640,350);
 str(score,scorest);
 str(bullets,bulletst);
 setcolor(15);
 settextstyle(0,0,1);
 outtextxy(575,310,'Score:');
 outtextxy(575,320,scorest);
 outtextxy(575,330,'Bullets:');
 outtextxy(575,340,bulletst);
end;
{--------------------------------pscore--------------------------------}
{------------------------------meetBullet------------------------------}
procedure meetBullet;
begin
 if (x>=xb-21) and (x<=xb+21) and (y>=yb-21) and (y<=yb+21) then
 begin
  usBT:=true;
  usBP1:=true;
 end;
 if usBT then
 begin
  setfillstyle(1,8);
  bar(xb,yb,xb+20,yb+20);
  bullets:=bullets+3;
  pscore;
  usBT:=false;
  xb:=-50;
  yb:=-50;
 end;
end;
{------------------------------meetbullet------------------------------}
{------------------------------meetmoney-------------------------------}
procedure meetMoney;
begin
 if (x>=x1-25) and (x<=x1+25) and (y>=y1-25) and (y<=y1+25) then
 begin
  usMT:=true;
  usMP:=true;
 end;
 if usMT then
 begin
  setfillstyle(1,8);
  bar(x1,y1,x1+20,y1+20);
  score:=score+1;
  pscore;
  usMT:=false;
  if (score mod 3=0) and (score>=3) then usBP2:=true;
 end;
end;
{------------------------------meetmoney-------------------------------}
{-----------------------------putbullet--------------------------------}
procedure putbullet;
var getp1,getp2,getp3,getp4:integer;
    b:pointer;
begin
 b:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/bul');
 repeat
  xb:=random(620);
  yb:=random(460);
  getp1:=getpixel(xb,yb);
  getp2:=getpixel(xb+20,yb);
  getp3:=getpixel(xb,yb+20);
  getp4:=getpixel(xb+20,yb+20);
 until (getp1=8) and (getp2=8) and (getp3=8) and (getp4=8);
 usBP1:=false;
 usBP2:=false;
 usBT:=false;
 putimage(xb,yb,b^,1);
 freemem(b,sz);
end;
{-----------------------------putBullet--------------------------------}
{------------------------------putmoney--------------------------------}
procedure putMoney;
var getp1,getp2,getp3,getp4:integer;
    t:pointer;
begin
 t:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Mon');
 repeat
  x1:=random(620);
  y1:=random(460);
  getp1:=getpixel(x1,y1);
  getp2:=getpixel(x1+20,y1);
  getp3:=getpixel(x1,y1+20);
  getp4:=getpixel(x1+20,y1+20);
 until (getp1=8) and (getp2=8) and (getp3=8) and (getp4=8);
 putimage(x1,y1,t^,0);
 usMP:=false;
 freemem(t,sz);
end;
{------------------------------putmoney--------------------------------}
{------------------------------drawfield-------------------------------}
procedure DrawField;
begin
   SetFillStyle(1,10);
   Bar(30,325,70,375);
   Bar(105,325,190,375);
   Bar(30,25,60,55);
   Bar(30,85,60,175);
   Bar(95,25,140,55);
   Bar(95,85,140,175);
   Bar(175,25,190,55);
   Bar(175,85,190,175);
   Bar(35,205,200,245);
   Bar(265,30,370,65);
   Bar(245,100,330,125);
   Bar(395,420,475,445);
   Bar(500,305,540,375);
   setcolor(7);
   Line(0,440,200,440);
   Line(200,440,200,400);
   Line(200,400,0,400);
   Line(0,400,0,300);
   Line(0,300,200,300);
   Line(200,300,200,270);
   Line(200,270,0,270);
   Line(0,270,0,0);
   Line(0,0,630,0);
   Line(630,0,630,290);
   Line(630,290,570,290);
   Line(570,290,570,475);
   Line(570,475,0,475);
   Line(0,475,0,440);
   Line(250,440,250,375);
   Line(250,375,360,375);
   Line(360,375,360,315);
   Line(360,315,442,315);
   Line(442,315,442,275);
   Line(442,275,360,275);
   Line(360,275,360,260);
   Line(360,260,457,260);
   Line(457,260,457,395);
   Line(457,395,370,395);
   Line(370,395,370,440);
   Line(370,440,250,440);
   Line(250,335,250,150);
   Line(250,150,370,150);
   Line(370,150,370,100);
   Line(370,100,432,100);
   Line(432,100,432,215);
   Line(432,215,320,215);
   Line(320,215,320,335);
   Line(320,335,250,335);
   Line(414,35,590,35);
   Line(590,35,590,245);
   Line(590,245,505,245);
   Line(505,245,505,163);
   Line(505,163,582,163);
   Line(582,163,582,133);
   Line(582,133,475,133);
   Line(475,133,475,72);
   Line(475,72,414,72);
   Line(414,72,414,35);
   SetFillStyle(1,8);
   FloodFill(13,443,7);
   SetFillStyle(1,2);
   FloodFill(251,431,7);
   FloodFill(251,231,7);
   FloodFill(505,46,7);
   SetFillStyle(1,10);
   Bar(30,325,70,375);
   Bar(105,325,190,375);
   Bar(30,25,60,55);
   Bar(30,85,60,175);
   Bar(95,25,140,55);
   Bar(95,85,140,175);
   Bar(175,25,190,55);
   Bar(175,85,190,175);
   Bar(35,205,200,245);
   Bar(265,30,370,65);
   Bar(245,100,330,125);
   Bar(395,420,475,445);
   Bar(500,305,540,375);
end;
{------------------------------drawfield-------------------------------}
{---------------------------------shot---------------------------------}
procedure shot;
var shx,shy:integer;
    gp1:byte;
begin
 pscore;
 shx:=4;
 shy:=4;
 if  (xf=x+2) and (yf=y-10) or
     (xf=x+2) and (yf=y+16) or
     (xf=x-10) and (yf=y+2) or
     (xf=x+16) and (yf=y+2) then
  begin
   putimage(xf,yf,pul^,1);
  end;
  if usFS then ns:=nt;
  putimage(xf,yf,pul^,1);
  if ns=1 then begin shx:=0;shy:=-shy;usFS:=false;end;{--------------}
  if ns=2 then begin shx:=0;shy:=shy;usFS:=false;end; {++Opredeliaem napravlenie dvizenia snariada++}
  if ns=3 then begin shx:=-shx;shy:=0;usFS:=false;end;
  if ns=4 then begin shx:=shx;shy:=0;usFS:=false;end; {--------------}
  xf:=xf+shx;
  yf:=yf+shy;
  gp1:=getpixel(xf,yf);
  if gp1<>8 then usshot:=0;{++Popali v stenu++}
  if xf<0 then usshot:=0;
  if xf>630 then usshot:=0;
  if yf<0 then usshot:=0;
  if yf>480 then usshot:=0;
  putimage(xf,yf,pul^,1);
  if (usshot=0) then putimage(xf,yf,pul^,1);
  delay(1000);
  if (xf>xp) and (xf<xp+20) and (yf>yp) and (yf<yp+20) then
  begin
   usshot:=0;
   setfillstyle(1,8);
   bar(xp,yp,xp+18,yp+18);
   score:=score+1;
   pscore;
   xp:=600;
   yp:=10;
  end;
end;
{---------------------------------shot---------------------------------}
{------------------------------moveplayer------------------------------}
procedure MovePlayer;
begin
  a:=readkey;
  i:=ord(a);
  case i of
            72: begin
                 setfillstyle(1,8);
                 bar(x,y+2,x+18,y+16);
                 pic:=getpixel(x,y-2);
                 pic1:=getpixel(x+19,y-2);
                 pic2:=getpixel(x+9,y-2);
                 pic3:=getpixel(x+10,y-2);
                 if (pic<>8) or (pic1<>8) or (pic2<>8) or (pic3<>8) then
                 y:=y
                 else
                 y:=y-2;
                 putimage(x,y,g^,0);
                 nt:=1;
                end;
            80: begin
                 setfillstyle(1,8);
                 bar(x,y+1,x+18,y+15);
                 pic1:=getpixel(x,y+23);
                 pic:=getpixel(x+18,y+23);
                 pic2:=getpixel(x+9,y+23);
                 pic3:=getpixel(x+10,y+23);
                 if (pic<>8) or (pic1<>8) or (pic2<>8) or (pic3<>8) then
                 y:=y
                 else
                 y:=y+2;
                 putimage(x,y,c^,0);
                 nt:=2;
                end;
            75: begin
                 setfillstyle(1,8);
                 bar(x+2,y,x+17,y+18);
                 pic:=getpixel(x-2,y);
                 pic1:=getpixel(x-2,y+18);
                 pic2:=getpixel(x-2,y+9);
                 pic3:=getpixel(x-2,y+10);
                 if (pic<>8) or (pic1<>8) or (pic2<>8) or (pic3<>8) then
                 x:=x
                 else
                 x:=x-2;
                 putimage(x,y,z^,0);
                 nt:=3;
                end;
            77: begin
                 setfillstyle(1,8);
                 bar(x,y,x+18,y+18);
                 pic:=getpixel(x+23,y);
                 pic1:=getpixel(x+23,y+18);
                 pic2:=getpixel(x+23,y+9);
                 pic3:=getpixel(x+23,y+10);
                 if (pic<>8) or (pic1<>8) or (pic2<>8) or (pic3<>8) then
                 x:=x
                 else
                 x:=x+2;
                 putimage(x,y,e^,0);
                 nt:=4;
                end;
             32:begin
                 if (usshot=0) and (bullets>0) then
                 begin
                  bullets:=bullets-1;
                  usshot:=1;
                  usFS:=true;
                  if nt=1 then begin xf:=x+2; yf:=y-10;end;
                  if nt=2 then begin xf:=x+2; yf:=y+16;end;
                  if nt=3 then begin xf:=x-10; yf:=y+2;end;
                  if nt=4 then begin xf:=x+16; yf:=y+2;end;
                 end;
                end;
   end;
end;
{------------------------------moveplayer------------------------------}
{------------------------------movepolice------------------------------}
procedure movePolice;
var pixp1,pixp2,pixp3,pixp4,napr:byte;
begin
 if (x>xp) then napr:=1;
 if (x<xp) then napr:=2;
 if (y>yp) then napr:=3;
 if (y<yp) then napr:=4;
 case napr of
             1:begin
                setfillstyle(1,8);
                bar(xp+1,yp+1,xp+18,yp+18);
                pixp1:=getpixel(xp+21,yp);
                pixp2:=getpixel(xp+21,yp+18);
                pixp3:=getpixel(xp+21,yp+9);
                pixp4:=getpixel(xp+21,yp+10);
                if (pixp1<>8) or (pixp2<>8) or (pixp3<>8) or (pixp4<>8) then
                 begin
                  if (yp>10) then
                   begin
                    yp:=yp+1;
                    putimage(xp,yp,n4^,0);
                   end
                  else
                   begin
                    xp:=xp-1;
                    putimage(xp,yp,n3^,0);
                   end;
                 end
                else
                 begin
                  xp:=xp+1;
                  putimage(xp,yp,n1^,0);
                 end;
                delay(2000);
               end;
             2:begin
                setfillstyle(1,8);
                bar(xp,yp,xp+18,yp+18);
                pixp1:=getpixel(xp-2,yp+1);
                pixp2:=getpixel(xp-2,yp+17);
                pixp3:=getpixel(xp-2,yp+9);
                pixp4:=getpixel(xp-2,yp+10);
                if (pixp1<>8) or (pixp2<>8) or (pixp3<>8) or (pixp4<>8) then
                 begin
                  yp:=yp-1;
                  putimage(xp,yp,n2^,0);
                 end
                else
                 begin
                  xp:=xp-1;
                  putimage(xp,yp,n3^,0);
                 end;
                delay(2000);
               end;
             3:begin
                setfillstyle(1,8);
                bar(xp,yp,xp+17,yp+17);
                pixp1:=getpixel(xp,yp+21);
                pixp2:=getpixel(xp+18,yp+21);
                pixp3:=getpixel(xp+9,yp+21);
                pixp4:=getpixel(xp+10,yp+21);
                if (pixp1<>8) or (pixp2<>8) or (pixp3<>8) or (pixp4<>8) then
                 begin
                  xp:=xp-1;
                  putimage(xp,yp,n2^,0);
                 end
                else
                 begin
                  yp:=yp+1;
                  putimage(xp,yp,n3^,0);
                 end;
                delay(2000);
               end;
             4:begin
                setfillstyle(1,8);
                bar(xp,yp,xp+18,yp+18);
                pixp1:=getpixel(xp,yp-1);
                pixp2:=getpixel(xp+18,yp-1);
                pixp3:=getpixel(xp+9,yp-1);
                pixp4:=getpixel(xp+10,yp-1);
                if (pixp1<>8) or (pixp2<>8) or (pixp3<>8) or (pixp4<>8) then
                 begin
                  xp:=xp-1;
                  putimage(xp,yp,n2^,0);
                 end
                else
                 begin
                  yp:=yp-1;
                  putimage(xp,yp,n3^,0);
                 end;
                delay(2000);
               end;
 end;
end;
{------------------------------movepolice------------------------------}
{------------------------------win-------------------------------------}
procedure win;
begin
 cleardevice;
 setcolor(15);
 settextstyle(0,0,4);
 outtextxy(150,150,'You win!!!');
 i:=27;
end;
{------------------------------win-------------------------------------}
{-------------------------------meetPolice-----------------------------}
procedure meetPolice;
begin
 if (xp>x-20) and (xp<x+20) and (yp>y-20) and (yp<y+20) then
 begin
  cleardevice;
  setcolor(15);
  settextstyle(0,0,4);
  outtextxy(150,150,'You lost!!!');
  i:=27;
 end;
end;
{-------------------------------meetPolice-----------------------------}
{----------------------------------Game--------------------------------}
procedure Game;
var
   t,zap:pointer;
   k,a : char;
   s:string;
begin
 Randomize;
 DrawField;
 e:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/m1');
 c:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/m2');
 g:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/m3');
 z:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/m4');
 n1:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Men1');
 n2:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Men2');
 n3:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Men3');
 n4:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Men4');
 zap:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Zap');
 pul:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/Pul');
 y:=450;
 x:=4;
 putimage(x,y,e^,0);
 xp:=600;
 yp:=10;
 score:=0;
 bullets:=0;
 usMP:=true;
 usMT:=false;
 usBP1:=true;
 usBP2:=false;
 usBT:=false;
 usFS:=true;
 usshot:=0;
 nt:=4;
 pscore;
 repeat
  if keypressed then MovePlayer;
  if keypressed then MovePlayer;
  MovePolice;
  if (usshot=1) then shot;
  meetMoney;
  meetBullet;
  if usMP then putmoney;
  if (usBP1) and (usBP2) then putBullet;
  if (score>=50000) then win;
  if (usshot=1) then shot;
  meetPolice;
 until i=27;
 readln;
 i:=0;
end;
{----------------------------------Game--------------------------------}
{---------------------------------About--------------------------------}
procedure about;
begin
 setcolor(9);
 setbkcolor(0);
 p1:=loader('c:/bp/bin/work/kurs1/vvodnik/alena/files/About');
 putimage(30,20,p1^,0);
end;
{---------------------------------About--------------------------------}
{---------------------------------Ingr---------------------------------}
procedure ingr;
var
    gd,gm:integer;

begin
     gd:=detect;
     initgraph(gd,gm,'d:\borland\bp\bgi');
end;
{---------------------------------Ingr---------------------------------}
{---------------------------------Menu---------------------------------}
procedure menu;
const
     xpoz=18;
     ct=9;
     ctt=15;
     ypoz: array [1..4] of integer= (50,180,300,400);
     text: array [1..4] of string  = ('Help','Start','About','Exit');
var
    poz,i,a,b:integer;
    key:char; p,z:pointer;
begin         setcolor(9);
  poz:=1;
  setcolor(14);
  setlinestyle(1,4,4);
  SetTextStyle(0, 0, 6);
  OutTextXY(205, 280, ' DriveR ');
  setcolor(3);
  OutTextXY(209, 280, ' DriveR ');
  setbkcolor(0);
  setcolor(ct);
  settextstyle(0,0,3);
  for i:=1 to 4 do
   begin
    outtextxy(xpoz,ypoz[i],text[i]);
    poz:=1;
   end;
  while key <> #13 do
   begin
    setcolor(ctt);
    outtextxy(xpoz,ypoz[poz],text[poz]);
    key:=readkey;
    if key=#0 then key:=readkey;
    case key of
         #72:begin
             setcolor(ct);
             outtextxy(xpoz,ypoz[poz],text[poz]);
             if poz=1 then poz:=4
             else poz:=poz-1
             end;
         #80:begin
             setcolor(ct);
             outtextxy(xpoz,ypoz[poz],text[poz]);
             if poz=4 then poz:=1
             else poz:=poz+1;
             end;
         #13:case poz of
                  1:begin cleardevice;help;key:=readkey;cleardevice;menu;end;
                  2:begin cleardevice;game;cleardevice;menu;end;
                  3:begin cleardevice;about;key:=readkey;cleardevice;menu;end;
                  4:halt;
             end;
    end;
    end;
end;
{---------------------------------Menu---------------------------------}
{-----------------------------^ZASTAVKA^---------------------------------------------}
 begin
 GraphDriver := Detect;
 InitGraph(GraphDriver, GraphMode,' ');

 {Рисуем Машину}
 circle(400,140,20); circle(450,140,20); {Фары}
 Line(420,135,430,135); {Лобовое окно}
 Line(420,145,430,145);
 Line(390,160,460,160);
 Line(460,160,480,230);
 Line(480,230,370,230);
 Line(370,230,390,160);
 Line(370,260,480,260); {копот}
 Arc(370,245,90,270,15);
 Arc(480,245,270,90,15);
 Ellipse(380,240,0,360,12,7);
 Ellipse(392,250,0,360,5,2);
 Ellipse(475,240,0,360,12,7);
 Ellipse(460,250,0,360,5,2);
 Line(370,260,360,265); {Колёса}
 line(380,260,370,265);
 Line(350,265,375,265);
 Arc(375,269,0,90,4);
 Line(379,269,379,305);
Arc(375,306,270,0,4);
 Line(375,310,350,310);
 Arc(350,306,180,270,4);
 line(346,306,346,270);
 Arc(350,269,90,180,4);
 Line(480,260,490,265);
 Line(470,260,480,265);
 Line(475,265,500,265);
 Arc(500,269,0,90,4);
 Line(504,269,504,305);
 Arc(500,306,270,0,4);
 Line(500,310,475,310);
 Arc(475,306,180,270,4);
 Line(471,306,471,270);
 Arc(475,270,90,180,4);
 line(390,260,390,283);
 Arc(386,283,270,0,4);
 Line(386,287,379,287);
 Line(460,260,460,283);
 Arc(464,283,180,270,4);
 Line(464,287,471,287);
 line(355,240,355,265);
 Line(495,240,495,265);
 Rectangle(10,10,280,300);  {Карта}
 Rectangle(10,10,30,50);
 Rectangle(50,40,70,90);
 Rectangle(30,110,75,130);
 Rectangle(10,155,60,189);
 Rectangle(150,30,180,55);
 Rectangle(30,220,90,240);
 Rectangle(70,70,90,90);
 Rectangle(87,10,107,45);
 Rectangle(200,160,230,180);
 Rectangle(210,200,230,240);
 Rectangle(130,200,200,260);

  Line(147,70,197,70);
  MoveTo(197,70);
 LineTo(197,45);
 LineTo(227,45); LineTo(227,100);
 Lineto(180,100); LineTo(180,110);
 LineTo(170,110); LineTo(170,100);
 LineTo(147,100); LineTo(147,70);
 Circle(100,180,20);
 Bar(185,110,230,113); {Зебра на карте}
 Bar(185,118,230,121);
 Bar(185,126,230,129);
 Bar(185,134,230,137);
 Bar(185,142,230,145);
 Bar(185,150,230,152);
 {Пишем Название}
  SetFillStyle(1,1);
  {П}
 Bar3D(30,360,60,450,10,TopOn);
 Bar3D(30,340,100,360,10,TopOn);
 Bar3D(80,360,100,450,10,TopOff);
  {O}
 Bar3D(120,360,150,430,10,TopOn);
 Bar3D(120,340,190,360,10,TopOn);
 Bar3D(170,360,190,450,10,TopOff);
 Bar3D(120,430,190,450,10,TopOn);
  {Г}
 Bar3D(210,360,230,450,10,TopOn);
 Bar3D(210,340,250,360,10,TopOn);
  {O}
 Bar3D(270,360,300,450,10,TopOn);
 Bar3D(270,340,340,360,10,TopOn);
 Bar3D(320,360,340,450,10,TopOff);
 Bar3D(270,430,340,450,10,TopOn);
   {Н}
 Bar3D(360,340,390,450,10,TopOn);
 Bar3D(390,360,420,370,10,TopOn);
 Bar3D(420,340,440,450,10,TopOn);
   {Я}
 Bar3D(460,350,490,400,10,TopOff);
 Bar3D(460,340,530,350,10,TopOn);
 Bar3D(530,340,550,450,10,TopOn);
 Bar3D(460,400,530,410,10,TopOn);
 MoveTo(490,410); {Ножка буквы Я}
 LineTo(550,410);
 LineTo(490,450); LineTo(460,450);
 LineTo(490,410);
 Line(0,0,10,10); Line(290,0,280,10);{Рамка для карты}
 Line(0,310,10,300); Line(290,310,280,300);
 Line(290,310,290,0); Line(290,0,0,0);
 Line(0,0,0,310);  Line(0,310,290,310);
  Line(345,275,330,311); Line(505,275,520,311); {Строим дорогу под машиной}
 Line(330,311,520,311);
 SetFillStyle(1,1);    {Красим Ножку у буквы Я}
 FloodFill(480,440,White); Bar(531,370,548,449);
 SetFillStyle(1,Green); {Красим домики на карте}
 FloodFill(90,30,White);
 SetFillStyle(1,13);
 FloodFill(151,31,White);
 SetFillStyle(1,6);
 Bar(89,32,100,42);
 Bar(153,35,156,45);
 SetFillStyle(1,5);
 FloodFill(12,12,White);
 FloodFill(11,156,White);
 SetFillStyle(1,0);
 Bar(11,30,20,34);
 Bar(24,35,26,47);
 SetFillStyle(1,2);
 FloodFill(110,185,White);
 FloodFill(160,99,White);
 SetFillStyle(1,1);
 FloodFill(201,161,White);
 SetFillStyle(1,4);
 Bar(133,163,140,165);
 Bar(123,163,125,175);
 SetFillStyle(1,14);
 FloodFill(131,201,White);
 FloodFill(51,88,White);
 SetFillStyle(1,6);
 FloodFill(31,111,WHite);
 Bar(125,145,130,155);
 SetFillStyle(1,12);
 FloodFill(80,80,White);
 SetFillStyle(1,10);
 FloodFill(50,230,White);
 SetFillStyle(1,3);
 FloodFill(218,201,White);
 SetFillStyle(1,6);
 Bar(210,164,219,167);
 Bar(110,264,119,267);
 SetFillStyle(1,8);    {Красим саму карту}
 FLoodFill(148,101,White);
 FLoodFill(30,152,White);
 SetFillStyle(SolidFill,9); {Красим фары}
 FloodFill(400,140,White);
 SetFillStyle(SolidFill,Red);
 FloodFill(431,140,White);
 SetFillStyle(8,15);      {Лоб.Окно}
 FloodFill(410,200,White);
 SetFillStyle(1,1);  {Копот}
 FloodFill(431,250,White);
 SetFillStyle(1,14);       {Фары}
 FloodFill(380,240,White);
 FloodFill(395,250,White);
 FloodFill(475,240,White);
 FloodFill(461,250,White);
 SetFillStyle(9,6);  {Колёса красим}
 FloodFill(361,290,White);
 FloodFill(479,290,White);
 FloodFill(381,261,White);
 FloodFill(365,261,White);
 FloodFill(489,261,White);
 FloodFill(465,261,White);
 SetFillStyle(1,7);      {Рамка Карты}
 FloodFill(1,9,White);
 SetFillStyle(1,7);
 FloodFill(200,1,White);
 SetFillStyle(1,LightGray);    {Дорога под машиной}
 FloodFill(335,310,White);
 FloodFill(450,310,White);
 FloodFill(519,310,White);
 {Закрашиваем Объём у названия}
 SetFillStyle(1,14);
  {Буква П}
 FloodFill(41,339,White);
 FloodFill(61,400,White);
 FloodFill(102,361,White);
 FloodFill(102,341,White);
 floodFill(102,341,White);
 FloodFill(220,338,White); {Буква Г}
 FloodFill(251,341,White);
 FloodFill(231,362,White);
 FloodFill(125,338,White);  {Буква О}
 FloodFill(153,368,White);
 FloodFill(194,358,White); FloodFill(192,428,White);
 FloodFill(194,338,White); FloodFill(152,428,White);
 FloodFill(162,428,White); FloodFill(192,445,White);
 FloodFill(275,338,White); {O}
 FloodFill(305,365,White); FloodFill(343,345,White);
 FloodFill(343,368,White); FloodFill(313,428,White);
 FloodFill(343,438,White); FloodFill(342,427,White);
 FloodFill(303,428,White);
 FloodFill(365,338,White);  {H}
 FloodFill(393,428,White);  FloodFill(395,358,White);
 FloodFill(395,346,White);  FloodFill(418,356,White);
 FloodFill(435,336,White);  FloodFill(445,436,White);
 FloodFill(522,338,White);  {Я}
 FloodFill(545,337,White);  FloodFill(538,336,White);
 FloodFill(495,356,White);  FloodFill(495,396,White);
 FloodFill(500,396,White);  FloodFill(555,436,White);
 SetFillStyle(1,1);    {krasim cosayki}
 Bar(121,341,149,449);  Bar(461,341,531,349);
 Bar(171,341,189,449);  Bar(491,401,531,422);
 Bar(31,341,59,449);    Bar(321,341,339,449);
 Bar(81,341,99,449);
 Bar(211,341,229,449);
 Bar(271,341,299,449);
 Bar(389,361,439,369);
 Bar(461,341,489,401);
 {неуправляемые движ.
 мил.машина едет
 p1:=loader('Car1');
  for i:=21 to 250 do
   begin
    putimage(i,130,p1^,1);
    delay(80);
    putimage(i,130,p1^,1);
   end;
   putimage(i,130,p1^,XORput); }
 repeat
  i:=i+1;
  if i mod 2=0 then
           begin        {Мигалки}
            z1:=9;
            p2:=4;
           end
  else
    begin
     z1:=4;
     p2:=9;
    end;
   setfillstyle(1,z1);
  floodfill(400,140,15);{П} floodfill(143,361,15);
 {О}  floodfill(41,404,15);   {Мигают Буквы}
{H} floodfill(384,408,15);  {Г} floodfill(241,342,15);
  {О} floodfill(306,356,15); {Я} floodfill(545,347,15);
{Я} floodfill(545,347,15);
 setfillstyle(1,p2);
  floodfill(450,140,15);
  delay(80);
 until KeyPressed;
 repeat
   SetBkColor(0);
until KeyPressed;
cleardevice;
 repeat
   a:=readkey;
   l:=ord(a);

{  k:=test;       }
  cleardevice;
  if k=1 then
     outtextxy(100,100,'Допущен !!! Молодец !!!! ')
  else
     outtextxy(100,100,'Не допущен !!! Попробуй еще раз !!!! ');
  Readln;
  cleardevice;
  until l=13;
{-----------------------------^ZASTAVKA^---------------------------------------------}
  {--------------------------------------------------------------------}
 { begin
  Zastavka; }
  ingr;
  cleardevice;
  menu;

 end.