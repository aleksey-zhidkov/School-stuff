uses crt,graph;
var i,x,y,xc,yc,smi,gd,gm:integer;
    fi:real;
    s:string;
begin
 gD:=Detect;
 InitGraph(gD, gM, '');
 xc:=320;
 yc:=300;
 smi:=180;
 fi:=0;
 for i:=0 to 360 do
 begin
  x:=round(xc+(cos((2*3.14*i)/360)*170));
  y:=round(yc+(sin((2*3.14*i)/360)*150-smi));
  putpixel(x,y,4);
  x:=round(xc+(cos((2*3.14*i)/360)*140));
  y:=round(yc+(sin((2*3.14*i)/360)*120-smi));
  putpixel(x,y,1);
  if (i<180) then dec(smi)
  else inc(smi);
  fi:=fi+0.5;
 end;
 readkey;
 fi:=0;
 for i:=0 to 360 do
 begin
  x:=round(xc+(cos((2*3.14*i)/360)*155));
  y:=round(yc+(sin((2*3.14*i)/360)*135-smi));
  setcolor(6);
  circle(x,y,11);
  delay(2000);
  setcolor(0);
  circle(x,y,11);
  if (i<180) then dec(smi)
  else inc(smi);
  if (i=360) then begin i:=0;smi:=180;end;
  if keypressed then i:=360;
  fi:=fi+0.5;
 end;
 readln;
end.