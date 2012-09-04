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
			Console.WriteLine("��������� � ����������� ���������: "+min.getName()+" : "+min.getZarplata());
			Console.WriteLine("��������� � ������������ ���������: "+max.getName()+" : "+max.getZarplata());
			Console.ReadLine();
		}

		private static void fillHash(Hashtable h) 
		{
			h.Add("������",new Sotrud("������ ��������� ��������",5786));
			h.Add("�������",new Sotrud("������� ������� �����������",9831));
			h.Add("������",new Sotrud("������ ������ ��������",82465));
			h.Add("�������",new Sotrud("������� �������� ����������",29873));
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