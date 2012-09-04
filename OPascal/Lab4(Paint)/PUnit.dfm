object Form1: TForm1
  Left = 192
  Top = 107
  Width = 598
  Height = 542
  Caption = 'Paint'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 14
  object ScrollBox1: TScrollBox
    Left = 0
    Top = 0
    Width = 590
    Height = 477
    Align = alTop
    AutoSize = True
    TabOrder = 0
    object Image1: TImage
      Left = 0
      Top = 0
      Width = 586
      Height = 473
      Align = alBottom
      OnMouseDown = Image1MouseDown
      OnMouseMove = Image1MouseMove
      OnMouseUp = Image1MouseUp
    end
  end
  object Panel1: TPanel
    Left = 0
    Top = 478
    Width = 590
    Height = 18
    Align = alBottom
    AutoSize = True
    TabOrder = 1
    object Color: TLabel
      Left = 9
      Top = 1
      Width = 26
      Height = 14
      Align = alCustom
      Caption = #1062#1074#1077#1090
    end
    object Label1: TLabel
      Left = 40
      Top = 1
      Width = 37
      Height = 14
      Align = alCustom
      Caption = #1056#1072#1079#1084#1077#1088
    end
    object Label2: TLabel
      Left = 113
      Top = 1
      Width = 34
      Height = 14
      Align = alCustom
      Caption = #1060#1086#1088#1084#1072
    end
  end
  object MainMenu1: TMainMenu
    Left = 672
    Top = 440
    object N1: TMenuItem
      Caption = #1060#1072#1081#1083
      object N2: TMenuItem
        Caption = #1054#1090#1082#1088#1099#1090#1100'...'
        OnClick = N2Click
      end
      object N3: TMenuItem
        Caption = #1057#1086#1093#1088#1072#1085#1080#1090#1100'...'
        OnClick = N3Click
      end
      object N4: TMenuItem
        Caption = #1042#1099#1093#1086#1076
        OnClick = N4Click
      end
    end
    object N5: TMenuItem
      Caption = #1056#1077#1076#1072#1082#1090#1080#1088#1086#1074#1072#1085#1080#1077
      object N6: TMenuItem
        Caption = #1062#1074#1077#1090'...'
        OnClick = N6Click
      end
      object N7: TMenuItem
        Caption = #1056#1072#1079#1084#1077#1088
        object N11: TMenuItem
          Caption = '2 px'
          OnClick = N11Click
        end
        object N4px1: TMenuItem
          Caption = '4 px'
          OnClick = N4px1Click
        end
        object N6px1: TMenuItem
          Caption = '6 px'
          OnClick = N6px1Click
        end
        object N10px1: TMenuItem
          Caption = '10 px'
          OnClick = N10px1Click
        end
        object N50px1: TMenuItem
          Caption = '50 px'
          OnClick = N50px1Click
        end
      end
      object N8: TMenuItem
        Caption = #1060#1086#1088#1084#1072
        object N9: TMenuItem
          Caption = #1050#1088#1091#1075
          OnClick = N9Click
        end
        object N10: TMenuItem
          Caption = #1050#1074#1072#1076#1088#1072#1090
          OnClick = N10Click
        end
      end
    end
  end
  object OpenPictureDialog1: TOpenPictureDialog
    Left = 528
    Top = 416
  end
  object ColorDialog1: TColorDialog
    Left = 488
    Top = 424
  end
  object SavePictureDialog1: TSavePictureDialog
    Left = 416
    Top = 400
  end
end
