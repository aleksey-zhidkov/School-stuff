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
			this.label1.Text = "��������� �������:";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(8, 8);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(80, 16);
			this.label2.TabIndex = 5;
			this.label2.Text = "����� �����:";
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
			this.label3.Text = "�.";
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(224, 32);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(32, 17);
			this.label4.TabIndex = 7;
			this.label4.Text = "�/�^2";
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
			this.Text = "����� ����";
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
			double mass=0;  // ����� 
			double rigidity=0;  // ���������

			// ����� Double.Parse ����� �������� ����������(exeption)
			// ���� �� ����� ����� ������������ ������ 
			try
			{
				/// ����� ����������� �� ����� �����
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
				// ��������� �������
				double dx = g * mass / rigidity;

				int ySz = pictureBox.Size.Height;
				int xSz = 110; // ���������� �� �����

				// ���� ����� 
				Pen scale = new Pen( Color.Black );  // ���� �����
				Pen spring = new Pen( Color.Blue );  // ���� �������
				Pen redLine = new Pen( Color.Red );  // ���� ����� - ������ �� �����
				redLine.DashStyle = DashStyle.Dash;  // ������� ���������� ����� 
				
				// ���� �������
				SolidBrush sbPoint = new SolidBrush( Color.Yellow );  // ���� ����� ��������� �������
				SolidBrush sbMass = new SolidBrush( Color.Gray ); // ���� �����

				// ������ ����� 
				gr.DrawLine( scale, xSz, 0, xSz, ySz );

				int sc = (int)Math.Log10( dx ); // ������� ����� 10^sc

				// ������ ������ ������ � ������������� ������
				StringFormat stF = new StringFormat();
				stF.Alignment = StringAlignment.Far;

				// ������ ����� �� 0 �� 100 � ����� 5
				// �������� ������� ������������ ������ ������� ��������� = 5 + 60(= 40 + 10 + 10) = 65px
				for( int y=0;y<301; y+=5)
				{
					uint dd=2; // ����� ������ �� ����� 

					if( y % 30 == 0 ) // ����� ������� 
					{
						// ��������� "�����" �� ����� ������� �����
						double t= (y/30) * Math.Pow( 10, sc); 

						gr.DrawString( t.ToString(), this.Font, new SolidBrush( Color.Black), new RectangleF(xSz-100, y+65-7, 90, 14), stF );
						dd=8;
					}
					else if( y % 15 == 0  ) // 1/2 ������ �������
					{
						dd = 4;
					}

					// ������ �����
					gr.DrawLine( scale, xSz-dd, y+65, xSz+dd, y+65 );
				}

				// ������ ����� ��������� �������
				gr.FillEllipse( sbPoint, xSz + 25, 0, 10, 10 );

				// �������� ����� � ������� ������ ��������� ����
				int pos = (int)(dx/Math.Pow( 10, sc-1))*3+65;

				// ������ ������� ����� � ������� �� ���
				gr.DrawLine( redLine, xSz-50, pos, xSz+50, pos );
				gr.DrawString( dx.ToString(), this.Font, new SolidBrush( Color.Black), xSz+50, pos-7 );

				
				// ������ ��������, ���������� ��������� ������� = 40px
				// ���������� �� 1�� �������� �� ������ ������� � �� ���������� �������� �� �����  = 10px
				double ddy = (double)(pos-25) / 40; 
				int yy = (int)(15 + ddy / 2); // �������������� �������� (1-� ����������)

				// ����� �� ������ �������
				gr.DrawLine( spring, xSz+30, 5, xSz+30, 15);

				// ������ ����������� 
				gr.DrawLine( spring, xSz+30, 15, xSz+20, yy);
				for(int i=0;i<39; i++ )
				{
					if( i % 2 == 0 )
						gr.DrawLine( spring, xSz + 20, yy + (int)(ddy * i), xSz + 40, yy + (int)(ddy * (i+1)));
					else 
						gr.DrawLine( spring, xSz + 40, yy + (int)(ddy * i), xSz + 20, yy + (int)(ddy * (i+1)));
				}
				// ��������� �����������
				gr.DrawLine( spring, xSz + 40, yy + (int)(ddy * 39), xSz + 30, pos-10);

				// ����� � �����
				gr.DrawLine( spring, xSz + 30, pos-10, xSz + 30, pos);

				// ������ ����
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
