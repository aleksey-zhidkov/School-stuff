object Form2: TForm2
  Left = 521
  Top = 120
  Width = 361
  Height = 321
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
    Left = 144
    Top = 16
    Width = 34
    Height = 13
    Caption = #1053#1086#1084#1077#1088
  end
  object Label2: TLabel
    Left = 144
    Top = 48
    Width = 49
    Height = 13
    Caption = #1060#1072#1084#1080#1083#1080#1103
  end
  object Label3: TLabel
    Left = 144
    Top = 80
    Width = 22
    Height = 13
    Caption = #1048#1084#1103
  end
  object Label4: TLabel
    Left = 144
    Top = 112
    Width = 25
    Height = 13
    Caption = #1057#1088#1086#1082
  end
  object Label5: TLabel
    Left = 320
    Top = 120
    Width = 19
    Height = 13
    Caption = #1058#1080#1087
  end
  object Edit1: TEdit
    Left = 16
    Top = 16
    Width = 121
    Height = 21
    TabOrder = 0
  end
  object Edit2: TEdit
    Left = 16
    Top = 48
    Width = 121
    Height = 21
    TabOrder = 1
  end
  object Edit3: TEdit
    Left = 16
    Top = 80
    Width = 121
    Height = 21
    TabOrder = 2
  end
  object Edit4: TEdit
    Left = 16
    Top = 112
    Width = 121
    Height = 21
    TabOrder = 3
  end
  object RadioGroup1: TRadioGroup
    Left = 216
    Top = 8
    Width = 105
    Height = 73
    Caption = #1055#1086#1083
    ItemIndex = 1
    Items.Strings = (
      #1084#1091#1078#1089#1082#1086#1081
      #1078#1077#1085#1089#1082#1080#1081)
    TabOrder = 4
  end
  object ComboBox1: TComboBox
    Left = 200
    Top = 136
    Width = 145
    Height = 21
    ItemHeight = 13
    TabOrder = 5
    Text = #1086#1076#1085#1086#1084#1077#1089#1090#1085#1099#1081
    Items.Strings = (
      #1086#1076#1085#1086#1084#1077#1089#1090#1085#1099#1081
      #1076#1074#1091#1084#1077#1089#1090#1085#1099#1081
      #1083#1102#1082#1089)
  end
  object RadioGroup2: TRadioGroup
    Left = 16
    Top = 144
    Width = 153
    Height = 105
    Caption = #1054#1087#1083#1072#1090#1072
    ItemIndex = 2
    Items.Strings = (
      #1085#1072#1083#1080#1095#1085#1099#1084#1080
      #1082#1088#1077#1076#1080#1090#1085#1072#1103' '#1082#1072#1088#1090#1086#1095#1082#1072
      #1095#1077#1082)
    TabOrder = 6
  end
  object BitBtn1: TBitBtn
    Left = 264
    Top = 184
    Width = 75
    Height = 25
    Caption = #1044#1086#1073#1072#1074#1080#1090#1100
    ModalResult = 1
    TabOrder = 7
    OnClick = BitBtn1Click
  end
  object BitBtn2: TBitBtn
    Left = 264
    Top = 224
    Width = 75
    Height = 25
    Caption = #1054#1090#1084#1077#1085#1072
    ModalResult = 2
    TabOrder = 8
    OnClick = BitBtn2Click
  end
end
