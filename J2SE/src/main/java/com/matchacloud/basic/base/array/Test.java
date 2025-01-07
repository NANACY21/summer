package com.matchacloud.basic.base.array;

/**
 * 数组
 * 异常
 */
public class Test {

    /**
     * main最先进栈，main调用c，c再进栈，异常会导致程序的退出，均出栈
     *
     * @param args
     */
    public static void main(String[] args) {
        int data1[][] = {{2, 4, 6}, {2, 5, 5}, {2, 6, 7}};
        int data2[][] = {{12, 3, 5, 2}, {2, 4, 3, 2}, {8, 9, 7, 32}};


        /**编译时异常要加try。运行时异常不处理
         * 异常不中断程序的执行，错误中断程序的执行
         */
        try {
            Matrix m1 = new Matrix(0, 3, data1);
            Matrix m2 = new Matrix(3, 4, data2);
            Matrix m3 = new Matrix(3, 4);

            System.exit(0);//try中有exit则不执行finally

            System.out.println(m1.getValue(1, 1));
            m1.setData(1, 1, 10);
            m3.multiply(m1, m2);
            //finally在return前执行
            return;

        } catch (NumException e) {
            System.out.println(e.getMessage());
        } catch (MatchException e) {
            System.out.println(e);
        } catch (Num2Exception e) {
            System.out.println(e);
        } finally {

        }
    }
}
