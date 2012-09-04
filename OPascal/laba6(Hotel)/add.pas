unit add;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls, Buttons;

type
  TForm2 = class(TForm)
    Edit1: TEdit;
    Edit2: TEdit;
    Edit3: TEdit;
    Edit4: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    RadioGroup1: TRadioGroup;
    ComboBox1: TComboBox;
    RadioGroup2: TRadioGroup;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    Label5: TLabel;
    procedure BitBtn2Click(Sender: TObject);
    procedure BitBtn1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form2: TForm2;

implementation

uses mform;

{$R *.dfm}

procedure TForm2.BitBtn2Click(Sender: TObject);
begin
 self.Close;
 Edit1.Text:='';
 Edit2.Text:='';
 Edit3.Text:='';
 Edit4.Text:='';
 RadioGroup1.ItemIndex:=1;
 RadioGroup2.ItemIndex:=2;
 ComboBox1.ItemIndex:=0;
end;

procedure TForm2.BitBtn1Click(Sender: TObject);
begin
  Form1.Table1NOMER.Value := strtoint(Edit1.Text);;
  Form1.Table1FAM.Value := Edit2.Text;
  Form1.Table1IM.Value := Edit3.Text;
  Form1.Table1SROK.Value := strtoint(Edit4.Text);
  Form1.Table1POL.Value := RadioGroup1.Items.Strings[RadioGroup1.ItemIndex];
  Form1.Table1PLATA.Value := RadioGroup2.Items.Strings[RadioGroup2.ItemIndex];
  Form1.Table1TYPENOMER.Value := ComboBox1.Text;
  Form1.Table1.Post;
  Form2.Close;
end;

end.
