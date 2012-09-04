object Form1: TForm1
  Left = 192
  Top = 107
  Width = 424
  Height = 350
  Caption = 'DTest'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = init
  PixelsPerInch = 96
  TextHeight = 14
  object Quest: TLabel
    Left = 16
    Top = 8
    Width = 58
    Height = 14
    Caption = #1042#1086#1087#1088#1086#1089': 0/0'
  end
  object point: TLabel
    Left = 160
    Top = 8
    Width = 51
    Height = 14
    Caption = #1041#1072#1083#1083#1099':0/0'
  end
  object Label1: TLabel
    Left = 328
    Top = 8
    Width = 65
    Height = 14
    Caption = #1055#1086#1087#1099#1090#1082#1072': 0/0'
  end
  object Label2: TLabel
    Left = 8
    Top = 176
    Width = 37
    Height = 14
    Caption = #1042#1086#1087#1088#1086#1089
  end
  object RadioButton1: TRadioButton
    Left = 56
    Top = 216
    Width = 113
    Height = 17
    Caption = #1054#1090#1074#1077#1090'1'
    TabOrder = 0
  end
  object RadioButton2: TRadioButton
    Left = 56
    Top = 248
    Width = 113
    Height = 17
    Caption = #1054#1090#1074#1077#1090'2'
    TabOrder = 1
  end
  object RadioButton3: TRadioButton
    Left = 56
    Top = 280
    Width = 113
    Height = 17
    Caption = #1054#1090#1074#1077#1090'3'
    TabOrder = 2
  end
  object RadioButton4: TRadioButton
    Left = 184
    Top = 216
    Width = 113
    Height = 17
    Caption = #1054#1090#1074#1077#1090'4'
    TabOrder = 3
  end
  object RadioButton5: TRadioButton
    Left = 184
    Top = 248
    Width = 113
    Height = 17
    Caption = #1054#1090#1074#1077#1090'5'
    TabOrder = 4
  end
  object Button1: TButton
    Left = 184
    Top = 280
    Width = 81
    Height = 17
    Caption = #1044#1072#1083#1100#1096#1077
    Enabled = False
    TabOrder = 5
    OnClick = Button1Click
  end
  object Memo1: TMemo
    Left = 8
    Top = 24
    Width = 401
    Height = 137
    Lines.Strings = (
      #1058#1077#1086#1088#1080#1103)
    TabOrder = 6
  end
  object OpenDialog1: TOpenDialog
    Left = 352
    Top = 176
  end
  object MainMenu1: TMainMenu
    Left = 384
    Top = 176
    object N1: TMenuItem
      Caption = #1060#1072#1081#1083
      object N2: TMenuItem
        Caption = #1054#1090#1082#1088#1099#1090#1100'...'
        OnClick = N2Click
      end
      object N4: TMenuItem
        Caption = #1047#1072#1085#1086#1074#1086
        Enabled = False
        OnClick = N4Click
      end
      object N3: TMenuItem
        Caption = #1042#1099#1093#1086#1076
        OnClick = N3Click
      end
    end
  end
end
