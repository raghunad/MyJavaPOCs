/* 
17. Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/
*/ 
import java.util.ArrayList;
import java.util.List;

public class LeetCode17 {
    private static final String[] KEYS = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current, String digits, int index) {
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        String letters = KEYS[digits.charAt(index) - '0'];
        for (char letter : letters.toCharArray()) {
            current.append(letter);
            backtrack(result, current, digits, index + 1);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public static void main(String[] args) {
        LeetCode17 solution = new LeetCode17();
        
        String digits1 = "23";
        List<String> combinations1 = solution.letterCombinations(digits1);
        System.out.println("Input : "+digits1+" --> Output:"+combinations1);

        String digits2 = "";
        List<String> combinations2 = solution.letterCombinations(digits2);
        System.out.println("Input : "+digits2+" --> Output:"+combinations2);

        String digits3 = "2";
        List<String> combinations3 = solution.letterCombinations(digits3);
        System.out.println("Input : "+digits3+" --> Output:"+combinations3);
    }
}