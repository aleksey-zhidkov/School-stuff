uses crt;
var ch:string;
    c:char;
begin
 repeat
  c:=readkey;
  writeln(ord(c));
  readln(ch);
 until (ch='Parol');
end.