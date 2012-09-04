object Form1: TForm1
  Left = 276
  Top = 161
  Width = 703
  Height = 488
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
    Top = 0
    Width = 673
    Height = 193
    DataSource = DataSource1
    TabOrder = 0
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
    OnMouseUp = DBGrid1MouseUp
  end
  object RadioGroup1: TRadioGroup
    Left = 496
    Top = 384
    Width = 185
    Height = 65
    Caption = #1057#1086#1088#1090#1080#1088#1086#1074#1082#1072': '
    Items.Strings = (
      #1080#1084#1103
      #1072#1076#1088#1077#1089)
    TabOrder = 1
  end
  object Button1: TButton
    Left = 416
    Top = 424
    Width = 75
    Height = 25
    Caption = #1057#1086#1088#1090#1080#1088#1086#1074#1082#1072
    TabOrder = 2
    OnClick = Button1Click
  end
  object GroupBox1: TGroupBox
    Left = 496
    Top = 296
    Width = 185
    Height = 81
    Caption = #1055#1086#1080#1089#1082': '
    TabOrder = 3
    object Label1: TLabel
      Left = 8
      Top = 24
      Width = 28
      Height = 13
      Caption = #1048#1084#1103': '
    end
    object Label2: TLabel
      Left = 8
      Top = 56
      Width = 37
      Height = 13
      Caption = #1040#1076#1088#1077#1089': '
    end
    object Edit1: TEdit
      Left = 56
      Top = 16
      Width = 121
      Height = 21
      TabOrder = 0
    end
    object Edit2: TEdit
      Left = 56
      Top = 48
      Width = 121
      Height = 21
      TabOrder = 1
    end
  end
  object Button2: TButton
    Left = 416
    Top = 352
    Width = 75
    Height = 25
    Caption = #1055#1086#1080#1089#1082
    TabOrder = 4
    OnClick = Button2Click
  end
  object DBNavigator1: TDBNavigator
    Left = 440
    Top = 200
    Width = 240
    Height = 25
    DataSource = DataSource1
    TabOrder = 5
  end
  object PageControl1: TPageControl
    Left = 8
    Top = 200
    Width = 401
    Height = 249
    ActivePage = TabSheet2
    TabIndex = 1
    TabOrder = 6
    object TabSheet1: TTabSheet
      Caption = #1044#1086#1073#1072#1074#1080#1090#1100
      object Label3: TLabel
        Left = 8
        Top = 16
        Width = 27
        Height = 13
        Caption = #1060#1048#1054
      end
      object Label4: TLabel
        Left = 8
        Top = 48
        Width = 31
        Height = 13
        Caption = #1040#1076#1088#1077#1089
      end
      object Label5: TLabel
        Left = 8
        Top = 80
        Width = 33
        Height = 13
        Caption = #1042#1088#1077#1084#1103
      end
      object Label6: TLabel
        Left = 8
        Top = 112
        Width = 36
        Height = 13
        Caption = #1059#1089#1083#1091#1075#1072
      end
      object Edit3: TEdit
        Left = 64
        Top = 8
        Width = 137
        Height = 21
        TabOrder = 0
      end
      object Edit4: TEdit
        Left = 64
        Top = 40
        Width = 137
        Height = 21
        TabOrder = 1
      end
      object Edit5: TEdit
        Left = 64
        Top = 72
        Width = 137
        Height = 21
        TabOrder = 2
      end
      object ComboBox1: TComboBox
        Left = 64
        Top = 104
        Width = 137
        Height = 21
        ItemHeight = 13
        ItemIndex = 0
        TabOrder = 3
        Text = 'serv1'
        Items.Strings = (
          'serv1'
          'serv2'
          'serv3'
          'serv4'
          'serv5'
          'serv6')
      end
      object Button3: TButton
        Left = 64
        Top = 160
        Width = 75
        Height = 25
        Caption = #1044#1086#1073#1072#1074#1080#1090#1100
        TabOrder = 4
        OnClick = Button3Click
      end
    end
    object TabSheet2: TTabSheet
      Caption = #1048#1079#1084#1077#1085#1080#1090#1100
      ImageIndex = 1
      object Label7: TLabel
        Left = 200
        Top = 24
        Width = 43
        Height = 13
        Caption = #1057#1077#1088#1074#1080#1089': '
      end
      object Label8: TLabel
        Left = 272
        Top = 24
        Width = 3
        Height = 13
      end
      object Label9: TLabel
        Left = 200
        Top = 48
        Width = 58
        Height = 13
        Caption = #1057#1090#1086#1080#1084#1086#1089#1090#1100':'
      end
      object Label10: TLabel
        Left = 272
        Top = 48
        Width = 3
        Height = 13
      end
      object Edit6: TEdit
        Left = 8
        Top = 16
        Width = 121
        Height = 21
        TabOrder = 0
      end
      object Edit7: TEdit
        Left = 8
        Top = 40
        Width = 121
        Height = 21
        TabOrder = 1
      end
      object Edit8: TEdit
        Left = 8
        Top = 64
        Width = 121
        Height = 21
        TabOrder = 2
      end
      object Button4: TButton
        Left = 16
        Top = 136
        Width = 75
        Height = 25
        Caption = #1048#1079#1084#1077#1085#1080#1090#1100
        TabOrder = 3
        OnClick = Button4Click
      end
    end
  end
  object DataSource1: TDataSource
    DataSet = Query2
    Left = 40
    Top = 424
  end
  object Query1: TQuery
    Active = True
    DatabaseName = 'myh'
    SQL.Strings = (
      'SELECT * FROM guest')
    Left = 80
    Top = 424
    object Query1ID_GUEST: TIntegerField
      FieldName = 'ID_GUEST'
      Origin = 'MYH.GUEST.ID_GUEST'
    end
    object Query1FIO: TStringField
      FieldName = 'FIO'
      Origin = 'MYH.GUEST.FIO'
      Size = 40
    end
    object Query1ADRESS: TStringField
      FieldName = 'ADRESS'
      Origin = 'MYH.GUEST.ADRESS'
      Size = 40
    end
    object Query1TIME: TIntegerField
      FieldName = 'TIME'
      Origin = 'MYH.GUEST.TIME'
    end
  end
  object Query2: TQuery
    Active = True
    DatabaseName = 'myh'
    SQL.Strings = (
      'SELECT * FROM guest')
    Left = 120
    Top = 424
    object Query2ID_GUEST: TIntegerField
      FieldName = 'ID_GUEST'
      Origin = 'MYH.GUEST.ID_GUEST'
    end
    object Query2FIO: TStringField
      FieldName = 'FIO'
      Origin = 'MYH.GUEST.FIO'
      Size = 40
    end
    object Query2ADRESS: TStringField
      FieldName = 'ADRESS'
      Origin = 'MYH.GUEST.ADRESS'
      Size = 40
    end
    object Query2TIME: TIntegerField
      FieldName = 'TIME'
      Origin = 'MYH.GUEST.TIME'
    end
  end
  object Query3: TQuery
    Active = True
    DatabaseName = 'myh'
    SQL.Strings = (
      'SELECT * FROM service')
    Left = 160
    Top = 424
    object Query3ID_SERV: TIntegerField
      FieldName = 'ID_SERV'
      Origin = 'MYH.SERVICE.ID_SERV'
    end
    object Query3SERV: TStringField
      FieldName = 'SERV'
      Origin = 'MYH.SERVICE.SERV'
    end
    object Query3CENA: TIntegerField
      FieldName = 'CENA'
      Origin = 'MYH.SERVICE.CENA'
    end
  end
  object Query4: TQuery
    Active = True
    DatabaseName = 'myh'
    SQL.Strings = (
      'SELECT * FROM ord')
    Left = 200
    Top = 424
    object Query4ID_GUEST: TIntegerField
      FieldName = 'ID_GUEST'
      Origin = 'MYH.ORD.ID_GUEST'
    end
    object Query4ID_SERVICE: TIntegerField
      FieldName = 'ID_SERVICE'
      Origin = 'MYH.ORD.ID_SERVICE'
    end
    object Query4ID_ORD: TIntegerField
      FieldName = 'ID_ORD'
      Origin = 'MYH.ORD.ID_ORD'
    end
  end
  object Database1: TDatabase
    AliasName = 'hotel'
    Connected = True
    DatabaseName = 'myh'
    LoginPrompt = False
    Params.Strings = (
      'user=STUDENT'
      'password=1')
    SessionName = 'Default'
    Top = 424
  end
end
