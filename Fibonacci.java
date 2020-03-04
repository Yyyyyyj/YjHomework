public class Fibonacci {

    //求斐波那契数列中的第n个数(从0开始计数)
    private static int of(int n){
        if(n <= 1){
            return n;
        }
        //每个数字只和它前面的两个数字有关，用a,b分别记录当前数字的前两个数和前一个数
        int a = 0;
        int b = 1;
        //初始化a,b为斐波那契数列最初的两个数，从第三个数开始依次更新
        for(int i = 2; i <= n; i++) {
            //计算当前数
            int sum = a + b;
            //更新下一个数的a,b
            a = b;
            b = sum;
        }
        //更新后b为下一个数的前一个数，即当前所求数
        return b;
    }

    public static void main(String[] args) {
        int res = 1;
        //输出值res最小值为1，所以从数列中第一个不小于1的位置开始打印
        int i = 1;
        while(res <= 200){
            System.out.println("Fibonacci.of(" + i + ") == " + res);
            i++;
            res = Fibonacci.of(i);
        }
    }
}
