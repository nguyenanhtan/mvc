package project.darp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import javax.swing.text.html.Option;
public class Debug {

//	public static void main(String[] args) {
//		int nQueen = 3;
//		CPModel m = new CPModel();
//		IntegerVariable [] it = Choco.makeIntVarArray("itt", nQueen);
//		System.out.println(it.length);
//		for(int i = 0; i < it.length;i++)
//		{
//			m.addVariable(it[i]);
//		}
//		m.addConstraint(Choco.allDifferent(it));
//		for(int i = 0;i < nQueen;i++)
//		{
//			m.addConstraint(Choco.leq(it[i],nQueen));
//			m.addConstraint(Choco.leq(0,it[i]));
//		}
//		CPSolver s = new CPSolver();
//		s.read(m);
//		int itr = 0;
//		s.solve();
//		do
//		{
//			System.out.print(itr+":\t");
//			
//			for(int i = 0;i < nQueen;i++)
//			{
//				System.out.print(s.getVar(it[i]).getVal()+"\t");
//			}
//			System.out.println();
//			//s.nextSolution();
//			itr++;
//		}while(s.nextSolution() == true && itr < 100);
//	}
	public static void display(Map<Integer,Set<Integer>> m)
	{
		Set setEntry = m.entrySet();
		Iterator it = setEntry.iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Set<Integer>> me = (Map.Entry<Integer, Set<Integer>>)it.next();
			System.out.print(me.getKey()+" --> ");
			display(me.getValue());
			System.out.println();
		}
	}
	public static void display(Set<Integer> s)
	{
		Iterator<Integer> it = s.iterator();
		while(it.hasNext())
		{
			System.out.print(it.next()+"; ");
		}
	}
	public static void display(float f[][])
	{
		for(int i = 0;i < f.length;i++)
		{
			for(int j = 0;j < f[0].length;j++)
			{
				System.out.print(f[i][j]+"    ");
			}
			System.out.println();
		}
	}
}
