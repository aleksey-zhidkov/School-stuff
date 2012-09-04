var st:string;
    dl:byte;
procedure inputString;
begin
 repeat
  writeln('Vvedite stroky');
  readln(st);
  writeln;
  dl:=length(st);
 until dl=0;
end;
procedure searchWord;
var i,j,k,kod:byte;
    patternWord,nowWord:string;
begin
 i:=0;
 for i:=97 to 123 do
 begin
  j:=0;
  for j:=2 to dl do
   begin
   kod:=ord(st[j]);
   if (st[j-1]=' ') or (st[j-1]=fdsdfsfsd


   fds',') and
 end;
end;