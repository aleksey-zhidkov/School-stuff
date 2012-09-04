uses crt,graph;
const
g=9.8;
r=6400; {Радиус земли (км)}
totr=10;
var
k,xmin,xmax,ymin,ymax:longint;
stup,dg,db:integer;
Mst,mst1,TR,S,P,Pd,Ue,i,j,l,n,m,imax,imin,jmax,jmin:longint;
mk,Ktr,tend,mtops,Mt,Ux,Uy,Ax,Ay,x,y,Ftiag,alph,dt,t,mtop,ex,log,U,Z,M0,c:real;
alphz,Ftx,Fty:real;
st,st1,st2,stz1,stz2:string;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure drawos(imin,imax,jmin,jmax:integer;st1,st2,stz1,stz2:string);
begin
 line(imin-5,jmax,imax+20,jmax);
 line(imin,jmax+5,imin,jmin-20);
 line(imax+15,jmax+5,imax+20,jmax);
 line(imax+15,jmax-5,imax+20,jmax);
 line(imin-5,jmin-15,imin,jmin-20);
 line(imin+5,jmin-15,imin,jmin-20);
 line(imax,jmax-5,imax,jmax+5);
 line(imin-5,jmin,imin+5,jmin);
 outtextxy(imin-10,jmax+2,'0');
 outtextxy(imax+5,jmax+2,st1);{0X}
 outtextxy(imin-15,jmin-15,st2);{0Y}
 outtextxy(imin-50,jmin-2,stz1);{0Y.zn}
 outtextxy(imax-30,jmax+15,stz2);{0X.zn}
end;
{+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure lstup(x,y,d:integer);
begin
 line(x,y-2*d,x-d,y+2*d);
 line(x-d,y+2*d,x,y+2*d);
 line(x,y+2*d,x,y-2*d);
end;
{+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure rstup(x,y,d:integer);
begin
 line(x+d,y-2*d,x+2*d,y+2*d);
 line(x+2*d,y+2*d,x+d,y+2*d);
 line(x+d,y+2*d,x+d,y-2*d);
end;
{+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure rok(x,y,n:integer);
const
d=40;
var
x1,y1:integer;
begin
 x1:=round(d/2);
 line(x,y+d,x+x1,y);
 line(x+x1,y,x+d,y+d);
 line(x+d,y+d,x,y+d);
 y:=y+d;
 line(x,y,x+d,y);
 line(x+d,y,x+d,y+2*d);
 line(x+d,y+2*d,x,y+2*d);
 line(x,y+2*d,x,y);
 y:=y+2*d;
 for i:=1 to n do
  begin
   line(x,y,x+d,y);
   line(x+d,y,x+d,y+2*d);
   line(x+d,y+2*d,x,y+2*d);
   line(x,y+2*d,x,y);
   if i=n then
    begin
     lstup(x,y,d);
     rstup(x,y,d);
    end;
   y:=y+2*d;
  end;
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure allinit;
begin
 initgraph(dg,db,'');
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
function davl(h:real):real;
const
la=0.0001251;
r0=1.18;
var
n:real;
begin
 ex:=exp(-la*h);
 h:=r0*ex;
 davl:=h;
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure ras;
begin
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure init;
begin
 dt:=0.1;
 t:=0;
 stup:=4;
 imax:=600;
 imin:=150;
 jmax:=200;
 jmin:=20;
 xmax:=200000;
 xmin:=0;
 ymax:=300000;
 ymin:=0;
 st1:='x';
 st2:='y';
 str(xmax,stz2);
 str(ymax,stz1);
 mst1:=100;{Масса одной ступени}
 mst:=mst1*stup;{Масса всех ступеней}
 Ue:=2500; {Скорость истечения газов (м/с}
 Mk:=1000; {Масса полезного груза (кг)}
 Mt:=(Mk+stup*mst1)*42.5; {Текущая масса ракеты (кг)}
 M0:=mt; {Стартовая масса ракеты (кг)}
 mtop:=Mt*0.98; {Масса топлива (кг)}
 mtops:=mtop/stup;{Масса топлива в одной ступени (кг)}
 {Z:=M0/Mk; Число Циолковского (кг)}
 ex:=exp(-alph*t);
 Ux:=cos(89);
 Uy:=sin(89);
 log:=ln(mtop/m0);
 alph:=(-1/totr)*log;
 ex:=exp(-alph*totr);
 Ftiag:=-alph*m0*ex*Ue;
 ex:=exp(1);
 {tend:=(1/alph);Полное время работы двигателей}
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
procedure vivod;
var
dav,tstup:real;
begin
 tstup:=0;
 repeat
  tstup:=tstup+dt;
  dav:=davl(y);
  Ktr:=dav*pi*(Ux*Ux)*(sin(60)*sin(60));
  log:=ln(Mt/(Mk+stup*mst));
  alphz:=1/totr*log;
  Ftx:=alphz*Ue*cos(89)*Mt;
  Fty:=alphz*Ue*sin(89)*Mt;
  Ax:=(-(Ktr*Ux)/Mt)+(Ftx/Mt);
  Ay:=-g-((Ktr*Uy)/Mt)+(Fty/Mt);
  x:=x+dt*Ux;
  y:=y+dt*Uy;
  Ux:=Ux+dt*Ax;
  Uy:=Uy+dt*Ay;
  i:=round(imin+((imax-imin)/(xmax-xmin))*(x-xmin));
  j:=round(jmax-((jmax-jmin)/(ymax-ymin))*(y-ymin));
  putpixel(i,j,10);
  t:=t+dt;
  ex:=exp(-alph*t);
  Mt:=Mk+stup*mst1+M0*ex;
  if tstup>=totr then
   begin
    tstup:=0;
    stup:=stup-1;
   end;
  str(stup,st);
  outtextxy(0,30,'stup');
  outtextxy(40,30,st);
  str(t:0:1,st);
  outtextxy(0,40,'totr');
  outtextxy(40,40,st);
  str(y:0:1,st);
  outtextxy(0,50,'y');
  outtextxy(40,50,st);
  str(Mt:0:1,st);
  outtextxy(0,60,'mt');
  outtextxy(40,60,st);
  delay(100);
  if stup<>0 then
   begin
    setfillstyle(1,0);
    bar(10,20,90,90);
   end;
 until (stup=0) or (keypressed);
end;
{++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++}
begin
 clrscr;
 allinit;
 init;
 drawos(imin,imax,jmin,jmax,st1,st2,stz1,stz2);
 vivod;
 readln;
end.
