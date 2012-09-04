var a,s:integer;
    totalst,sot,des,ed:string;
procedure inputData;
begin
 repeat
  writeln('Vvedite 1000>A>100');
  readln(a);
 until (a>100) and (a<1000);
 repeat
  writeln('Vvedite padeg:1-im;2-rod;3-dat;4-tvor;5-predl');
  readln(s);
 until (s>0) and (s<6);
end;
procedure createSt;
var ch1,ch2,ch3:byte;
begin
 ch1:=a mod 10;
 a:=a div 10;
 ch2:=a mod 10;
 ch3:=a div 10;
 case ch3 of
            1:sot:='Sto ';
            2:sot:='Dvesti ';
            3:sot:='Trista ';
            4:sot:='Chetiresta ';
            5:sot:='Patsot ';
            6:sot:='Shestsot ';
            7:sot:='Semsot ';
            8:sot:='Vosemsot ';
            9:sot:='Devatsot ';
 end;
 case ch2 of
            0:des:='';
            1:begin
               case ch1 of
                          0:des:='Desat';
                          1:des:='Odinnadcat';
                          2:des:='Dvenadcat';
                          3:des:='Trinadcat';
                          4:des:='Chetirnidcat';
                          5:des:='Patnadcat';
                          6:des:='Shestnadcat';
                          7:des:='Semnadcat';
                          8:des:='Vosemnadcat';
                          9:des:='Devatnadcat';
              end;
             end;
            2:des:='Dvadcat ';
            3:des:='Tridcat ';
            4:des:='Sorok ';
            5:des:='Petdesat ';
            6:des:='Shestdesat ';
            7:des:='Semdesat ';
            8:des:='Vosemdesat ';
            9:des:='Devanosto ';
 end;
 if (ch2<>1) then
 begin
  case ch1 of
             0:ed:='';
             1:begin
                case s of
                          1:ed:='Odin';
                          2:ed:='Odnogo';
                          3:ed:='Odnomy';
                          4:ed:='Odnim';
                          5:ed:='Odnom';
                end;
               end;
             2:begin
                case s of
                          1:ed:='Dva';
                          2:ed:='Dvuh';
                          3:ed:='Dvum';
                          4:ed:='Dvoim';
                          5:ed:='Dvoih';
                end;
               end;
             3:begin
                case s of
                          1:ed:='Tri';
                          2:ed:='Treh';
                          3:ed:='Troim';
                          4:ed:='Trema';
                          5:ed:='Troih';
                end;
               end;
             4:begin
                case s of
                          1:ed:='Chetire';
                          2:ed:='Chetireh';
                          3:ed:='Chetirem';
                          4:ed:='Chetirima';
                          5:ed:='Chetverih';
                end;
               end;
             5:begin
                case s of
                          1:ed:='Pat';
                          2:ed:='Peterih';
                          3:ed:='Paterim';
                          4:ed:='Peti';
                          5:ed:='Peterih';
                end;
               end;
             6:begin
                case s of
                          1:ed:='Shest';
                          2:ed:='Shesterih';
                          3:ed:='Shesterim';
                          4:ed:='Shesti';
                          5:ed:='Shesterih';
                end;
               end;
             7:begin
                case s of
                          1:ed:='Sem';
                          2:ed:='Semerih';
                          3:ed:='Semerim';
                          4:ed:='Semi';
                          5:ed:='Semerih';
                end;
               end;
             8:begin
                case s of
                          1:ed:='Vosem';
                          2:ed:='Vosmerih';
                          3:ed:='Vosmerim';
                          4:ed:='Vosmi';
                          5:ed:='Vosmerih';
                end;
               end;
             9:begin
                case s of
                          1:ed:='Devat';
                          2:ed:='Devatogo';
                          3:ed:='Devaterim';
                          4:ed:='Devati';
                          5:ed:='Devaterih';
                end;
               end;
  end;
 end;
end;
procedure writeSt;
begin
 totalst:=sot+des+ed;
 writeln(totalst);
end;
begin
 inputData;
 createSt;
 writeSt;
 readln;
end.