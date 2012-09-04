object Form3: TForm3
  Left = 250
  Top = 167
  Width = 208
  Height = 203
  Caption = 'Sort'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object RadioGroup1: TRadioGroup
    Left = 8
    Top = 8
    Width = 185
    Height = 129
    Caption = #1055#1086#1083#1077': '
    Items.Strings = (
      #1053#1086#1084#1077#1088
      #1060#1072#1084#1080#1083#1080#1103
      'C'#1088#1086#1082
      #1058#1080#1087' '#1053#1086#1084#1077#1088#1072
      #1054#1087#1083#1072#1090#1072)
    TabOrder = 0
  end
  object Button1: TButton
    Left = 8
    Top = 144
    Width = 75
    Height = 25
    Caption = 'Ok'
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 120
    Top = 144
    Width = 75
    Height = 25
    Caption = #1054#1090#1084#1077#1085#1072
    TabOrder = 2
    OnClick = Button2Click
  end
end
