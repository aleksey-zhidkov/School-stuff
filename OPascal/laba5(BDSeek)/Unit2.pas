unit Unit2;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, Mask, DBCtrls;

type
  TForm2 = class(TForm)
    Label1: TLabel;
    DBEdit1: TDBEdit;
    Label2: TLabel;
    DBEdit2: TDBEdit;
    Label3: TLabel;
    DBEdit3: TDBEdit;
    Label4: TLabel;
    DBEdit4: TDBEdit;
    Button1: TButton;
    Button2: TButton;
    Label5: TLabel;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form2: TForm2;

implementation

uses bd;

{$R *.dfm}

procedure TForm2.Button1Click(Sender: TObject);
begin
  form1.Table1.Post;
  self.Hide;
end;

procedure TForm2.Button2Click(Sender: TObject);
begin
  form1.Table1.Cancel;
  form2.Hide;
end;

end.
