object Form1: TForm1
  Left = 157
  Top = 174
  Width = 835
  Height = 545
  Caption = #1054#1090#1077#1083#1100
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object DBGrid1: TDBGrid
    Left = 8
    Top = 8
    Width = 825
    Height = 305
    DataSource = DataSource1
    TabOrder = 0
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
    Columns = <
      item
        Expanded = False
        FieldName = 'NOMER'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'FAM'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'IM'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'POL'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'TYPENOMER'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'SROK'
        Visible = True
      end
      item
        Expanded = False
        FieldName = 'PLATA'
        Visible = True
      end>
  end
  object Button1: TButton
    Left = 8
    Top = 328
    Width = 75
    Height = 25
    Caption = #1044#1086#1073#1072#1074#1080#1090#1100
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 96
    Top = 328
    Width = 75
    Height = 25
    Caption = #1048#1079#1084#1077#1085#1080#1090#1100
    TabOrder = 2
    OnClick = Button2Click
  end
  object Button4: TButton
    Left = 272
    Top = 328
    Width = 75
    Height = 25
    Caption = #1057#1086#1088#1090#1080#1088#1086#1074#1082#1072
    TabOrder = 3
    OnClick = Button4Click
  end
  object Button5: TButton
    Left = 360
    Top = 328
    Width = 75
    Height = 25
    Caption = #1055#1086#1080#1089#1082
    TabOrder = 4
    OnClick = Button5Click
  end
  object Button3: TButton
    Left = 184
    Top = 328
    Width = 75
    Height = 25
    Caption = #1059#1076#1072#1083#1080#1090#1100
    TabOrder = 5
    OnClick = Button3Click
  end
  object DataSource1: TDataSource
    DataSet = Table1
    Left = 16
    Top = 432
  end
  object Table1: TTable
    Active = True
    DatabaseName = 'mya'
    TableName = 'HOTEL'
    Left = 72
    Top = 416
    object Table1NOMER: TIntegerField
      FieldName = 'NOMER'
      Required = True
    end
    object Table1FAM: TStringField
      FieldName = 'FAM'
    end
    object Table1IM: TStringField
      FieldName = 'IM'
    end
    object Table1POL: TStringField
      FieldName = 'POL'
    end
    object Table1TYPENOMER: TStringField
      FieldName = 'TYPENOMER'
    end
    object Table1SROK: TIntegerField
      FieldName = 'SROK'
    end
    object Table1PLATA: TStringField
      FieldName = 'PLATA'
    end
  end
  object Database1: TDatabase
    AliasName = 'mydb'
    Connected = True
    DatabaseName = 'mya'
    LoginPrompt = False
    Params.Strings = (
      'user=STUDENT'
      'password=1')
    SessionName = 'Default'
    Left = 200
    Top = 408
  end
end
