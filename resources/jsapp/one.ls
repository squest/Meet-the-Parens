/**
 * User: macquest
 * Date: 8/31/14
 * Time: 18:22
 */

sqr = (x) ->
  x * x

fibo-helper = (x, i, lst) ->
  | (x == i) => lst
  | otherwise => (fibo-helper x ++i ())

fibolist = (x) ->
  | (x <= 1) => [1]
  | (x == 2) => [1 1]
  | otherwise => (fibo-helper x 2 [1 1])