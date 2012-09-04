unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

const und=0;
      pls=1;
      mns=2;
      mul=3;
      dvd=4;
type
  TForm1 = class(TForm)
    Edit1: TEdit;
    Button1: TButton;
    Button2: TButton;
    Button3: TButton;
    Button4: TButton;
    Button5: TButton;
    Button6: TButton;
    Button7: TButton;
    Button9: TButton;
    Button10: TButton;
    Button11: TButton;
    Button12: TButton;
    Button13: TButton;
    Button14: TButton;
    Button15: TButton;
    Button16: TButton;
    Button17: TButton;
    Button8: TButton;
    procedure Button7Click(Sender: TObject);
    procedure Button9Click(Sender: TObject);
    procedure Button10Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button6Click(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button11Click(Sender: TObject);
    procedure Edit1KeyPress(Sender: TObject; var Key: Char);
    procedure FormCreate(Sender: TObject);
    procedure Button17Click(Sender: TObject);
    procedure Button12Click(Sender: TObject);
    procedure Button13Click(Sender: TObject);
    procedure Button14Click(Sender: TObject);
    procedure Button15Click(Sender: TObject);
    procedure Button18Click(Sender: TObject);
    procedure Button8Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  d: String;
  optype : integer;
  num1,num2: real;

implementation

{$R *.dfm}

procedure addDigit(d:String);
begin
   if (Form1.Edit1.Text='0') then
     begin
      Form1.Edit1.Text:=d
     end
   else
     begin
      Form1.Edit1.Text:=Form1.Edit1.Text+d;
     end;
end;

procedure addSep();
var i:integer;
    hasSep:boolean;
    str: String;
begin
     str:=Form1.Edit1.Text;
     hasSep:=false;
     for i:=1 to length(str) do
     begin
      if (str[i]='.') then hasSep:=true;
     end;
     if (not(hasSep)) then str:=str+'.';
     Form1.Edit1.Text:=str;
end;

procedure TForm1.Button7Click(Sender: TObject);
begin
  addDigit('1');
end;

procedure TForm1.Button9Click(Sender: TObject);
begin
  addDigit('2');
end;

procedure TForm1.Button10Click(Sender: TObject);
begin
  addDigit('3');
end;

procedure TForm1.Button5Click(Sender: TObject);
begin
  addDigit('4');
end;

procedure TForm1.Button4Click(Sender: TObject);
begin
  addDigit('5');
end;

procedure TForm1.Button6Click(Sender: TObject);
begin
  addDigit('6');
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
  addDigit('7');
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  addDigit('8');
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
  addDigit('9');
end;

procedure TForm1.Button11Click(Sender: TObject);
begin
  addDigit('0');
end;

procedure TForm1.Edit1KeyPress(Sender: TObject; var Key: Char);
begin
   case (Key) of
       '1': addDigit('1');
       '2': addDigit('2');
       '3': addDigit('3');
       '4': addDigit('4');
       '5': addDigit('5');
       '6': addDigit('6');
       '7': addDigit('7');
       '8': addDigit('8');
       '9': addDigit('9');
       '0': addDigit('0');
       '.': addSep();
   end;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
optype:=und;
end;

procedure TForm1.Button17Click(Sender: TObject);
begin
  addSep();
end;

procedure TForm1.Button12Click(Sender: TObject);
var code:integer;
begin
  if (optype=und) then
  begin
   val(Form1.Edit1.Text,num1,code);
   optype:=pls;
   Form1.Edit1.Text:='0';
  end;
end;

procedure TForm1.Button13Click(Sender: TObject);
var code:integer;
begin
  if (optype=und) then
  begin
   val(Form1.Edit1.Text,num1,code);
   optype:=mns;
   Form1.Edit1.Text:='0';
  end;
end;

procedure TForm1.Button14Click(Sender: TObject);
var code:integer;
begin
  if (optype=und) then
  begin
   val(Form1.Edit1.Text,num1,code);
   optype:=mul;
   Form1.Edit1.Text:='0';
  end;
end;

procedure TForm1.Button15Click(Sender: TObject);
var code:integer;
begin
  if (optype=und) then
  begin
   val(Form1.Edit1.Text,num1,code);
   optype:=dvd;
   Form1.Edit1.Text:='0';
  end;
end;

procedure TForm1.Button18Click(Sender: TObject);
var result:extended;
    code: integer;
begin
  if (optype<>und) then
  begin
   val(Form1.Edit1.Text,num2,code);
   case (optype) of
     pls: result:=num1+num2;
     mns: result:=num1-num2;
     mul: result:=num1*num2;
     dvd: result:=num1/num2;
   end;
    Form1.Edit1.Text:=floattostr(result);
    optype:=und;
  end;
end;

procedure TForm1.Button8Click(Sender: TObject);
begin
  Form1.Edit1.Text:='0';
end;

end.

