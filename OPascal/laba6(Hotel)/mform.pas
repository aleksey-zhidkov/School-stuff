unit mform;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Grids, DBGrids, DB, DBTables, StdCtrls;

type
  TForm1 = class(TForm)
    DataSource1: TDataSource;
    Table1: TTable;
    DBGrid1: TDBGrid;
    Button1: TButton;
    Button2: TButton;
    Button4: TButton;
    Button5: TButton;
    Database1: TDatabase;
    Table1NOMER: TIntegerField;
    Table1FAM: TStringField;
    Table1IM: TStringField;
    Table1POL: TStringField;
    Table1TYPENOMER: TStringField;
    Table1SROK: TIntegerField;
    Table1PLATA: TStringField;
    Button3: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses add, sort, search;

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
begin
 Table1.Append;
 Form2.Show;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
 Table1.Edit;
 Form2.Edit1.Text := inttostr(Table1NOMER.Value);
 Form2.Edit2.Text := Table1FAM.Value;
 Form2.Edit3.Text := Table1IM.Value;
 Form2.Edit4.Text := inttostr(Table1SROK.Value);
 if (Table1POL.Value='мужской') then Form2.RadioGroup1.ItemIndex:=0
 else Form2.RadioGroup1.ItemIndex:=1;
 if (Table1PLATA.Value='наличными') then Form2.RadioGroup2.ItemIndex:=0
 else if (TABLE1PLATA.Value='кредитная карточка') then Form2.RadioGroup2.ItemIndex:=1
 else Form2.RadioGroup1.ItemIndex:=2;
 if (Table1TYPENOMER.Value='одноместный') then Form2.ComboBox1.ItemIndex:=0
 else if (Table1TYPENOMER.Value='двуместный') then Form2.ComboBox1.ItemIndex:=1
 else Form2.ComboBox1.ItemIndex:=2;
 Form2.show;
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
 Table1.Edit;
 Table1.Delete;
end;

procedure TForm1.Button4Click(Sender: TObject);
begin
Form3.show;
end;

procedure TForm1.Button5Click(Sender: TObject);
begin
  Form4.show;
end;

end.
