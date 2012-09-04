using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace Lab6
{
	public class MainForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.TextBox RigidityEditBox;
		private System.Windows.Forms.PictureBox pictureBox;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.TextBox MassEditBox;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;

		private System.ComponentModel.Container components = null;

		public MainForm()
		{
			InitializeComponent();
		}
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		private void InitializeComponent()
		{
			this.pictureBox = new System.Windows.Forms.PictureBox();
			this.RigidityEditBox = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.MassEditBox = new System.Windows.Forms.TextBox();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// pictureBox
			// 
			this.pictureBox.Location = new System.Drawing.Point(8, 64);
			this.pictureBox.Name = "pictureBox";
			this.pictureBox.Size = new System.Drawing.Size(272, 370);
			this.pictureBox.TabIndex = 0;
			this.pictureBox.TabStop = false;
			this.pictureBox.Paint += new System.Windows.Forms.PaintEventHandler(this.pictureBox_Paint);
			// 
			// RigidityEditBox
			// 
			this.RigidityEditBox.Location = new System.Drawing.Point(128, 32);
			this.RigidityEditBox.Name = "RigidityEditBox";
			this.RigidityEditBox.Size = new System.Drawing.Size(88, 20);
			this.RigidityEditBox.TabIndex = 2;
			this.RigidityEditBox.Text = "1";
			this.RigidityEditBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.RigidityEditBox_KeyPress);
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(8, 32);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(120, 16);
			this.label1.TabIndex = 3;
			this.label1.Text = "Жесткость пружины:";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(8, 8);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(80, 16);
			this.label2.TabIndex = 5;
			this.label2.Text = "Масса груза:";
			// 
			// MassEditBox
			// 
			this.MassEditBox.Location = new System.Drawing.Point(128, 4);
			this.MassEditBox.Name = "MassEditBox";
			this.MassEditBox.Size = new System.Drawing.Size(88, 20);
			this.MassEditBox.TabIndex = 4;
			this.MassEditBox.Text = "1";
			this.MassEditBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.MassEditBox_KeyPress);
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(224, 7);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(16, 17);
			this.label3.TabIndex = 6;
			this.label3.Text = "г.";
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(224, 32);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(32, 17);
			this.label4.TabIndex = 7;
			this.label4.Text = "г/с^2";
			// 
			// MainForm
			// 
			this.AutoScale = false;
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(288, 447);
			this.Controls.Add(this.label4);
			this.Controls.Add(this.label3);
			this.Controls.Add(this.label2);
			this.Controls.Add(this.MassEditBox);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.RigidityEditBox);
			this.Controls.Add(this.pictureBox);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "MainForm";
			this.Text = "Закон Гука";
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new MainForm());
		}

		private void pictureBox_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			Graphics gr = e.Graphics;
			gr.Clear( this.BackColor );	

			double g = 9.8;
			double mass=0;  // масса 
			double rigidity=0;  // жесткость

			// метод Double.Parse может вызывать исключение(exeption)
			// если на входе будет неправильная строка 
			try
			{
				/// берем коэфициенты из полей ввода
				mass = Double.Parse(MassEditBox.Text);
				rigidity = Double.Parse(RigidityEditBox.Text);
			}
			catch( Exception ex )
			{
				mass = 0;
				rigidity = 0;
			}

			if( mass >= 0 && rigidity > 0 ) 
			{
				// расчетная формула
				double dx = g * mass / rigidity;

				int ySz = pictureBox.Size.Height;
				int xSz = 110; // расстояние до шкалы

				// Цвет линий 
				Pen scale = new Pen( Color.Black );  // цвет шкалы
				Pen spring = new Pen( Color.Blue );  // цвет пружины
				Pen redLine = new Pen( Color.Red );  // цвет линии - отмеки на шкале
				redLine.DashStyle = DashStyle.Dash;  // красная пунктираня линия 
				
				// Цвет заливки
				SolidBrush sbPoint = new SolidBrush( Color.Yellow );  // цвет точки крепления пружины
				SolidBrush sbMass = new SolidBrush( Color.Gray ); // цвет груза

				// Рисуем шкалу 
				gr.DrawLine( scale, xSz, 0, xSz, ySz );

				int sc = (int)Math.Log10( dx ); // порядок шкалы 10^sc

				// Задаем формат строки с выравниванием справа
				StringFormat stF = new StringFormat();
				stF.Alignment = StringAlignment.Far;

				// рисуем шкалу от 0 до 100 с шагом 5
				// смещение делений относительно начала области рисования = 5 + 60(= 40 + 10 + 10) = 65px
				for( int y=0;y<301; y+=5)
				{
					uint dd=2; // длина штриха на шкале 

					if( y % 30 == 0 ) // целое деление 
					{
						// вычисляем "число" на целом делении шкалы
						double t= (y/30) * Math.Pow( 10, sc); 

						gr.DrawString( t.ToString(), this.Font, new SolidBrush( Color.Black), new RectangleF(xSz-100, y+65-7, 90, 14), stF );
						dd=8;
					}
					else if( y % 15 == 0  ) // 1/2 целого деления
					{
						dd = 4;
					}

					// рисуем штрих
					gr.DrawLine( scale, xSz-dd, y+65, xSz+dd, y+65 );
				}

				// рисуем точку крепления пружины
				gr.FillEllipse( sbPoint, xSz + 25, 0, 10, 10 );

				// вычиляем точку в которой должен находится груз
				int pos = (int)(dx/Math.Pow( 10, sc-1))*3+65;

				// Рисуем красную линию и подпись на ней
				gr.DrawLine( redLine, xSz-50, pos, xSz+50, pos );
				gr.DrawString( dx.ToString(), this.Font, new SolidBrush( Color.Black), xSz+50, pos-7 );

				
				// Рисуем пружинку, количество сегментов пружины = 40px
				// расстояние от 1го сегмента до начала пружины и от последнего сегмента до груза  = 10px
				double ddy = (double)(pos-25) / 40; 
				int yy = (int)(15 + ddy / 2); // дополнительной смещение (1-й полупериод)

				// линия от начала пружины
				gr.DrawLine( spring, xSz+30, 5, xSz+30, 15);

				// первый полусегмент 
				gr.DrawLine( spring, xSz+30, 15, xSz+20, yy);
				for(int i=0;i<39; i++ )
				{
					if( i % 2 == 0 )
						gr.DrawLine( spring, xSz + 20, yy + (int)(ddy * i), xSz + 40, yy + (int)(ddy * (i+1)));
					else 
						gr.DrawLine( spring, xSz + 40, yy + (int)(ddy * i), xSz + 20, yy + (int)(ddy * (i+1)));
				}
				// последний полусегмент
				gr.DrawLine( spring, xSz + 40, yy + (int)(ddy * 39), xSz + 30, pos-10);

				// линия к грузу
				gr.DrawLine( spring, xSz + 30, pos-10, xSz + 30, pos);

				// рисуем груз
				gr.FillRectangle( sbMass, xSz + 25, pos - 5, 10, 10 );
			}
		}

		private void MassEditBox_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			pictureBox.Invalidate();
		}

		private void RigidityEditBox_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			pictureBox.Invalidate();
		}
	}
}
