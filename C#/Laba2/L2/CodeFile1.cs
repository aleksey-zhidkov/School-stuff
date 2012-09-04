using System;

class magic
{
	static void Main() 
	{   
		int n = 3;
		int[][] mas;
		mas = new int[5][];
		int max = n*n;
		for(int i=0; i<5; i++)
		{
			mas[i] = new int[5];
			for(int j=0; j<5; j++)
			{
				mas[i][j] = 0;			
			}
		}
		int k1=2, k2=0;
		int m1=5, m2=3;
		int s=1;
		while (s<=max)
		{
			for(int i=k1; i<m1; i++)
			{
				for(int j=k2; j<m2; j++)
				{
					mas[i][j] = s;
					i++;
					s++;
				}
				k1--;
				k2++;
				m1--;
				m2++;
			}
		}
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<5; j++)
			{
				Console.Write(" "+mas[i][j]);			
			}
			Console.WriteLine();
		}
		Console.WriteLine();
		for(int j=0; j<5; j++)
		{
			if (mas[0][j] != 0)
			{
				mas[0+n][j]=mas[0][j];
			}
		}
		for(int j=0; j<5; j++)
		{
			if (mas[4][j] != 0)
			{
				mas[4-n][j]=mas[4][j];
			}
		}
		for(int i=0; i<5; i++)
		{
			if (mas[i][0] != 0)
			{
				mas[i][0+n]=mas[i][0];
			}
		}
		for(int i=0; i<5; i++)
		{
			if (mas[i][4] != 0)
			{
				mas[i][4-n]=mas[i][4];
			}
		}
		for(int i=1; i<4; i++)
		{
			for(int j=1; j<4; j++)
			{
				Console.Write(" "+mas[i][j]);			
			}
			Console.WriteLine();
		}
		Console.Read();
	}
}