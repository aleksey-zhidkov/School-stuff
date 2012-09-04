using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace Lab7
{
	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class MainForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.TextBox ParameterA_TextBox;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.PictureBox PictureBox;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		int[] YMax;
		int[] YMin;

		double a=1;
		
		const int NoValue=7777;

		public MainForm()
		{
			InitializeComponent();

			YMax = new int[PictureBox.Size.Width];
			YMin = new int[PictureBox.Size.Width];
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
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
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.ParameterA_TextBox = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.PictureBox = new System.Windows.Forms.PictureBox();
			this.SuspendLayout();
			// 
			// ParameterA_TextBox
			// 
			this.ParameterA_TextBox.Location = new System.Drawing.Point(32, 0);
			this.ParameterA_TextBox.MaxLength = 3;
			this.ParameterA_TextBox.Name = "ParameterA_TextBox";
			this.ParameterA_TextBox.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
			this.ParameterA_TextBox.Size = new System.Drawing.Size(136, 20);
			this.ParameterA_TextBox.TabIndex = 0;
			this.ParameterA_TextBox.Text = "1";
			this.ParameterA_TextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.ParameterA_TextBox_KeyPress);
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(8, 3);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(14, 16);
			this.label1.TabIndex = 1;
			this.label1.Text = "A";
			// 
			// PictureBox
			// 
			this.PictureBox.Location = new System.Drawing.Point(8, 32);
			this.PictureBox.Name = "PictureBox";
			this.PictureBox.Size = new System.Drawing.Size(536, 416);
			this.PictureBox.TabIndex = 2;
			this.PictureBox.TabStop = false;
			this.PictureBox.Paint += new System.Windows.Forms.PaintEventHandler(this.PictureBox_Paint);
			// 
			// MainForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(546, 453);
			this.Controls.Add(this.PictureBox);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.ParameterA_TextBox);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.MinimizeBox = false;
			this.Name = "MainForm";
			this.Text = "Графинк функции";
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// просто 2х-мерная точка
		/// </summary>
		class Point
		{
			public int x;
			public int y;
		}

		/// <summary>
		/// Рисование линии методом Брезенхема, делается самостоятельно чтобы 
		/// учитывать "плавающий горизонт"
		/// </summary>
		/// <param name="gr">графический контекст</param>
		/// <param name="p1">начало отрезка</param>
		/// <param name="p2">конец отрезка</param>
		private void DrawLine( Graphics gr, Point p1, Point p2 )
		{
			Pen UpColor = new Pen( Color.LightGreen ); // цвет верхней грани
			Pen DownColor = new Pen( Color.LightGray ); // цвет нижней грани
			Pen ErColor = new Pen( Color.Red ); 
			Pen PrColor = new Pen( Color.Yellow );

			int dx = Math.Abs( p2.x-p1.x );
			int dy = Math.Abs( p2.y-p1.y );

			int sx = p2.x >= p1.x ? 1 : -1;
			int sy = p2.y >= p1.y ? 1 : -1;

			if( dy <= dx )
			{
				// стандартный вариант
				int d = -dx;
				int d1 = dy * 2;
				int d2 = (dy-dx)*2;

				for( int x=p1.x, y=p1.y, i=0; i<=dx; i++, x+=sx )
				{
					if( x>-1 && x<PictureBox.Size.Width )
					{
						if( YMin[x] == NoValue )
						{
							gr.DrawLine( UpColor, x,y, x+1, y );
							YMin[x] = YMax[x] = y;
						}
						else if( y < YMin[x] ) 
						{
							gr.DrawLine( UpColor, x,y, x+1, y );
							YMin[x] = y;
						}
						else if( y > YMax[x] ) 
						{
							gr.DrawLine( DownColor, x,y, x+1, y );
							YMax[x] = y;
						}
					}

					if( d>0 )
					{
						d += d2;
						y += sy;
					}
					else 
						d +=d1;
				}
			}
			else 
			{
				// для того чтобы небыло выпавщих пикселов

				int d = -dy;
				int d1 = dx * 2;
				int d2 = (dx-dy)*2;

				int m1=0;
				int m2=0;

				if( p1.x>-1 && p1.x<PictureBox.Size.Width )
				{
					m1 = YMin[p1.x];
					m2 = YMax[p1.x];
				}


				for( int x=p1.x, y=p1.y, i=0; i<=dy; i++, y+=sy )
				{
					if( x>-1 && x<PictureBox.Size.Width )
					{
						if( YMin[x] == NoValue )
						{
							gr.DrawLine( UpColor, x,y, x+1, y );
							YMin[x] = YMax[x] = y;
						}
						else if( y < m1 ) 
						{
							gr.DrawLine( UpColor, x,y, x+1, y );
							if( y < YMin[x] ) YMin[x] = y;
						}
						else if( y > m2  ) 
						{
							gr.DrawLine( DownColor, x,y, x+1, y );
							if( y > YMax[x] ) YMax[x] = y;
						}
					}

					if( d>0 )
					{
						d += d2;
						x += sx;
						if( x>-1 && x<PictureBox.Size.Width )
						{
							m1 = YMin[x];
							m2 = YMax[x];
						}
					}
					else 
						d +=d1;
				}

			}
		}

		private void PlotSurface( Graphics gr, double x1, double y1, double x2, double y2, double fmin, double fmax, int n1, int n2)
		{
			
			Point[] CurLine = new Point[n1];
			Point[] NextLine = new Point[n1];

			double phi = 30 * Math.PI / 180;
			double psi = 60 * Math.PI / 180;
			double cphi = Math.Cos( phi );
			double sphi = Math.Sin( phi );
			double cpsi = Math.Cos( psi );
			double spsi = Math.Sin( psi );

			double[] e1 = {cphi, sphi, 0 };
			double[] e2 = {spsi * sphi, -spsi*cphi, cpsi };

			double hx = (x2-x1) / n1;
			double hy = (x2-x1) / n2;

			double xmin = ( e1[0]>=0 ? x1 : x2 ) * e1[0] + ( e1[1]>=0 ? y1 : y2 ) * e1[1];
			double xmax = ( e1[0]>=0 ? x2 : x1 ) * e1[0] + ( e1[1]>=0 ? y2 : y1 ) * e1[1];
			double ymin = ( e2[0]>=0 ? x1 : x2 ) * e2[0] + ( e2[1]>=0 ? y1 : y2 ) * e2[1];
			double ymax = ( e2[0]>=0 ? x2 : x1 ) * e2[0] + ( e2[1]>=0 ? y2 : y1 ) * e2[1];

			double x,y;
			int i,j,k;

			if( e2[2] >= 0 ) 
			{
				ymin += fmin * e2[2];
				ymax += fmax * e2[2];
			}
			else 
			{
				ymin += fmax * e2[2];
				ymax += fmin * e2[2];
			}

			//scale image to (5,5, width-5, height-5)
			double ax=5-(PictureBox.Size.Width-10) * xmin/(xmax-xmin);
			double bx=(PictureBox.Size.Width-10)/(xmax-xmin);

			double ay=(PictureBox.Size.Height-5)+(PictureBox.Size.Height-10) * ymin/(ymax-ymin);
			double by=-(PictureBox.Size.Height-10)/(ymax-ymin);

			for( i=0; i<PictureBox.Size.Width; i++ )
				YMin[i] = YMax[i] = NoValue;

			Pen red = new Pen(Color.Red);
			gr.DrawLine(red, (int)(ax+bx*( x1*e1[0] + y1*e1[1])), (int)(ay+by*( x1*e2[0] + y1*e2[1]) ),
				(int)(ax+bx*( x1*e1[0] + y2*e1[1])), (int)(ay+by*( x1*e2[0] + y2*e2[1]) ) );

			gr.DrawLine(red, (int)(ax+bx*( x1*e1[0] + y1*e1[1])), (int)(ay+by*( x1*e2[0] + y1*e2[1]) ),
				(int)(ax+bx*( x2*e1[0] + y1*e1[1])), (int)(ay+by*( x2*e2[0] + y1*e2[1]) ) );

			gr.DrawLine(red, (int)(ax+bx*( x1*e1[0] + y1*e1[1])), (int)(ay+by*( x1*e2[0] + y1*e2[1] + fmin*e2[2]) ),
				(int)(ax+bx*( x1*e1[0] + y1*e1[1])), (int)(ay+by*( x1*e2[0] + y1*e2[1] + fmax*e2[2]) ) );

			for( i=0; i<n1; i++)
			{
				x = x1 + i*hx;
				y = y1 + (n2-1)*hy;

				CurLine[i] = new Point();
				NextLine[i] = new Point();
				CurLine[i].x = (int)(ax+bx*( x*e1[0] + y*e1[1] ));
				CurLine[i].y = (int)(ay+by*( x*e2[0] + y*e2[1] + f(x,y)*e2[2]));
			}

			for( i= n2-1; i> -1; i-- )
			{
				for( j=0; j<n1-1; j++ )
				{
					DrawLine( gr, CurLine[j], CurLine[j+1] );
				}

				if( i > 0 )
				{
					for( j=0; j<n1; j++)
					{
						x = x1 + j*hx;
						y = y1 + (i-1)*hy;

						NextLine[j].x = (int)(ax+bx*( x*e1[0] + y*e1[1] ));
						NextLine[j].y = (int)(ay+by*( x*e2[0] + y*e2[1] + f(x,y)*e2[2]));

						DrawLine( gr, CurLine[j], NextLine[j] );
						CurLine[j].x =  NextLine[j].x;
						CurLine[j].y =  NextLine[j].y;
					}
				}
			}

			CurLine = null;
		}
		/// функция z=f(x,y) график которой мы строим
		private double f( double x, double y )
		{
			double r = x*x + y*y;
			return a * Math.Sqrt( r);
		}

		private void PictureBox_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			Graphics gr = e.Graphics;
			gr.Clear(Color.Black);

			double d = 3;
			
			double k = 1.5*d*a;
			PlotSurface( gr, -d, -d, d, d, k<0 ? k : 0, k<0 ? 0 : k, 30, 30 );
		}

		private void ParameterA_TextBox_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
		{
			try 
			{
				if( !ParameterA_TextBox.Text.Equals( "" ) )
					a = Double.Parse(ParameterA_TextBox.Text);
				else 
					a = 0;
			}
			catch( Exception ex )
			{
				a = 0; 
			}

			PictureBox.Invalidate();
		}

		[STAThread]
		static void Main() 
		{
			Application.Run(new MainForm());
		}

	}
}
