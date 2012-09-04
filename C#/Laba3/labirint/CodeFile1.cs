
using System;

class Labirint 
{
	static void Main() 
	{
		int[] masvh;
		masvh=new int[2];
		int[,] matr = {
						{-1,-1,-1, 1,-1,-1,-1,-1},
						{-1, 0, 0, 0, 0,-1, 0,-2},
						{-1,-1,-1,-1, 0,-1, 0,-1},
						{-2, 0,-1, 0, 0,-1, 0,-1},
						{-1, 0,-1, 0,-1,-1, 0,-1},
						{-1, 0, 0, 0, 0, 0, 0,-1},
						{-1, 0,-1,-1,-1,-1, 0,-1},
						{-1,-1,-1,-1,-1,-1,-1,-1}
					  };
		int step=1;
		int sch=0;
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if (matr[i,j]==1)
				{
					masvh[0]=i;
					masvh[1]=j;
				}
				if (matr[i,j]==-2)
				{
					sch++;
				}
			}
		}
		int istep=masvh[0];
		int jstep=masvh[1];
		int[] m = new int[sch];
		int[] ma = new int[sch];
		step++;
		if (matr[istep+1,jstep]==0)
		{
			matr[istep+1,jstep]=step;
		}
		if (matr[istep,jstep-1]==0)
		{
			matr[istep,jstep-1]=step;
		}
		if (matr[istep,jstep+1]==0)
		{
			matr[istep,jstep+1]=step;
		}
		int sc=0;
		bool f=false;
		while(sc<sch)
		{
			for (int i=1;i<8;i++)
			{
				for (int j=1;j<8;j++)
				{
					istep=i;
					jstep=j;
					if (matr[i,j]==step)
					{
						if (matr[istep+1,jstep]==0)
						{
							matr[istep+1,jstep]=step+1;
						}
						if (matr[istep-1,jstep]==0)
						{
							matr[istep-1,jstep]=step+1;
						}
						if (matr[istep,jstep-1]==0)
						{
							matr[istep,jstep-1]=step+1;
						}
						if (matr[istep,jstep+1]==0)
						{
							matr[istep,jstep+1]=step+1;
						}

						if (matr[istep+1,jstep]==-2)
						{
							matr[istep+1,jstep]=step+1;
							sc++;
							m[sc-1]=step+1;
							if (f==false)
							{
								ma[0]=istep+1;
								ma[1]=jstep;
								f=true;
							}
						}
						if (matr[istep-1,jstep]==-2)
						{
							matr[istep-1,jstep]=step+1;
							sc++;
							m[sc-1]=step+1;
							if (f==false)
							{
								ma[0]=istep-1;
								ma[1]=jstep;
								f=true;
							}
						}
						if (matr[istep,jstep-1]==-2)
						{
							matr[istep,jstep-1]=step+1;
							sc++;
							m[sc-1]=step+1;
							if (f==false)
							{
								ma[0]=istep;
								ma[1]=jstep-1;
								f=true;
							}
						}
						if (matr[istep,jstep+1]==-2)
						{
							matr[istep,jstep+1]=step+1;
							sc++;
							m[sc-1]=step+1;
							if (f==false)
							{
								ma[0]=istep;
								ma[1]=jstep+1;
								f=true;
							}
						}
					}
				}
			}
			step++;
		}
		Console.WriteLine("Существуют пути длинами:");
		for (int i=0;i<sch;i++)
		{
			Console.WriteLine(m[i]);
		}
		for(int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				if (matr[i,j]==-1||matr[i,j]>9||matr[i,j]==-2)
				{
					Console.Write(" "+matr[i,j]);
				}
				else
				{
					Console.Write("  "+matr[i,j]);
				}
			}
			Console.WriteLine();
		}
		Console.WriteLine();
		step=m[0];
		int i1=ma[0];
		int j1=ma[1];
		matr[i1,j1]=-3;

		step--;
		if (matr[i1+1,j1]==step)
		{
			matr[i1+1,j1]=-3;
			i1=i1+1;
		}
		if (matr[i1-1,j1]==step)
		{
			matr[i1-1,j1]=-3;
			i1=i1-1;
		}
		if (matr[i1,j1+1]==step)
		{
			matr[i1,j1+1]=-3;
			j1=j1+1;
		}
		while(step>0)
		{
			step--;
			if (matr[i1+1,j1]==step)
			{
				matr[i1+1,j1]=-3;
				i1=i1+1;
			}
			if (matr[i1-1,j1]==step)
			{
				matr[i1-1,j1]=-3;
				i1=i1-1;
			}
			if (matr[i1,j1-1]==step)
			{
				matr[i1,j1-1]=-3;
				j1-=j1-1;
			}
			if (matr[i1,j1+1]==step)
			{
				matr[i1,j1+1]=-3;
				j1=j1+1;
			}
		}
		matr[masvh[0],masvh[1]]=-3;
		Console.WriteLine("Самый короткий путь помечен звездочкой *");
		for(int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				if (matr[i,j]==-1||matr[i,j]>9||matr[i,j]==-2)
				{
					Console.Write(" "+matr[i,j]);
				}
				else
				{
					if (matr[i,j]==-3)
					{
						Console.Write("  *");
					}
					else
					{
						Console.Write("  "+matr[i,j]);
					}
				}
			}
			Console.WriteLine();
		}
		Console.Read();
	}
}