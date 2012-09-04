var stc:char;
    fn:file of integer;
    fc:file of char;
    nst,code:integer;
    stn:integer;
procedure readNumber;
begin
  read(fn,stn);
  write(stn,' ');
end;
procedure readChar;
begin
 read(fc,stc);
 writeln(stc);
end;
begin
 assign(fn,'c:/bp/bin/work/kurs1/inform/tema10j/numbers.num');
 reset(fn);
 assign(fc,'c:/bp/bin/work/kurs1/inform/tema10j/char.chr');
 reset(fc);
 repeat
  readNumber;
{  seek(fc,stn);
  readChar;}
 until eof(fn);
 readln;
 close(fc);
 close(fn);
end.