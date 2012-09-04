unit UArr;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    Memo1: TMemo;
    Memo2: TMemo;
    Button1: TButton;
    Edit1: TEdit;
    procedure Button1Click(Sender: TObject);
    procedure check;
    function checkChar(ch:char):boolean;
    procedure setArr(str:string);
    function findAvarange:real;
    function binarySearch(e:integer):integer;
    procedure sort;
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  arr: array of integer;
  num:byte;

implementation

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
var sortedArr:string;
    i:byte;
begin
 num:=0;
 Memo2.Clear;
 check;
 sort;
 for i:=0 to num-1 do
   sortedarr:=sortedArr+inttostr(arr[i])+' ';
 Memo2.Lines.Add('Sorted array: '+sortedArr);
 Memo2.Lines.Add('Max: '+inttostr(arr[num-1]));
 Memo2.Lines.Add('Min: '+inttostr(arr[0]));
 Memo2.Lines.Add('Avarange: '+floattostr(findAvarange));
 Memo2.Lines.Add('Element: '+inttostr(binarySearch(strtoint(Edit1.Text))+1));
end;
procedure TForm1.check;
var str,cstr:string;
    i:byte;
    isOk:boolean;
begin
 str:=Memo1.Lines.CommaText;
 trim(str);
 for i:=1 to length(str)-1 do
 begin
   isOk:=checkChar(str[i]);
   if (isOk) then
    if (str[i]<>' ')then cstr:=cstr+str[i]
    else if (str[i]=' ')and(str[i+1]<>' ') then
    begin
     cstr:=cstr+str[i];
     inc(num);
    end;
 end;
 inc(num);
 setLength(arr,num);
 setArr(cstr);
end;

function TForm1.checkChar(ch:char):boolean;
var res:boolean;
begin
  res:=false;
  case (ch) of
    '0':res:=true;
    '1':res:=true;
    '2':res:=true;
    '3':res:=true;
    '4':res:=true;
    '5':res:=true;
    '6':res:=true;
    '7':res:=true;
    '8':res:=true;
    '9':res:=true;
    ' ':res:=true;
  end;
  result:=res;
end;

procedure TForm1.setArr(str:string);
var i,count:byte;
    substr:string;
begin
  trim(str);
  count:=0;
  substr:='';
  for i:=1 to length(str) do
  begin
   if (str[i]=' ') then
   begin
     arr[count]:=strtoint(substr);
     inc(count);
     substr:='';
   end
   else
   begin
    substr:=substr+str[i];
   end;
  end;
  arr[count]:=strtoint(substr);
end;

procedure TForm1.sort;

  function getMax(i,l:integer):integer;
  var res:integer;
  begin
    res:=i*2;
    if (i*2+1<l) then
    begin
     if (arr[i*2+1]>arr[i*2]) then res:=i*2+1;
    end;
    result:=res;
  end;

  procedure shift(l:integer);
  var i,index,t,c:integer;
  begin
   c:=0;
   i:=(l div 2);
   while i>=0 do
   begin
      index:=getMax(i,l);
      if (arr[i]<arr[index]) then
      begin
        t:=arr[i];
        arr[i]:=arr[index];
        arr[index]:=t;
      end;
      dec(i);
   end;
  end;
var i,t:integer;
begin

  for i:=num-1 downto 1 do
  begin
    shift(i);
    t:=arr[0];
    arr[0]:=arr[i];
    arr[i]:=t;
  end;
end;

function TForm1.findAvarange:real;
var res:real;
    i:byte;
begin
  res:=0;
  for i:=0 to num-1 do
    res:=res+arr[i];
  result:=res/num;
end;

function TForm1.binarySearch(e:integer):integer;
var l,r,c,res:integer;
begin
  res:=-1;
  l:=0;
  r:=num-1;
  while (l<r) do
  begin
    c:=l+((r-l) div 2);
    if (arr[l]=e) then begin res:=l;break;end
    else if (arr[c]=e)then begin res:=c;break;end
    else if (arr[r]=e)then begin res:=r;break;end;
    if (arr[c]<e) then l:=c+1
    else r:=c;
  end;
  result:=res;
end;

end.
