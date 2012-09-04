var a,l,k,y1,y2,e:real;
begin
     writeln('Введите a');
     readln(a);
     writeln('Введите e');
     readln(e);
     l:=0;
     k:=0;
     y1:=a;
     repeat
           y2:=a-y1/5;
           k:=k+1;
           if abs(y2-y1)<e then l:=k;
           y1:=y2
     until l<>0;
     writeln(l:0:3);
     readln;
end.