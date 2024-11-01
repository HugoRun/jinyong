package com.pub.operation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Operation {
    private boolean isRightFormat = true;

    public static void main(String[] args) {
        String str = "fjsdakl/jf\\ksajflks";
        Operation Operation = new Operation();
        //System.out.print(Operation.getResult(str));
    }

    public int getResult(String formula) {
        int returnValue = 0;
        try {
            returnValue = doAnalysis(formula);
        } catch (NumberFormatException nfe) {
            ////System.out.println("公式格式有误，请检查:" + formula);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isRightFormat) {
            ////System.out.println("公式格式有误，请检查:" + formula);
        }
        return returnValue;
    }

    private int doAnalysis(String formula) {
        int returnValue = 0;
        LinkedList<Integer> stack = new LinkedList<Integer>();

        int curPos = 0;
        String beforePart = "";
        String afterPart = "";
        String calculator = "";
        isRightFormat = true;
        while (isRightFormat && (formula.indexOf('(') >= 0 || formula.indexOf(')') >= 0)) {
            curPos = 0;
            for (char s : formula.toCharArray()) {
                if (s == '(') {
                    stack.add(curPos);
                } else if (s == ')') {
                    if (!stack.isEmpty()) {
                        beforePart = formula.substring(0, stack.getLast());
                        afterPart = formula.substring(curPos + 1);
                        calculator = formula.substring(stack.getLast() + 1, curPos);
                        formula = beforePart + doCalculation(calculator) + afterPart;
                        stack.clear();
                        break;
                    } else {
                        ////System.out.println("有未关闭的右括号！");
                        isRightFormat = false;
                    }
                }
                curPos++;
            }
            if (!stack.isEmpty()) {
                ////System.out.println("有未关闭的左括号！");
                break;
            }
        }
        if (isRightFormat) {
            returnValue = doCalculation(formula);
        }
        return returnValue;
    }

    private int doCalculation(String formula) {
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> operators = new ArrayList<String>();
        int curPos = 0;
        int prePos = 0;
        for (char s : formula.toCharArray()) {
            if (s == '+' || s == '-' || s == '*' || s == '/') {
                values.add(Double.parseDouble(formula.substring(prePos, curPos).trim()));
                operators.add("" + s);
                prePos = curPos + 1;
            }
            curPos++;
        }
        values.add(Double.parseDouble(formula.substring(prePos).trim()));
        char op;
        for (curPos = operators.size() - 1; curPos >= 0; curPos--) {
            op = operators.get(curPos).charAt(0);
            switch (op) {
                case '*':
                    values.add(curPos, values.get(curPos) * values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    break;
                case '/':
                    values.add(curPos, values.get(curPos) / values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    break;
            }
        }
        for (curPos = operators.size() - 1; curPos >= 0; curPos--) {
            op = operators.get(curPos).charAt(0);
            switch (op) {
                case '+':
                    values.add(curPos, values.get(curPos) + values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    break;
                case '-':
                    values.add(curPos, values.get(curPos) - values.get(curPos + 1));
                    values.remove(curPos + 1);
                    values.remove(curPos + 1);
                    operators.remove(curPos);
                    break;
            }
        }
        return values.get(0).intValue();
    }
}
