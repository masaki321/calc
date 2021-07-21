import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String str = "";
        Pattern p = Pattern.compile("/0");

        do
        {

            System.out.println("式：");
            str = sc.nextLine();
            str = str.replaceAll(" ","");

            Matcher m = p.matcher(str);
            Matcher m2 = p.matcher(str);
            if(m.find())
            {
                System.out.println("ゼロで割り切れません。");
                continue;
            }

            String[] opr = str.split("(?<=[+--*/])+|(?=[+--*/])+");

            if(str.equals("end"))
            {
                break;
            }else if((opr.length % 2 == 0))
            {
                System.out.println("そんな式はありません。");
                continue;
            }

            Deque<String> stack = new ArrayDeque<>();
            Deque<Double> ans = new ArrayDeque<>();
            Deque<String> que = new ArrayDeque<>();

            for(String s:opr)//逆ポーランド記法の変換
            {
                switch(s)
                {
                    case "+":case "-":
                    while(!stack.isEmpty())
                    {
                        if(stack.peekFirst().equals("*")||stack.peekFirst().equals("/")||stack.peekFirst().equals("+")||stack.peekFirst().equals("-"))
                        {
                            que.offerLast(stack.removeFirst());
                            continue;
                        }
                    }
                    stack.addFirst(s);
                    break;

                    case "*": case "/":
                    while(true)
                    {
                        if(!stack.isEmpty())
                        {
                            if (stack.peekFirst().equals("*") || stack.peekFirst().equals("/"))
                            {
                                que.offerLast(stack.removeFirst());
                                continue;
                            } else
                            {
                                stack.addFirst(s);
                                break;
                            }
                        }else
                        {
                            stack.addFirst(s);
                            break;
                        }
                    }
                    break;

                    default:
                        que.offerLast(s);
                        break;
                }

            }
            while(!stack.isEmpty())
            {
                que.offerLast(stack.removeFirst());
            }
            //System.out.println(que);

            while(!que.isEmpty())
            {
                double arg1,arg2 = 0;
                switch(que.peekFirst())
                {
                    case "+":
                        try
                        {
                            que.removeFirst();
                            arg2 = ans.removeFirst();
                            arg1 = ans.removeFirst();
                            ans.addFirst(arg1 + arg2);
                        }catch (NoSuchElementException e) {}
                        break;
                    case "-":
                        try
                        {
                            que.removeFirst();
                            arg2 = ans.removeFirst();
                            arg1 = ans.removeFirst();
                            ans.addFirst(arg1 - arg2);
                        }catch (NoSuchElementException e) {}
                        break;
                    case "*":
                        try
                        {
                            que.removeFirst();
                            arg2 = ans.removeFirst();
                            arg1 = ans.removeFirst();
                            ans.addFirst(arg1 * arg2);
                        }catch (NoSuchElementException e) {}
                        break;
                    case "/":
                        try
                        {
                            que.removeFirst();
                            arg2 = ans.removeFirst();
                            arg1 = ans.removeFirst();
                            ans.addFirst(arg1 / arg2);
                        }catch (NoSuchElementException e) {}
                        break;
                    default:
                        ans.addFirst(Double.parseDouble(que.removeFirst()));
                        break;
                }
            }

            System.out.println("答え："+ans);

        }while(!str.equals("end"));
    }
}

