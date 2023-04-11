import {isBalanced} from '../src/parentheses';

describe('isBalanced - stack', () => {
  test('determines if parentheses str is balanced', () => {
    expect(isBalanced('')).toBeTruthy();
    expect(isBalanced('[]')).toBeTruthy();
    expect(isBalanced('[[]]')).toBeTruthy();
    expect(isBalanced('[[][]]')).toBeTruthy();
    expect(isBalanced('[')).toBeFalsy();
    expect(isBalanced('[][')).toBeFalsy();
    expect(isBalanced('[[]]]]')).toBeFalsy();
  });
});
