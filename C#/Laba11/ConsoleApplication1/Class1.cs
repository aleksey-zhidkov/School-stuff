using System;

namespace Lab11
{
	class ConsoleApp
	{
		[STAThread]
		static void Main(string[] args)
		{
			uint[,] a = new uint[15,15];
			uint x=7; 
			uint y=7;
			// направление движения 
			int dir=0; // 0-вправо, 1-вниз, 2-влево, 3-вверх
			uint i,j;
			uint step=0;
			bool nextDir; 
			while(!((x==15) && (y==0)) && step<15*15 )
			{
				step++;
				a[x,y]=step;
				nextDir=false;
				switch(dir)
				{
					case 0:
						x=x+1;
						if( a[x,y+1]==0 || x > 13 ) 
							nextDir=true;
						break;
					case 1:
						y=y+1;
						if( a[x-1,y]==0 || y > 13 )
							nextDir = true;
						break;
					case 2:
						x=x-1;
						if( a[x,y-1]==0 || x < 1 ) 
							nextDir=true;
						break;
					case 3:
						y=y-1;
						if( a[x+1,y]==0 || y < 1 )
							nextDir = true;
						break;
				}
				if(nextDir)
					dir=(dir+1) % 4;
			}
			for( j=0;j<15;j++)
			{
				for( i=0;i<15;i++)
				{
					// Проверка является ли число простым
					bool simple = true; 
					uint num = a[i,j];
					if( num > 3 )
					{
						for( int k=2; k<=(int)Math.Sqrt(num); k=k+1 )
						{
							// если остаток от деления = 0, число делится нацело 
							if(num % k == 0)
							{
								simple=false;
								break;
							}
						}
					}
					Console.Write(((simple ? "*" : "") + num.ToString() ).PadLeft(5,' '));
				}
				Console.WriteLine();
			}
			Console.ReadLine();
		}
	}
}