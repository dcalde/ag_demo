package join.ag.demo.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Deque;
import java.util.LinkedList;

@Service
public class TaskService {

    /**
     * Checks if brackets in a string are balanced
     * <p>Brackets in a string are considered to be balanced if the following criteria are met:</p>
     * <ul><li>For every opening bracket (i.e., (, {, or [), there is a matching closing bracket (i.e., ), }, or ]) of
     *      the same type (i.e., ( matches ), { matches }, and [ matches ]). An opening bracket must appear before (to
     *      the left of) its matching closing bracket. For example, ]{}[ is not balanced.
     *  </li>
     *  <li>No unmatched braces lie between some pair of matched bracket. For example, ({[]}) is balanced, but {[}]
     *      and [{)] are not balanced.</li></ul>
     * @param input contains string to be checked. Must only contain any of these characters: ([{}])
     * @return true if the input has balanced brackets
     */
    public boolean checkIfBracketsBalanced(@NotNull String input) {
        /*
        using LinkedList instead Stack as Stack uses unnecessary synchronized method and is backed by an array
        which is more expensive to update
        */
        Deque<Character> brackets = new LinkedList<>();
        for(char c : input.toCharArray()) {
            switch(c) {
                case '(':
                case '[':
                case '{':
                    brackets.push(c);
                    break;

                case ')':
                case ']':
                case '}':
                    //if our stack is empty, we had more closing than opening brackets
                    if(brackets.isEmpty()) {
                        return false;
                    }

                    //check if our most recent opening bracket matches our current closing bracket
                    char lastOpening = brackets.pop();
                    if(c != closingBracketFor(lastOpening)) {
                        return false;
                    }
                    break;
            }
        }
        return brackets.isEmpty();
    }

    /***
     * Return the expected closing bracket for a given opening bracket
     * @param openingbracket the opening bracket to find the respective closing bracket for. Expected: ([{
     * @return e.g. )]}
     */
    private char closingBracketFor(char openingbracket) {
        //use switch (or if statements) instead if Hashmap, as this should be faster to execute for many executions
        switch (openingbracket) {
            case '(':
                return ')';
            case '[':
                return ']';
            case '{':
                return '}';
            default:
                throw new IllegalArgumentException("Invalid Input <"+openingbracket+">. Expected: ([{");
        }
    }

}
