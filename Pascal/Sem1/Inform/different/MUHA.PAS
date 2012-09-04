const
 st:string='muha po polu poshla, muha denushku nashla, muha na bazar poshla';
 podst1:string='muha';
 podst2:string='slon';
var i,j,k:integer;
begin
 i:=0;
 j:=0;
 k:=length(podst1);
 repeat
  i:=pos(podst1,st);
  if i<>0 then
  begin
   delete(st,i,k);
   insert(podst2,st,i);
   j:=j+1;
  end;
 until i=0;
 writeln(st);
 writeln('Slovo muha vstrechaetsa ',j,' raz');
 readln;
end.
