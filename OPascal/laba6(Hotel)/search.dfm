object Form4: TForm4
  Left = 299
  Top = 396
  Width = 337
  Height = 130
  Caption = #1055#1086#1080#1089#1082
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
    Top = 16
    Width = 32
    Height = 13
    Caption = #1055#1086#1083#1077': '
  end
  object Label2: TLabel
    Left = 8
    Top = 48
    Width = 54
    Height = 13
    Caption = #1047#1085#1072#1095#1077#1085#1080#1077': '
  end
  object ComboBox1: TComboBox
    Left = 72
    Top = 8
    Width = 145
    Height = 21
    ItemHeight = 13
    ItemIndex = 0
    TabOrder = 0
    Text = #1060#1072#1084#1080#1083#1080#1103
    Items.Strings = (
      #1060#1072#1084#1080#1083#1080#1103
      #1053#1086#1084#1077#1088
      #1054#1087#1083#1072#1090#1072
      #1058#1080#1087' '#1085#1086#1084#1077#1088#1072)
  end
  object RadioGroup1: TRadioGroup
    Left = 232
    Top = 8
    Width = 89
    Height = 57
    Caption = #1058#1080#1087' '#1087#1086#1080#1089#1082#1072': '
    Items.Strings = (
      #1058#1086#1095#1085#1099#1081
      #1052#1103#1075#1082#1080#1081)
    TabOrder = 1
  end
  object Button1: TButton
    Left = 8
    Top = 72
    Width = 75
    Height = 25
    Caption = #1048#1089#1082#1072#1090#1100
    TabOrder = 2
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 96
    Top = 72
    Width = 75
    Height = 25
    Caption = #1047#1072#1082#1088#1099#1090#1100
    TabOrder = 3
    OnClick = Button2Click
  end
  object Edit1: TEdit
    Left = 72
    Top = 40
    Width = 145
    Height = 21
    TabOrder = 4
  end
end
