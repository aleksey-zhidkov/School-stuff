using System;

class Naznachenie
{
	static void Main()
	{
		int n;
		int[][] mas;
		Console.WriteLine("¬ведите кол-во человек ");
		n=Console.Read();
		mas = new int[n][];
		Random r=new Random();
		r.Next(n);
		for(int i=0; i<n; i++)
		{
			mas[i] = new int[n];
			for (int j=0; j<n; j++)
			{
				mas[i][j]=r.Next();
			}
		}
		for(int i=0; i<n; i++)
		{
			for (int j=0; j<n; j++)
			{
				Console.Write(" "+mas[i][j]);
			}
			Console.WriteLine();
		}
		Console.Read();
	}
}