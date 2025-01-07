package com.matchacloud.basic.base.array;

/**
 * 矩阵
 */
public class Matrix {

    //行数
    private int row;
    //列数
    private int col;
    private double value[][];//值

    Matrix() {
        this.row = 1;
        this.col = 1;
        this.value = new double[this.row][this.col];
        this.value[0][0] = 0;
    }

    /**
     * 使用throws关键字，谁调用该方法谁就处理异常，
     * 处理异常方法2种：1.加try catch捕获 2.继续throws抛出
     * throws、throw 配套使用
     * throw、try 配套使用
     *
     * @param row
     * @param col
     * @throws NumException
     */
    Matrix(int row, int col) throws NumException {
        if (row >= 1 && col >= 1) {
            this.row = row;
            this.col = col;
            this.value = new double[this.row][this.col];
        } else {
            throw new NumException("矩阵的行数>=1且列数>=1");//手动抛出异常
        }
    }

    Matrix(int row, int col, int data[][]) throws NumException {
        if (row >= 1 && col >= 1) {
            this.row = row;
            this.col = col;
            this.value = new double[this.row][this.col];
            for (int i = 0; i < this.row; i++) {

                for (int j = 0; j < this.col; j++) {
                    this.value[i][j] = data[i][j];
                }
            }
        } else {
            throw new NumException("矩阵的行数>=1且列数>=1");
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    /**
     * @param rowLocation 第几行
     * @param colLocation 第几列
     * @return 矩阵该行列位置的值
     * @throws Num2Exception
     */
    double getValue(int rowLocation, int colLocation) throws Num2Exception {

        if (rowLocation <= this.row && colLocation <= this.col)
            return this.value[rowLocation - 1][colLocation - 1];
        else {
            throw new Num2Exception();
        }
    }

    void setData(int rowLocation, int colLocation, double value) throws Num2Exception {

        if (rowLocation <= this.row && colLocation <= this.col)
            this.value[rowLocation - 1][colLocation - 1] = value;
        else {
            throw new Num2Exception();
        }
    }

    /**
     * 矩阵相乘
     *
     * @param m1
     * @param m2
     * @throws MatchException
     */
    void multiply(Matrix m1, Matrix m2) throws MatchException {

        if (m1.getCol() == m2.getRow()) {
            for (int i = 0; i < m1.row; i++) {
                for (int j = 0; j < m2.col; j++) {
                    this.value[i][j] = 0;
                    for (int k = 0; k < m1.col; k++) {
                        this.value[i][j] = this.value[i][j] + (m1.value[i][k] * m2.value[k][j]);
                    }
                }
            }
        } else {
            throw new MatchException();
        }
    }

    void show() throws NumException {

        if (this.row < 1 || this.col < 1) {
            throw new NumException();
        }
        System.out.println("row " + this.row + "\tcol " + this.col);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.value[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
