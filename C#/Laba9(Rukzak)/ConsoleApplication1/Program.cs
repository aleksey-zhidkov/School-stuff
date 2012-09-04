using System;
using System.Collections.Generic;
using System.Text;

namespace Labs
{
    class Unit
    {
        public int weight;
        public int usefullness;
        public int inuse;
        public int volume;

        public Unit(Unit u)
        {
            this.weight = u.weight;
            this.usefullness = u.usefullness;
            this.inuse = u.inuse;
            this.volume = u.volume;
        }

        public Unit() { }
    };

    class Laba8
    {
        static LinkedList<Unit> have = new LinkedList<Unit>();
        static LinkedList<Unit> store = new LinkedList<Unit>();
        static LinkedList<Unit> max = new LinkedList<Unit>();

        static int maxWeight;
        static int maxUFNess = 0;
        static int maxVolume;
        static int curWeight;
        static int curVolume;

        static int calculateUsefullness()
        {
            int res = 0;
            for (IEnumerator<Unit> e = have.GetEnumerator(); e.MoveNext(); )
            {
                res += e.Current.usefullness;
            }
            return res;
        }

        static void search()
        {
            for (IEnumerator<Unit> e = store.GetEnumerator(); e.MoveNext(); )
            {
                Unit u = e.Current;
                int curUFNess = calculateUsefullness();
                if (u.inuse == 0 &&
                    u.weight + curWeight <= maxWeight &&
                    u.volume + curVolume <= maxVolume)
                {

                    u.inuse = 1;
                    curWeight += u.weight;
                    curVolume += u.volume;
                    have.AddLast(u);
                    search();
                    u.inuse = 0;
                    curWeight -= u.weight;
                    curVolume -= u.volume;
                    have.RemoveLast();
                }
                else if (curUFNess > maxUFNess)
                {
                    max.Clear();
                    for (IEnumerator<Unit> e2 = have.GetEnumerator(); e2.MoveNext(); )
                        max.AddLast(new Unit(e2.Current));
                    maxUFNess = curUFNess;
                }
            }

        }

        static void Main(string[] args)
        {
            System.IO.StreamReader sr = new System.IO.StreamReader("D:/tmp/laba8.txt");
            string line;
            line = sr.ReadLine();
            maxWeight = int.Parse(line);
            line = sr.ReadLine();
            maxVolume = int.Parse(line);
            Console.WriteLine("Max weight: " + maxWeight);
            Console.WriteLine("Max volume: " + maxVolume);
            while (!sr.EndOfStream)
            {
                line = sr.ReadLine();
                string[] spl = line.Split(new char[] { ',' });
                Unit u = new Unit();
                u.inuse = 0;
                u.usefullness = int.Parse(spl[0]);
                u.volume = int.Parse(spl[1]);
                u.weight = int.Parse(spl[2]);
                store.AddLast(u);
            }
            search();
            Console.WriteLine("Results:");
            int curV = 0;
            int curW = 0;
            for (IEnumerator<Unit> e = max.GetEnumerator(); e.MoveNext(); )
            {
                Console.WriteLine(e.Current.usefullness + " : " +
                    e.Current.weight + " : " +
                    e.Current.volume);
                curV += e.Current.volume;
                curW += e.Current.weight;
            }
            Console.WriteLine("Total:");
            Console.WriteLine("  Usefullness: "+maxUFNess);
            Console.WriteLine("  Weight: " + curW);
            Console.WriteLine("  Volume: " + curV);
            Console.ReadLine();
        }
    }
}
