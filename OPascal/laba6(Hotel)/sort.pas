unit sort;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls;

type
  TForm3 = class(TForm)
    RadioGroup1: TRadioGroup;
    Button1: TButton;
    Button2: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form3: TForm3;

implementation

uses mform;

{$R *.dfm}

procedure TForm3.Button1Click(Sender: TObject);
begin
Case (RadioGroup1.ItemIndex) of
    0:Form1.Table1.IndexName:='NOMER';
    1:Form1.Table1.IndexName:='FAM';
    2:Form1.Table1.IndexName:='TYPENOMER';
    3:Form1.Table1.IndexName:='SROK';
    4:Form1.Table1.IndexName:='PLATA';
  end;
end;

procedure TForm3.Button2Click(Sender: TObject);
begin
self.Hide;
end;

end.
