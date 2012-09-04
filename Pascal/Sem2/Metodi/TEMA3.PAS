uses crt;
type data=record
           m:byte;
           d:byte;
          end;
     kard=record
          Name:string;
          Year:word;
            ID:word;
          case tip:byte of
               1:(author:string;
                  city:string;
                  izdvo:string;
                  pages:word);
               2:(tom:string;
                  number:word);
               3:(town:string;
                  dat:data);
     end;
var source:file of kard;
    Nowrec,CurrentRec:kard;
    NowString:string;
    isAccept,isRight,isNext:boolean;
    point,i:byte;
    count,countst:word;
procedure password;
begin
 writeln('Vvedite parol (esli vi ne znaete parol mozhete vvesti chto ugodno)');
 readln(NowString);
 clrscr;
 if (NowString='password') then isAccept:=true
 else isAccept:=false;
 if (isAccept) then writeln('Verniy parol, vi mozhete iskat i dobovlat izdania!')
 else writeln('Parol ne veren, vi mozhete tolko smotret izdania!');
 NowString:='';
 readln;
 clrscr;
end;
procedure searchBook;
begin
 assign(source,'sor.krd');
 reset(source);
 repeat
  isRight:=false;
  isNext:=false;
  {$I-}
  writeln('Vvedite tip izdania (1-kniga; 2-zhurnal; 3-gazeta; 4- prosmotret vse izdania):');
  repeat
   readln(point);
   if (ioresult<>0) or (point<1) or (point>4) then writeln('Vi mozhete vvodit tolko cifri ot 1 do 4 vkluchitelno');
  until (ioresult=0) and (point<5) and (point>0);
  {$I+}
  NowRec.tip:=point;
  CurrentRec.tip:=point;
  case point of
                1:begin
                   writeln('Vvedite nazvanie');
                   readln(NowRec.Name);
                   {$i-}
                   repeat
                    writeln('Vvedite god izdania');
                    readln(Nowrec.year);
                   until (ioresult=0);
                   writeln('Vvidite gorod');
                   readln(NowRec.city);
                   writeln('Vvedite avtora');
                   readln(NowRec.author);
                   writeln('Vvedite izdatelstvo');
                   readln(NowRec.izdvo);
                   repeat
                    writeln('Vvedite kol-vo stranic');
                    readln(NowRec.pages);
                   until (ioresult=0);
                   repeat
                    writeln('Vvedite ID izdania');
                    readln(NowRec.ID);
                   until (ioresult=0);
                   {$I+}
                   if (NowRec.name<>'') or (NowRec.year<>0) or
                   (NowRec.city<>'') or (NowRec.author<>'') or
                   (NowRec.izdvo<>'') or (NowRec.pages<>0) or
                   (NowRec.ID<>0) then
                   isRight:=true;
                  end;
                2:begin
                   writeln('Vvedite nazvanie');
                   readln(NowRec.Name);
                   {$I-}
                   repeat
                    writeln('Vvedite god izdania');
                    readln(Nowrec.year);
                   until (ioresult=0);
                   writeln('Vvedite Tom');
                   readln(NowRec.tom);
                   repeat
                    writeln('Vvedite nomer');
                    readln(NowRec.number);
                   until (ioresult=0);
                   repeat
                    writeln('Vvedite ID izdania');
                    readln(NowRec.ID);
                   until (ioresult=0);
                   {$I+}
                   if (NowRec.name<>'') or (NowRec.year<>0) or
                   (NowRec.Tom<>'') or (NowRec.Number<>0) or
                   (NowRec.ID<>0) then
                   isRight:=true;
                  end;
                3:begin
                   writeln('Vvedite nazvanie');
                   readln(NowRec.Name);
                   writeln('Vvedite Gorod');
                   readln(NowRec.town);
                   {$I-}
                   repeat
                    writeln('Vvedite god izdania');
                    readln(NowRec.year);
                   until (ioresult=0);
                   repeat
                    writeln('Vvedite mesac izdania');
                    readln(NowRec.dat.m);
                   until (ioresult=0);
                   repeat
                    writeln('Vvedite den izdania');
                    readln(NowRec.dat.d);
                   until (ioresult=0);
                   repeat
                    writeln('Vvedite ID izdania');
                    readln(NowRec.ID);
                   until (ioresult=0);
                   {$I+}
                   if (NowRec.name<>'') or (NowRec.town<>'') or
                   (NowRec.year<>0) or (NowRec.dat.m<>0) or
                   (NowRec.dat.d<>0) or (NowRec.ID<>0) then
                   isRight:=true;
                  end;
                4:begin
                   countst:=0;
                   for i:=1 to 3 do
                   begin
                    writeln;
                    case i of
                              1:begin writeln('Knigi:');end;
                              2:begin writeln('Zhurnali:');end;
                              3:begin writeln('Gazeti:');end;
                    end;
                    seek(source,0);
                    count:=0;
                    while not(eof(source)) do
                    begin
                     read(source,CurrentRec);
                     if (countst=25) and (not(eof(source))) then
                     begin
                      writeln('Dla prodolzhenia nazhmite lubuu klavishu');
                      readln;
                      clrscr;
                      countst:=0;
                      writeln;
                      case i of
                                1:writeln('Knigi:');
                                2:writeln('Zhurnali:');
                                3:writeln('Gazeti:')
                      end;
                     end;
                     if (CurrentRec.tip=1) and (i=1) then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.city,'-');
                      write(CurrentRec.author,'-');
                      write(CurrentRec.izdvo,'-');
                      write(CurrentRec.pages,'// ');
                      write('Unikalniy ID izdania: ',CurrentRec.id);
                      writeln;
                     end;
                     if (CurrentRec.tip=2) and (i=2) then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.Tom,'-');
                      write(CurrentRec.Number,'// ');
                      write('Unikalniy ID izdania: ',CurrentRec.id);
                      writeln;
                     end;
                     if (CurrentRec.tip=3) and (i=3) then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.town,'-');
                      write(CurrentRec.dat.m,'-');
                      write(CurrentRec.dat.d,'// ');
                      write('Unikalniy ID izdania: ',CurrentRec.id);
                      writeln;
                     end;
                    end;
                    end;
                  end;
  end;
  if (isRight) then
  begin
  writeln('Zaprosu otvechut sldeuushie izdania:');
  writeln;
  seek(source,0);
  count:=0;
  countst:=0;
   while not(eof(source)) do
   begin
    read(source,CurrentRec);
    if (countst>25) and (not(eof(source))) then
    begin
     writeln('Dla prodolzhenia nazhmite lubuu klavishu');
     readln;
     clrscr;
     countst:=0;
     writeln;
    end;
    case point of
                  1:begin
                     if ((NowRec.tip=CurrentRec.tip) and
                        ((NowRec.name=CurrentRec.name) or
                        (NowRec.year=CurrentRec.year) or
                        (NowRec.city=CurrentRec.city) or
                        (NowRec.author=CurrentRec.author) or
                        (NowRec.izdvo=CurrentRec.izdvo) or
                        (NowRec.pages=Currentrec.pages) or
                        (NowRec.Id=CurrentRec.id)))  then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.city,'-');
                      write(CurrentRec.author,'-');
                      write(CurrentRec.izdvo,'-');
                      write(CurrentRec.pages,'// ');
                      writeln('Unikalniy ID izdania: ',CurrentRec.id)
                     end;
                    end;
                  2:begin
                     if ((NowRec.tip=CurrentRec.tip) and
                        ((NowRec.name=CurrentRec.name) or
                        (NowRec.year=CurrentRec.year) or
                        (NowRec.Tom=CurrentRec.Tom) or
                        (NowRec.Number=CurrentRec.Number) or
                        (NowRec.Id=CurrentRec.id))) then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.Tom,'-');
                      write(CurrentRec.Number,'// ');
                      writeln('Unikalniy ID izdania: ',CurrentRec.id)
                     end;
                    end;
                  3:begin
                     if ((NowRec.tip=CurrentRec.tip) and
                        ((NowRec.name=CurrentRec.name) or
                        (NowRec.year=CurrentRec.year) or
                        (NowRec.town=CurrentRec.town) or
                        (NowRec.dat.m=CurrentRec.dat.m) or
                        (NowRec.dat.d=CurrentRec.dat.d) or
                        (NowRec.Id=CurrentRec.id)))
                        then
                     begin
                      count:=count+1;
                      inc(countst);
                      write('#',count,' ',CurrentRec.name,'-');
                      write(CurrentRec.year,'-');
                      write(CurrentRec.town,'-');
                      write(CurrentRec.dat.m,'-');
                      write(CurrentRec.dat.d,'// ');
                      writeln('Unikalniy ID izdania: ',CurrentRec.id)
                     end;
                    end;
    end;
   end;
   if (count=0) then writeln('Net izdaniy otvachaushih zaprosy');
  end
  else if (point<>4) then writeln('Vi ne vveli ni odnogo polia poiska');
  writeln;
  repeat
   writeln('Vi hotite naiti eshe izdanie?(Y)es/(N)o');
   readln(NowString);
  until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
  if (NowString='y') then isNext:=true
  else isNext:=false;
  clrscr;
  NowString:='';
 until not(isNext);
 textcolor(15);
end;
procedure menuSotr;
 procedure addBook;
 var size:word;
 begin
 assign(source,'sor.krd');
 reset(source);
 size:=FileSize(source);
 seek(source,size);
 repeat
  isRight:=true;
  isNext:=false;
  {$I-}
  writeln('Vvedite tip izdania (1-kniga; 2-zhurnal; 3-gazeta):');
  repeat
   readln(point);
   if (ioresult<>0) or (point<1) or (point>3) then writeln('Vi mozhete vvodit tolko cifri ot 1 do 3 vkluchitelno');
  until (ioresult=0) and (point>0) and (point<4);
  {$I+}
  NowRec.tip:=point;
  case point of
                1:begin
                   {$I-}
                   repeat
                    writeln('Vvedite nazvanie');
                    readln(NowRec.Name);
                   until (NowRec.Name<>'');
                   repeat
                    writeln('Vvedite god izdania');
                    readln(Nowrec.year);
                   until (ioresult=0) and (NowRec.year>=1900) and (NowRec.year<=2004);
                   repeat
                    writeln('Vvidite gorod');
                    readln(NowRec.city);
                   until (NowRec.city<>'');
                   repeat
                    writeln('Vvedite avtora');
                    readln(NowRec.author);
                   until (NowRec.author<>'');
                   repeat
                    writeln('Vvedite izdatelstvo');
                    readln(NowRec.izdvo);
                   until (NowRec.izdvo<>'');
                   repeat
                    writeln('Vvedite kol-vo stranic');
                    readln(NowRec.pages);
                   until (NowRec.pages>=0) and (NowRec.pages<=2500) and (ioresult=0);
                   repeat
                    writeln('Vi uvereni, chto hotite dobavit knigu: (Y)es/(N)o');
                    writeln('Nazvanie: ',NowRec.name);
                    writeln('God: ',NowRec.year);
                    writeln('Gorod: ',NowRec.city);
                    writeln('Avtor: ',NowRec.author);
                    writeln('Izdatelstvo: ',NowRec.izdvo);
                    writeln('Kol-vo stranic: ',NowRec.pages);
                    readln(NowString);
                   until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
                   if (NowString='No') or (NowString='no') or
                      (NowString='N')  or (NowString='n') then isRight:=false;
                  end;
                2:begin
                   repeat
                    writeln('Vvedite nazvanie');
                    readln(NowRec.Name);
                   until (NowRec.Name<>'');
                   repeat
                    writeln('Vvedite god izdania');
                    readln(Nowrec.year);
                   until (ioresult=0) and (NowRec.year>=1900) and (NowRec.year<=2004);
                   repeat
                    writeln('Vvedite Tom');
                    readln(NowRec.tom);
                   until (NowRec.Tom<>'');
                   repeat
                    writeln('Vvedite nomer');
                    readln(NowRec.number);
                   until (ioresult=0) and (NowRec.number>=0) and (NowRec.number<=10000);
                   repeat
                    writeln('Vi uvereni, chto hotite dobavit zhurnal: (Y)es/(N)o');
                    writeln('Nazvanie: ',NowRec.name);
                    writeln('God: ',NowRec.year);
                    writeln('Tom: ',NowRec.Tom);
                    writeln('Nomer: ',NowRec.number);
                    readln(NowString);
                   until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
                   if (NowString='No') or (NowString='no') or
                      (NowString='N')  or (NowString='n') then isRight:=false;
                  end;
                3:begin
                   repeat
                    writeln('Vvedite nazvanie');
                    readln(NowRec.Name);
                   until (NowRec.name<>'');
                   repeat
                    writeln('Vvedite Gorod');
                    readln(NowRec.town);
                   until (NowRec.town<>'');
                   repeat
                    writeln('Vvedite god izdania');
                    readln(NowRec.year);
                   until (ioresult=0) and (NowRec.year>=1900) and (NowRec.year<=2004);
                   repeat
                    writeln('Vvedite mesac izdania');
                    readln(NowRec.dat.m);
                   until (ioresult=0) and (NowRec.dat.m>=1) and (NowRec.dat.m<=12);
                   repeat
                    writeln('Vvedite den izdania');
                    readln(NowRec.dat.d);
                   until (ioresult=0) and (NowRec.dat.d>=1) and (NowRec.dat.d<=31);
                   repeat
                    writeln('Vi uvereni, chto hotite dobavit gazetu: (Y)es/(N)o');
                    writeln('Nazvanie: ',NowRec.name);
                    writeln('Gorod: ',NowRec.town);
                    writeln('God: ',NowRec.year);
                    writeln('Mesac: ',NowRec.dat.m);
                    writeln('Den: ',NowRec.dat.d);
                    readln(NowString);
                   until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
                   if (NowString='No') or (NowString='no') or
                      (NowString='N')  or (NowString='n') then isRight:=false;
                  end;
  end;
  if (isRight) then
  begin
   size:=FileSize(source);
   seek(source,size);
   NowRec.ID:=size+2;
   write(source,NowRec);
   writeln('Unikalniy ID izdania: ',Nowrec.id);
  end
  else writeln('Vi otkazalis ot dobovlenia izdania');
  repeat
   writeln('Vi hotite dobavit eshe izdanie?(Y)es/(N)o');
   readln(NowString);
  until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
  if (NowString='y') then isNext:=true
  else isNext:=false;
  clrscr;
  {$I-}
 until not(isNext);
 close(source);
 textcolor(15);
 end;
 procedure deleteBook;
  var temp:file of kard;
 begin
  assign(temp,'tmp.krd');
  assign(source,'sor.krd');
  {$I-}
  reset(source);
  rewrite(temp);
  repeat
   writeln('Vvedite unikalniy ID izdania');
   readln(NowRec.Id);
  until (ioresult=0) and (NowRec.ID<=filesize(source)+1);
  repeat
   writeln('Vi uverenni, chto hotite udalit izdanie ID#',NowRec.ID,'? (Y)es/(N)o');
   readln(NowString);
  until (NowString='Y') or (NowString='y') or (NowString='N') or (NowString='n');
  if (NowString='Y')   or (NowString='y') then
  begin
   while not(eof(source)) do
   begin
    read(source,CurrentRec);
    if (CurrentRec.ID<>NowRec.ID) then write(temp,CurrentRec)
    else begin isRight:=true;writeln('Izdanie id#',NowRec.id,' udaleno!');end;
   end;
   {$i+}
  erase(source);
  rename(temp,'sor.krd');
  close(temp);
 end
 else writeln('Vi otkazalis ot udalenia izdania');
 textcolor(15);
 readln;
 end;
 begin
  repeat
   {$I-}
   writeln('Vi mozhete provesti sleduushie deistvia:');
   repeat
    writeln('1 - naiti izdanie');
    writeln('2 - dobavit izdanie');
    writeln('3 - udolit izdanie');
    writeln('4 - Viiti');
    readln(point);
    if (ioresult<>0) or (point<1) or (point>4) then writeln('Mozhno vvodit tolko cifri ot 1 do 3 vkluchitelno');
   until (ioresult=0) and (point>=1) and (point<=4);
   {$I+}
   case point of
                1:begin searchBook;clrscr;end;
                2:begin addBook;clrscr;end;
                3:begin deleteBook;clrscr;end;
                4:begin
                   writeln('Goodbye');
                   delay(32000);
                   textbackground(0);
                   textcolor(7);
                   clrscr;
                   halt;
                  end;
   end;
   clrscr;
  until (false);
end;
procedure menuKL;
begin
 repeat
  {$I-}
  writeln('Vi mozhete proizvesti sleduushie deistvia:');
  repeat
   writeln('1 - naiti knigu');
   writeln('2 - vvesti parol');
   writeln('3 - viiti');
   readln(point);
   if (ioresult<>0) or (point<1) or (point>3) then writeln('Mozhno vvodit tolko cifri ot 1 do 3 vkluchitelno');
  until (ioresult=0) and (point>=1) and (point<=3);
  {$I+}
  case point of
               1:begin searchBook;clrscr;end;
               2:begin password; if (isAccept) then begin clrscr;menuSotr;end;end;
               3:begin
                  writeln('Goodbye');
                  delay(32000);
                  textbackground(0);
                  textcolor(7);
                  clrscr;
                  halt;
                 end;
  end;
 until (false);
end;
begin
 textbackground(3);
 textcolor(15);
 clrscr;
 password;
 if (isAccept) then menuSotr
 else menuKl;
 close(source);
end.