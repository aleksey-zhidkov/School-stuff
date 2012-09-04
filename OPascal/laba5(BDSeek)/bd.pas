unit bd;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, DB, DBTables, Grids, DBGrids, StdCtrls, ExtCtrls;

type
  TForm1 = class(TForm)
    DataSource1: TDataSource;
    Table1: TTable;
    Button1: TButton;
    DBGrid1: TDBGrid;
    Table1FIO: TStringField;
    Table1GRP: TStringField;
    Table1PASS: TStringField;
    Table1ID: TIntegerField;
    Button2: TButton;
    Database1: TDatabase;
    Panel1: TPanel;
    RadioButton1: TRadioButton;
    RadioButton2: TRadioButton;
    Edit1: TEdit;
    Edit2: TEdit;
    Button3: TButton;
    Button4: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure RadioButton1Click(Sender: TObject);
    procedure RadioButton2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses Unit2;

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
begin
 form2.label5.caption:='Изменить';
 form2.visible:=true;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  form2.label5.caption:='Добавить';
  table1.Append;
  form2.Visible:=true;
end;

procedure TForm1.RadioButton1Click(Sender: TObject);
begin
  Edit1.Enabled:=true;
  Edit2.Enabled:=false;
end;

procedure TForm1.RadioButton2Click(Sender: TObject);
begin
  Edit2.Enabled:=true;
  Edit1.Enabled:=false;
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
  if (RadioButton1.Checked) then
  begin
    if (not Table1.Locate('FIO',Edit1.Text,[])) then
    begin
      showMessage('данные не найдены');
    end;
  end;
  if (RadioButton2.Checked) then
  begin
    if (not Table1.Locate('GRP',Edit2.Text,[])) then
    begin
      showMessage('данные не найдены');
    end;
  end;
end;

procedure TForm1.Button4Click(Sender: TObject);
var LookupRes: Variant; 
begin
  if (RadioButton1.Checked) then
  begin
    LookupRes := Table1.Lookup('FIO',Edit1.Text,'fio;grp');
    if not VarIsNull(LookupRes) then
    begin
      ShowMessage(VarToStr(LookupRes[0])+' : '+VarToStr(LookupRes[1])) //имя автора
    end
    else
    begin
      ShowMessage('Не найденно');
    end;
  end;
  if (RadioButton2.Checked) then
  begin
    LookupRes := Table1.Lookup('GRP',Edit2.Text,'fio;grp');
    if not VarIsNull(LookupRes) then
    begin
      ShowMessage(VarToStr(LookupRes[0])+' : '+VarToStr(LookupRes[1])) //имя автора
    end
    else
    begin
      ShowMessage('Не найденно');
    end;
  end;
end;
end.
