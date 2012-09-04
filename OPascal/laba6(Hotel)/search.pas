unit search;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls,DB;

type
  TForm4 = class(TForm)
    ComboBox1: TComboBox;
    Label1: TLabel;
    Label2: TLabel;
    RadioGroup1: TRadioGroup;
    Button1: TButton;
    Button2: TButton;
    Edit1: TEdit;
    procedure Button2Click(Sender: TObject);
    procedure Button1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form4: TForm4;

implementation

uses mform;

{$R *.dfm}

procedure TForm4.Button2Click(Sender: TObject);
begin
self.close;
end;

procedure TForm4.Button1Click(Sender: TObject);
var key:String;
begin
  case (ComboBox1.ItemIndex) of
    0:begin
        key:='FAM';
      end;
    1:begin
        key:='NOMER';
      end;
    2:begin
        key:='PLATA';
      end;
    3:begin
        key:='TYPENOMER';
      end;
  end;
  if (RadioGroup1.ItemIndex=0) then
  begin
   if not Form1.Table1.Locate(key,Form4.Edit1.Text,[])
      then ShowMessage ('Запись не найдена');
  end
  else
  begin
      if not Form1.Table1.Locate(key,Form4.Edit1.Text,[loPartialKey])
      then ShowMessage ('Запись не найдена');
  end;
end;

end.
