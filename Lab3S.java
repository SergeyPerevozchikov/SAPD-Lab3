import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class Lab3S
{
	
	
	public static void main(String[] args)
	{
		try
		{
			Scanner input = new Scanner(System.in);
			System.out.print("Input string: ");
			String line = input.nextLine();
			System.out.print("Input string for search: ");
			String forSearch = input.nextLine();
			System.out.print("Input true if you want to find substring in any case: ");
			String flag = input.nextLine();
			input.close();
			if(flag.equals("true"))
			{
				forSearch.toLowerCase();
				line.toLowerCase();
			}
			if(args[0].equals("KMP"))
			{
				int index = KMP(line, forSearch);
				System.out.println(index);
			}
			else
			{
				if(args[0].equals("BM"))
				{
					int index = BM(line, forSearch);
					System.out.println(index);
				}
				else
				{
					System.out.println("Arguments error, type_of_search must be 'KMP'(Knuth-Morris-Pratt) or 'BM' (Boyer-Moore)");
				}
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Arguments error, input form: java Lab3S type_of_search");
			ex.printStackTrace();
		}
	}
	
	
	public static int KMP(String line, String forSearch)
	{
		int index = -1, compare = 0;
		line = forSearch + "^" + line;
		System.out.println(line);
		for(int i = forSearch.length() + 1; i < line.length(); i++)
		{
			String start = "";
			String end = "";
			for(int j = i; j > 0; j--)
			{
				if(line.charAt(j) == '^')
				{
					break;
				}
				start += line.charAt(i - j);
				end = line.charAt(j) + end;
				if(start.equals(end))
				{
					compare = start.length();
				}
				if(compare == forSearch.length())
				{
					index = j - forSearch.length() - 1;
					return index;
				}
			}
		}
		
		return index;
	}
	
	
	public static int BM(String line, String forSearch)
	{
		int index = -1;
		int[] d = new int[256];
		for(int i = 0; i < d.length; i++)
		{
			d[i] = forSearch.length();
		}
		for(int i = forSearch.length() - 1; i >= 0; i--)
		{
			d[forSearch.charAt(i)] = forSearch.length() - 1 - i;
		}
		for(int i = forSearch.length() - 1; i < line.length();)
		{
			if(line.charAt(i) == forSearch.charAt(forSearch.length() - 1))
			{
				boolean isFounded = true;
				int shift = forSearch.length() - 1;
				for(int j = 0; j < forSearch.length(); j++)
				{
					if(forSearch.charAt(forSearch.length() - 1 - j) != line.charAt(i - j))
					{
						shift = forSearch.length() - 1 - j;
						isFounded = false;
						break;
					}
				}

				if(isFounded)
				{
					index = i - (forSearch.length() - 1);
					break;
				}
				else
				{
					i+= d[forSearch.charAt(shift)];
				}
			}
			i+= d[line.charAt(i)];
		}
		
		return index;
	}
	
}