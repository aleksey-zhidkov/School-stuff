var mnx,mny,mnz:set of byte;
    fx,fy,fz:file of byte;
    x,y,z:byte;
procedure openfile;
begin
 assign(fx,'c:/bp/bin/work/kurs1/inform/tema10j/x.num');
 reset(fx);
 assign(fy,'c:/bp/bin/work/kurs1/inform/tema10j/y.num');
 reset(fy);
 assign(fz,'c:/bp/bin/work/kurs1/inform/tema10j/z.num');
 rewrite(fz);
end;
procedure readxy;
begin
 write('X: ');
 repeat
  read(fx,x);
 { include(mnx,x);}
  write(x,' ');
 until eof(fx);
{ writeln;
 write('Y: ');
 repeat
  read(fy,y);
  include(mny,y);
  write(y,' ');
 until eof(fy);
 mnz:=mnx+mny;
 repeat
  repeat
   z:=random(256);
  until (z in mnz);
  write(z,' ');
  exclude(mnz,z);
 until (mnz=[]);}
end;
begin
 openfile;
 readxy;
 {createz;}
 readln;
end.



