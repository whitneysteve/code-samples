module Palindrome

  public

  #Find the longest palindrome in a String.
  #
  def findLongestPalindrome( chars )

    return chars if is_palindrome? chars

    len = chars.length
    substring_len = len - 1

    while substring_len > 0 do

      substring_idx = 0

      while substring_idx < len - substring_idx

        substring = chars[ substring_idx ... substring_idx + substring_len ]
        return substring if is_palindrome? substring

        substring_idx += 1

      end

      substring_len -= 1

    end

    chars[ 0 ]

  end

  #Check if a palindrome.
  #
  def is_palindrome?( chars )

    if chars.is_a? Integer

      chars == reverse( chars )

    else

      len = chars.length

      ( ( len / 2 ) + 1 ).times { |i|

        return false unless chars[ i ] == chars[ len - i - 1 ]

      }

      true

    end

  end

  private

  #Reverse a number.
  #
  def reverse( num )

    reverse = 0

    until num <= 0

      rem = num % 10
      reverse = ( reverse * 10 ) + rem
      num = num / 10

    end

    reverse

  end

end

if __FILE__ == $0

  include Palindrome

  #Check if some numbers are palindromic, check if some Strings are palindromic, then find the longest palindrome
  #in some Strings.

  puts is_palindrome? 12321
  puts is_palindrome? 12343
  puts is_palindrome? '123454321'
  puts is_palindrome? '1234543210'

  puts findLongestPalindrome( '123454321' )
  puts findLongestPalindrome( '999123454321999' )
  puts findLongestPalindrome( '999123454321888' )

end