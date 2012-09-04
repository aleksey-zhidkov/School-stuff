unit Test;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, StdCtrls;

type
  TForm1 = class(TForm)
    OpenDialog1: TOpenDialog;
    MainMenu1: TMainMenu;
    N1: TMenuItem;
    N2: TMenuItem;
    N3: TMenuItem;
    point: TLabel;
    Quest: TLabel;
    RadioButton1: TRadioButton;
    RadioButton2: TRadioButton;
    RadioButton3: TRadioButton;
    RadioButton4: TRadioButton;
    RadioButton5: TRadioButton;
    Button1: TButton;
    Label1: TLabel;
    Memo1: TMemo;
    Label2: TLabel;
    N4: TMenuItem;
    procedure N3Click(Sender: TObject);
    procedure N2Click(Sender: TObject);
    procedure init(Sender: TObject);
    function substring(str:string; sindex,findex:byte):string;
    procedure setQuestion(cqn,tn:byte);
    procedure Button1Click(Sender: TObject);
    procedure N4Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;
type
  question = record
     theory:string;
     question:string;
     answer1,answer2,answer3,answer4,answer5:string;
     ranswer:byte;
  end;
var
  Form1: TForm1;
  list: array of question;
  maxQnum,curQnum,trynum:byte;
  pointsNeed:real;
  pointsHave:real;
  source:TextFile;

implementation

{$R *.dfm}

procedure TForm1.N3Click(Sender: TObject);
begin
 Application.Terminate;
end;

procedure TForm1.N2Click(Sender: TObject);
var path,str:string;
    i:byte;
begin
 getdir(0,path);
 OpenDialog1.FileName:=path;
 if (OpenDialog1.Execute) then
 begin
  path:=OpenDialog1.FileName;
  AssignFile(source,path);
  Reset(source);
  readln(source,str);
  while (str<>'[GENERAL]') do readln(source,str);
  while (str<>'qnum') do readln(source,str);
  readln(source,str);
  maxQnum:=strtoint(str);
  setLength(list,(maxQnum*3));
  while (str<>'pneed') do readln(source,str);
  readln(source,str);
  pointsNeed:=strtoint(str);

  for i:=0 to (maxQnum*3)-1 do
  begin
   while (str<>'theory')and(not(eof(source))) do readln(source,str);

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='quest') then break;
     list[i].theory:=list[i].theory+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='answer1') then break;
     list[i].question:=list[i].question+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='answer2') then break;
     list[i].answer1:=list[i].answer1+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='answer3') then break;
     list[i].answer2:=list[i].answer2+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='answer4') then break;
     list[i].answer3:=list[i].answer3+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='answer5') then break;
     list[i].answer4:=list[i].answer4+str;
   end;

   while not(eof(source)) do
   begin
     readln(source,str);
     if (str='ranswer') then break;
     list[i].answer5:=list[i].answer5+str;
   end;

   readln(source,str);
   list[i].ranswer:=strtoint(str);
  end;
  Button1.Enabled:=true;
  N4.Enabled:=true;
  setQuestion(curQnum,trynum);
 end;
end;

procedure TForm1.setQuestion(cqn,tn:byte);
begin
  if (cqn=maxQnum) then
  begin
    Button1.Enabled:=false;
    Quest.caption:='Вопрос: '+inttostr(cqn)+'/'+inttostr(maxQnum);
    Point.caption:='Баллы: '+floattostr(pointsHave)+'/'+floattostr(pointsNeed);
    Label1.caption:='Попытка: '+inttostr(tn+1)+'/'+inttostr(3);
    Memo1.Clear;
    if (pointsHave>=pointsNeed) then
      Memo1.Lines.Add('Поздровляем! Вы прошли тест')
    else
      Memo1.Lines.Add('Вы не прошли тест. Попробуйте ещё.');
  end
  else
  begin
    Quest.caption:='Вопрос: '+inttostr(cqn+1)+'/'+inttostr(maxQnum);
    Point.caption:='Баллы: '+floattostr(pointsHave)+'/'+floattostr(pointsNeed);
    Label1.caption:='Попытка: '+inttostr(tn+1)+'/'+inttostr(3);
    Memo1.Clear;
    Memo1.Lines.Add(list[cqn*3+tn].theory);
    label2.Caption:=list[cqn*3+tn].question;
    RadioButton1.Caption:=list[cqn*3+tn].answer1;
    RadioButton2.Caption:=list[cqn*3+tn].answer2;
    RadioButton3.Caption:=list[cqn*3+tn].answer3;
    RadioButton4.Caption:=list[cqn*3+tn].answer4;
    RadioButton5.Caption:=list[cqn*3+tn].answer5;
  end;
end;

procedure TForm1.init(Sender: TObject);
begin
  maxQnum:=0;
  curQnum:=0;
  trynum:=0;
  pointsNeed:=0;
  pointsHave:=0;
end;

function TForm1.substring(str:string; sindex,findex:byte):string;
var i:byte;
    res:string;
begin
 res:='';
 for i:=sindex to findex do
  res:=res+str[i];
 result:=res;
end;
procedure TForm1.Button1Click(Sender: TObject);
var isRight:boolean;
begin
  isRight:=false;
  case (list[curQnum*3+trynum].ranswer) of
    1: if (RadioButton1.Checked) then isRight:=true;
    2: if (RadioButton2.Checked) then isRight:=true;
    3: if (RadioButton3.Checked) then isRight:=true;
    4: if (RadioButton4.Checked) then isRight:=true;
    5: if (RadioButton5.Checked) then isRight:=true;
  end;
  if (isRight) then
  begin
    case (trynum) of
      0: pointsHave:=pointsHave+5;
      1: pointsHave:=pointsHave+4;
      2: pointsHave:=pointsHave+3;
    end;
    curQnum:=curQnum+1;
    trynum:=0;
  end
  else
  begin
    if (trynum<2) then trynum:=trynum+1
    else
    begin
      curQnum:=curQnum+1;
      trynum:=0;
    end;
  end;
  setQuestion(curQnum,trynum);
end;

procedure TForm1.N4Click(Sender: TObject);
begin
  trynum:=0;
  curQnum:=0;
  pointsHave:=0;
  setQuestion(curQnum,trynum);
  Button1.Enabled:=true;
end;

end.
