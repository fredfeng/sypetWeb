
public class TestSource19 {
public static boolean test0() throws Throwable {
		return (Solution19.getDayFromString("2015/10/21", "yyyy/MM/dd") == 21);
}
	
public static boolean test1() throws Throwable {
		return (Solution19.getDayFromString("2013/6/13", "yyyy/MM/dd") == 13);
}
	
public static boolean test() throws Throwable {
		return test0() && test1();
}
}