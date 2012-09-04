var st,patternWord,totalSt:string;
    dlst:byte;
procedure inputString;
begin
 repeat
  writeln('Vvedite stroku okanchivaeshuusia tochkoi');
  readln(st);
  dlst:=length(st);
 until (dlst<>0) and (st[dlst]='.')
end;
procedure createString;
var i,j,k,ns,nal,ch,dlpw,dltst:integer;
    copyst:string;
begin
 i:=0;
 ns:=1;
 ch:=0;
 nal:=0;
 for i:=1 to dlst do
  begin
  copyst:=st;
  if ((copyst[i-1]=' ') or (copyst[i-1]=',') or (copyst[i-1]='.')) and
     ((copyst[i]<>' ') or (copyst[i]<>',') or (copyst[i]<>'.')) then ns:=i;
  if ((copyst[i+1]=' ') or (copyst[i+1]=',') or (copyst[i+1]='.')) and
     ((copyst[i]<>' ') or (copyst[i]<>',') or (copyst[i]<>'.')) then
  begin
   k:=i;
   for j:=ns to k do
    patternWord:=patternWord+copyst[j];
   dlpw:=length(patternWord);
   repeat
    nal:=pos(patternWord,copyst);
    if (nal<>0) then
    begin
     delete(copyst,nal,dlpw);
     ch:=ch+1;
    end;
   until nal=0;
   repeat
    nal:=pos(patternword,totalst);
    if (nal<>0) and (ch>1) then delete(totalst,nal,dlpw);
   until nal=0;
   if ch>1 then begin totalst:=totalst+(patternWord)+' ';end;
   patternWord:='';
   ns:=0;
   ch:=0;
  end;
 end;
 i:=1;
 repeat
  delete(totalst,i,1);
 until (totalst[i]<>' ');
 dltst:=length(totalst);
 if (dltst=0) then writeln ('V stroke net povtoriaushihsia slov')
 else writeln(totalst);
end;
begin
 repeat
  inputString;
  createString;
 until (st<>'exit');
 readln;
end.