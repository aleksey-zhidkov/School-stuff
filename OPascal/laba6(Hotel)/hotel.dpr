program hotel;

uses
  Forms,
  mform in 'mform.pas' {Form1},
  add in 'add.pas' {Form2},
  sort in 'sort.pas' {Form3},
  search in 'search.pas' {Form4};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.CreateForm(TForm2, Form2);
  Application.CreateForm(TForm3, Form3);
  Application.CreateForm(TForm4, Form4);
  Application.Run;
end.
