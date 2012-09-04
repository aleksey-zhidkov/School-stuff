uses crt,graph;
const Nu=0.3; nu2=0.003; hk=3; lk=23; rm=0.18; rk=0.35; g=9.8; p=10; mass=1;
      sXmax=600; sYmin=20; sXmin=40;
      sYmax=360; Xmin=0; xmax=26; ymax=6; ymin=0;
type param = record
              value:real;
              name:string;
             end;
var  par:array[1..p] of param;
     dt,ymax2:real;
{----------------------\/INGR\/------------------}
procedure ingr;
var gd,gm,errcode:integer;
begin
 gD:=Detect;
 InitGraph(gD, gM, '');
end;
{----------------------/\INGR/\------------------}
{++++++++++++++++++++++++++++++++++++++++++++++++++++}
{----------------------\/HELP\/-----------------------}
procedure help;
var f:text;
    x,y:word;
    str:string;
begin
 assign(f,'help.txt');
 reset(f);
 setcolor(15);
 outtextxy(400,467,'Alexey Zhidkov, HCI NSU, 2004');
 setcolor(9);
 settextstyle(0,0,4);
 outtextxy(266,16,'Help');
 setcolor(11);
 outtextxy(264,14,'Help');
 x:=25;y:=56;
 setcolor(9);
 line(x,y,x,y+400);
 line(x,y,x+592,y);
 line(x+592,y,x+592,y+400);
 line(x,y+400,x+592,y+400);
 setcolor(11);
 line(x-2,y-2,x-2,y+398);
 line(x-2,y-2,x+590,y-2);
 line(x+590,y-2,x+590,y+398);
 line(x-2,y+398,x+590,y+398);
 setcolor(15);
 settextstyle(0,0,1);
 y:=y+2;
 while (eof(f)<>true) do
 begin
  readln(f,str);
  outtextxy(x+3,y,str);
  y:=y+10;
 end;
end;
{----------------------/\HELP/\-----------------------}
{++++++++++++++++++++++++++++++++++++++++++++++++++++}
{----------------------\/Formuls\/-----------------------}
procedure formuls;
var f:text;
    x,y,i:word;
    str:string;
begin
 assign(f,'formuls.txt');
 reset(f);
 setcolor(15);
 outtextxy(400,467,'Alexey Zhidkov, HCI NSU, 2004');
 setcolor(9);
 settextstyle(0,0,4);
 outtextxy(206,16,'Formuls');
 setcolor(11);
 outtextxy(204,14,'Formuls');
 x:=25;y:=56;
 setcolor(9);
 line(x,y,x,y+400);
 line(x,y,x+592,y);
 line(x+592,y,x+592,y+400);
 line(x,y+400,x+592,y+400);
 setcolor(11);
 line(x-2,y-2,x-2,y+398);
 line(x-2,y-2,x+590,y-2);
 line(x+590,y-2,x+590,y+398);
 line(x-2,y+398,x+590,y+398);
 setcolor(15);
 settextstyle(0,0,1);
 y:=y+2;
 i:=0;
 while (eof(f)<>true) do
 begin
  readln(f,str);
  outtextxy(x+3,y,str);
  y:=y+10;
  inc(i);
  if (i=36) then
  begin
   y:=58;
   readkey;
   setfillstyle(0,0);
   bar(x+2,y,614,438);
  end;
 end;
end;
{----------------------/\Formuls/\-----------------------}
{++++++++++++++++++++++++++++++++++++++++++++++++++++++}
{----------------------\/Dem\/-------------------------}
 procedure dem;
 var mv,xk,yk,rs,rm2,xt,yt,ex,ey:integer;
     fex,fey,al:real;
     s:string;
 procedure drawfield;
 begin
  xk:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(lk-xmin));
  yk:=round(symax-((symax-symin)/(ymax-ymin))*(hk-ymin));
  rs:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(rk-xmin));
  rm2:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(rm-xmin));
  setcolor(4);
  line(xk,yk,xk+rs,yk);
  line(xk+rs+15,yk-40,xk+rs+15,yk+10);
  setcolor(11);
  line(sxmin,symin,sxmax,symin);
  line(sxmin,symin,sxmin,symax);
  line(sxmin,symax,sxmax,symax);
  line(sxmax,symin,sxmax,symax);
  setfillstyle(1,9);
  setcolor(11);
  rectangle(0,0,639,375);
  floodfill(3,3,11);
  setfillstyle(1,1);
  bar(0,376,640,386);
  setcolor(9);
  rectangle(0,387,639,451);
  setcolor(4);
  line(320,376,320,453);
  setcolor(9);
  line(319,376,319,453);
  line(321,376,321,453);
  settextstyle(0,0,1);
  setcolor(15);
  outtextxy(130,378,'Constanti');
  outtextxy(440,378,'Peremennie');
  setcolor(9);
  line(159,387,159,453);
  line(161,387,161,453);
  line(479,387,479,453);
  line(481,387,481,453);
  line(80,387,80,453);
  line(240,387,240,453);
  line(400,387,400,453);
  line(560,387,560,453);
  setcolor(4);
  line(480,387,480,453);
  line(160,387,160,453);
  setfillstyle(1,1);
  bar(0,452,640,480);
  setcolor(15);
  outtextxy(400,467,'Alexey Zhidkov, HCI NSU, 2004');
 end;
 procedure drawval;
 var s:string;
     ep:real;
 begin
  setcolor(11);
  line(sxmin,symin,sxmax,symin);
  line(sxmin,symin,sxmin,symax);
  line(sxmin,symax,sxmax,symax);
  line(sxmax,symin,sxmax,symax);
  setfillstyle(1,9);
  setcolor(11);
  rectangle(0,0,639,375);
  floodfill(3,3,11);
  {==========================}
  setcolor(15);
  outtextxy(2,389,'Nu1=');
  str(nu:7:4,s);
  outtextxy(83,389,s);
  outtextxy(2,399,'Nu2=');
  str(nu2:7:4,s);
  outtextxy(83,399,s);
  outtextxy(2,409,'G=');
  str(g:7:4,s);
  outtextxy(83,409,s);
  outtextxy(2,419,'Mass=');
  str(mass:7,s);
  outtextxy(83,419,s);
  outtextxy(2,429,'sXmin=');
  str(sxmin:7,s);
  outtextxy(83,429,s);
  outtextxy(2,439,'sXmax=');
  str(sxmax:7,s);
  outtextxy(83,439,s);
  {===============================}
  outtextxy(163,389,'sYmin=');
  str(symin:7,s);
  outtextxy(243,389,s);
  outtextxy(163,399,'sYmax=');
  str(symax:7,s);
  outtextxy(243,399,s);
  outtextxy(163,409,'xmin=');
  str(xmin:7,s);
  outtextxy(243,409,s);
  outtextxy(163,419,'xmax=');
  str(xmax:7,s);
  outtextxy(243,419,s);
  outtextxy(163,429,'ymin=');
  str(ymin:7,s);
  outtextxy(243,429,s);
  outtextxy(163,439,'ymax=');
  str(ymax:7,s);
  outtextxy(243,439,s);
  {==================================}
  setfillstyle(1,0);
  bar(403,389,477,399);
  outtextxy(323,389,'Vx=');
  str(par[1].value:7:3,s);
  outtextxy(403,389,s);
  bar(403,399,477,409);
  outtextxy(323,399,'Vy=');
  str(par[2].value:7:3,s);
  outtextxy(403,399,s);
  bar(403,409,477,419);
  outtextxy(323,409,'Ax=');
  str(par[3].value:7:3,s);
  outtextxy(403,409,s);
  bar(403,419,477,429);
  outtextxy(323,419,'Ay=');
  str(par[4].value:7:3,s);
  outtextxy(403,419,s);
  bar(403,429,477,439);
  outtextxy(323,429,'X=');
  str(par[5].value:7:3,s);
  outtextxy(403,429,s);
  bar(403,439,477,449);
  outtextxy(323,439,'Y=');
  str(par[6].value:7:3,s);
  outtextxy(403,439,s);
  {===========================}
  bar(563,389,623,399);
  outtextxy(483,389,'Fi=');
  str(par[7].value:7:3,s);
  outtextxy(563,389,s);
  bar(563,399,623,409);
  outtextxy(483,399,'W=');
  str(par[8].value:7:3,s);
  outtextxy(563,399,s);
  ep:=mass*(sqr(par[2].value)/2)+mass*g*par[6].value+((2/3)*mass*sqr(rm))*(sqr(par[8].value)/2);
  bar(563,409,623,419);
  outtextxy(483,409,'Ep=');
  str(ep:7:3,s);
  outtextxy(563,409,s);
 end;
 function meetwall(x,y:word):byte;
 var a,tx,ty,tx2,ty2,p:word;
     pi:real;
     isOk:boolean;
 begin
  a:=0;
  pi:=3.14;
  meetwall:=0;
  isOk:=true;
  repeat
   tx:=round((rm2/2+2)*(cos(a*pi/180)));
   ty:=round((rm2/2+2)*(sin(a*pi/180)));
   inc(a);
   p:=getpixel(tx+x,ty+y);
   if (p<>0)and(p<>1) then
   begin
    isOk:=false;
    if ((a>=315)or(a<45)) then meetwall:=1;
    if ((a>=135)and(a<225)) then meetwall:=3;
    if ((a>=45)and(a<135)) then meetwall:=2;
    if ((a>=225)and(a<315)) then meetwall:=4;
   end;
  until ((a>360)or(not(isOk)));
 end;
procedure otsk(x1,y1,fi,om:real);
 var vx,vy,tga,x3,y3,vx3,vy3,ax3,ay3:real;
begin
 ax3:=-par[1].value*nu;
 ay3:=-g-par[2].value*nu;
 vx3:=par[1].value+dt*par[3].value;
 vy3:=par[2].value+dt*par[4].value;
 x3:=par[5].value+dt*vx3;
 y3:=par[6].value+dt*vy3;
 if (x3=x1) then x3:=x3-0.0001;
 tga:=(y3-y1)/(x3-x1);
 if (tga>=2*nu2) then par[8].value:=om+2*nu2*(mass*rm*y1)/((2*mass*sqr(rm)/3))
 else par[8].value:= om*(1-((dt*nu*rm)/((2/3)*mass*sqr(rm))));
 if (mv=1) then
 begin
  if (tga<=2*nu2) then par[2].value:=0
  else par[2].value:=par[2].value-2*nu2*x1;
  par[1].value:=-par[1].value;
 end;
 if (mv=2) then
 begin
  if (tga<=2*nu2) then par[2].value:=0
  else par[2].value:=-par[2].value;
  par[1].value:=par[1].value-2*nu2*x1;
 end;
 if (mv=3) then
 begin
  if (tga<=2*nu2) then par[2].value:=0
  else par[2].value:=par[2].value-2*nu2*x1;
  par[1].value:=-par[1].value;
 end;
 if (mv=4) then
 begin
  if (tga<=2*nu2) then par[2].value:=0
  else par[2].value:=-par[2].value;
  par[1].value:=par[1].value-2*nu2*x1;
 end;
 if ((abs(par[1].value)<0.5)and(abs(par[3].value)<0.5)and(abs(par[6].value)<0.5)
 and(abs(par[2].value)<0.5)) then
 par[1].value:=0;
 if ((abs(par[1].value)<0.5)and(abs(par[3].value)<0.5)and(abs(par[6].value)<0.5)
 and(abs(par[2].value)<0.5)) then
 par[2].value:=0;
end;
begin
 cleardevice;
 drawfield;
 al:=(par[10].value*pi)/180;
 par[1].value:=par[9].value*cos(al);
 par[2].value:=par[9].value*sin(al);
 repeat
  drawval;
  setcolor(0);
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
  par[5].value:=par[5].value+dt*par[1].value;
  par[6].value:=par[6].value+dt*par[2].value-(g*sqr(dt)/2);
  par[3].value:=-par[1].value*nu;
  par[4].value:=-g-par[2].value*nu;
  par[1].value:=par[1].value+dt*par[3].value;
  par[2].value:=par[2].value+dt*par[4].value;
  par[7].value:=par[7].value+dt*par[8].value;
  par[8].value:=par[8].value*(1-((dt*nu*rm)/((2/3)*mass*sqr(rm))));
  setcolor(6);
  xt:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(par[5].value-xmin));
  yt:=round(symax-((symax-symin)/(ymax-ymin))*(par[6].value-ymin));
  setfillstyle(1,6);
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
  delay(1);
  mv:=meetwall(xt,yt);
  if (mv=1) then otsk(par[6].value,par[5].value,par[7].value,par[8].value);
  if (mv=2) then otsk(par[5].value,par[6].value,par[7].value,par[8].value);
  if (mv=3) then otsk(par[6].value,par[5].value,par[7].value,par[8].value);
  if (mv=4) then otsk(par[5].value,par[6].value,par[7].value,par[8].value);
 until (keypressed) or
 (abs(par[1].value)<0.001)and(abs(par[3].value)<0.5)and(abs(par[6].value)<0.5)and(abs(par[2].value)<0.5);
 setfillstyle(1,0);
 setcolor(15);
 bar(403,389,477,396);
 outtextxy(323,389,'Vx=');
 outtextxy(403,389,'  0.000');
 bar(563,399,623,409);
 outtextxy(483,399,'W=');
 outtextxy(563,399,'  0.000');
 bar(563,409,623,419);
 outtextxy(483,409,'Ep=');
 outtextxy(563,409,'  0.000');
 bar(403,409,477,416);
 outtextxy(323,409,'Ax=');
 outtextxy(403,409,'  0.000');
 readkey;
end;
{----------------------/\Dem/\-------------------------}
{+++++++++++++++++++++++++++++++++++++++++++++++++++++++}
{---------------------\/pStart\/-------------------------}
procedure pstart;
 var mv,xk,yk,rs,rm2,xt,yt,ex,ey:integer;
     fex,fey:real;
     s:string;
 procedure drawfield;
 begin
  xk:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(lk-xmin));
  yk:=round(symax-((symax-symin)/(ymax-ymin))*(hk-ymin));
  rs:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(rk-xmin));
  rm2:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(rm-xmin));
  setcolor(4);
  line(xk,yk,xk+rs,yk);
  line(xk+rs+15,yk-40,xk+rs+15,yk+10);
  setcolor(11);
  line(sxmin,symin,sxmax,symin);
  line(sxmin,symin,sxmin,symax);
  line(sxmin,symax,sxmax,symax);
  line(sxmax,symin,sxmax,symax);
  setfillstyle(1,9);
  setcolor(11);
  rectangle(0,0,639,375);
  floodfill(3,3,11);
  setfillstyle(1,1);
  bar(0,376,640,386);
  setcolor(9);
  rectangle(0,387,639,451);
  setcolor(4);
  line(320,376,320,453);
  setcolor(9);
  line(319,376,319,453);
  line(321,376,321,453);
  settextstyle(0,0,1);
  setcolor(15);
  outtextxy(130,378,'Constanti');
  outtextxy(440,378,'Peremennie');
  setcolor(9);
  line(159,387,159,453);
  line(161,387,161,453);
  line(479,387,479,453);
  line(481,387,481,453);
  line(80,387,80,453);
  line(240,387,240,453);
  line(400,387,400,453);
  line(560,387,560,453);
  setcolor(4);
  line(480,387,480,453);
  line(160,387,160,453);
  setfillstyle(1,1);
  bar(0,452,640,480);
  setcolor(15);
  outtextxy(400,467,'Alexey Zhidkov, HCI NSU, 2004');
 end;
 procedure drawval;
 var s:string;
 begin
  {==========================}
  setcolor(15);
  outtextxy(2,389,'Nu1=');
  str(nu:7:4,s);
  outtextxy(83,389,s);
  outtextxy(2,399,'Nu2=');
  str(nu2:7:4,s);
  outtextxy(83,399,s);
  outtextxy(2,409,'G=');
  str(g:7:4,s);
  outtextxy(83,409,s);
  outtextxy(2,419,'Mass=');
  str(mass:7,s);
  outtextxy(83,419,s);
  outtextxy(2,429,'sXmin=');
  str(sxmin:7,s);
  outtextxy(83,429,s);
  outtextxy(2,439,'sXmax=');
  str(sxmax:7,s);
  outtextxy(83,439,s);
  {===============================}
  outtextxy(163,389,'sYmin=');
  str(symin:7,s);
  outtextxy(243,389,s);
  outtextxy(163,399,'sYmax=');
  str(symax:7,s);
  outtextxy(243,399,s);
  outtextxy(163,409,'xmin=');
  str(xmin:7,s);
  outtextxy(243,409,s);
  outtextxy(163,419,'xmax=');
  str(xmax:7,s);
  outtextxy(243,419,s);
  outtextxy(163,429,'ymin=');
  str(ymin:7,s);
  outtextxy(243,429,s);
  outtextxy(163,439,'ymax=');
  str(ymax:7,s);
  outtextxy(243,439,s);
  {==================================}
  setfillstyle(1,0);
  bar(403,389,477,399);
  outtextxy(323,389,'Vx=');
  str(par[1].value:7:3,s);
  outtextxy(403,389,s);
  bar(403,399,477,409);
  outtextxy(323,399,'Vy=');
  str(par[2].value:7:3,s);
  outtextxy(403,399,s);
  bar(403,409,477,419);
  outtextxy(323,409,'Ax=');
  str(par[3].value:7:3,s);
  outtextxy(403,409,s);
  bar(403,419,477,429);
  outtextxy(323,419,'Ay=');
  str(par[4].value:7:3,s);
  outtextxy(403,419,s);
  bar(403,429,477,439);
  outtextxy(323,429,'X=');
  str(par[5].value:7:3,s);
  outtextxy(403,429,s);
  bar(403,439,477,449);
  outtextxy(323,439,'Y=');
  str(par[6].value:7:3,s);
  outtextxy(403,439,s);
  {===========================}
  bar(563,389,623,399);
  outtextxy(483,389,'Fi=');
  str(par[7].value:7:3,s);
  outtextxy(563,389,s);
  bar(563,399,623,409);
  outtextxy(483,399,'W=');
  str(par[8].value:7:3,s);
  outtextxy(563,399,s);
 end;
 procedure initdef;
 begin
  par[1].value:=15;{vx}
  par[2].value:=8;{vy}
  par[5].value:=1.3;{x}
  par[6].value:=1.8;{y}
 end;
 function meetwall(x,y:word):byte;
 var a,tx,ty,tx2,ty2,p:word;
     pi:real;
     isOk:boolean;
 begin
  a:=0;
  pi:=3.14;
  meetwall:=0;
  isOk:=true;
  repeat
   tx:=round((rm2/2+2)*(cos(a*pi/180)));
   ty:=round((rm2/2+2)*(sin(a*pi/180)));
   inc(a);
   p:=getpixel(tx+x,ty+y);
   if (p<>0)and(p<>1) then
   begin
    isOk:=false;
    if ((a>=315)or(a<45)) then meetwall:=1;
    if ((a>=135)and(a<225)) then meetwall:=3;
    if ((a>=45)and(a<135)) then meetwall:=2;
    if ((a>=225)and(a<315)) then meetwall:=4;
   end;
  until ((a>360)or(not(isOk)));
 end;
procedure otsk(x1,y1,fi,om:real);
 var vx,vy,tga,x3,y3,vx3,vy3,ax3,ay3:real;
begin
 ax3:=-par[1].value*nu;
 ay3:=-g-par[2].value*nu;
 vx3:=par[1].value+dt*par[3].value;
 vy3:=par[2].value+dt*par[4].value;
 x3:=par[5].value+dt*vx3;
 y3:=par[6].value+dt*vy3;
 tga:=(y3-y1)/(x3-x1);
 if (tga>=2*nu2) then par[8].value:=om+2*nu2*(mass*rm*y1)/((2*mass*sqr(rm)/3))
 else par[8].value:= om*(1-((dt*nu*rm)/((2/3)*mass*sqr(rm))));
 if (mv=1) then
 begin
  par[2].value:=par[2].value-2*nu2*x1;
  par[1].value:=-par[1].value;
 end;
 if (mv=2) then
 begin
  par[1].value:=par[1].value-2*nu2*x1;
  par[2].value:=-par[2].value;
 end;
 if (mv=3) then
 begin
  par[2].value:=par[2].value-2*nu2*x1;
  par[1].value:=-par[1].value;
 end;
 if (mv=4) then
 begin
  par[1].value:=par[1].value-2*nu2*x1;
  par[2].value:=-par[2].value;
 end;
end;
begin
 cleardevice;
 initdef;
 drawfield;
 repeat
  drawval;
  setcolor(0);
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
  par[5].value:=par[5].value+dt*par[1].value;
  par[6].value:=par[6].value+dt*par[2].value;
  par[3].value:=-par[1].value*nu;
  par[4].value:=-g-par[2].value*nu;
  par[1].value:=par[1].value+dt*par[3].value;
  par[2].value:=par[2].value+dt*par[4].value;
  par[7].value:=par[7].value+dt*par[8].value;
  par[8].value:=par[8].value*(1-((dt*nu*rm)/((2/3)*mass*sqr(rm))));
  setcolor(6);
  xt:=round(sxmin+((sxmax-sxmin)/(xmax-xmin))*(par[5].value-xmin));
  yt:=round(symax-((symax-symin)/(ymax-ymin))*(par[6].value-ymin));
  setfillstyle(1,6);
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
  delay(1);
  mv:=meetwall(xt,yt);
  if (mv=1) then otsk(par[6].value,par[5].value,par[7].value,par[8].value);
  if (mv=2) then otsk(par[5].value,par[6].value,par[7].value,par[8].value);
  if (mv=3) then otsk(par[6].value,par[5].value,par[7].value,par[8].value);
  if (mv=4) then otsk(par[5].value,par[6].value,par[7].value,par[8].value);
 until (keypressed) or ((abs(par[2].value)<1.5)and(yt>392-rm2/2));
  setcolor(0);
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
  setcolor(6);
  yt:=round(symax-rm2/2)-1;
  circle(xt,yt,round(rm2/2));
  line(xt,yt,xt+round(rm2/2*cos(par[7].value)),round(yt+rm2/2*sin(par[7].value)));
 readkey;
end;
{---------------------/\pStart/\-------------------------}
{+++++++++++++++++++++++++++++++++++++++++++++++++++++++}
{-----------------------\/MENU\/------------------------}
procedure menu;
var key:char;
    point:byte;
    isBegin:boolean;
 {----------------------\/Menu-initialize\/---------------}
 procedure initialize;
 begin
  par[1].name:='Vx= ';
  par[1].value:=15;{vx}
  par[2].name:='Vy= ';
  par[2].value:=8;{vy}
  par[3].name:='Ax= ';
  par[3].value:=0;
  par[4].name:='Ay= ';
  par[4].value:=-g;
  par[5].name:='X= ';
  par[5].value:=1.3;
  par[6].name:='Y= ';
  par[6].value:=1.8;{y}
  par[7].name:='Fi= ';
  par[7].value:=0;
  par[8].name:='W= ';
  par[8].value:=100;{y}
  par[9].value:=27;{V}
  par[9].name:='V0= ';
  par[10].value:=15; {alfa}
  par[10].name:='Alfa0= ';
  dt:=0.005;
 end;
 {----------------------/\Menu-initialize/\---------------}
 {----------------------\/Menu-textfield\/----------------}
 function textfield : real;
 var key_tf:char;
     text,text1:string;
     code,i:integer;
     t:real;
 begin
 text:='';
 settextstyle(0,0,1);
 setfillstyle(1,6);
 bar(230,210,400,230);
 floodfill(320,220,6);
 setfillstyle(1,0);
 bar(232,212,398,228);
 floodfill(320,220,6);
 code:=0;
 repeat
  if ((code<>0)and(key_tf=#13)) then
  begin
   outtextxy(260,300,'Ne verniy format!');
   readkey;
   bar(260,290,400,310);
  end;
  key_tf:=readkey;
  if (key_tf<>#13) then
  begin
   text:=text+key_tf;
  end;
  if (key_tf=#8) then
  begin
   text1:=text;
   text:='';
   for i:=1 to length(text1)-2 do
   text:=text+text1[i];
  end;
 bar(232,212,398,228);
 floodfill(320,220,6);
 outtextxy(235,217,text);
 val(text,t,code);
 until ((key_tf=#13) and (code=0));
 textfield:=t;
 bar(230,210,410,230);
 floodfill(320,220,0);
 end;
 {----------------------/\Menu-textfield/\---------------}
 {----------------------\/Menu-drawBut\/------------------}
 procedure drawBut(num:byte);
 const cap:array[1..5] of string=('Srart','Parametri','Graphiki','Help','Exit');
 var i,x,y:word;
 begin
  cleardevice;
  y:=5;
  x:=10;
  settextstyle(0,0,1);
  setcolor(15);
  outtextxy(400,467,'Alexey Zhidkov, HCI NSU, 2004');
  for i:=1 to 5 do
  begin
   if num=i then
   begin
    setfillstyle(1,9);
    bar(x,y,x+116,y+20);
    floodfill(x+58,y+10,9);
    setcolor(15);
    outtextxy(x+10,y+5,cap[i]);
    x:=x+126;
   end
   else
   begin
    setfillstyle(1,1);
    bar(x,y,x+116,y+20);
    floodfill(x+58,y+10,1);
    setcolor(9);
    outtextxy(x+10,y+5,cap[i]);
    x:=x+126;
   end;
  end;
 end;
 {----------------------/\Menu-drawBut/\------------------}
 {----------------------\/Menu-DrawMenu\/-----------------}
 procedure drawMenu(num:byte);
 const s=2; g=9;
       start:array[1..s] of string=('Demonstracia','Start');
       graph:array[1..g] of string=('Vx(t)','Vy(t)','Ax(t)','Ay(t)',
                                    'Y(x)','Fi(t)','Wi(t)','E polnaia','Formuli');
 var point:byte;
     x,y,i:word;
     partx,tv:string;
 begin
  case num of
             1:begin
                point:=1;
                repeat
                 x:=10;
                 y:=30;
                 for i:=1 to s do
                 begin
                  if (i=point) then
                  begin
                   setfillstyle(1,6);
                   bar(x,y,x+116,y+20);
                   floodfill(x+58,y+10,6);
                   setcolor(15);
                   outtextxy(x+10,y+5,start[i]);
                   y:=y+25;
                  end
                  else
                  begin
                   setfillstyle(1,1);
                   bar(x,y,x+116,y+20);
                   floodfill(x+58,y+10,1);
                   setcolor(15);
                   outtextxy(x+10,y+5,start[i]);
                   y:=y+25;
                  end
                 end;
                 key:=readkey;
                 case key of
                            #72:Begin
                                 if (point=1) then point:=2
                                 else point:=1;
                                end;
                            #80:Begin
                                 if (point=2) then point:=1
                                 else point:=2;
                                end;
                            #27:begin
                                 setfillstyle(1,0);
                                 bar(0,30,130,100);
                                end;
                            #13:begin
                                 if (point=1) then
                                 begin
                                  dem;
                                  menu;
                                 end
                                 else
                                 begin
                                  pstart;
                                  menu;
                                 end;
                                end;
                 end;
                until (key=#27);
               end;
             2:begin
               point:=5;
                repeat
                 x:=136;
                 y:=30;
                 for i:=1 to p do
                 begin
                  if (i=point) then
                  begin
                   if ((i<>3)and(i<>4)and(i<>1)and(i<>2)) then
                   begin
                    setfillstyle(1,6);
                    bar(x,y,x+116,y+20);
                    floodfill(x+58,y+10,6);
                    setcolor(15);
                    str(par[i].value:6:3,tv);
                    partx:=par[i].name+tv;
                    outtextxy(x+10,y+5,partx);
                    y:=y+25;
                   end;
                  end
                  else
                  begin
                   if ((i<>3)and(i<>4)and(i<>1)and(i<>2)) then
                   begin
                    setfillstyle(1,1);
                    bar(x,y,x+116,y+20);
                    floodfill(x+58,y+10,1);
                    setcolor(15);
                    str(par[i].value:6:3,tv);
                    partx:=par[i].name+tv;
                    outtextxy(x+10,y+5,partx);
                    y:=y+25;
                   end;
                  end
                 end;
                 key:=readkey;
                 case key of
                            #72:Begin
                                 if (point=5) then point:=p
                                 else point:=point-1;
                                end;
                            #80:Begin
                                 if (point=p) then point:=5
                                 else point:=point+1;
                                end;
                            #27:begin
                                 setfillstyle(1,0);
                                 bar(126,30,255,y+25*p);
                                end;
                            #13:begin
                                 par[point].value:=textfield;
                                end;
                 end;
                until (key=#27);
               end;
             3:begin
               point:=1;
                repeat
                 x:=262;
                 y:=30;
                 for i:=1 to g do
                 begin
                  if (i=point) then
                  begin
                   setfillstyle(1,6);
                   bar(x,y,x+116,y+20);
                   floodfill(x+58,y+10,6);
                   setcolor(15);
                   outtextxy(x+10,y+5,graph[i]);
                   y:=y+25;
                  end
                  else
                  begin
                   setfillstyle(1,1);
                   bar(x,y,x+116,y+20);
                   floodfill(x+58,y+10,1);
                   setcolor(15);
                   outtextxy(x+10,y+5,graph[i]);
                   y:=y+25;
                  end
                 end;
                 key:=readkey;
                 case key of
                            #72:Begin
                                 if (point=1) then point:=g
                                 else point:=point-1;
                                end;
                            #80:Begin
                                 if (point=g) then point:=1
                                 else point:=point+1;
                                end;
                            #13:begin
                                case point of
  1:begin

    end;
  2:begin

    end;
  3:begin

    end;
  4:begin

    end;
  5:begin

    end;
  6:begin

    end;
  7:begin

    end;
  8:begin

    end;
  9:begin
     cleardevice;
     Formuls;
     readkey;
     menu;
    end;
                                 end;
                                end;
                            #27:begin
                                 setfillstyle(1,0);
                                 bar(242,30,380,30+25*g);
                                end;
                 end;
                until (key=#27);
               end;
             4:begin cleardevice;help;readkey;drawBut(4);end;
             5:halt;
  end;
 end;
 {----------------------/\Menu-DrawMenu/\-----------------}
begin
 initialize;
 point:=1;
 drawBut(point);
 repeat
  key:=readkey;
  case key of
              #77:begin
                   if point=5 then point:=1
                   else point:=point+1;
                   drawBut(point);
                  end;
              #75:begin
                   if point=1 then point:=5
                   else point:=point-1;
                   drawBut(point);
                  end;
               #13:drawMenu(point);
  end;
 until (false)
end;
{-----------------------/\MENU/\------------------------}
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
{------------------\/MAIN PROGRAMM\/----------------------}
begin
 ingr;
 cleardevice;
 menu;
end.
{------------------/\MAIN PROGRAMM/\----------------------}