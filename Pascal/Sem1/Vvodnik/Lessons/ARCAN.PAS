uses graph,crt;
var gd,gm: integer;
    p1,p2: pointer;
    xsh1,ysh1,shx,shy,xsh2,ysh2:integer;
    yr1,xr1,xr2,yr2:integer;
    key: char;
    namef: string;
function loader(namef:string) : pointer;
var fl: file;
    size : integer;
    p: pointer;
begin
 assign(fl,namef);
 reset(fl,1);
 size:=filesize(fl);
 getmem(p,size);
 blockread(fl,p^,size);
 close(fl);
 loader:=p;
end;
procedure picture;
 begin
  p1:=loader('shar');
  p2:=loader('arkrak');
 end;
procedure neupr;
 begin
  delay(1000);
  putimage(xsh1,ysh1,p1^,1);
  xsh2:=xsh2+shx;
  ysh2:=ysh2+shx;
  if ysh2<10 then shy:=1;
  if ysh2>440 then shx:=-1;
  if xsh2<10 then shx:=1;
  if xsh2>630 then shx:=-1;
  putimage(xsh2,ysh2,p1^,1);
  xsh1:=xsh2;
  ysh1:=ysh2;
 end;
procedure upr;
 begin
  putimage(xr1,470,p2^,1);
  key:=readkey;
  yr2:=470;
  if key=#0 then key:=readkey;
  case key of
   #75:begin xr2:=xr2-5; if xr2<2 then xr2:=2;end;
   #77:begin xr2:=xr2+5; if xr2>630 then xr2:=620;end;
  end;
  putimage(xr2,yr2,p2^,1);
  xr1:=xr2;
  while keypressed do readkey;
 end;
begin
 gd:=detect;
 initgraph(gd,gm,'');
 shx:=2;
 shy:=2;
 xsh1:=random(500)+100;
 ysh1:=random(300)+50;
 xr1:=320;
 yr1:=470;
 xr2:=xr1;
 yr2:=yr1;
 picture;
 xsh1:=random(640);
 ysh1:=random(200);
 putimage(xsh1,ysh1,p1^,1);
 putimage(xr1,yr1,p2^,1);
 repeat
  neupr;
  if keypressed then upr;
 until key = #27;
 closegraph;
end.














