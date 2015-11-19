package com.JasonILTG.NumbersMod;

import java.util.ArrayList;

public class Reference
{
	public static final String MOD_ID = "nm";
	public static final String MOD_NAME = "Numbers Mod";
	public static final String VERSION = "1.0";
	public static final String CLIENT_PROXY_CLASS = "com.JasonILTG.NumbersMod.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.JasonILTG.NumbersMod.proxy.CommonProxy";
	
	public static final int MAX_NUMBER = 100;
	
	public static ArrayList<Integer> factor( int n )
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for( int f = 1; f <= n; f++ )
			if( n % f == 0 ) factors.add( f );
		
		return factors;
	}
}
