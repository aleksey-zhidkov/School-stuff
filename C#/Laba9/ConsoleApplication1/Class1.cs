using System;
using System.Collections;
using System.Text;
using System.IO;

namespace Labs
{
	class Unit
	{
		public int vesvezhi;
		public int stoimost;
		public int ispolz;
		public int volume;

		public Unit(Unit u)
		{
			this.vesvezhi = u.vesvezhi;
			this.stoimost = u.stoimost;
			this.ispolz = u.ispolz;
			this.volume = u.volume;
		}

		public Unit() { }
	};

	class Laba9
	{
		static ArrayList teknabor = new ArrayList();
		static ArrayList sklad = new ArrayList();
		static ArrayList max = new ArrayList();

		static int maxvesvezhi;
		static int maxSt = 0;
		static int maxVolume;
		static int curvesvezhi;
		static int curVolume;

		static int calculatestoimost()
		{
			int res = 0;
			for (IEnumerator e = teknabor.GetEnumerator(); e.MoveNext(); )
			{
				res += ((Unit) e.Current).stoimost;
			}
			return res;
		}

		static void search()
		{
			for (IEnumerator e = sklad.GetEnumerator(); e.MoveNext(); )
			{
				Unit u = (Unit) e.Current;
				int curSt = calculatestoimost();
				if (u.ispolz == 0 &&
					u.vesvezhi + curvesvezhi <= maxvesvezhi &&
					u.volume + curVolume <= maxVolume)
				{

					u.ispolz = 1;
					curvesvezhi += u.vesvezhi;
					curVolume += u.volume;
					teknabor.Add(u);
					search();
					u.ispolz = 0;
					curvesvezhi -= u.vesvezhi;
					curVolume -= u.volume;
					teknabor.RemoveAt(teknabor.Count-1);
				}
				else if (curSt > maxSt)
				{
					max.Clear();
					for (IEnumerator e2 = teknabor.GetEnumerator(); e2.MoveNext(); )
						max.Add(new Unit((Unit) e2.Current));
					maxSt = curSt;
				}
			}

		}

		static void Main(string[] args)
		{
			Console.WriteLine("Start");            
			StreamReader sr = new StreamReader("laba9.txt");
			string line;
			line = sr.ReadLine();
			maxvesvezhi = int.Parse(line);
			line = sr.ReadLine();
			maxVolume = int.Parse(line);
			Console.WriteLine("Max vesvezhi: " + maxvesvezhi);
			Console.WriteLine("Max volume: " + maxVolume);
			while (true)
			{
				line = sr.ReadLine();
				if (line == null)
					break;
				string[] spl = line.Split(new char[] { ',' });
				Unit u = new Unit();
				u.ispolz = 0;
				u.stoimost = int.Parse(spl[0]);
				u.volume = int.Parse(spl[1]);
				u.vesvezhi = int.Parse(spl[2]);
				sklad.Add(u);
			}
			search();
			Console.WriteLine("Results:");
			int curV = 0;
			int curW = 0;
			for (IEnumerator e = max.GetEnumerator(); e.MoveNext(); )
			{
				Console.WriteLine(((Unit) e.Current).stoimost + " : " +
					((Unit) e.Current).vesvezhi + " : " +
					((Unit) e.Current).volume);
				curV += ((Unit) e.Current).volume;
				curW += ((Unit) e.Current).vesvezhi;
			}
			Console.WriteLine("Total:");
			Console.WriteLine("  stoimost: "+maxSt);
			Console.WriteLine("  vesvezhi: " + curW);
			Console.WriteLine("  Volume: " + curV);
			Console.ReadLine();
		}
	}
}
