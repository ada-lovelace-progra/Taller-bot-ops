package bdRespuestas;

public class SimpsonsBD extends BaseDato{
	
	private int id;
	private String frase;
	
	public SimpsonsBD()
	{}
	
	public SimpsonsBD(int id, String frase)
	{
		this.id=id;
		this.frase=frase;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setFrase(String frase)
	{
		this.frase=frase;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public  String getFrase()
	{
		return this.frase;
	}
	
	
	//@SuppressWarnings("finally")
	public String getSimpsonsCaps()
	{
//		SimpsonsBD cap = null;
//		
//		try
//		{
//			@SuppressWarnings("deprecation")
//			Criteria cb = session.createCriteria(SimpsonsBD.class).
//			add(Restrictions.sqlRestriction("1=1 order by random()")).setMaxResults(1);
//			if(!cb.list().isEmpty())
//			{
//				cap = (SimpsonsBD) cb.uniqueResult();
//			}
//		}
//		catch (HibernateException e)
//		{
//			e.printStackTrace();
//			return null;
//		}
//		finally
//		{
//			return cap!=null?cap.frase:null;
//		}
		return "";
	}

}