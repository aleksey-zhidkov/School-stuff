unit Main;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Grids, DBGrids, DB, DBTables, ComCtrls, StdCtrls, ExtCtrls,
  DBCtrls;

type
  TForm1 = class(TForm)
    DataSource1: TDataSource;
    Query1: TQuery;
    Query2: TQuery;
    Query3: TQuery;
    Query4: TQuery;
    Database1: TDatabase;
    DBGrid1: TDBGrid;
    RadioGroup1: TRadioGroup;
    Button1: TButton;
    GroupBox1: TGroupBox;
    Label1: TLabel;
    Label2: TLabel;
    Edit1: TEdit;
    Edit2: TEdit;
    Button2: TButton;
    DBNavigator1: TDBNavigator;
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    TabSheet2: TTabSheet;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Edit3: TEdit;
    Edit4: TEdit;
    Edit5: TEdit;
    ComboBox1: TComboBox;
    Button3: TButton;
    Edit6: TEdit;
    Edit7: TEdit;
    Edit8: TEdit;
    Label7: TLabel;
    Label8: TLabel;
    Label9: TLabel;
    Label10: TLabel;
    Query1ID_GUEST: TIntegerField;
    Query1FIO: TStringField;
    Query1ADRESS: TStringField;
    Query1TIME: TIntegerField;
    Query2ID_GUEST: TIntegerField;
    Query2FIO: TStringField;
    Query2ADRESS: TStringField;
    Query2TIME: TIntegerField;
    Query4ID_GUEST: TIntegerField;
    Query4ID_SERVICE: TIntegerField;
    Query4ID_ORD: TIntegerField;
    Query3ID_SERV: TIntegerField;
    Query3SERV: TStringField;
    Query3CENA: TIntegerField;
    Button4: TButton;
    procedure Button3Click(Sender: TObject);
    procedure DBGrid1MouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure Button1Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.Button3Click(Sender: TObject);
var query: String;
    m,n: integer;
begin
  query:='INSERT INTO guest (ID_GUEST,FIO,ADRESS,TIME) VALUES(';
  query:=query+'gen_id(GEN1,1)'+','+#39+Edit3.text+#39+','+#39+Edit4.text;
  query:=query+#39+','+#39+Edit5.text+#39+')';
  Query1.SQL.Clear;
  Query1.SQL.Add(query);
  Query1.Close;
  Query1.ExecSQL;
  Query2.Close;
  Query2.Open;
  query:='SELECT MAX(ID_GUEST) FROM guest';
  showmessage(query);
  Query1.SQL.Clear;
  Query1.SQL.Add(query);
  Query1.ExecSQL;
  m:=Query1.Fields[0].AsInteger;
  query:='SELECT id_serv FROM service WHERE serv='+#39+ComboBox1.Text+#39;
  Query1.SQL.Clear;
  Query1.SQL.Add(query);
  Query1.ExecSQL;
  n:=Query1.Fields[0].AsInteger;
  query:='INSERT INTO ord (id_ord,id_guest,id_service) VALUES(';
  query:=query+'gen_id(GEN2,1),'+#39+inttostr(m)+#39+','+#39;
  query:=query+inttostr(n)+#39+')';
  showmessage(query);
  Query1.SQL.Clear;
  Query1.SQL.Add(query);
  Query1.ExecSQL;
end;

procedure TForm1.DBGrid1MouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
var n,time,cena:integer;
    sname: string;
begin
  n:=Query2ID_GUEST.AsInteger;
  Query2.Locate('ID_GUEST',n,[]);
  Edit6.Text:=QUERY2FIO.AsString;
  Edit7.Text:=QUERY2ADRESS.AsString;
  time:=strtoint(QUERY2TIME.AsString);
  Edit8.Text:=inttostr(time);
  Query4.Locate('ID_GUEST',n,[]);
  n:=Query4ID_SERVICE.AsInteger;
  Query3.Locate('ID_SERV',n,[]);
  sname:=Query3ID_SERV.AsString;
  Label8.Caption:=sname;
  cena:=Query3CENA.AsInteger;
  Label10.Caption:=inttostr(time*cena);
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
  Query2.SQL.Clear;
  case (RadioGroup1.ItemIndex) of
       0: begin
          Query2.SQL.Add('SELECT * FROM guest ORDER BY fio');
       end;
       1: begin
          Query2.SQL.Add('SELECT * FROM guest ORDER BY adress');
       end;
   end;
  Query2.ExecSQL;
  Query2.Open;
end;

procedure TForm1.Button4Click(Sender: TObject);
var query,fio,adr,time:string;
n:integer;
begin
   n:=Query2ID_GUEST.AsInteger;
   fio:=#39+Edit6.Text+#39;
   adr:=#39+Edit7.Text+#39;
   time:=#39+Edit8.Text+#39;
   query:='UPDATE guest SET FIO='+fio+', adress='+adr+',time='+time+'WHERE id_guest='+inttostr(n);
   Query1.SQL.Clear;
   Query1.SQL.Add(query);
   Query1.ExecSQL;
   Query2.Close;
   Query2.Open;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  if Edit1.Text<>'' then
  begin
    Query2.Locate('fio',Edit1.Text,[])
  end
  else
  begin
    Query2.Locate('adress',Edit2.Text,[])
  end;
end;

end.
