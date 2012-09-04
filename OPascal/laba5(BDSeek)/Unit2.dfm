object Form2: TForm2
  Left = 637
  Top = 412
  Width = 304
  Height = 291
  Caption = 'Form2'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 32
    Width = 17
    Height = 13
    Caption = 'FIO'
    FocusControl = DBEdit1
  end
  object Label2: TLabel
    Left = 8
    Top = 72
    Width = 23
    Height = 13
    Caption = 'GRP'
    FocusControl = DBEdit2
  end
  object Label3: TLabel
    Left = 8
    Top = 112
    Width = 28
    Height = 13
    Caption = 'PASS'
    FocusControl = DBEdit3
  end
  object Label4: TLabel
    Left = 8
    Top = 152
    Width = 11
    Height = 13
    Caption = 'ID'
    FocusControl = DBEdit4
  end
  object Label5: TLabel
    Left = 8
    Top = 8
    Width = 32
    Height = 13
    Caption = 'Label5'
  end
  object DBEdit1: TDBEdit
    Left = 8
    Top = 48
    Width = 265
    Height = 21
    DataField = 'FIO'
    DataSource = Form1.DataSource1
    TabOrder = 0
  end
  object DBEdit2: TDBEdit
    Left = 8
    Top = 88
    Width = 69
    Height = 21
    DataField = 'GRP'
    DataSource = Form1.DataSource1
    TabOrder = 1
  end
  object DBEdit3: TDBEdit
    Left = 8
    Top = 128
    Width = 264
    Height = 21
    DataField = 'PASS'
    DataSource = Form1.DataSource1
    TabOrder = 2
  end
  object DBEdit4: TDBEdit
    Left = 8
    Top = 168
    Width = 134
    Height = 21
    DataField = 'ID'
    DataSource = Form1.DataSource1
    TabOrder = 3
  end
  object Button1: TButton
    Left = 8
    Top = 200
    Width = 75
    Height = 25
    Caption = #1055#1088#1080#1085#1103#1090#1100
    TabOrder = 4
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 8
    Top = 232
    Width = 75
    Height = 25
    Caption = #1054#1090#1084#1077#1085#1080#1090#1100
    TabOrder = 5
    OnClick = Button2Click
  end
end
