using System;
//using System.Collections.Generic;
using System.Text;

namespace Project1
{
	class Uzel
	{
		public Uzel left;
		public Uzel right; 
		public char value;
	}
	class Class1
	{
		static void Main(string[] args)
		{
			Uzel root = new Uzel();
			root.value = Console.ReadLine()[0];
			while (true) 
			{
				string s =Console.ReadLine();
				if (s.Equals("quit"))
					break;                
				AddUzel(root,s.ToCharArray()[0]);
			}
			obhod(root);
			Console.ReadLine();
		}

		private static void AddUzel(Uzel u, char v)
		{
			if (u.value < v && u.right == null)
			{
				Uzel subu = new Uzel();
				subu.value = v;
				u.right = subu;
			}
			else if (u.value >= v && u.left == null)
			{
				Uzel subu = new Uzel();
				subu.value = v;
				u.left = subu;
			}
			else if (u.value < v && u.right != null)
			{
				AddUzel(u.right,v);
			}
			else if (u.value >= v && u.left != null)
			{
				AddUzel(u.left, v);
			}
			return;
		}

		private static void obhod(Uzel u)
		{
			if (u.left != null)
				obhod(u.left);
			Console.WriteLine(u.value);
			if (u.right != null)
				obhod(u.right);
		}
	}
}