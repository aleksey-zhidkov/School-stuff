using System;
using System.Collections;

namespace laba7
{
	class Class1
	{
		[STAThread]
		static void Main(string[] args)
		{
			Hashtable h = new Hashtable();
			fillHash(h);
			IEnumerator en = h.Values.GetEnumerator();		   
			Sotrud min = new Sotrud("",int.MaxValue);
			Sotrud max = new Sotrud("",0);
			while(en.MoveNext()) 
			{
				Sotrud e = (Sotrud) en.Current;
				if(e.getZarplata() < min.getZarplata())
					min = e;
				if(e.getZarplata() > max.getZarplata())
					max = e;
			}
			h.Add("max",max);
			h.Add("min",min);
			Console.WriteLine("Сотрудник с минимальной зарплатой: "+min.getName()+" : "+min.getZarplata());
			Console.WriteLine("Сотрудник с максимальной зарплатой: "+max.getName()+" : "+max.getZarplata());
			Console.ReadLine();
		}

		private static void fillHash(Hashtable h) 
		{
			h.Add("Рогова",new Sotrud("Рогова Валентина Ивановна",5786));
			h.Add("Черевко",new Sotrud("Черевко Аркадий Григорьевич",9831));
			h.Add("Лаптев",new Sotrud("Лаптев Сергей Петрович",82465));
			h.Add("Сумкина",new Sotrud("Сумкина Светлана Алексеевна",29873));
		}
	}

	class Sotrud
	{
		private string name;
		private int Zarplata;

		public Sotrud(string _name, int _Zarplata) 
		{
			name = _name;
			Zarplata = _Zarplata;
		}

		public string getName() 
		{
			return name;
		}

		public int getZarplata() 
		{
			return Zarplata;
		}
	}		   
}