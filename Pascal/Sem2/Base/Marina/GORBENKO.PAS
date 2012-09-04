uses crt,graph;
const g=9.8;pi=3.14; xo=239;yo=204;
type param = record
              value:real;
              name:string;
             end;
var
 gd,gm:integer;
 jek,iek, xcnek, ycnek,xek,yek, il,ir,ju,jd:integer;
 ch:string;
 par:array[1..6] of param;
 I,Fk,dt,tk,xcn,ycn,xmin,xmax,ymin,ymax,x,y,ml,mc:real;
{------------------\/Initvar\/------------}
procedure initvar;
begin
  dt:=0.0001;
  ml:=0.5;
  mc:=2.;
  il:=40; ir:=600;
  ju:=40; jd:=440;
  par[1].value:=1.0; {pi/10;}  {alfa}
  par[1].name:='Alfa=';
  par[2].value:=20;{WK}
  par[2].name:='Wk=';
  par[3].value:=0.8;{l}
  par[3].name:='L=';
  par[4].value:=0.05;{R}
  par[4].name:='R=';
  par[5].value:=0.1;{x}
  par[5].name:='Beta=';
  par[6].name:='Initialize';
  xmin:=-1.5*par[3].value;
  xmax:=1.005*par[3].value;
  ymax:=2.005*par[3].value;
  ymin:=-0.005*par[3].value;
end;
{------------------/\Initvar/\------------}
{------------------\/help\/-----------------}
procedure help;
var f:text;
    st:string;
    yt:integer;
begin
 cleardevice;
 assign(f,'help.txt');
 reset(f);
 settextstyle(0,0,4);
 outtextxy(250,10,'Help');
 setcolor(15);
 settextstyle(0,0,1);
 yt:=51;
 repeat
  readln(f,st);
  outtextxy(22,yt+30,st);
  yt:=yt+13;
 until (eof(f)=true);
 readkey;
end;
{------------------/\help/\-----------------}
{------------------\/Dvigenie\/---------------}
procedure dvigenie;
var
  Rek: integer;
begin
  I:=(1/12)*ml*sqr(par[3].value)+(2/5)*mc*sqr(par[4].value);
  tk:=0;
  Rek:=round((ir-il)/(xmax-xmin)*par[4].value)+10;
  xcn:=0.;
  ycn:=par[3].value;
  xcnek:=round(il+(ir-il)/(xmax-xmin)*(xcn-xmin));
  ycnek:=round(jd-(jd-ju)/(ymax-ymin)*(ycn-ymin));
  x:=par[3].value*sin(par[1].value);
  y:=par[3].value*(1-cos(par[1].value));
 repeat
  setcolor(4);
  line(280,240,320,240);
  Fk:=((-par[3].value*g*(ml+mc)/I)*sin(par[1].value))-(((par[5].value*par[3].value)/I)*par[2].value);
  par[1].value:=par[1].value+(dt*par[2].value);
  par[2].value:=par[2].value+(dt*Fk);
  tk:=tk+dt;
  x:=par[3].value*sin(par[1].value);
  y:=par[3].value*(1-cos(par[1].value));
  xek:=round(il+((ir-il)/(xmax-xmin))*(x-xmin))-75;
  yek:=round(jd-((jd-ju)/(ymax-ymin))*(y-ymin));
  setcolor(7);
  line(xcnek-75,ycnek,xek,yek);
  setcolor(5);
  circle(xek,yek,Rek);
  setfillstyle(1,5);
  floodfill(xek,yek,5);
  delay(10);
  setcolor(0);
  circle(xek,yek,Rek);
  setfillstyle(1,0);
  floodfill(xek,yek,0);
  setcolor(0);
  line(xcnek-75,ycnek,xek,yek);
  if keypressed then  ch:=readkey;
 until ch=#13;
  ch:=#0;
end;
{------------------/\Dvigenie/\---------------}
{------------------\/Epoln\/----------------}
procedure epoln;
const xsmin=100;xsmax=540;xmin=1;xmax=500;
      ysmin=100;ysmax=300;ymin=0;ymax=30;
 procedure drawfield;
 begin
  setcolor(9);
  settextstyle(0,0,2);
  outtextxy(100,20,'Graphik polnoy energii');
  settextstyle(0,0,1);
  setcolor(15);
  outtextxy(xsmin-20,ysmin-10,'Ep=30');
  outtextxy(xsmax,ysmax+10,'T=500');
  outtextxy(xsmin-9,ysmax+2,'0');
  setcolor(1);
  line(xsmin,ysmin,xsmin,ysmax+10);
  line(xsmin-10,ysmax,xsmax,ysmax);
 end;
var ep:real;
    stt:string;
    i,xekr,yekr:integer;
begin
 cleardevice;
 initvar;
 drawfield;
 for i:=1 to 500 do
 begin
  y:=par[3].value*(1-cos(par[1].value));
  ep:=mc*g*y+(par[2].value*par[3].value*mc)/2;
  xekr:=round(xsmin+((xsmax-xsmin)/(xmax-xmin)*(i-xmin)));
  yekr:=round(ysmax-((ysmax-ysmin)/(ymax-ymin)*(ep-ymin)));
  setcolor(15);
  str(ep:5:2,stt);
  outtextxy(xsmin-45,yekr-10,stt);
  putpixel(xekr,yekr,4);
  delay(500);
 end;
 readkey;
end;
{------------------/\Epoln/\----------------}
{------------------\/Fpl\/----------------}
procedure Fpl;
const xsmin=100;xsmax=540;xmin=0;xmax=1;
      ysmin=100;ysmax=300;ymin=0;ymax=3;
 procedure drawfield;
 begin
  setcolor(9);
  settextstyle(0,0,2);
  outtextxy(100,20,'Graphik Fazovoy ploskosty');
  settextstyle(0,0,1);
  setcolor(15);
  outtextxy(xsmin-20,ysmin-10,'p=30');
  outtextxy(xsmax,ysmax+10,'alfa=500');
  outtextxy(xsmin-9,ysmax+2,'0');
  setcolor(1);
  line(xsmin,ysmin,xsmin,ysmax+10);
  line(xsmin-10,ysmax,xsmax,ysmax);
 end;
var i,xekr,yekr:integer;
begin
 cleardevice;
 initvar;
 drawfield;
 for i:=1 to 500 do
 begin
  Fk:=((-par[3].value*g*(ml+mc)/I)*sin(par[1].value))-(((par[5].value*par[3].value)/I)*par[2].value);
  par[1].value:=par[1].value+(dt*par[2].value);
  par[2].value:=par[2].value+(dt*Fk);
  xekr:=round(xsmin+((xsmax-xsmin)/(xmax-xmin)*(par[1].value-xmin)));
  yekr:=round(ysmax-((ysmax-ysmin)/(ymax-ymin)*(par[2].value-ymin)));
  setcolor(15);
  putpixel(xekr,yekr,4);
  delay(500);
 end;
 readkey;
end;
{------------------/\Fpl/\----------------}
{------------------\/Menu\/---------------}
procedure menu;
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
 procedure drawpop(c:byte);
 var popp,j:byte;
     key:char;
     partx,tv:string;
 begin
  popp:=1;
  settextstyle(0,0,1);
  repeat
   case c of
             1:begin
                for j:=1 to 2 do
                begin
                 setcolor(9);
                 if (j=popp) then setcolor(14);
                 case j of
                          1:outtextxy(170,175,'E polnaia');
                          2:outtextxy(170,190,'Fazovaia plsk.');
                 end;
                end;
               end;
             2:begin
                y:=190;
                yek:=200;
                setfillstyle(1,0);
                bar(339,174,520,320);
                floodfill(180,190,0);
                for j:=1 to 6 do
                begin
                 setcolor(9);
                 if (j=popp) then setcolor(14);
                 str(par[j].value:6:3,tv);
                 if (j=6) then partx:=par[j].name
                 else partx:=par[j].name+tv;
                 outtextxy(340,yek,partx);
                 yek:=yek+15;
                end;
               end;
   end;
   key:=readkey;
   case key of
               #72:begin
                    if (c=1) then
                    begin
                     if (popp=1) then popp:=2
                     else popp:=1;
                    end
                    else
                    begin
                     if (popp=1) then popp:=6
                     else popp:=popp-1;
                    end;
                   end;
               #80:begin
                    if (c=1) then
                    begin
                     if (popp=2) then popp:=1
                     else popp:=2;
                    end;
                    if (c=2) then
                    begin
                     if (popp=6) then popp:=1
                     else popp:=popp+1;
                    end;
                   end;
   end;
   if (key=#13) then
   begin
    if ((c=1)and(popp=1)) then begin epoln;menu;end;
    if ((c=1)and(popp=2)) then begin Fpl;menu;end;
    if ((c=2)and(popp=1)) then par[1].value:=textfield;
    if ((c=2)and(popp=2)) then par[2].value:=textfield;
    if ((c=2)and(popp=3)) then par[3].value:=textfield;
    if ((c=2)and(popp=4)) then par[4].value:=textfield;
    if ((c=2)and(popp=5)) then par[5].value:=textfield;
    if ((c=2)and(popp=6)) then initvar;
   end;
  until (key=#27);
  settextstyle(0,0,2);
 end;
 procedure drawmenu;
 type mb = record
      name:string;
      x:word;
      y:word;
      end;
 var point,i:byte;
     key:char;
     amb:array[1..5] of mb;
 begin
  point:=1;
  amb[1].name:='start';
  amb[1].x:=175;
  amb[1].y:=120;
  amb[2].name:='help';
  amb[2].x:=275;
  amb[2].y:=120;
  amb[3].name:='exit';
  amb[3].x:=375;
  amb[3].y:=120;
  amb[4].name:='graphiki';
  amb[4].x:=160;
  amb[4].y:=150;
  amb[5].name:='parametri';
  amb[5].x:=330;
  amb[5].y:=150;
  settextstyle(0,0,2);
  repeat
   cleardevice;
   for i:=1 to 5 do
   begin
    setcolor(9);
    if (i=point) then
    begin
     setcolor(13);
     setfillstyle(1,13);
     line(amb[i].x-15,amb[i].y+2,amb[i].x-15,amb[i].y+14);
     line(amb[i].x-15,amb[i].y+2,amb[i].x-5,amb[i].y+8);
     line(amb[i].x-15,amb[i].y+14,amb[i].x-5,amb[i].y+8);
     floodfill(amb[i].x-14,amb[i].y+8,13);
    end;
    outtextxy(amb[i].x,amb[i].y,amb[i].name);
   end;
   key:=readkey;
   case key of
              #75:begin
                   if (point>1) then point:=point-1
                   else point:=5;
                  end;
              #77:begin
                   if (point<5) then point:=point+1
                   else point:=1;
                  end;
              #72:begin
                   case point of 1: point:=4;
                                 2: point:=4;
                                 3: point:=5;
                                 4: point:=1;
                                 5: point:=3;
                   end;
                  end;
              #80:begin
                   case point of 1: point:=4;
                                 2: point:=4;
                                 3: point:=5;
                                 4: point:=1;
                                 5: point:=3;
                   end;
                  end;
   end;
   if (key=#13) then
   begin
    case point of
                 1:begin
                    cleardevice;
                    dvigenie;
                    cleardevice;
                    menu;
                   end;
                 2:begin
                    help;
                    menu;
                   end;
                 3:halt;
                 4:begin
                    drawpop(1);
                   end;
                 5:begin
                    drawpop(2);
                   end;
   end;
  end;
  until (key=#27);
 end;
begin
 drawmenu;
end;
{------------------/\Menu/\---------------}
{------------------\/Main p\/-------------}
begin
  initgraph(gd,gm,'');
  initvar;
  menu;
  readln;
end.





