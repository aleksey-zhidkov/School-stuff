var fbase,fnew:text;
    stbase,stnew,dlst,nomst,eltst:string;
    nom,dl,elt,i:integer;
procedure openFile;
begin
 assign(fbase,'c:/bp/bin/work/kurs1/inform//tema10j/fbase.txt');
 reset(fbase);
 assign(fnew,'c:/bp/bin/work/kurs1/inform/tema10j/fnew.txt');
 rewrite(fnew);
end;
procedure readFbase;
begin
 readln(fbase,stbase);
 nom:=nom+1;
end;
procedure modifyString;
begin
 elt:=1;
 dl:=length(stbase);
 for i:=2 to dl do
 begin
  if ((stbase[i-1]=',') or (stbase[i-1]='.') or (stbase[i-1]=' ')) and
  ((stbase[i]<>',') or (stbase[i]<>'.') or (stbase[i]<>' '))
  then elt:=elt+1;
 end;
 str(dl,dlst);
 str(nom,nomst);
 str(elt,eltst);
 stnew:='Nomer:'+nomst+'; Dlina:'+dlst+';Kol-vo eltov:'+eltst+' '+stbase;
end;
procedure writeFnew;
begin
 writeln(fnew,stnew);
end;
procedure readFnew;
begin
 assign(fnew,'c:/bp/bin/work/kurs1/inform/tema10j/fnew.txt');
 reset(fnew);
 repeat
  readln(fnew,stnew);
  writeln(stnew);
 until eof(fnew);
end;
begin
 openfile;
 nom:=0;
 repeat
  readFbase;
  modifyString;
  writeFnew;
 until eof(fbase);
 close(fbase);
 close(fnew);
 readFnew;
 close(fnew);
 readln;
end.




