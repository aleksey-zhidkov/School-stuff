unit PUnit;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, ExtCtrls, ExtDlgs, StdCtrls;

type
  TForm1 = class(TForm)
    MainMenu1: TMainMenu;
    ScrollBox1: TScrollBox;
    N1: TMenuItem;
    N2: TMenuItem;
    N3: TMenuItem;
    N4: TMenuItem;
    Image1: TImage;
    OpenPictureDialog1: TOpenPictureDialog;
    ColorDialog1: TColorDialog;
    N5: TMenuItem;
    N6: TMenuItem;
    SavePictureDialog1: TSavePictureDialog;
    N7: TMenuItem;
    N11: TMenuItem;
    N4px1: TMenuItem;
    N6px1: TMenuItem;
    N10px1: TMenuItem;
    N50px1: TMenuItem;
    N8: TMenuItem;
    N9: TMenuItem;
    N10: TMenuItem;
    Panel1: TPanel;
    Color: TLabel;
    Label1: TLabel;
    Label2: TLabel;
    procedure N2Click(Sender: TObject);
    procedure N4Click(Sender: TObject);
    procedure Image1MouseMove(Sender: TObject; Shift: TShiftState; X,
      Y: Integer);
    procedure Image1MouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure Image1MouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure N6Click(Sender: TObject);
    procedure N3Click(Sender: TObject);
    procedure N11Click(Sender: TObject);
    procedure N4px1Click(Sender: TObject);
    procedure N6px1Click(Sender: TObject);
    procedure N10px1Click(Sender: TObject);
    procedure N50px1Click(Sender: TObject);
    procedure N9Click(Sender: TObject);
    procedure N10Click(Sender: TObject);
    procedure setLabels;
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  isMouseDown:boolean;
  mcolor:TColor;
  size,shape:byte;

implementation

{$R *.dfm}

procedure TForm1.N2Click(Sender: TObject);
var path:string;
begin
  getDir(0,path);
  OpenPictureDialog1.InitialDir:=path;
  if (OpenPictureDialog1.Execute) then
  begin
    Image1.Picture.LoadFromFile
      (OpenPictureDialog1.FileName);
    ScrollBox1.Width:=Image1.Picture.Width;
    ScrollBox1.Height:=Image1.Picture.Height;
  end;
end;

procedure TForm1.N4Click(Sender: TObject);
begin
  Application.Terminate;
end;

procedure TForm1.Image1MouseMove(Sender: TObject; Shift: TShiftState; X,
  Y: Integer);
begin
  if (isMouseDown) then
  begin
   case (shape) of
     1:begin
        Image1.Canvas.Brush.Color:=mcolor;
        Image1.Canvas.Ellipse(X-size,Y-size,X+size,Y+size);
       end;
     2:begin
        Image1.Canvas.Brush.Color:=mcolor;
        Image1.Canvas.Rectangle(x-size,y-size,x+size,y+size);
       end;
   end;
  end;
end;

procedure TForm1.Image1MouseDown(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
  isMouseDown:=true;
end;

procedure TForm1.Image1MouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
 isMouseDown:=false;
end;

procedure TForm1.N6Click(Sender: TObject);
begin
  if (ColorDialog1.Execute) then  mcolor:=ColorDialog1.Color;
  setLabels;
end;

procedure TForm1.N3Click(Sender: TObject);
var path:string;
begin
  getDir(0,path);
  SavePictureDialog1.InitialDir:=path;
  if (SavePictureDialog1.Execute) then
    Image1.Picture.SaveToFile(SavePictureDialog1.FileName);
  setLabels;
end;

procedure TForm1.N11Click(Sender: TObject);
begin
  size:=1;
  setLabels;
end;

procedure TForm1.N4px1Click(Sender: TObject);
begin
  size:=2;
  setLabels;
end;

procedure TForm1.N6px1Click(Sender: TObject);
begin
  size:=3;
  setLabels;
end;

procedure TForm1.N10px1Click(Sender: TObject);
begin
  size:=5;
  setLabels;
end;

procedure TForm1.N50px1Click(Sender: TObject);
begin
  size:=25;
  setLabels;
end;

procedure TForm1.N9Click(Sender: TObject);
begin
  shape:=1;
  setLabels;
end;

procedure TForm1.N10Click(Sender: TObject);
begin
  shape:=2;
  setLabels;
end;

procedure TForm1.setLabels;
begin
  Color.Color:=mcolor;
  Color.Font.Color:=mcolor;
  Label1.Caption:='Размер: '+inttostr(size*2);
  if (shape=1) then Label2.caption:='Форма: круг'
  else Label2.caption:='Форма: кварат'
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  mcolor:=clBlack;
  size:=1;
  shape:=1;
  setLabels;
end;

end.
