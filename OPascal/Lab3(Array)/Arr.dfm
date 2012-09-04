object Form1: TForm1
  Left = 192
  Top = 107
  Width = 433
  Height = 480
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 14
  object Memo1: TMemo
    Left = 8
    Top = 8
    Width = 409
    Height = 177
    Lines.Strings = (
      #1042#1074#1077#1076#1080#1090#1077' '#1084#1072#1089#1089#1080#1074)
    TabOrder = 0
  end
  object Memo2: TMemo
    Left = 8
    Top = 200
    Width = 409
    Height = 217
    ReadOnly = True
    TabOrder = 1
  end
  object Button1: TButton
    Left = 328
    Top = 424
    Width = 89
    Height = 17
    Caption = #1043#1086#1090#1086#1074#1086
    TabOrder = 2
    OnClick = Button1Click
  end
end
