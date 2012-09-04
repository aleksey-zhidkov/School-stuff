object Form1: TForm1
  Left = 192
  Top = 107
  Width = 221
  Height = 251
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  DesignSize = (
    213
    224)
  PixelsPerInch = 96
  TextHeight = 14
  object Edit1: TEdit
    Left = 8
    Top = 8
    Width = 201
    Height = 17
    Anchors = [akTop, akRight]
    AutoSelect = False
    AutoSize = False
    BiDiMode = bdRightToLeft
    ParentBiDiMode = False
    ReadOnly = True
    TabOrder = 0
    Text = '0'
    OnKeyPress = Edit1KeyPress
  end
  object Button1: TButton
    Left = 7
    Top = 32
    Width = 58
    Height = 25
    Caption = '7'
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 72
    Top = 32
    Width = 65
    Height = 25
    Caption = '8'
    TabOrder = 2
    OnClick = Button2Click
  end
  object Button3: TButton
    Left = 144
    Top = 32
    Width = 64
    Height = 25
    Caption = '9'
    TabOrder = 3
    OnClick = Button3Click
  end
  object Button4: TButton
    Left = 71
    Top = 64
    Width = 66
    Height = 25
    Caption = '5'
    TabOrder = 4
    OnClick = Button4Click
  end
  object Button5: TButton
    Left = 7
    Top = 64
    Width = 58
    Height = 25
    Caption = '4'
    TabOrder = 5
    OnClick = Button5Click
  end
  object Button6: TButton
    Left = 143
    Top = 64
    Width = 66
    Height = 25
    Caption = '6'
    TabOrder = 6
    OnClick = Button6Click
  end
  object Button7: TButton
    Left = 7
    Top = 96
    Width = 58
    Height = 25
    Caption = '1'
    TabOrder = 7
    OnClick = Button7Click
  end
  object Button9: TButton
    Left = 71
    Top = 96
    Width = 66
    Height = 25
    Caption = '2'
    TabOrder = 8
    OnClick = Button9Click
  end
  object Button10: TButton
    Left = 143
    Top = 96
    Width = 66
    Height = 25
    Caption = '3'
    TabOrder = 9
    OnClick = Button10Click
  end
  object Button11: TButton
    Left = 7
    Top = 128
    Width = 58
    Height = 25
    Caption = '0'
    TabOrder = 10
    OnClick = Button11Click
  end
  object Button12: TButton
    Left = 71
    Top = 128
    Width = 66
    Height = 25
    Caption = '+'
    TabOrder = 11
    OnClick = Button12Click
  end
  object Button13: TButton
    Left = 143
    Top = 128
    Width = 66
    Height = 25
    Caption = '-'
    TabOrder = 12
    OnClick = Button13Click
  end
  object Button14: TButton
    Left = 7
    Top = 160
    Width = 58
    Height = 25
    Caption = '*'
    TabOrder = 13
    OnClick = Button14Click
  end
  object Button15: TButton
    Left = 71
    Top = 160
    Width = 66
    Height = 25
    Caption = '/'
    TabOrder = 14
    OnClick = Button15Click
  end
  object Button16: TButton
    Left = 111
    Top = 192
    Width = 98
    Height = 25
    Caption = '='
    TabOrder = 15
    OnClick = Button18Click
  end
  object Button17: TButton
    Left = 143
    Top = 160
    Width = 66
    Height = 25
    Caption = '.'
    TabOrder = 16
    OnClick = Button17Click
  end
  object Button8: TButton
    Left = 8
    Top = 192
    Width = 97
    Height = 25
    Caption = 'C'
    TabOrder = 17
    OnClick = Button8Click
  end
end
