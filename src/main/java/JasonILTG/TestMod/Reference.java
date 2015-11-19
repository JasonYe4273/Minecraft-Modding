package JasonILTG.TestMod;

import java.util.ArrayList;

public class Reference
{
	public static final String MOD_ID = "tm";
	public static final String MOD_NAME = "Test Mod";
	public static final String VERSION = "42.0";
	public static final String CLIENT_PROXY_CLASS = "JasonILTG.TestMod.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "JasonILTG.TestMod.proxy.CommonProxy";
	
	public static final int MAX_NUMBER = 100;
	
	public static ArrayList<Integer> factor( int n )
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for( int f = 1; f <= n; f++ )
			if( n % f == 0 ) factors.add( f );
		
		return factors;
	}
}
