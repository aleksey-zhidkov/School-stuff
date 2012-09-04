{--------------------------\/READ ME\/-----------------------------}
{--(\/)/\RAZDEL PROGRAMMI/\(\/)--}
{==Razdelenie razdelov==}
{++Kommentrii po deistviam++}
{.:Naznachenie peremennih:.}
{--++Podrazdel programmi++--}
{--------------------------/\READ ME/\-----------------------------}
{==================================================================}
{----------------------------\/HEAD\/------------------------------}
uses crt,graph;
type napr=1..4;
type screenxy=0..640;
var p1,p2,p3,p4,p5,p6,psg,psv,plev,psh,pfr:pointer;
    key:char;
    f:file;
    name,lst,prl,prr:string;
    x1,y1,xs1,ys1,xa1,ya1,xa2,ya2,xa3,ya3,pxr1,pxl1,pxr3,pxl3,pxr2,pxl2:screenxy;
    chet,ndl,ds,size,ydm,xb,yb:integer;
    level:word;
    lch1,rch1,lch2,rch2,lch3,rch3:word;
    usLost,usLevelup,usExit,usSound,usBonus,usBonusPut,usFS,usSHOT:boolean;
    usExtrmove1,usExtrmove2,usExtrmove3,usOPTV,usFreeze,stop,MASTER:boolean;
    usAIstor11,usAIstor12,usAIstor21,usAIstor22,usAIstor31,usAIstor32:boolean;
    gp1,gp2,gp3,gp4,gp5,gp6,gp7,gp8,kve,kvs,sp,chem1,chem2,chem3,schbo,col1,col2:byte;
    shagm1,ns,nt,nap1,nap2,nap3,typeB:napr;
{----------------------------/\HEAD/\------------------------------}
{==================================================================}
{----------------------------\/ZAST\/------------------------------}
procedure zast;
var a,m,ch:integer;
    key:char;
begin
 a:=15;{.:Cvet slova 'armor':.}
 m:=1; {.:Cvet slova 'modern':.}
 ch:=1; {.:Kol-vo povtorenii:.}
 setfillstyle(1,m);
 bar(75,150,567,345);
 setfillstyle(1,a);
 bar(78,153,564,342);
 setfillstyle(1,0);
 bar(80,155,562,340);
 setcolor(1);
 settextstyle(0,0,10);
 outtextxy(89,164,'Modern');
 setcolor(1);
 settextstyle(0,0,10);
 outtextxy(124,254,'Armor');
 setcolor(9);
 settextstyle(0,0,10);
 outtextxy(87,162,'Modern');
 setcolor(9);
 settextstyle(0,0,10);
 outtextxy(122,252,'Armor');
 repeat
  if ch mod 2=0 then
   begin
    a:=15;
    m:=1;
   end;
  if ch mod 2<>0 then
   begin
    a:=1;
    m:=15;
   end;
  ch:=ch+1;
  setcolor(m);
  settextstyle(0,0,10);
  outtextxy(85,160,'Modern');
  setcolor(a);
  settextstyle(0,0,10);
  outtextxy(120,250,'Armor');
  setcolor(m);
  settextstyle(0,0,1);
  outtextxy(370,460,'Design & programming by LEXX');
  delay(19999);
  delay(19999);
  if keypressed then key:=readkey;
  if key=#27 then ch:=25;{++Prerivanie zastavki++}
 until ch>15;
end;
{----------------------------/\ZAST/\------------------------------}
{==================================================================}
{---------------------------\/LOADER\/-----------------------------}
function loader(namef:string) : pointer;
var fl: file;
    p: pointer;
begin
 {$i-}
 assign(fl,namef);
 reset(fl,1);
 write(ioresult);
 size:=filesize(fl);
 getmem(p,size);
 blockread(fl,p^,size);
 close(fl);
 loader:=p;
end;
{---------------------------/\LOADER/\-----------------------------}
{===================================================================}
{---------------------------\/NAME\/--------------------------------}
function recname:string;
var dlp,i,ndl,pr,prln,prrn:byte;
begin
 MASTER:=False;
 repeat
  write('Enter your name: ');
  readln(name);
  ndl:=length(name);
  if (ndl>28) then writeln('Name must be less than 28 chars');
  if (ndl=0) then writeln('You are forget Enter Name');
 until (ndl<28) and (ndl>0);
 recname:=name;
 pr:=28-ndl;
 if (pr mod 2)=0 then begin prln:=pr div 2;prrn:=pr div 2;end
 else begin prln:=(pr div 2)+1; prrn:=(pr div 2);end;
 for i:=1 to prln do
  prl:=prl+' ';
 for i:=1 to prrn do
  prr:=prr+' ';
 if (name='lexx') or (name='Lexx') or (name='LEXX') then MASTER:=true;
end;
{---------------------------/\NAME/\--------------------------------}
{===================================================================}
{---------------------------\/Y_N\/---------------------------------}
function y_n(var ydm:integer):boolean;
var epos:byte;
begin
 settextstyle(0,0,3);
 epos:=1;
 setfillstyle(1,15);
 bar(214,ydm+4,414,ydm+44);
 setfillstyle(1,9);
 bar(212,ydm+2,412,ydm+42);
 setfillstyle(1,1);
 bar(210,ydm,410,ydm+40);
 setfillstyle(1,9);
 bar(215,ydm+5,305,ydm+35);
 setfillstyle(1,1);
 bar(310,ydm,410,ydm+40);
 setfillstyle(1,0);
 bar(315,ydm+5,405,ydm+35);
 setcolor(1);
 outtextxy(225,ydm+10,'Yes');
 setcolor(15);
 outtextxy(340,ydm+10,'No');
 setcolor(4);
 if ydm=200 then
 begin
  settextstyle(0,0,2);
  outtextxy(140,ydm-20,'Do really want to quit?');
 end;
 if ydm=225 then
 begin
  settextstyle(0,0,2);
  outtextxy(80,ydm-20,'Do really want to leave the game?');
 end;
 if ydm=325 then
 begin
  setcolor(15);
  settextstyle(0,0,2);
  outtextxy(215,ydm-20,'Save record?');
 end;
 while (key<>#13) do
 begin
  settextstyle(0,0,3);
  key:=readkey;
  case key of
             #75:begin
                  epos:=epos-1;
                  if epos=0 then epos:=2;
                 end;
             #77:begin
                  epos:=epos+1;
                  if epos=3 then epos:=1;
                 end;
  end;
  case epos of
              1:begin
                  setfillstyle(1,1);
                  bar(210,ydm,310,ydm+40);
                  setfillstyle(1,9);
                  bar(215,ydm+5,305,ydm+35);
                  setfillstyle(1,1);
                  bar(310,ydm,410,ydm+40);
                  setfillstyle(1,0);
                  bar(315,ydm+5,405,ydm+35);
                  setcolor(1);
                  outtextxy(225,ydm+10,'Yes');
                  setcolor(15);
                  outtextxy(340,ydm+10,'No');
                end;
              2:begin
                  setfillstyle(1,1);
                  bar(210,ydm,310,ydm+40);
                  setfillstyle(1,0);
                  bar(215,ydm+5,305,ydm+35);
                  setfillstyle(1,1);
                  bar(310,ydm,410,ydm+40);
                  setfillstyle(1,9);
                  bar(315,ydm+5,405,ydm+35);
                  setcolor(15);
                  outtextxy(225,ydm+10,'Yes');
                  setcolor(1);
                  outtextxy(340,ydm+10,'No');
                end;
  end;
  if usSound then begin sound(1000);delay(5000); nosound;end;
 end;
 if (epos=1) then y_n:=true
 else  y_n:=false;
 key:=#0;
end;
{---------------------------/\Y_N/\---------------------------------}
{===================================================================}
{--------------------------\/PICTURE\/------------------------------}
procedure picture;
 begin
  p1:=loader('paskal\sem1\vvodnik\gam\files\pav');
  p2:=loader('paskal\sem1\vvodnik\gam\files\ain');
  p3:=loader('paskal\sem1\vvodnik\gam\files\shot');
  p4:=loader('paskal\sem1\vvodnik\gam\files\ain');
  p5:=loader('paskal\sem1\vvodnik\gam\files\ain');
  p6:=loader('paskal\sem1\vvodnik\gam\files\headpa');
  psg:=loader('paskal\sem1\vvodnik\gam\files\stenag');
  psv:=loader('paskal\sem1\vvodnik\gam\files\stenav');
  plev:=loader('paskal\sem1\vvodnik\gam\files\level');
  psh:=loader('paskal\sem1\vvodnik\gam\files\shield');
  pfr:=loader('paskal\sem1\vvodnik\gam\files\freeze');
 end;
{--------------------------/\PICTURE/\------------------------------}
{===================================================================}
{--------------------------\/INITVAR\/------------------------------}
procedure initvar;
 begin
  cleardevice;
  usLevelup:=false;
  usLost:=false;
  usExtrmove1:=false;
  usExtrmove2:=false;
  usExtrmove3:=false;
  usFS:=false;
  usBonusPut:=true;
  usFreeze:=false;
  stop:=false;
  nap1:=4;
  nap2:=4;
  nap3:=4;
  chem1:=0;
  chem2:=0;
  chem3:=0;
  x1:=260;{.:Polozenie tanka igroka v nachale igri:.}
  y1:=435;{.:Polozenie tanka igroka v nachale igri:.}
  putimage(x1,y1,p1^,1);
  if kve=1 then
   begin
    xa1:=40*random(13)+1;{.:Polozenie tanka protivnika v nachale igri:.}
   end;
   if kve=2 then
   begin
    xa1:=40*random(4)+1;{.:Polozenie tanka protivnika v nachale igri:.}
    xa2:=40*(random(5)+7);
   end;
   if kve=3 then
   begin
    xa1:=40*(random(3)+1);{.:Polozenie tanka protivnika v nachale igri:.}
    xa2:=40*(random(3)+6);
    xa3:=40*(random(3)+11);
   end;
   ya1:=0;{.:Polozenie tanka protivnika v nachale igri:.}
   ya2:=0;{.:Polozenie tanka protivnika v nachale igri:.}
   ya3:=0;
   nt:=3;
   nap1:=4;
   nap2:=4;
   nap3:=4;
   usshot:=false;
   chet:=-1;
   shagm1:=1;
   schbo:=1;
  if usOPTV then
  begin
   kve:=2;
   kvs:=8;
   sp:=50;
   usSound:=false;
   usBonus:=true;
  end;
  usOPTV:=false;
 end;
{--------------------------/\INITVAR/\------------------------------}
{===================================================================}
{---------------------------\/HELP\/--------------------------------}
procedure help;
var f:text;
    tx,ty:integer;
    sth:string;
begin
 assign(f,'paskal\sem1\vvodnik\gam\files\Help.txt');
 reset(f);
 tx:=50;
 ty:=65;
 setfillstyle(1,1);
 bar(39,59,604,466);
 setfillstyle(1,9);
 bar(37,57,602,464);
 setfillstyle(1,1);
 bar(35,55,600,462);
 setfillstyle(3,0);
 bar(40,60,595,457);
 settextstyle(0,0,3);
 setcolor(15);
 outtextxy(284,14,'Help');
 setcolor(9);
 outtextxy(282,12,'Help');
 setcolor(1);
 outtextxy(280,10,'Help');
 while not(eof(f)) do
 begin
  readln(f,sth);
  setcolor(15);
  settextstyle(0,0,1);
  outtextxy(tx,ty,sth);
  ty:=ty+20;
  setcolor(1);
  line(35,ty-7,600,ty-7);
 end;
end;
{----------------------------/\HELP/\------------------------------}
{==================================================================}
{----------------------------\/REC\/-------------------------------}
procedure rec;
var f:text;
    tx,ty:integer;
    strec:string;
begin
 assign(f,'c:/files/Record.txt');
 reset(f);
 tx:=50;
 ty:=45;
 setfillstyle(1,9);
 bar(37,12,327,441);
 setfillstyle(1,1);
 bar(35,10,325,439);
 setfillstyle(3,0);
 bar(40,15,320,436);
 setcolor(1);
 line(80,10,80,436);
 line(260,10,260,436);
 settextstyle(0,0,3);
 setcolor(15);
 outtextxy(354,14,'Records');
 setcolor(9);
 outtextxy(352,12,'Records');
 setcolor(1);
 outtextxy(350,10,'Records');
 setcolor(1);
 line(35,ty-7,325,ty-7);
 settextstyle(0,0,1);
 setcolor(15);
 outtextxy(tx,ty-20,'Pos');
 outtextxy(tx+104,ty-20,'Name');
 outtextxy(tx+219,ty-20,'LeveL');
while not(eof(f)) do
 begin
  readln(f,strec);
  setcolor(15);
  settextstyle(0,0,1);
  outtextxy(tx,ty,strec);
  ty:=ty+20;
  setcolor(1);
  line(35,ty-7,325,ty-7);
 end;
end;
{----------------------------/\REC/\-------------------------------}
{==================================================================}
{----------------------------\/SAVERECORD\/------------------------}
procedure saverec;
var arec:array[1..21] of string;
    f:text;
    tx,ty,code:integer;
    arch,srst,stlev,lev,stwork:string;
    i,j,levi,border:byte;
    usLev,usBordDown:boolean;
begin
 cleardevice;
 usBordDown:=true;
 usLev:=true;
 assign(f,'paskal\sem1\vvodnik\gam\files\Record.txt');
 reset(f);
 while not(eof(f)) do
  begin
   readln(f,arch);
   arec[i]:=arch;
   inc(i);
  end;
 for i:=1 to 21 do
 begin
  stlev:=arec[i];
  lev:=stlev[31]+stlev[32];
  val(lev,levi,code);
  if (levi<level) and usBordDown then
   begin
   Border:=i;
   usBordDown:=false;
  end;
 end;
 if level<10 then srst:=name+prr+'0'+lst
 else srst:=name+prr+lst;
 if Border<21 then
  begin
   for i:=20 downto border-1 do
    begin
     arec[i+1]:=arec[i];
    end;
  end
 else
  begin
   setcolor(15);
   settextstyle(0,0,1);
   outtextxy(100,200,'You are not enter in records');
  end;
 arec[border]:= srst;
 close(f);
 rewrite(f);
 ty:=100;
 settextstyle(0,0,1);
 for i:=1 to 20 do
  begin
   outtextxy(100,ty,arec[i]);
   ty:=ty+10;
  end;
 readkey;
 for i:=1 to 20 do
  writeln(f,arec[i]);
 close(f);
 cleardevice;
 rec;
 readkey;
end;
{----------------------------/\SAVERECORD/\------------------------}
{==================================================================}
{---------------------------\/OPTIONS\/----------------------------}
procedure options;
var opos:byte;
    kvest,kvsst,spst,sost,bost:string;
begin
 settextstyle(0,0,3);
 setcolor(15);
 outtextxy(54,84,'Options');
 setcolor(9);
 outtextxy(52,82,'Options');
 setcolor(1);
 outtextxy(50,80,'Options');
 setfillstyle(1,1);
 bar(44,114,329,234);
 setfillstyle(1,9);
 bar(42,112,327,232);
 setfillstyle(1,1);
 bar(40,110,325,230);
 setfillstyle(1,0);
 bar(45,115,320,225);
 setfillstyle(1,1);
 bar(260,110,265,230);
 opos:=1;
 sost:='Off';
 bost:='On';
 str(kve,kvest);
 str(kvs,kvsst);
 str(sp,spst);
 setfillstyle(1,1);
 bar(48,117,255,135);
 setcolor(15);
 settextstyle(0,0,2);
 outtextxy(50,120,'Enemy number');
 outtextxy(270,120,kvest);
 setcolor(9);
 outtextxy(50,140,'Wall number');
 outtextxy(270,140,kvsst);
 outtextxy(50,160,'Game speed');
 outtextxy(270,160,spst);
 outtextxy(50,180,'Sound');
 outtextxy(270,180,sost);
 outtextxy(50,200,'Bonus');
 outtextxy(270,200,bost);
 while (key<>#27) do
  begin
  if usSound then begin sound(1000);delay(5000); nosound;end;
  key:=readkey;
  case key of
             #72:begin
                  opos:=opos-1;
                  if (opos=0) then opos:=5;
                 end;
             #80:begin
                  opos:=opos+1;
                  if (opos=6) then opos:=1;
                 end;
             #75:begin
                  case opos of
                               1:begin kve:=kve-1; if (kve=0) then kve:=3;end;
                               2:begin kvs:=kvs-1; if (kvs=0) then kvs:=16;end;
                               3:begin sp:=sp-1; if (sp=0) then sp:=100;end;
                               4:begin
                                  usSound:=not(usSound);
                                  if usSound then sost:='On'
                                  else sost:='Off'
                                 end;
                               5:begin
                                  usBonus:=not(usBonus);
                                  if usBonus then bost:='On'
                                  else bost:='Off'
                                 end;
                  end;
                 end;
             #77:begin
                  case opos of
                               1:begin kve:=kve+1; if (kve=4) then kve:=1;end;
                               2:begin kvs:=kvs+1; if (kvs=17) then kvs:=1;end;
                               3:begin sp:=sp+1; if (sp=101) then sp:=1;end;
                               4:begin
                                  usSound:=not(usSound);
                                  if usSound then sost:='On'
                                  else sost:='Off'
                                 end;
                               5:begin
                                  usBonus:=not(usBonus);
                                  if usBonus then bost:='On'
                                  else bost:='Off'
                                 end;
                  end;
             end;
  end;
  str(kve,kvest);
  str(kvs,kvsst);
  str(sp,spst);
  case opos of
             1:begin
                setfillstyle(1,0);
                bar(270,120,320,220);
                bar(48,117,255,215);
                setfillstyle(1,1);
                bar(48,117,255,135);
                setcolor(15);
                settextstyle(0,0,2);
                outtextxy(50,120,'Enemy number');
                outtextxy(270,120,kvest);
                setcolor(9);
                outtextxy(50,140,'Wall number');
                outtextxy(270,140,kvsst);
                outtextxy(50,160,'Game speed');
                outtextxy(270,160,spst);
                outtextxy(50,180,'Sound');
                outtextxy(270,180,sost);
                outtextxy(50,200,'Bonus');
                outtextxy(270,200,bost);
               end;
             2:begin
                setfillstyle(1,0);
                bar(270,120,320,220);
                bar(48,117,255,215);
                setcolor(9);
                settextstyle(0,0,2);
                outtextxy(50,120,'Enemy number');
                outtextxy(270,120,kvest);
                setfillstyle(1,1);
                bar(48,137,255,155);
                setcolor(15);
                outtextxy(50,140,'Wall number');
                outtextxy(270,140,kvsst);
                setcolor(9);
                outtextxy(50,160,'Game speed');
                outtextxy(270,160,spst);
                outtextxy(50,180,'Sound');
                outtextxy(270,180,sost);
                outtextxy(50,200,'Bonus');
                outtextxy(270,200,bost);
               end;
             3:begin
                setfillstyle(1,0);
                bar(270,120,320,220);
                bar(48,117,255,215);
                setcolor(9);
                settextstyle(0,0,2);
                outtextxy(50,120,'Enemy number');
                outtextxy(270,120,kvest);
                outtextxy(50,140,'Wall number');
                outtextxy(270,140,kvsst);
                setfillstyle(1,1);
                bar(48,157,255,175);
                setcolor(15);
                outtextxy(50,160,'Game speed');
                outtextxy(270,160,spst);
                setcolor(9);
                outtextxy(50,180,'Sound');
                outtextxy(270,180,sost);
                outtextxy(50,200,'Bonus');
                outtextxy(270,200,bost);
               end;
             4:begin
                setfillstyle(1,0);
                bar(270,120,320,220);
                bar(48,117,255,215);
                setcolor(9);
                settextstyle(0,0,2);
                outtextxy(50,120,'Enemy number');
                outtextxy(270,120,kvest);
                outtextxy(50,140,'Wall number');
                outtextxy(270,140,kvsst);
                outtextxy(50,160,'Game speed');
                outtextxy(270,160,spst);
                setfillstyle(1,1);
                bar(48,177,255,195);
                setcolor(15);
                outtextxy(50,180,'Sound');
                outtextxy(270,180,sost);
                setcolor(9);
                outtextxy(50,200,'Bonus');
                outtextxy(270,200,bost);
               end;
             5:begin
                setfillstyle(1,0);
                bar(270,120,320,220);
                bar(48,117,255,215);
                setcolor(9);
                settextstyle(0,0,2);
                outtextxy(50,120,'Enemy number');
                outtextxy(270,120,kvest);
                outtextxy(50,140,'Wall number');
                outtextxy(270,140,kvsst);
                outtextxy(50,160,'Game speed');
                outtextxy(270,160,spst);
                setcolor(9);
                outtextxy(50,180,'Sound');
                outtextxy(270,180,sost);
                setfillstyle(1,1);
                bar(48,197,255,215);
                setcolor(15);
                outtextxy(50,200,'Bonus');
                outtextxy(270,200,bost);
               end;
  end;
 end;
 key:=#0;
end;
{---------------------------/\OPTIONS/\----------------------------}
{==================================================================}
{----------------------------\/SCHET\/-----------------------------}
procedure schet;
var chs:string;
begin
 setfillstyle(1,0);
 bar(570,15,630,35);
 chet:=chet+1;
 if (chet mod 5=0) and (chet<>0) then begin level:=level+1; uslevelup:=true;end;
 str(level,lst);
 str(chet,chs);
 setcolor(15);
 settextstyle(0,0,1);
 outtextxy(571,15,'Level:');
 outtextxy(618,15,lst);
 outtextxy(571,25,'Score:');
 outtextxy(618,25,chs);
end;
{----------------------------/\SCHET/\-----------------------------}
{==================================================================}
{----------------------------\/CHEAT\/-----------------------------}
procedure cheat;
begin
 cleardevice;
 setcolor(4);
 settextstyle(0,0,3);
 outtextxy(140,150,'Master coming!!!');
 usLevelup:=true;
 level:=level+1;
 str(level,lst);
 readkey;
 key:=#0;
end;
{----------------------------/\CHEAT/\-----------------------------}
{==================================================================}
{----------------------------\/SHOT\/------------------------------}
procedure shot;
var shx,shy:integer;
    us:boolean;
begin
 shx:=10;
 shy:=10;
 us:=false; {.:Esli snariad popal v stenu eu prisvaivaetsia '1':.}
 if  (xs1=x1) and (ys1=y1+15) or
     (xs1=x1+35) and (ys1=y1+15) or
     (xs1=x1+17) and (ys1=y1) or
     (xs1=x1+17) and (ys1=y1+35) then
  begin
   ds:=5000-(50*sp);
   delay(ds);
   putimage(xs1,ys1,p3^,1);
  end;
  if usFS then ns:=nt;
  if not(us) then putimage(xs1,ys1,p3^,1);
  if ns=1 then begin shx:=-shx;shy:=0;usFS:=false;end;{--------------}
  if ns=2 then begin shx:=shx;shy:=0;;usFS:=false;end; {++Opredeliaem napravlenie dvizenia snariada++}
  if ns=3 then begin shx:=0;shy:=-shy;;usFS:=false;end;
  if ns=4 then begin shx:=0;shy:=shy;;usFS:=false;end; {--------------}
  xs1:=xs1+shx;
  ys1:=ys1+shy;
  gp1:=getpixel(xs1,ys1);
  if gp1=4 then usshot:=false;{++Popali v stenu++}
  if xs1<0 then usshot:=false;
  if xs1>560 then usshot:=false;
  if ys1<0 then usshot:=false;
  if ys1>480 then usshot:=false;
  if (xs1>xa1) and (ys1>ya1) and (ys1<ya1+40) and (xs1<xa1+40) then
   begin
    usshot:=false;
    putimage(xa1,ya1,p2^,1);
    if kve=1 then
    begin
     xa1:=40*(random(13)+1);{.:Polozenie tanka protivnika v nachale igri:.}
    end;
    if kve=2 then
    begin
     xa1:=40*(random(5)+1);{.:Polozenie tanka protivnika v nachale igri:.}
    end;
    if kve=3 then
    begin
     xa1:=40*(random(3)+1);{.:Polozenie tanka protivnika v nachale igri:.}
    end;
    ya1:=0;
    if usFreeze then putimage(xa1,ya1,p2^,1);
    nap1:=4;
    schet;
   end;
  if (xs1>xa2) and (ys1>ya2) and (ys1<ya2+40) and (xs1<xa2+40) then
   begin
    usshot:=false;
    putimage(xa2,ya2,p4^,1);
    if kve=2 then
    begin
     xa2:=40*(random(6)+8);
    end;
    if kve=3 then
    begin
     xa2:=40*(random(2)+7);
   end;
    ya2:=0;
    if usFreeze then putimage(xa2,ya2,p4^,1);
    nap2:=4;
    schet;
   end;
  if (xs1>xa3) and (ys1>ya3) and (ys1<ya3+40) and (xs1<xa3+40) then
   begin
    usshot:=false;
    putimage(xa3,ya3,p5^,1);
    xa3:=40*(random(3)+10);
    ya3:=0;
    if usFreeze then putimage(xa3,ya3,p5^,1);
    nap3:=4;
    schet;
   end;
 putimage(xs1,ys1,p3^,1);
 if (usshot=false) then putimage(xs1,ys1,p3^,1);
 us:=false;
end;
{----------------------------/\SHOT/\------------------------------}
{==================================================================}
{----------------------------\/UPR\/-------------------------------}
procedure upr;
 begin
 putimage(x1,y1,p1^,1);
 key:=readkey;
 if key=#0 then key:=readkey;
 case key of {++Opredeliaem napravlenie dvizenia++}
            #75:begin
                 gp1:=getpixel(x1-1,y1+2); {++Proveriaem nalichae steni++}
                 gp2:=getpixel(x1-1,y1+38);{++Proveriaem nalichae steni++}
                 gp3:=getpixel(x1-1,y1+19);
                 gp4:=getpixel(x1-1,y1+21);
                 if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then x1:=x1 {++Ne naezaem na steni++}
                 else x1:=x1-5;
                 if x1<0 then x1:=0; {++Ne vihodim za ekran++}
                 nt:=1;{.:Opredeliaem napr dviz tanka dlia proceduri shot:1-levo;2-pravo;3-vverh;4-vniz:.}
                 freemem(p1,size);
                 p1:=loader('paskal\sem1\vvodnik\gam\files\pal');
                end;
            #77:begin
                 gp1:=getpixel(x1+41,y1+2);
                 gp2:=getpixel(x1+41,y1+38);
                 gp3:=getpixel(x1+41,y1+19);
                 gp4:=getpixel(x1+41,y1+21);
                 if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then x1:=x1
                 else x1:=x1+5;
                 if x1>600 then x1:=600;
                 nt:=2;
                 freemem(p1,size);
                 p1:=loader('paskal\sem1\vvodnik\gam\files\par');
                end;
            #72:begin
                 gp1:=getpixel(x1+2,y1-1);
                 gp2:=getpixel(x1+38,y1-1);
                 gp3:=getpixel(x1+19,y1-1);
                 gp4:=getpixel(x1+21,y1-1);
                 if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then y1:=y1
                 else y1:=y1-5;
                 if y1<0 then y1:=0;
                 nt:=3;
                 freemem(p1,size);
                 p1:=loader('paskal\sem1\vvodnik\gam\files\pav');
                end;
            #80:begin
                 gp1:=getpixel(x1+2,y1+41);
                 gp2:=getpixel(x1+38,y1+41);
                 gp3:=getpixel(x1+19,y1+41);
                 gp4:=getpixel(x1+21,y1+41);
                 if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then y1:=y1
                 else y1:=y1+5;
                 if y1>440 then y1:=440;
                 nt:=4;
                 freemem(p1,size);
                 p1:=loader('paskal\sem1\vvodnik\gam\files\pan');
                end;
            #32:begin
                 if not(usshot) then
                 begin
                  usshot:=true;
                  usFS:=true;
                  if nt=1 then begin xs1:=x1; ys1:=y1+15;end;
                  if nt=2 then begin xs1:=x1+35; ys1:=y1+15;end;
                  if nt=3 then begin xs1:=x1+17; ys1:=y1;end;
                  if nt=4 then begin xs1:=x1+17; ys1:=y1+35;end;
                 end;
                end;
 end;
 putimage(x1,y1,p1^,1);
 while keypressed do readkey;
 if usShot then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,124,630,133);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(585,125,'Fire');
end;
{----------------------------/\UPR/\-------------------------------}
{==================================================================}
{----------------------------\/AI1\/-------------------------------}
procedure ai1;
begin
 if usSound then sound(100);
 nap1:=4; {++Dvizenie v niz++}
 pxl1:=xa1;
 pxr1:=xa1+40;
 lch1:=1;
 rch1:=1;
 usAIstor11:=false;
 usAIstor12:=false;
 putimage(xa1,ya1,p2^,1);
 if ya1=0 then putimage(xa1,ya1,p2^,1);
  if usExtrmove1 then
  begin
   gp1:=getpixel(xa1-1,ya1+2);
   gp2:=getpixel(xa1-1,ya1+38);
   if (gp1=0) and (gp2=0) then nap1:=1
   else nap2:=3;
   gp3:=getpixel(xa1+2,ya1+41);
   gp4:=getpixel(xa1+38,ya1+41);
   if (gp1=0) and (gp2=0) and (gp3=0) and (gp4=0) then chem1:=chem1+1;
   if chem1>3 then begin chem1:=0; usExtrmove1:=false;end;
  end
  else
  begin
  chem1:=0;
  gp1:=getpixel(xa1+2,ya1+41); {.:Nalichae steni nizhe AI:.}
  gp2:=getpixel(xa1+38,ya1+41);
  if (xa1<300) and (ya1=440) then nap1:=2 else
  if (xa1>340) and (ya1=440) then nap1:=1 else
  if (gp1=0) and (gp2=0) then nap1:=4 else
   begin
    while not(usAIstor11) and not(usAIstor12) do
    begin
     gp1:=getpixel(pxl1,ya1);
     gp2:=getpixel(pxl1,ya1+41);
     if pxl1<1 then lch1:=640;
     if (gp1<>0) and (gp2<>0) then lch1:=640;
     if (gp2=0) then usAIstor11:=true else
     if not(usAIstor11) then begin pxl1:=pxl1-1; lch1:=lch1+1;end;
     gp3:=getpixel(pxr1,ya1);
     gp4:=getpixel(pxr1,ya1+41);
     if pxr1>639 then rch1:=640;
     if (gp3<>0) and (gp4<>0) then rch1:=640;
     if (gp4=0) then usAIstor12:=true else
     if not(usAIstor12) then begin pxr1:=pxr1+1; rch1:=rch1+1;end
    end;
      if (lch1<rch1) then nap1:=1;
      if (lch1>rch1) then nap1:=2;
      if (lch1>639) and (rch1>639) then usExtrmove1:=true;
      if (lch1=rch1) then nap1:=random(2)+1;
  end;
 end;
 if not(stop) then case nap1 of
            1:begin
               gp1:=getpixel(xa1-1,ya1+2);
               gp2:=getpixel(xa1-1,ya1+38);
               gp3:=getpixel(xa1-1,ya1+19);
               gp4:=getpixel(xa1-1,ya1+21);
               xa1:=xa1-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa1:=xa1+5;
               freemem(p2,size);
               p2:=loader('paskal\sem1\vvodnik\gam\files\ail');
              end;
            2:begin
               gp1:=getpixel(xa1+41,ya1+2);
               gp2:=getpixel(xa1+41,ya1+38);
               gp3:=getpixel(xa1+41,ya1+19);
               gp4:=getpixel(xa1+41,ya1+21);
               xa1:=xa1+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa1:=xa1-5;
               freemem(p2,size);
               p2:=loader('paskal\sem1\vvodnik\gam\files\air');
              end;
            3:begin
               gp1:=getpixel(xa1+2,ya1-1);
               gp2:=getpixel(xa1+38,ya1-1);
               gp3:=getpixel(xa1+21,ya1-1);
               gp4:=getpixel(xa1+19,ya1-1);
               ya1:=ya1-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya1:=ya1+5;
               freemem(p2,size);
               p2:=loader('paskal\sem1\vvodnik\gam\files\aiv');
              end;
            4:begin
               gp1:=getpixel(xa1+2,ya1+41);
               gp2:=getpixel(xa1+38,ya1+41);
               gp3:=getpixel(xa1+19,ya1+41);
               gp4:=getpixel(xa1+21,ya1+41);
               ya1:=ya1+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya1:=ya1-5;
               freemem(p2,size);
               p2:=loader('paskal\sem1\vvodnik\gam\files\ain');
              end;
  end;
 if usFreeze then stop:=true;
 putimage(xa1,ya1,p2^,1);
 if (ya1>360) then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,44,630,53);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(574,45,'Danger1');
end;
{----------------------------/\AI1/\-------------------------------}
{==================================================================}
{----------------------------\/AI2\/-------------------------------}
procedure ai2;
begin
 if key<>#27 then
 begin
 nap2:=4; {++Dvizenie v niz++}
 pxl2:=xa2;
 pxr2:=xa2+40;
 lch2:=0;
 rch2:=0;
 usAIstor21:=false;
 usAIstor22:=false;
 putimage(xa2,ya2,p4^,1);
 if ya2=0 then putimage(xa2,ya2,p4^,1);
  if usExtrmove2 then
  begin
   gp1:=getpixel(xa2-1,ya2+2);
   gp2:=getpixel(xa2-1,ya2+38);
   if (gp1=0) and (gp2=0) then nap2:=1
   else nap2:=3;
   gp3:=getpixel(xa2+2,ya2+41);
   gp4:=getpixel(xa2+38,ya2+41);
   if (gp1=0) and (gp2=0) and (gp3=0) and (gp4=0) then chem2:=chem2+1;
   if chem2>3 then begin chem2:=0; usExtrmove2:=false;end;
  end
  else
  begin
  gp1:=getpixel(xa2+2,ya2+41); {.:Nalichae steni nizhe AI:.}
  gp2:=getpixel(xa2+38,ya2+41);
  if (xa2<300) and (ya2=440) then nap2:=2 else
  if (xa2>340) and (ya2=440) then nap2:=1 else
  if (gp1=0) and (gp2=0) then nap2:=4 else
   begin
    while not(usAIstor21) and not(usAIstor22) do
    begin
     gp1:=getpixel(pxl2,ya2);
     gp2:=getpixel(pxl2,ya2+41);
     if pxl2<1 then lch2:=640;
     if (gp1<>0) and (gp2<>0) then lch2:=640;
     if (gp2=0) then usAIstor21:=true else
     if not(usAIstor21) then begin pxl2:=pxl2-1; lch2:=lch2+1;end;
     gp3:=getpixel(pxr2,ya2);
     gp4:=getpixel(pxr2,ya2+41);
     if pxr2>639 then rch2:=640;
     if (gp3<>0) and (gp4<>0) then rch2:=640;
     if (gp4=0) then usAIstor22:=true else
     if not(usAIstor22) then begin pxr2:=pxr2+1; rch2:=rch2+1;end
    end;
      if (lch2<rch2) then nap2:=1;
      if (lch2>rch2) then nap2:=2;
      if (lch2>639) and (rch2>639) then usExtrmove2:=true;
      if (lch2=rch2) then nap2:=random(1)+1;
  end;
 end;
 if not(stop) then case nap2 of
            1:begin
               gp1:=getpixel(xa2-1,ya2+2);
               gp2:=getpixel(xa2-1,ya2+38);
               gp3:=getpixel(xa2-1,ya2+19);
               gp4:=getpixel(xa2-1,ya2+21);
               xa2:=xa2-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa2:=xa2+5;
               freemem(p4,size);
               p4:=loader('paskal\sem1\vvodnik\gam\files\ail');
              end;
            2:begin
               gp1:=getpixel(xa2+41,ya2+2);
               gp2:=getpixel(xa2+41,ya2+38);
               gp3:=getpixel(xa2+41,ya2+19);
               gp4:=getpixel(xa2+41,ya2+21);
               xa2:=xa2+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa2:=xa2-5;
               freemem(p4,size);
               p4:=loader('paskal\sem1\vvodnik\gam\files\air');
              end;
            3:begin
               gp1:=getpixel(xa2+2,ya2-1);
               gp2:=getpixel(xa2+38,ya2-1);
               gp3:=getpixel(xa2+19,ya2-1);
               gp4:=getpixel(xa2+21,ya2-1);
               ya2:=ya2-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya2:=ya2+5;
               freemem(p4,size);
               p4:=loader('paskal\sem1\vvodnik\gam\files\aiv');
              end;
            4:begin
               gp1:=getpixel(xa2+2,ya2+41);
               gp2:=getpixel(xa2+38,ya2+41);
               gp3:=getpixel(xa2+19,ya2+41);
               gp4:=getpixel(xa2+21,ya2+41);
               ya2:=ya2+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya2:=ya2-5;
               freemem(p4,size);
               p4:=loader('paskal\sem1\vvodnik\gam\files\ain');
              end;
  end;
 end;
 if usFreeze then stop:=true;
 putimage(xa2,ya2,p4^,1);
 if (ya2>360) then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,59,630,68);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(574,60,'Danger2');
end;
{----------------------------/\AI2/\-------------------------------}
{==================================================================}
{----------------------------\/AI3\/-------------------------------}
procedure ai3;
begin
 if key<>#27 then
 begin
 nap3:=4; {++Dvizenie v niz++}
 pxl3:=xa3;
 pxr3:=xa3+40;
 lch3:=0;
 rch3:=0;
 usAIstor31:=false;
 usAIstor32:=false;
 putimage(xa3,ya3,p5^,1);
 if ya3=0 then putimage(xa3,ya3,p5^,1);
  if usExtrmove3 then
  begin
   gp1:=getpixel(xa3-1,ya3+2);
   gp2:=getpixel(xa3-1,ya3+38);
   if (gp1=0) and (gp2=0) then nap3:=1
   else nap3:=3;
   gp3:=getpixel(xa3+2,ya3+41);
   gp4:=getpixel(xa3+38,ya3+41);
   if (gp1=0) and (gp2=0) and (gp3=0) and (gp4=0) then chem3:=chem3+1;
   if chem3>3 then begin chem3:=0; usExtrmove3:=false;end;
  end
  else
  begin
  gp1:=getpixel(xa3+2,ya3+41); {.:Nalichae steni nizhe AI:.}
  gp2:=getpixel(xa3+38,ya3+41);
  if (xa3<300) and (ya3=440) then nap3:=2 else
  if (xa3>340) and (ya3=440) then nap3:=1 else
  if (gp1=0) and (gp2=0) then nap3:=4 else
   begin
    while not(usAIstor31) and not(usAIstor32) do
    begin
     gp1:=getpixel(pxl3,ya3);
     gp2:=getpixel(pxl3,ya3+41);
     if pxl3<1 then lch3:=640;
     if (gp1<>0) and (gp2<>0) then lch3:=640;
     if (gp2=0) then usAIstor31:=true else
     if not(usAIstor31) then begin pxl3:=pxl3-1; lch3:=lch3+1;end;
     gp3:=getpixel(pxr3,ya3);
     gp4:=getpixel(pxr3,ya3+41);
     if pxr3>639 then rch3:=640;
     if (gp3<>0) and (gp4<>0) then rch3:=640;
     if (gp4=0) then usAIstor32:=true else
     if not(usAIstor32) then begin pxr3:=pxr3+1; rch3:=rch3+1;end
    end;
      if (lch3<rch3) then nap3:=1;
      if (lch3>rch3) then nap3:=2;
      if (lch3>639) and (rch3>639) then usExtrmove3:=true;
      if (lch3=rch3) then nap3:=random(1)+1;
  end;
 end;
 if not(stop) then case nap3 of
            1:begin
               gp1:=getpixel(xa3-1,ya3+2);
               gp2:=getpixel(xa3-1,ya3+38);
               gp3:=getpixel(xa3-1,ya3+19);
               gp4:=getpixel(xa3-1,ya3+21);
               xa3:=xa3-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa3:=xa3+5;
               freemem(p5,size);
               p5:=loader('paskal\sem1\vvodnik\gam\files\ail');
              end;
            2:begin
               gp1:=getpixel(xa3+41,ya3+2);
               gp2:=getpixel(xa3+41,ya3+38);
               gp3:=getpixel(xa3+41,ya3+19);
               gp4:=getpixel(xa3+41,ya3+21);
               xa3:=xa3+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then xa3:=xa3-5;
               freemem(p5,size);
               p5:=loader('paskal\sem1\vvodnik\gam\files\air');
              end;
            3:begin
               gp1:=getpixel(xa3+2,ya3-1);
               gp2:=getpixel(xa3+38,ya3-1);
               gp3:=getpixel(xa3+19,ya3-1);
               gp4:=getpixel(xa3+21,ya3-1);
               ya3:=ya3-5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya3:=ya3+5;
               freemem(p5,size);
               p5:=loader('paskal\sem1\vvodnik\gam\files\aiv');
              end;
            4:begin
               gp1:=getpixel(xa3+2,ya3+41);
               gp2:=getpixel(xa3+38,ya3+41);
               gp3:=getpixel(xa3+19,ya3+41);
               gp4:=getpixel(xa3+21,ya3+41);
               ya3:=ya3+5;
               if (gp1<>0) or (gp2<>0) or (gp3<>0) or (gp4<>0) then ya3:=ya3-5;
               freemem(p5,size);
               p5:=loader('paskal\sem1\vvodnik\gam\files\ain');
              end;
  end;
 end;
 if usFreeze then Stop:=true;
 putimage(xa3,ya3,p5^,1);
 if (ya3>360) then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,74,630,83);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(574,75,'Danger3');
end;
{----------------------------/\AI3/\-------------------------------}
{==================================================================}
{--------------------------\/LEVELUP\/-----------------------------}
procedure levelup;
begin
 cleardevice;
 setcolor(4);
 str(level,lst);
 settextstyle(0,0,4);
 outtextxy(110,170,'Next level');
 outtextxy(450,170,lst);
 readln;
 cleardevice;
end;
{--------------------------/\LEVELUP/\-----------------------------}
{==================================================================}
{----------------------------\/LOST\/------------------------------}
procedure lost;
begin
 cleardevice;
 setcolor(15);
 settextstyle(0,0,5);
 outtextxy(170,170,'You Lost');
 level:=1;
 Key:=#27;
 readln;
end;
{----------------------------/\LOST/\------------------------------}
{==================================================================}
{--------------------------\/ENDGAME\/-----------------------------}
procedure EndGame;
begin
 cleardevice;
 setcolor(4);
 settextstyle(0,0,4);
 outtextxy(70,170,'Russia FOREVER -');
 setcolor(1);
 outtextxy(70,220,'NATO MUST DIE!!!');
 setcolor(15);
 settextstyle(0,0,2);
 outtextxy(190,280,'Your level is');
 settextstyle(0,0,2);
 outtextxy(410,280,lst);
 ydm:=325;
 if y_n(ydm) then saverec;
 key:=#0;
end;
{--------------------------/\ENDGAME/\-----------------------------}
{==================================================================}
{---------------------------\/GENMAP\/-----------------------------}
procedure genmap;
var
    i,j,kd:integer;
    sx,sy,xy:word;
begin
 putimage(300,440,p6^,0);
 setcolor(4);
 line(0,0,560,0);
 line(0,0,0,480);
 line(0,479,560,479);
 line(560,0,560,480);
 setfillstyle(1,1);
 bar(561,0,640,480);
 setfillstyle(1,9);
 bar(563,2,638,478);
 setfillstyle(1,1);
 bar(565,4,636,476);
 setfillstyle(3,0);
 bar(567,0,634,474);
 setcolor(9);
 line(567,3,583,3);
 line(634,3,617,3);
 line(566,2,583,2);
 line(634,2,617,2);
 setcolor(1);
 line(567,1,583,1);
 line(634,1,617,1);
 line(566,0,640,0);
 line(566,10,634,10);
 setcolor(9);
 line(566,11,634,11);
 setcolor(1);
 line(566,38,634,38);
 setcolor(9);
 line(566,39,634,39);
 setcolor(1);
 line(566,89,634,89);
 setcolor(9);
 line(566,90,634,90);
 if not(usSound) then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,94,630,103);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(580,95,'Sound');
 if not(usBonus) then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,109,630,118);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(580,110,'Bonus');
 setcolor(1);
 line(566,138,634,138);
 setcolor(9);
 line(566,139,634,139);
 if usShot then begin col1:=12; col2:=4;end
 else begin col1:=10; col2:=2;end;
 setfillstyle(1,col1);
 bar(570,124,630,133);
 setcolor(col2);
 settextstyle(0,0,1);
 outtextxy(585,125,'Fire');
 setcolor(15);
 settextstyle(0,0,1);
 outtextxy(584,0,'Info');
 for i:=1 to kvs do
  begin
   xy:=random(2);
   if xy=0 then
   begin
    sx:=(random(4)+1)*80;
    sy:=(random(3)+2)*80;
    kd:=random(4)+1;
    for j:=1 to kd do
    begin
     putimage(sx,sy,psg^,0);
     sx:=sx+40;
    end;
   end
   else
   begin
    sx:=(random(4)+1)*80;
    sy:=(random(3)+2)*80;
    kd:=random(4)+1;
    for j:=1 to kd do
    begin
     putimage(sx,sy,psv^,0);
     if sy<360 then sy:=sy+40;
    end;
   end;
  end;
end;
{---------------------------/\GENMAP/\-----------------------------}
{==================================================================}
{---------------------------\/PAUSE\/------------------------------}
procedure pause;
begin
 key:=#0;
 setcolor(15);
 settextstyle(0,0,2);
 setfillstyle(1,0);
 repeat
  delay(19000);
  delay(19000);
  outtextxy(280,40,'Pause');
  delay(19000);
  delay(19000);
  bar(280,40,360,60);
  if keypressed then key:=readkey;
 until key='p';
 key:=#0;
end;
{---------------------------/\PAUSE/\------------------------------}
{==================================================================}
{---------------------------\/MEETB\/------------------------------}
procedure meetBonus;
begin
 if (x1>xb-55) and (x1<xb+55) and (y1>yb-55) and (y1<yb+55) then
 begin
  case typeb of
               1:begin
                  putimage(280,440,psv^,0);
                  putimage(340,440,psv^,0);
                  putimage(280,420,psg^,0);
                  putimage(320,420,psg^,0);
                 end;
               2:usFreeze:=true;
               3:Begin
                  level:=level+5;
                  usLevelup:=true;
                 end;
  end;
  setfillstyle(1,0);
  bar(xb,yb,xb+40,yb+40);
  xb:=-40;
  yb:=-40;
  usBonusPut:=true;
  schbo:=1;
 end;
 if (schbo>=150) then
 begin
  setfillstyle(1,0);
  bar(xb,yb,xb+40,yb+40);
  xb:=-40;
  yb:=-40;
  usBonusPut:=true;
  schbo:=1;
 end;
 schbo:=schbo+1;
end;
{---------------------------/\MEETB/\------------------------------}
{==================================================================}
{---------------------------\/BONUS\/------------------------------}
procedure bonus;
begin
 typeB:=random(3)+1;
 repeat
  xb:=(random(13)+1)*40;
  yb:=(random(8)+2)*40;
  gp1:=getpixel(xb,yb);
  gp2:=getpixel(xb+40,yb);
  gp3:=getpixel(xb,yb+40);
  gp4:=getpixel(xb+40,yb+40);
  gp5:=getpixel(xb+20,yb+20);
 until (gp1=0) and (gp2=0) and (gp3=0) and (gp4=0) and (gp5=0);
 case typeB of
              1:putimage(xb,yb,psh^,0);
              2:putimage(xb,yb,pfr^,0);
              3:putimage(xb,yb,plev^,0);
 end;
 usBonusPut:=false;
end;
{---------------------------/\BONUS/\------------------------------}
{==================================================================}
{----------------------------\/GAME\/------------------------------}
procedure Game;
begin
 initvar;
 genmap;
 level:=1;
 schet;
 repeat
  if usBonus and usBonusPut then bonus;
  meetBonus;
  if key='p' then pause;
  if uslevelup then
   begin
    levelup;
    picture;
    initvar;
    genmap;
    schet;
   end;
   if keypressed then upr;
   if (usshot) then shot;
   if not(usFreeze) then ai1;
   if (kve>1) and not(usFreeze) then ai2;
   if (kve>2) and not(usFreeze) then ai3;
   if (usshot) then shot;
   ds:=10000-(50*sp);
   delay(ds);
   if (xa1>=265) and (ya1>=435) and (xa1<=340) or
      (xa2>=265) and (ya2>=435) and (xa2<=340) or
      (xa3>=265) and (ya3>=435) and (xa3<=340) then
   Lost;
 if (key='l') and (MASTER) then cheat;
 if (key=#27) then
 begin
  ydm:=225;
  cleardevice;
  if y_n(ydm) then key:=#27
  else
   begin
    cleardevice;
    initvar;
    key:=#0;
    genmap;
    schet;
   end;
 end;
 until key=#27;
 EndGame;
end;
{----------------------------/\GAME/\------------------------------}
{==================================================================}
{----------------------------\/INGR\/------------------------------}
procedure ingr;
var gd,gm:integer;
begin
 gd:=detect;
 initgraph(gd,gm,'c:\bp\bgi\');
end;
{----------------------------/\INGR/\------------------------------}
{==================================================================}
{----------------------------\/PEGG\/------------------------------}
procedure pegg;
var txm,tym,txa,tya:word;
    i,col:byte;
begin
 key:=#0;
 setcolor(15);
 settextstyle(0,0,1);
 outtextxy(10,10,'Programming: Alexey Zhidkov');
 outtextxy(10,20,'Design: Alexey Zhidkov');
 outtextxy(10,30,'Scenario: Alexey Zhidkov');
 outtextxy(10,40,'Version: 1.0.1');
 outtextxy(10,50,'All rights reserved');
 settextstyle(0,0,5);
 i:=1;
 txm:=180;
 tym:=165;
 txa:=205;
 tya:=215;
 repeat
  case i of
           1:col:=1;
           2:col:=9;
           3:col:=9;
           4:col:=1;
  end;
  setcolor(col);
  outtextxy(txm,tym,'Modern');
  outtextxy(tya,tya,'Armor');
  txm:=txm+2;
  tym:=tym+2;
  txa:=txa+2;
  tya:=tya+2;
  inc(i);
  if i=5 then
   begin
    delay(10000);delay(10000);delay(10000);delay(10000);
    delay(10000);delay(10000);delay(10000);delay(10000);
    delay(10000);delay(10000);delay(10000);delay(10000);
    delay(10000);delay(10000);delay(10000);delay(10000);
    setfillstyle(1,0);
    bar(180,165,450,270);
    txm:=180;
    tym:=165;
    txa:=205;
    tya:=215;
    i:=1;
   end;
  if i=1 then begin delay(19999);delay(19999);end;
  delay(10000);
  if keypressed then key:=readkey;
 until key=#27;
end;
{----------------------------/\PEGG/\------------------------------}
{==================================================================}
{----------------------------\/MENU\/------------------------------}
procedure menu;
var cx,cy,pos:integer;
var key:char;
begin
 {-----------------------------++Treugolnic++----------------------}
 setbkcolor(0);
 setcolor(15);
 line(320,50,130,310);
 line(320,50,510,310);
 line(130,310,510,310);
 line(232,170,408,170);
 line(320,310,408,170);
 line(232,170,320,310);
 line(232,310,320,450);
 line(320,450,408,310);
 setfillstyle(1,1);
 floodfill(320,130,15);
 floodfill(320,230,15);
 floodfill(405,230,15);
 floodfill(235,230,15);
 floodfill(320,400,15);
 {----------------------------++Treugolnic++----------------------}
 setcolor(1);
 settextstyle(0,1,8);
 outtextxy(84,54,'Modern');
 outtextxy(82,52,'Modern');
 setcolor(9);
 outtextxy(80,50,'Modern');
 setcolor(1);
 settextstyle(0,1,8);
 outtextxy(624,89,'Armor');
 outtextxy(622,87,'Armor');
 setcolor(9);
 outtextxy(620,85,'Armor');
 setcolor(15);
 settextstyle(0,0,3);
 outtextxy(274,120,'Game');
 setcolor(9);
 outtextxy(180,270,'Help');
 outtextxy(250,175,'Record');
 outtextxy(368,270,'Exit');
 outtextxy(248,315,'Option');
 setcolor(4);
 outtextxy(274,120,'G');
 outtextxy(180,270,'H');
 outtextxy(250,175,'R');
 outtextxy(368,270,'E');
 outtextxy(248,315,'O');
 pos:=1; {.:Opredeliaet posiciu ykozatelia:1-game;2-help;3-record;4-exit:.}
 cx:=320; {.:Koordinati ykozatelia:.}
 cy:=80; {.:Koordinati ykozatelia:.}
 while (key<>#13) or (key<>'o') or (key<>'g') or (key<>'h') or (key<>'r') or (key<>'e') do
 begin
  if usSound then begin sound(1000);delay(5000); nosound;end;
  key:=readkey;
  if key=#0 then key:=readkey;
  case key of
             #72:begin {++Opredeliaem smeshenie ukozatelia, ego novuu poziciu i  koordinati++}
                  cy:=cy-100;
                  if cy<80 then cy:=280;
                  if cy=80 then begin pos:=1;cx:=320;end;
                  if (cy=180) and (cx=235) then begin pos:=2;cx:=320;end;
                  if (cy=180) and (cx=320) then pos:=3;
                  if (cy=180) and (cx=405) then begin pos:=4;cx:=320;end;
                  if (cy=280) and (cx=320) then pos:=5;
                 end;
             #80:begin
                  cy:=cy+100;
                  if cy>280 then cy:=80;
                  if cy=80 then pos:=1;
                  if (cy=180) and (cx=235) then begin pos:=2;cx:=320;end;
                  if (cy=180) and (cx=320) then pos:=3;
                  if (cy=180) and (cx=405) then begin pos:=4;cx:=320;end;
                  if (cy=280) and (cx=320) then pos:=5;
                 end;
             #75:begin
                  cx:=cx-85;
                  if cx<235 then cx:=405;
                  if cy=80 then begin pos:=1; cx:=320 end;
                  if (cy=180) and (cx=235) then pos:=2;
                  if (cy=180) and (cx=320) then pos:=3;
                  if (cy=180) and (cx=405) then pos:=4;
                 end;
             #77:begin
                  cx:=cx+85;
                  if cx>405 then cx:=235;
                  if cy=80 then begin pos:=1; cx:=320 end;
                  if (cy=180) and (cx=235) then pos:=2;
                  if (cy=180) and (cx=320) then pos:=3;
                  if (cy=180) and (cx=405) then pos:=4;
                 end;
  end;
  case pos of {++Vidiliaem knopky menu v zavisimosti ot pozicii ukozatelia:1-game;2-help;3-record;4-exit;++}
              1:begin
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(274,120,'Game');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(180,270,'Help');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(250,175,'Record');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(368,270,'Exit');
                 outtextxy(248,315,'Option');
                 setcolor(4);
                 outtextxy(274,120,'G');
                 outtextxy(180,270,'H');
                 outtextxy(250,175,'R');
                 outtextxy(368,270,'E');
                 outtextxy(248,315,'O');
                end;
              2:begin
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(274,120,'Game');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(180,270,'Help');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(250,175,'Record');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(368,270,'Exit');
                 outtextxy(248,315,'Option');
                 setcolor(4);
                 outtextxy(274,120,'G');
                 outtextxy(180,270,'H');
                 outtextxy(250,175,'R');
                 outtextxy(368,270,'E');
                 outtextxy(248,315,'O');
                end;
              3:begin
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(274,120,'Game');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(180,270,'Help');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(250,175,'Record');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(368,270,'Exit');
                 outtextxy(248,315,'Option');
                 setcolor(4);
                 outtextxy(274,120,'G');
                 outtextxy(180,270,'H');
                 outtextxy(250,175,'R');
                 outtextxy(368,270,'E');
                 outtextxy(248,315,'O');
                end;
              4:begin
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(274,120,'Game');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(180,270,'Help');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(250,175,'Record');
                 setcolor(15);
                 settextstyle(0,0,3);
                 outtextxy(368,270,'Exit');
                 setcolor(9);
                 outtextxy(248,315,'Option');
                 setcolor(4);
                 outtextxy(274,120,'G');
                 outtextxy(180,270,'H');
                 outtextxy(250,175,'R');
                 outtextxy(368,270,'E');
                 outtextxy(248,315,'O');
                end;
              5:begin
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(274,120,'Game');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(180,270,'Help');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(250,175,'Record');
                 setcolor(9);
                 settextstyle(0,0,3);
                 outtextxy(368,270,'Exit');
                 setcolor(15);
                 outtextxy(248,315,'Option');
                 setcolor(4);
                 outtextxy(274,120,'G');
                 outtextxy(180,270,'H');
                 outtextxy(250,175,'R');
                 outtextxy(368,270,'E');
                 outtextxy(248,315,'O');
                end;
  end;
  if key='g' then
  begin
   cleardevice;
   game;
   cleardevice;
   menu;
  end;
  if key='h' then
  begin
   cleardevice;
   help;
   key:=readkey;
   cleardevice;
   menu;
  end;
  if key='r' then
   begin
    cleardevice;
    rec;
    key:=readkey;
    cleardevice;
    menu;
   end;
  if key='o' then
   begin
    cleardevice;
    options;
    cleardevice;
    menu;
   end;
  if key='e' then
   begin
    cleardevice;
    key:=#0;
    ydm:=200;
    if y_n(ydm) then halt
    else begin cleardevice; menu;end;
   end;
  if (key='l') and (MASTER) then
  begin
   cleardevice;
   PEGG;
   cleardevice;
   menu;
  end;
  if key=#13 then
  case pos of {++Vizivaem proceduru v zavisimosti ot pozicii ukozatelia:1-game;2-help;3-record;4-exit;++}
              1:begin
                 cleardevice;
                 game;
                 cleardevice;
                 menu;
                end;
              2:begin
                 cleardevice;
                 help;
                 key:=readkey;
                 cleardevice;
                 menu;
                end;
              3:begin
                 cleardevice;
                 rec;
                 key:=readkey;
                 cleardevice;
                 menu;
                end;
              4:begin
                 cleardevice;
                 key:=#0;
                 ydm:=200;
                 if y_n(ydm) then halt
                 else begin cleardevice; menu;end;
                end;
              5:begin
                 cleardevice;
                 options;
                 cleardevice;
                 menu;
                end;
  end;
 end;
end;
{----------------------------/\MENU/\---------------------------------}
{=====================================================================}
{------------------------\/MAIN PROGRAM\/-----------------------------}
begin
 clrscr;
 name:=recname;
 randomize;
 ingr;{++Inicializiruem grafiku++}
 usOPTV:=true;
 picture;{++Zagruzaem kartinki++}
 zast;{++Zapuskaem zastavku++}
 initvar;
 cleardevice;
 menu;{++Vizivaem menu++}
 nosound;
 closegraph;
end.
{------------------------/\MAIN PROGRAM/\-----------------------------}