import org.junit.Test;

/**
 * Created by Panda on 2016/12/2.
 */
public class TestExam {
    @Test
    public void test() throws Exception{
        int a=-67,b=116,c=78;
        int d=~a|b&c;
        System.out.println(d);
        boolean check = check();
        System.out.println(check);
    }



    private int x;
    private int y;

    public void setX(int i) {
        this.x = i;
    }

    public void setY(int i) {
        this.y = i;
    }

    public synchronized boolean check() {
        return x!=y;
    }

    public synchronized void setdd(int i) {
        setX(i);
        setY(i);
    }
}
