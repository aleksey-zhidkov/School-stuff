uses crt;
var a2,b4,c5,d10,e20,x:real;
begin
 writeln('vvedite x'); read(x);
 a2:=x*x;
 b4:=a2*a2;
 c5:=b4*x;
 d10:=c5*c5;
 e20:=d10*d10;
 writeln('chislo v 4 stepeni= ', b4:10:3, ' chislo v 20 stepeni=', e20:10:3);
 readln;
end.