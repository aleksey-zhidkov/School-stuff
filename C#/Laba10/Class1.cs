using System;
using System.Collections;
using System.Text;
using System.IO;

namespace Labs
{
	class Laba10
	{
		static int[,] graph;
		static int[] path;
		static int[] mpath;
		static int minPath = int.MaxValue;
		static int curPath = 0;
		static int pointA;
		static int pointB;
		static int deph;

		static void search(int peak)
		{
			graph[peak, peak] = -1;
			path[deph] = peak+1;            
			for (int j = 0; j < graph.GetLength(0); j++)            
			{
				curPath += graph[peak, j];
				if ((j == pointB) && (curPath < minPath) && graph[peak,j] > 0)
				{
					path[deph + 1] = pointB+1;
					path.CopyTo(mpath, 0);
					path[deph + 1] = 0;
					minPath = curPath;
				}
				else if ((graph[peak,j] > 0) && (graph[j, j] == 0))
				{
					deph++;
					search(j);
					deph--;
				}
				curPath -= graph[peak, j];
			}
			path[deph] = 0;
			graph[peak, peak] = 0;
		}

		static void Main(string[] args)
		{
			StreamReader sr = new StreamReader("laba10.txt");
			string line = sr.ReadLine();
			int size = int.Parse(line);
			graph = new int[size, size];
			path = new int[size];
			mpath = new int[size];
			line = sr.ReadLine();
			pointA = int.Parse(line)-1;
			line = sr.ReadLine();
			pointB = int.Parse(line)-1;
			while (true)
			{
				line = sr.ReadLine();
				if (line == null)
					break;
				string[] spl = line.Split(new char[] { ',' });
				int i = int.Parse(spl[0]);
				int j = int.Parse(spl[1]);
				int c = int.Parse(spl[2]);
				graph[i-1,j-1] = c;
			}
			search(pointA);
			Console.WriteLine("Graph:");
			for(int i=0; i < size; i++) {
				for(int j=0;j<size;j++) 
				{
					Console.Write(graph[i,j]+" ");
				}
				Console.WriteLine();
            }
			Console.WriteLine("Path:");
			for (int i = 0; i < size && mpath[i]>0; i++)
			{
				Console.Write(mpath[i] + ", ");
			}
			Console.ReadLine();
		}
	}
}
