/**
 * Check if a string of parentheses is balanced.
 *
 * @param str the String to check.
 * @returns true if the string is balanced.
 */
export const isBalanced = (str: string): boolean => {
  const stack = new Array<string>();
  for (let i = 0; i < str.length; i++) {
    const char = str.charAt(i);
    if (isOpen(char)) {
      stack.unshift(char);
    } else {
      const lastStack = stack.shift();
      if (!lastStack || !isOpen(lastStack)) {
        return false;
      }
    }
  }

  return stack.length === 0;
};

const isOpen = (char: string) => char === '[';
