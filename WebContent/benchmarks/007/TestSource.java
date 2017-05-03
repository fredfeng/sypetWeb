public static boolean test0() throws Throwable {
    
    edu.utexas.sypet.test.Point nodeA = new edu.utexas.sypet.test.Point(2,3);
   
    edu.utexas.sypet.test.MyPoint mypt = new edu.utexas.sypet.test.MyPoint();
    mypt.setX(2);mypt.setY(3);

    edu.utexas.sypet.test.MyPoint res = convertPoint(nodeA);
    return (isEq(mypt, res) == true);
}


public static boolean isEq(edu.utexas.sypet.test.MyPoint pt1, edu.utexas.sypet.test.MyPoint pt2) {
    if(pt1 == null || pt2 == null) {
        return false;
    } else {
        return ((pt1.getX() == pt2.getX()) && (pt1.getY() == pt2.getY())) ;
    }
}

public static boolean test() throws Throwable {

    return test0(); 

}
