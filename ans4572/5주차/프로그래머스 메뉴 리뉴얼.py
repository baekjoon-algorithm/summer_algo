from itertools import combinations
from collections import Counter

def solution(orders, course):
    result = []

    for c in course:
        temp = []
        for order in orders:
            temp += combinations(sorted(order), c)
        counter = Counter(temp)

        max_value = 2
        max_list = []
        for key, value in counter.items():
            if value > max_value:
                max_list = []
                max_value = value
                max_list.append("".join(key))
            elif value == max_value:
                max_list.append("".join(key))

        for i in max_list:
            result.append(i)

    return sorted(result)

solution(["ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"], [2,3,5])