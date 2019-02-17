
public class ReturnChar {
	
	
	
	public static String extra(int col)
	{
		switch(col)
		{
		case 0:
		case 1:
			return "ESC";
		case 2:
			return "F1";
		case 3:
			return "F2";
		case 4:
			return "F3";
		case 5:
			return "F4";
		case 6:
			return "F5";
		case 7:
			return "F6";
		case 8:
			return "F7";
		case 9:
			return "F8";
		case 10:
			return "F9";
		case 11:
			return "F10";
		case 12:
			return "F11";
		case 13:
			return "F12";
		case 14:
		case 15:
			return "PtrSc";
		case 16:
		case 17:
			return "Scrlk";
		case 18:
		case 19:
		case 20:
			return "Pause";
		}
		return "11";
	}
	public static String extra1(int col)
	{
		switch(col)
		{
		case 0:
			return "~";
		case 1:
			return "1";
		case 2:
			return "2";
		case 3:
			return "3";
		case 4:
			return "4";
		case 5:
			return "5";
		case 6:
			return "6";
		case 7:
			return "7";
		case 8:
			return "8";
		case 9:
			return "9";
		case 10:
			return "0";
		case 11:
			return "-";
		case 12:
			return "=";
		case 13:
			return "backspace";
		case 14:
			return "Ins";
		case 15:
			return "Home";
		case 16:
			return "PgUp";
		case 17:
			return "Num";
		case 18:
			return "/";
		case 19:
			return "*";
		case 20:
			return "-";
		}
		return "11";
	}
	public static String extra2(int col)
	{
		switch(col)
		{
		case 0:
			return "Tab";
		case 1:
			return "Q";
		case 2:
			return "W";
		case 3:
			return "E";
		case 4:
			return "R";
		case 5:
			return "T";
		case 6:
			return "Y";
		case 7:
			return "U";
		case 8:
			return "I";
		case 9:
			return "O";
		case 10:
			return "P";
		case 11:
			return "[";
		case 12:
			return "]";
		case 13:
			return "|";
		case 14:
			return "Del";
		case 15:
			return "End";
		case 16:
			return "PgDn";
		case 17:
			return "7";
		case 18:
			return "8";
		case 19:
			return "9";
		case 20:
			return "+";
		}
		return "11";
	}
	public static String extra3(int col)
	{
		switch(col)
		{
		case 0:
			
		case 1:
			return "Caps";
		case 2:
			return "A";
		case 3:
			return "S";
		case 4:
			return "D";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "J";
		case 9:
			return "K";
		case 10:
			return "L";
		case 11:
			return ";";
		case 12:
			return "'";
		case 13:
			
		case 14:
			
		case 15:
			
		case 16:
			return "Enter";
		case 17:
			return "4";
		case 18:
			return "5";
		case 19:
			return "6";
		case 20:
			return "+";
		}
		return "11";
	}
	public static  String extra4(int col)
	{
		switch(col)
		{
		case 0:
			
		case 1:
			
		case 2:
			return "Shift";
		case 3:
			return "Z";
		case 4:
			return "X";
		case 5:
			return "C";
		case 6:
			return "V";
		case 7:
			return "B";
		case 8:
			return "N";
		case 9:
			return "M";
		case 10:
			return ",";
		case 11:
			return ".";
		case 12:
			return "/";
		case 13:
			
		case 14:
			
		case 15:
			return "Shift";
		case 16:
			return "上";
		case 17:
			return "1";
		case 18:
			return "2";
		case 19:
			return "3";
		case 20:
			return "Enter";
		}
		return "11";
	}
	public static  String extra5(int col)
	{
		switch(col)
		{
		case 0:
			
		case 1:
			return "Ctrl";
		case 2:
			return "Win";
		case 3:
			return "Alt";
		case 4:
			
		case 5:
			
		case 6:
			
		case 7:
			
		case 8:
			
		case 9:
			return "空格";
		case 10:
			return "Alt";
		case 11:
			return "Fn";
		case 12:
			return "未知";
		case 13:
			
		case 14:
			return "Ctrl";
		case 15:
			return "左";
		case 16:
			return "下";
		case 17:
			return "右";
		case 18:
			return "0";
		case 19:
			return "。";
		case 20:
			return "Enter";
		}
		return "11";
	}
		public static  String getKey(double x,double y)
		{
			
			int col,row;
			col=(int) (x/0.04762);
			row=(int) (y/0.1667);
			switch(row)
			{
			case 0:
				return extra(col);
			case 1:
				return extra1(col);
			case 2:
				return extra2(col);
			case 3:
				return extra3(col);
			case 4:
				return extra4(col);
			case 5:
				return extra5(col);
			}

			return "11";
		}

		public static void main(String[] args) {
			String ans = ReturnChar.getKey(0.82,0.22);
			System.out.println(ans);
		}

}