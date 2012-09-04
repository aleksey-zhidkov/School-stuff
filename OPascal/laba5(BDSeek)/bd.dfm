object Form1: TForm1
  Left = 242
  Top = 132
  Width = 543
  Height = 291
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Button1: TButton
    Left = 8
    Top = 168
    Width = 73
    Height = 25
    Caption = #1048#1079#1084#1077#1085#1080#1090#1100
    TabOrder = 0
    OnClick = Button1Click
  end
  object DBGrid1: TDBGrid
    Left = 8
    Top = 8
    Width = 505
    Height = 145
    DataSource = DataSource1
    TabOrder = 1
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
    Columns = <
      item
        Expanded = False
        FieldName = 'FIO'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'GRP'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'PASS'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'ID'
        Visible = True
      end>
  end
  object Button2: TButton
    Left = 8
    Top = 200
    Width = 73
    Height = 25
    Caption = #1044#1086#1073#1072#1074#1080#1090#1100
    TabOrder = 2
    OnClick = Button2Click
  end
  object Panel1: TPanel
    Left = 232
    Top = 160
    Width = 273
    Height = 97
    TabOrder = 3
    object RadioButton1: TRadioButton
      Left = 8
      Top = 8
      Width = 113
      Height = 25
      Caption = #1055#1086' '#1092#1072#1084#1080#1083#1080#1080
      Checked = True
      TabOrder = 0
      TabStop = True
      OnClick = RadioButton1Click
    end
    object RadioButton2: TRadioButton
      Left = 8
      Top = 40
      Width = 113
      Height = 17
      Caption = #1055#1086' '#1075#1088#1091#1087#1087#1077
      TabOrder = 1
      OnClick = RadioButton2Click
    end
    object Edit1: TEdit
      Left = 96
      Top = 8
      Width = 169
      Height = 21
      TabOrder = 2
      Text = #1060#1072#1084#1080#1083#1080#1103
    end
    object Edit2: TEdit
      Left = 96
      Top = 32
      Width = 169
      Height = 21
      Enabled = False
      TabOrder = 3
      Text = #1043#1088#1091#1087#1087#1072
    end
    object Button3: TButton
      Left = 8
      Top = 64
      Width = 75
      Height = 25
      Caption = #1055#1086#1080#1089#1082
      TabOrder = 4
      OnClick = Button3Click
    end
  end
  object Button4: TButton
    Left = 8
    Top = 232
    Width = 75
    Height = 25
    Caption = 'LookUp'
    TabOrder = 4
    OnClick = Button4Click
  end
  object DataSource1: TDataSource
    DataSet = Table1
    Left = 48
    Top = 40
  end
  object Table1: TTable
    Active = True
    DatabaseName = 'int'
    TableName = 'TABLE1'
    Left = 136
    Top = 32
    object Table1FIO: TStringField
      FieldName = 'FIO'
      FixedChar = True
      Size = 40
    end
    object Table1GRP: TStringField
      FieldName = 'GRP'
      FixedChar = True
      Size = 5
    end
    object Table1PASS: TStringField
      FieldName = 'PASS'
      FixedChar = True
    end
    object Table1ID: TIntegerField
      FieldName = 'ID'
      Required = True
    end
  end
  object Database1: TDatabase
    AliasName = 'INTRBASE1'
    Connected = True
    DatabaseName = 'int'
    Params.Strings = (
      'username=student'
      'password=1'
      'USER NAME=student')
    SessionName = 'Default'
    Left = 144
    Top = 176
  end
end
